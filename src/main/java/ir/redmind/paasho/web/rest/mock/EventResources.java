package ir.redmind.paasho.web.rest.mock;


import io.micrometer.core.annotation.Timed;
import ir.redmind.paasho.domain.Event;
import ir.redmind.paasho.domain.Notification;
import ir.redmind.paasho.domain.User;
import ir.redmind.paasho.domain.enumeration.ContactType;
import ir.redmind.paasho.domain.enumeration.NotificationStatus;
import ir.redmind.paasho.domain.enumeration.NotificationType;
import ir.redmind.paasho.domain.enumeration.PriceType;
import ir.redmind.paasho.security.SecurityUtils;
import ir.redmind.paasho.service.CategoryService;
import ir.redmind.paasho.service.EventService;
import ir.redmind.paasho.service.NotificationService;
import ir.redmind.paasho.service.UserService;
import ir.redmind.paasho.service.dto.mock.*;
import ir.redmind.paasho.service.mapper.CategoryMapper;
import ir.redmind.paasho.service.mapper.EventMapper;
import ir.redmind.paasho.service.mapper.NotificationMapper;
import ir.redmind.paasho.web.rest.errors.BadRequestAlertException;
import ir.redmind.paasho.web.rest.util.HeaderUtil;
import ir.redmind.paasho.web.rest.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events/")
public class EventResources {

    private final EventService eventService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;
    private final EventMapper eventMapper;
    private final CategoryMapper categoryMapper;

    @PersistenceContext
    private EntityManager em;
    static List<titleDTO> titles = new ArrayList<>();

    static {
        titles.add(new titleDTO("پایه ای بریم فوتبال", 1l));
        titles.add(new titleDTO("پایه ای بریم دورهمی", 2l));
        titles.add(new titleDTO("پایه ای بریم سینما", 3l));
        titles.add(new titleDTO("پایه ای بریم کوه", 4l));
    }

    public EventResources(EventService eventService, UserService userService, CategoryService categoryService, NotificationService notificationService, NotificationMapper notificationMapper, EventMapper eventMapper, CategoryMapper categoryMapper) {
        this.eventService = eventService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.notificationService = notificationService;
        this.notificationMapper = notificationMapper;
        this.eventMapper = eventMapper;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping(value = "{code}/detail")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<DetailEventDTO> myEvents(@PathVariable("code") String code) {

        Event event = eventService.findByCode(code);
        DetailEventDTO eventDTO = getDetailEventDTO(code, event);
        return ResponseEntity.ok(eventDTO);

    }

    private DetailEventDTO getDetailEventDTO(@PathVariable("code") String code, Event event) {
        DetailEventDTO eventDTO = new DetailEventDTO();
        eventDTO.setCode(code);
//        eventDTO.setPic("");
        eventDTO.setTitle(event.getTitle());
        eventDTO.setPricing(event.getPriceType());
        eventDTO.setScore(event.getCreator().getScore().floatValue());
        eventDTO.setTime(event.getTimeString());
        eventDTO.setDate(event.getDateString());
        eventDTO.setAddress(event.getAddress());
        eventDTO.setCategory(event.getCategories().iterator().next().getName());
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
        }
        else if(notification!=null && notification.getStatus().equals(NotificationStatus.REJECTED) ){
            eventDTO.setJoinStatus(JoinStatus.REQUESTED);
        }
        else eventDTO.setJoinStatus(JoinStatus.NOT_JOINED);
        eventDTO.setLatitude(event.getLatitude());
        eventDTO.setLongitude(event.getLongitude());
        eventDTO.setCreator(event.getCreator().getFirstName() + " " + event.getCreator().getLastName());
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
//            event.setPic(e.);
        event.setTitle(e.getTitle());
        event.setPricing(PriceType.FREE);
        event.setTime(e.getTimeString());
        event.setDate(e.getDateString());
        event.setCategoryId(Math.toIntExact(e.getCategories().iterator().next().getId()));
        User creator = userService.getUserWithAuthorities(e.getCreatorId()).get();
        event.setScore(creator.getScore().floatValue());
        event.setCreator(creator.getFirstName() + " " + creator.getLastName());
        event.setEditable(creator.getLogin().equalsIgnoreCase(SecurityUtils.getCurrentUserLogin().get()));
        return event;
    }

    @GetMapping(value = "{category}/titles")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<titleDTO>> getTitles(@PathVariable("category") String category) {
        List<titleDTO> titles = new ArrayList<>();
        titles.add(new titleDTO("پایه ای بریم فوتبال", 1l));
        titles.add(new titleDTO("پایه ای بریم دورهمی", 2l));
        titles.add(new titleDTO("پایه ای بریم سینما", 3l));
        titles.add(new titleDTO("پایه ای بریم کوه", 4l));
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
        profileDTO.setTelegram(p.getContacts().stream().filter(c -> c.getType().equals(ContactType.TELEGRAM)).findFirst().get().getValue());
        profileDTO.setInstagram(p.getContacts().stream().filter(c -> c.getType().equals(ContactType.INSTAGRAM)).findFirst().get().getValue());
        profileDTO.setLastName(p.getLastName());
        return profileDTO;
    }

    @GetMapping(value = "{code}/share")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<ShareDTO> share(@PathVariable("code") String code) {
        Event event = eventService.findByCode(code);
        User user= userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        ShareDTO shareDto = new ShareDTO();
        shareDto.setUser(user.getFirstName() + " " + user.getLastName());
        shareDto.setContent("میخواد با شما رویداد" + event.getTitle() + " را به اشتراک بگذارد دریافت پاشو از ");
        shareDto.setAndroidMarketURL("https://cafebazaar.ir");
        shareDto.setIosMarketURL("https://sibapp.com");
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
//todo create notification
        Notification notification = new Notification();
        Event event = eventService.findByCode(code);
        notification.setDescription("درخواست شرکت در رویداد : " + event.getTitle());
        notification.addUsers(event.getCreator());
        notification.setRelatedEvent(event);
        notification.setFrom(userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get());
        notification.setStatus(NotificationStatus.PENDING);
        notification.setType(NotificationType.REQUEST);
        notificationService.save(notificationMapper.toDto(notification));
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @PostMapping(value = "{code}/refuse")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<HttpStatus> refuse(@PathVariable("code") String code) {
//todo delete notification
        return ResponseEntity.ok(HttpStatus.OK);

    }


    @PostMapping(value = "")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<ir.redmind.paasho.service.dto.EventDTO> createEvent(@RequestBody CreateEventDTO createEventDTO) {

        Event event = new Event();
        event.setAddress(createEventDTO.getAddress());
        event.setCode(UUID.randomUUID().toString());
        event.setDescription(createEventDTO.getDescription());
        if (createEventDTO.getCustomTitle() == null || createEventDTO.getCustomTitle().length() == 0)
            event.setTitle(titles.get(Integer.parseInt(createEventDTO.getTitle())-1).getTitle());
        else {
            event.setTitle(createEventDTO.getCustomTitle());
        }
        event.setTel(createEventDTO.getTel());
        event.setLatitude(createEventDTO.getLatitude());
        event.setLongitude(createEventDTO.getLongitude());
        event.setMinAge(createEventDTO.getAgeLimitFrom());
        event.setMaxAge(createEventDTO.getAgeLimitTo());
        event.setPriceType(createEventDTO.getPricing());
        event.setDateString(createEventDTO.getDate());
        event.setTimeString(createEventDTO.getTime());
        event.addCategories(categoryMapper.toEntity(categoryService.findOne((long) createEventDTO.getCategoryId()-1).get()));
//        event.setTelegram(createEventDTO.gett);
        event.setCreator(userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get());
        event.setCapacity(createEventDTO.getCapacity());
        eventService.save(eventMapper.toDto(event));
        return ResponseEntity.ok(eventMapper.toDto(event));
    }

    @PostMapping(value = "/{code}/upload")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile multipartFile, @PathVariable String code) {
//todo to ftp end save address to event
//        createEventDTO.setId(10l);
//        return ResponseEntity.ok(createEventDTO);
        return ResponseEntity.ok(multipartFile.getOriginalFilename());
    }

    @PutMapping("")
    public ResponseEntity<ir.redmind.paasho.service.dto.EventDTO> updateEvent(@RequestBody CreateEventDTO eventDTO) throws URISyntaxException {
        if (eventDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", "EVENT", "idnull");
        }
        Event event = eventService.findByCode(eventDTO.getCode());
        event.setDateString(eventDTO.getDate());
        event.setTimeString(eventDTO.getTime());
        event.setCapacity(eventDTO.getCapacity());
        event.setPriceType(eventDTO.getPricing());
        if (eventDTO.getCustomTitle() == null || eventDTO.getCustomTitle().length() == 0)
            event.setTitle(titles.get(Integer.parseInt(eventDTO.getTitle())-1).getTitle());
        else {
            event.setTitle(eventDTO.getCustomTitle());
        }
        event.setCategories(new HashSet<>());
        event.addCategories(categoryMapper.toEntity(categoryService.findOne((long) eventDTO.getCategoryId()).get()));
        event.setDescription(eventDTO.getDescription());
        event.setTel(eventDTO.getTel());
        event.setLatitude(eventDTO.getLatitude());
        event.setLongitude(eventDTO.getLongitude());
        event.setMinAge(eventDTO.getAgeLimitFrom());
        event.setMaxAge(eventDTO.getAgeLimitTo());
        ir.redmind.paasho.service.dto.EventDTO result = eventService.save(eventMapper.toDto(event));
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("EVENT", eventDTO.getId().toString()))
            .body(result);
    }

    @GetMapping(value = "map")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<MapEventDTO>> events(@RequestParam("latitude") Double latitude, @RequestParam("longitude") Double longitude) {

        String query = "SELECT  code,title,custom_Title,price_Type,date_String,time_String,latitude,longitude,visit_count FROM event e  WHERE  ((6371 * acos ( cos ( radians(?1) ) * cos( radians( cast(latitude as double precision) ) ) * cos( radians( cast(e.longitude as double precision) ) - radians(?3) ) +sin ( radians(?2) ) * sin( radians( cast(e.latitude as double precision) ) ))) < 5);  ";
//                                    System.out.println(a);
        javax.persistence.Query query2 = em.createNativeQuery(query);
        query2.setParameter(1, latitude);
        query2.setParameter(2, latitude);
        query2.setParameter(3, longitude);

        List<Object[]> l = query2.getResultList();
        List<MapEventDTO> eventDTOS = new ArrayList<>();

        for (Object[] e : l) {
            MapEventDTO event1 = new MapEventDTO();
            event1.setCode(String.valueOf(e[0]));
            event1.setPic("https://media.glassdoor.com/l/00/05/01/26/mhw-mt-shasta-climbing-event.jpg");
            if (e[1] == null || e[1] == "")
                event1.setTitle(String.valueOf(e[2]));
            else
                event1.setTitle(String.valueOf(e[1]));
            event1.setPricing(PriceType.valueOf(String.valueOf(e[3])));
            event1.setTime(String.valueOf(e[5]));
            Event ee = eventService.findByCode(event1.getCode());
            event1.setCategoryId(Math.toIntExact(ee.getCategories().iterator().next().getId()));
            event1.setEditable(ee.getCreator().getLogin().equalsIgnoreCase(SecurityUtils.getCurrentUserLogin().get()));

            event1.setScore(ee.getCreator().getScore().floatValue());
            event1.setDate(String.valueOf(e[4]));
            event1.setLatitude((Double) e[6]);
            event1.setLongitude((Double) e[7]);
            event1.setView(Long.valueOf((Integer) e[8]));

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
        eventService.save(eventMapper.toDto(ev));
        return ResponseEntity.ok(HttpStatus.OK);

    }

}
