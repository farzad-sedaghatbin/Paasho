package ir.redmind.paasho.web.rest.mock;


import io.micrometer.core.annotation.Timed;
import ir.redmind.paasho.domain.*;
import ir.redmind.paasho.domain.Event;
import ir.redmind.paasho.domain.enumeration.*;
import ir.redmind.paasho.repository.*;
import ir.redmind.paasho.security.SecurityUtils;
import ir.redmind.paasho.service.*;
import ir.redmind.paasho.service.dto.MediaDTO;
import ir.redmind.paasho.service.dto.mock.*;
import ir.redmind.paasho.service.mapper.*;
import ir.redmind.paasho.web.rest.errors.BadRequestAlertException;
import ir.redmind.paasho.web.rest.util.HeaderUtil;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/events/")
public class EventResources {

    private final EventService eventService;
    private final EventRepository eventRepository;
    private final MediaService mediaService;
    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;
    private final UserService userService;
    private final UserRepository userRepository;
    private final TitlesRepository titlesRepository;
    private final CategoryService categoryService;
    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;
    private final EventMapper eventMapper;
    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;
    private final ReportRepository reportRepository;

    @PersistenceContext
    private EntityManager em;

    public EventResources(EventService eventService, EventRepository eventRepository, MediaService mediaService, MediaRepository mediaRepository, MediaMapper mediaMapper, UserService userService, UserRepository userRepository, TitlesRepository titlesRepository, CategoryService categoryService, NotificationService notificationService, NotificationMapper notificationMapper, EventMapper eventMapper, CategoryMapper categoryMapper, UserMapper userMapper, ReportRepository reportRepository) {
        this.eventService = eventService;
        this.eventRepository = eventRepository;
        this.mediaService = mediaService;
        this.mediaRepository = mediaRepository;
        this.mediaMapper = mediaMapper;
        this.userService = userService;
        this.userRepository = userRepository;
        this.titlesRepository = titlesRepository;
        this.categoryService = categoryService;
        this.notificationService = notificationService;
        this.notificationMapper = notificationMapper;
        this.eventMapper = eventMapper;
        this.categoryMapper = categoryMapper;
        this.userMapper = userMapper;
        this.reportRepository = reportRepository;
    }

    @GetMapping(value = "{code}/detail")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<DetailEventDTO> myEvents(@PathVariable("code") String code) {

        Event event = eventService.findByCode(code);
        DetailEventDTO eventDTO = getDetailEventDTO(code, event);
        return ResponseEntity.ok(eventDTO);

    }

    private DetailEventDTO getDetailEventDTO(String code, Event event) {
        DetailEventDTO eventDTO = new DetailEventDTO();
        eventDTO.setCode(code);
        if (event.getMedias() != null && event.getMedias().size() != 0) {
            Iterator<Media> it = event.getMedias().iterator();
            while (it.hasNext()) {
                Media ss = it.next();
                eventDTO.getPic().add(ss.getId());
            }
        }
        eventDTO.setGender(event.getGender() == null ? GenderType.NONE : event.getGender());
        eventDTO.setTitle(event.getTitle());
        eventDTO.setTitleId(event.getTitleId());
        eventDTO.setPricing(event.getPriceType());
        eventDTO.setScore(event.getCreator().getScore().floatValue());
        eventDTO.setTime(event.getTimeString());
        eventDTO.setDate(event.getDateString());
        eventDTO.setToDate(event.getDateString());
        eventDTO.setAddress(event.getAddress());
        Category cat = event.getCategories().iterator().next();
        eventDTO.setCategory(cat.getName());
        eventDTO.setCategoryId(cat.getId());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setInstagram(event.getInstagram());
        eventDTO.setTel(event.getTel());
        eventDTO.setTelegram(event.getTelegram());
        eventDTO.setParticipantNumber(event.getParticipants().size());
        eventDTO.setView(event.getVisitCount());
        eventDTO.setLatitude(event.getLatitude());
        eventDTO.setLongitude(event.getLongitude());
        eventDTO.setCapacity(Math.toIntExact(event.getCapacity()));
        eventDTO.setId(event.getId());
        eventDTO.setAgeLimitFrom(event.getMinAge());
        eventDTO.setAgeLimitTo(event.getMaxAge());
        Notification notification = notificationService.findByFromAndRelatedEvent(userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()), event);
        if (event.getParticipants().stream().anyMatch(u -> u.getLogin().equalsIgnoreCase(SecurityUtils.getCurrentUserLogin().get()))) {
            eventDTO.setJoinStatus(JoinStatus.JOINED);
        } else if (notification != null && !notification.getStatus().equals(NotificationStatus.REJECTED)) {
            eventDTO.setJoinStatus(JoinStatus.REQUESTED);
        } else eventDTO.setJoinStatus(JoinStatus.NOT_JOINED);
        eventDTO.setLatitude(event.getLatitude());
        eventDTO.setLongitude(event.getLongitude());
        if (event.getTelegram() == null)
            eventDTO.setTelegram(event.getCreator().getTelegram());
        else
            eventDTO.setTelegram(event.getTelegram());
        if (event.getInstagram() == null)
            eventDTO.setInstagram(event.getCreator().getInstagram());
        else
            eventDTO.setInstagram(event.getInstagram());
        eventDTO.setCreator(event.getCreator().getFirstName() + " " + event.getCreator().getLastName());
        eventDTO.setCreatorId(event.getCreator().getId());
        return eventDTO;
    }

    @GetMapping(value = "search")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<EventDTO>> events(@RequestParam("key") String key, Pageable pageable) {
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(multiMatchQuery("key").field("title").field("description").type(MultiMatchQueryBuilder.Type.BEST_FIELDS)).build();
        List<ir.redmind.paasho.service.dto.EventDTO> events = eventMapper.toDto(eventService.searchByTitleOrDescription(key));
        List<EventDTO> eventDTOS = new ArrayList<>();

        events.forEach(e -> {
            EventDTO event = getEventDTO(e);
            eventDTOS.add(event);

        });

        return ResponseEntity.ok().body(eventDTOS);

    }

    private EventDTO getEventDTO(ir.redmind.paasho.service.dto.EventDTO e) {
        EventDTO event = new EventDTO();
        event.setCode(e.getCode());
        if (e.getMedias().iterator().hasNext())
            event.setPic(e.getMedias().iterator().next().getId());
        event.setTitle(e.getTitle());
        event.setPricing(e.getPriceType());
        event.setTime(e.getTimeString());
        event.setDate(e.getDateString());
        event.setView(e.getVisitCount());
        event.setId(e.getId());
        event.setCategoryId(Math.toIntExact(e.getCategories().iterator().next().getId()));
        User creator = userRepository.findById(e.getCreatorId()).get();
        event.setScore(creator.getScore().floatValue());
        event.setCreator(creator.getFirstName() + " " + creator.getLastName());
        event.setExpire(e.getEventTime().isAfter(ZonedDateTime.now()));
        event.setEditable(creator.getLogin().equalsIgnoreCase(SecurityUtils.getCurrentUserLogin().get()));
        return event;
    }

    @GetMapping(value = "{category}/titles")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<titleDTO>> getTitles(@PathVariable("category") String category) {

        List<Titles> titleList = titlesRepository.findByCategory_Id(Long.valueOf(category));

        List<titleDTO> titles = new ArrayList<>();

        titleList.forEach(t -> {
            titles.add(new titleDTO(t.getTitle(), t.getId()));
        });
        return ResponseEntity.ok(titles);

    }

    @GetMapping(value = "{code}/participants")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<ProfileDTO>> participants(@PathVariable("code") String code) {

        Event event = eventService.findByCode(code);
        List<ProfileDTO> profileDTOS = new ArrayList<>();
//        if(event.getParticipants()!=null)
        event.getParticipants().forEach(p -> {
            ProfileDTO profileDTO = getProfileDTO(p);
            profileDTOS.add(profileDTO);
        });
        return ResponseEntity.ok(profileDTOS);

    }

    private ProfileDTO getProfileDTO(User p) {
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setAvatar(p.getAvatar());
        profileDTO.setBirthYear(Integer.parseInt(p.getBirthDate()));
        profileDTO.setEmail(p.getEmail());
        profileDTO.setFirstName(p.getFirstName());
        profileDTO.setGender(p.getGender());
        profileDTO.setId(p.getId());
        profileDTO.setTelegram(p.getTelegram());
        profileDTO.setInstagram(p.getInstagram());
        profileDTO.setLastName(p.getLastName());
        return profileDTO;
    }

    @GetMapping(value = "{code}/share")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<ShareDTO> share(@PathVariable("code") String code) {
        Event event = eventService.findByCode(code);
        User user = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        ShareDTO shareDto = new ShareDTO();
        shareDto.setUser(user.getFirstName() + " " + user.getLastName());
        shareDto.setContent("میخواد با شما رویداد " + event.getTitle() + " را به اشتراک بگذارد دریافت پاشو از ");
        shareDto.setAndroidMarketURL("https://cafebazaar.ir/app/com.pasho");
        shareDto.setIosMarketURL("http://paasho.com/app/paasho.apk");
        return ResponseEntity.ok(shareDto);

    }


    @PostMapping(value = "{code}/rating")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<HttpStatus> rating(@PathVariable("code") String code, @RequestParam("rating") Double rating) {
//todo do rating
        return ResponseEntity.ok(HttpStatus.OK);

    }


    @PostMapping(value = "{code}/join")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<HttpStatus> join(@PathVariable("code") String code) {
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get());
        Notification notification = new Notification();
        Event event = eventService.findByCode(code);
        notification.setDescription(user.get().getFirstName() + " " + user.get().getLastName() + " درخواست شرکت در رویداد : " + event.getTitle());
        notification.addUsers(event.getCreator());
        notification.setRelatedEvent(event);
        notification.setFrom(user.get());
        notification.setStatus(NotificationStatus.PENDING);
        notification.setType(NotificationType.REQUEST);
        notificationService.save(notificationMapper.toDto(notification));
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @PostMapping(value = "{code}/join-fake")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<HttpStatus> join(@PathVariable("code") String code, @RequestParam("mobile") String mobile) {
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(mobile);
        Event event = eventService.findByCode(code);
        event.getParticipants().add(user.get());
        eventRepository.save(event);
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @PostMapping(value = "{code}/refuse")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<HttpStatus> refuse(@PathVariable("code") String code) {
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get());
        Event event = eventService.findByCode(code);
        event.setParticipants(event.getParticipants().stream().filter(u -> u.getId() == user.get().getId()).collect(Collectors.toSet()));
        Notification notif = notificationService.findByFromAndRelatedEvent(user, event);
        notificationService.delete(notif.getId());
        eventService.save(eventMapper.toDto(event));
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @PostMapping(value = "{code}/suspend")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<HttpStatus> cancel(@PathVariable("code") String code) {
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get());
        Event event = eventService.findByCode(code);
        if (event.getCreator().getId().equals(user.get().getId())) {
            event.setStatus(EventStatus.SUSPEND);
        }
        eventRepository.save(event);
        return ResponseEntity.ok(HttpStatus.OK);

    }


    @PostMapping(value = "")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<ir.redmind.paasho.service.dto.EventDTO> createEvent(@RequestBody CreateEventDTO createEventDTO) {

        Event event = new Event();
        event.setAddress(createEventDTO.getAddress());
        event.setAddress(createEventDTO.getAddress());
        event.setCode(UUID.randomUUID().toString());
        event.setDescription(createEventDTO.getDescription());
        if (createEventDTO.getCustomTitle() == null || createEventDTO.getCustomTitle().length() == 0) {
            event.status(EventStatus.APPROVED);
            event.setTitleId(Long.valueOf(createEventDTO.getTitle()));
            event.setTitle(titlesRepository.findById(Long.valueOf(createEventDTO.getTitle())).get().getTitle());
        } else {
            event.status(EventStatus.PENDING);
            event.setTitle(createEventDTO.getCustomTitle());
        }
        event.setTel(createEventDTO.getTel());
        event.setLatitude(createEventDTO.getLatitude());
        event.setLongitude(createEventDTO.getLongitude());
        event.setMinAge(createEventDTO.getAgeLimitFrom());
        event.setMaxAge(createEventDTO.getAgeLimitTo());
        event.setPriceType(createEventDTO.getPricing());
        event.setDateString(createEventDTO.getDate());
        event.setToDateString(createEventDTO.getDate());
        event.setTimeString(createEventDTO.getTime());
        event.setGender(createEventDTO.getGender());
        User user = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        event.addCategories(categoryMapper.toEntity(categoryService.findOne((long) createEventDTO.getCategoryId()).get()));
        event.setCreator(user);
        event.setCapacity(createEventDTO.getCapacity());
        eventRepository.save(event);

        user.setPoint(user.getPoint() + 10);
        userRepository.save(user);
        return ResponseEntity.ok(eventMapper.toDto(event));

    }

    @PostMapping(value = "/upload")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam String code) throws IOException {
        Event event = eventService.findByCode(code);
//        Path testFile = Files.createTempFile(UUID.randomUUID().toString(), "." + multipartFile.getOriginalFilename().split("\\.")[1]);
//        System.out.println("Creating and Uploading Test File: " + testFile);
//        //todo remove this code
//
//        mediaService.removeByEvent(event);
//        event.setMedias(new HashSet<>());
//        eventService.save(eventMapper.toDto(event));
//        Files.write(testFile, multipartFile.getBytes());
//        String url = FileUpload.uploadFile(new FileSystemResource(testFile.toFile()));
        Media media = new Media(multipartFile.getBytes(), MediaType.PHOTO, event);
        mediaRepository.save(media);
        event.getMedias().add(media);
        event.setStatus(EventStatus.PENDING);
        eventService.save(eventMapper.toDto(event));
        return ResponseEntity.ok(multipartFile.getOriginalFilename());
    }

    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(@RequestParam("id") Long id) throws IOException {

        MediaDTO media = mediaService.findOne(id).get();
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=img.jpg");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        ByteArrayResource resource = null;
        if (media.getPath() == null) {
            resource = new ByteArrayResource(media.getContent());
            return ResponseEntity.ok()
                .headers(header)
                .contentLength(media.getContent().length)
                .contentType(org.springframework.http.MediaType.IMAGE_JPEG)
                .body(resource);
        } else {
            URL url = new URL(media.getPath());
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("User-Agent", "Firefox");

            try (InputStream inputStream = conn.getInputStream()) {
                int n = 0;
                byte[] buffer = new byte[1024];
                while (-1 != (n = inputStream.read(buffer))) {
                    output.write(buffer, 0, n);
                }
            }
            byte[] img = output.toByteArray();
            resource = new ByteArrayResource(img);
            return ResponseEntity.ok()
                .headers(header)
                .contentLength(img.length)
                .contentType(org.springframework.http.MediaType.IMAGE_JPEG)
                .body(resource);
        }
    }


    @PutMapping(value = "/{code}/media")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> addUrlToEvent(@RequestParam("id") Long id, @PathVariable String code) throws IOException {
        //todo remove this code
        MediaDTO m = mediaService.findOne(id).get();
        Event e = eventService.findByCode(code);
        Media m2 = mediaMapper.toEntity(m);
        e.addMedias(m2);
        eventService.save(eventMapper.toDto(e));
        mediaRepository.save(m2);
        return ResponseEntity.ok(HttpStatus.OK.toString());
    }


    @PutMapping(value = "/{code}/url")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> addUrlToEvent(@RequestParam("url") String url, @PathVariable String code) throws IOException {
        //todo remove this code
        eventService.addUrl(url, code);
        return ResponseEntity.ok(url);
    }


    @DeleteMapping(value = "/{code}/url")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> removeUrlToEvent(@RequestParam("url") String url, @PathVariable String code) throws IOException {
        Event event = eventService.findByCode(code);
        //todo remove this code

        Media media = mediaService.findByPath(url);

        event.getMedias().remove(event.getMedias().stream().filter(m -> m.getId().equals(media.getId())).findFirst().get());
        eventService.save(eventMapper.toDto(event));
        mediaService.delete(media.getId());

        return ResponseEntity.ok(url);
    }

    @DeleteMapping(value = "/{code}/media")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> removeUrlToEvent(@RequestParam("id") Long id, @PathVariable String code) throws IOException {
        Event event = eventService.findByCode(code);
        //todo remove this code

        Media media = mediaMapper.toEntity(mediaService.findOne(id).orElse(new MediaDTO()));

        event.getMedias().remove(event.getMedias().stream().filter(m -> m.getId().equals(media.getId())).findFirst().get());
        eventService.save(eventMapper.toDto(event));
        media.getEvent().remove(event);
        mediaRepository.save(media);

        return ResponseEntity.ok(HttpStatus.OK.toString());
    }

    @PutMapping("")
    public ResponseEntity<ir.redmind.paasho.service.dto.EventDTO> updateEvent(@RequestBody CreateEventDTO eventDTO) throws URISyntaxException {
        if (eventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", "EVENT", "idnull");
        }
        Event event = eventService.findByCode(eventDTO.getCode());
        event.setDateString(eventDTO.getDate());
        event.setToDateString(eventDTO.getToDate());
        event.setTimeString(eventDTO.getTime());
        event.setCapacity(eventDTO.getCapacity());
        event.setPriceType(eventDTO.getPricing());
        if (eventDTO.getCustomTitle() == null || eventDTO.getCustomTitle().length() == 0) {
            event.status(EventStatus.APPROVED);
            event.setTitleId(Long.valueOf(eventDTO.getTitle()));
            event.setTitle(titlesRepository.findById(Long.valueOf(eventDTO.getTitle())).get().getTitle());
        } else {
            event.status(EventStatus.PENDING);
            event.setTitle(eventDTO.getCustomTitle());
        }

        event.setCategories(new HashSet<>());
        event.addCategories(categoryMapper.toEntity(categoryService.findOne((long) eventDTO.getCategoryId()).get()));
        event.setDescription(eventDTO.getDescription());
        event.setTel(eventDTO.getTel());
        event.setGender(eventDTO.getGender());
        event.setLatitude(eventDTO.getLatitude());
        event.setLongitude(eventDTO.getLongitude());
        event.setMinAge(eventDTO.getAgeLimitFrom());
        event.setMaxAge(eventDTO.getAgeLimitTo());
        Event result = eventRepository.save(event);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("EVENT", eventDTO.getId().toString()))
            .body(eventMapper.toDto(result));
    }

    @GetMapping(value = "map")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<MapEventDTO>> events(@RequestParam("latitude") Double latitude, @RequestParam("longitude") Double longitude) {

        String query = "SELECT  DISTINCT on(e.id)  e.code,title,custom_Title,price_Type,date_String,time_String,latitude,longitude,visit_count, ec.categories_id,em.medias_id FROM event e  left join event_categories ec on e.id = ec.event_id left join category c on ec.categories_id = c.id LEFT JOIN media_event em ON e.id = em.event_id LEFT JOIN media m ON eM.medias_id=M.id    WHERE  e.status='APPROVED' and ((6371 * acos ( cos ( radians(?1) ) * cos( radians( cast(latitude as double precision) ) ) * cos( radians( cast(e.longitude as double precision) ) - radians(?3) ) +sin ( radians(?2) ) * sin( radians( cast(e.latitude as double precision) ) ))) < 5);  ";
//                                    System.out.println(a);
        javax.persistence.Query query2 = em.createNativeQuery(query);
        query2.setParameter(1, latitude);
        query2.setParameter(2, latitude);
        query2.setParameter(3, longitude);

        List<Object[]> l = query2.getResultList();
        List<MapEventDTO> eventDTOS = new ArrayList<>();


        l.parallelStream().forEach(e -> {
            MapEventDTO event1 = new MapEventDTO();
            event1.setCode(String.valueOf(e[0]));
            if (e[1] == null || e[1] == "")
                event1.setTitle(String.valueOf(e[2]));
            else
                event1.setTitle(String.valueOf(e[1]));
            event1.setPricing(PriceType.valueOf(String.valueOf(e[3])));
            event1.setTime(String.valueOf(e[5]));
            if (e[9] == null)
                event1.setCategoryId(1);
            else
                event1.setCategoryId(((BigInteger) e[9]).intValue());
            event1.setEditable(false);
            if (e[10] != null)
                event1.setPic(((BigInteger) e[10]).longValue());

//            event1.setScore(ee.getCreator().getScore().floatValue());
            event1.setDate(String.valueOf(e[4]));
            event1.setLatitude((Double) e[6]);
            event1.setLongitude((Double) e[7]);
            event1.setView(Long.valueOf((Integer) e[8]));

            eventDTOS.add(event1);
        });

        return ResponseEntity.ok(eventDTOS);

    }

    @GetMapping(value = "map-all")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<MapEventDTO>> allMap() {


        List<Event> l = eventRepository.findByStatusAndEventTimeAfterOrderByIdDesc(EventStatus.APPROVED, ZonedDateTime.now());

        List<MapEventDTO> eventDTOS = new ArrayList<>();
        for (Event e : l) {
            MapEventDTO event1 = new MapEventDTO();
            event1.setCode(String.valueOf(e.getCode()));
            if (e.getTitle() == null || e.getTitle() == "")
                event1.setTitle(String.valueOf(e.getCustomTitle()));
            else
                event1.setTitle(String.valueOf(e.getTitle()));
            event1.setPricing(e.getPriceType());
            event1.setTime(e.getTimeString());
            event1.setCategoryId(Math.toIntExact(e.getCategories().iterator().next().getId()));
            event1.setEditable(e.getCreator().getLogin().equalsIgnoreCase(SecurityUtils.getCurrentUserLogin().get()));
            if (e.getMedias().iterator().hasNext())
                event1.setPic(e.getMedias().iterator().next().getId());

            event1.setScore(e.getCreator().getScore().floatValue());
            event1.setDate(String.valueOf(e.getDateString()));
            event1.setLatitude(e.getLatitude());
            event1.setLongitude(e.getLongitude());
            event1.setView(e.getVisitCount().longValue());

            eventDTOS.add(event1);
        }

        return ResponseEntity.ok(eventDTOS);

    }


    @PutMapping(value = "{code}/view")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<HttpStatus> view(@PathVariable("code") String code) {
        Event ev = eventService.findByCode(code);
        ev.setVisitCount(ev.getVisitCount() + 1);
        eventRepository.save(ev);
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @PostMapping(value = "{code}/report")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<HttpStatus> report(@PathVariable("code") String code, ReportDTO reportDTO) {
        Event ev = eventService.findByCode(code);
        User user = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        Report report = new Report();
        report.setDescription(reportDTO.getDescription());
        report.setEvent(ev);
        report.setReason(reportDTO.getReason());
        report.setUser(user);
        reportRepository.save(report);
        return ResponseEntity.ok(HttpStatus.OK);

    }

}
