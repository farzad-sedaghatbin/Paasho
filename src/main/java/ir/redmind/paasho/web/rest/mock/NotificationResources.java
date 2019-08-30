package ir.redmind.paasho.web.rest.mock;


import io.micrometer.core.annotation.Timed;
import ir.redmind.paasho.domain.Contact;
import ir.redmind.paasho.domain.Event;
import ir.redmind.paasho.domain.Notification;
import ir.redmind.paasho.domain.User;
import ir.redmind.paasho.domain.enumeration.ContactType;
import ir.redmind.paasho.domain.enumeration.NotificationStatus;
import ir.redmind.paasho.domain.enumeration.NotificationType;
import ir.redmind.paasho.repository.NotificationRepository;
import ir.redmind.paasho.repository.UserRepository;
import ir.redmind.paasho.security.SecurityUtils;
import ir.redmind.paasho.service.EventService;
import ir.redmind.paasho.service.NotificationService;
import ir.redmind.paasho.service.UserService;
import ir.redmind.paasho.service.dto.mock.NotificationDTO;
import ir.redmind.paasho.service.mapper.EventMapper;
import ir.redmind.paasho.service.mapper.NotificationMapper;
import ir.redmind.paasho.service.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications/")
public class NotificationResources {


    private final NotificationService notificationService;
    private final UserService userService;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final NotificationMapper notificationMapper;
    private final UserMapper userMapper;

    public NotificationResources(NotificationService notificationService, UserService userService, NotificationRepository notificationRepository, UserRepository userRepository, EventService eventService, EventMapper eventMapper, NotificationMapper notificationMapper, UserMapper userMapper) {
        this.notificationService = notificationService;
        this.userService = userService;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.eventService = eventService;
        this.eventMapper = eventMapper;
        this.notificationMapper = notificationMapper;
        this.userMapper = userMapper;
    }

    @PostMapping(value = "{requestId}/approved")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<HttpStatus> approved(@PathVariable("requestId") String requestId) {

        // todo join user to participants

        Notification n = notificationMapper.toEntity(notificationService.findOne(Long.valueOf(requestId)).get());
        User user = userRepository.findById(n.getFrom().getId()).get();

        user.setPoint(user.getPoint() + 10);
        userRepository.save(user);

        User user1 = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get());

        user1.setPoint(user1.getPoint() + 10);
        userRepository.save(user1);
        Event ev = eventService.findOne(n.getRelatedEvent().getId()).get();
        ev.addParticipants(n.getFrom());
        n.setStatus(NotificationStatus.ACCEPTED);
        eventService.save(eventMapper.toDto(ev));
        notificationService.save(notificationMapper.toDto(n));




        Notification notification = new Notification();
        notification.setDescription("درخواست شرکت در رویداد : "+ notification.getRelatedEvent().getTitle() + " پذیرفته شد");
        notification.addUsers(user);
        notification.setRelatedEvent(notification.getRelatedEvent());
        notification.setFrom(user1);
        notification.setStatus(NotificationStatus.ACCEPTED);
        notification.setType(NotificationType.NEWS);
        notificationService.save(notificationMapper.toDto(notification));

        return ResponseEntity.ok(HttpStatus.OK);

    }

    @PostMapping(value = "{requestId}/cancel")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<HttpStatus> cancel(@PathVariable("requestId") String requestId) {
        Notification n = notificationMapper.toEntity(notificationService.findOne(Long.valueOf(requestId)).get());
        n.getRelatedEvent().addParticipants(n.getFrom());
        n.setStatus(NotificationStatus.REJECTED);
        notificationService.save(notificationMapper.toDto(n));
        return ResponseEntity.ok(HttpStatus.OK);

    }


    @GetMapping(value = "")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<NotificationDTO>> listNotification(Pageable pageable) {

        //todo list notifications
        List<ir.redmind.paasho.service.dto.NotificationDTO> list = notificationService.findAllForMe(pageable);
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        list.forEach(l -> {
            NotificationDTO notif = new NotificationDTO();
            notif.setRequestId(String.valueOf(l.getId()));
            if (l.getStatus().equals(NotificationStatus.PENDING))
                notif.setPending(true);
            else{
                notif.setPending(false);
            }
            notif.setText(l.getDescription());
            notif.setRelatedEventcode(String.valueOf(l.getRelatedEventId()));
            notif.setRelatedUserId(String.valueOf(l.getFromId()));
            User user = userRepository.getOne(l.getFromId());
            notif.setAvatar(user.getAvatar());
            if (user.getContacts().size() > 0) {
                notif.setRelatedUserTelegram(user.getTelegram());
                notif.setRelatedUserInstagram(user.getInstagram());
            }
            notif.setRelatedUserName(user.getFirstName()+" "+user.getLastName());
            notificationDTOS.add(notif);
        });


        NotificationDTO notif1 = new NotificationDTO();
        notif1.setPending(false);
        notif1.setAvatar("");
        notif1.setRelatedUserInstagram("paasho_com");
        notif1.setRelatedUserInstagram("paasho_com");
        notif1.setRelatedUserId("4601");
        notif1.setText("به پاشو خوش آمدید");
        notificationDTOS.add(notif1);
//        return ResponseEntity.ok(new PageImpl<>(notificationDTOS, new PageRequest(pageable.getPageNumber(), list.getSize() + 1), list.getTotalElements() + 1));
        return ResponseEntity.ok(notificationDTOS);
    }



    @GetMapping(value = "/pageable")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<Page<NotificationDTO>> listNotificationPage(Pageable pageable) {

        //todo list notifications
        List<User> users = new ArrayList<>();
        users.add(userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get());
        Page<Notification> list = (notificationRepository.findAllByUsersInOrderByIdDesc(users,pageable));
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        list.forEach(l -> {
            NotificationDTO notif = new NotificationDTO();
            notif.setRequestId(String.valueOf(l.getId()));
            if (l.getStatus().equals(NotificationStatus.PENDING))
                notif.setPending(true);
            else{
                notif.setPending(false);
            }
            notif.setText(l.getDescription());
            notif.setRelatedEventcode(String.valueOf(l.getRelatedEvent().getId()));
            notif.setRelatedUserId(String.valueOf(l.getFrom().getId()));
            User user = l.getFrom();
            notif.setAvatar(user.getAvatar());
            if (user.getContacts().size() > 0) {
                notif.setRelatedUserTelegram(user.getTelegram());
                notif.setRelatedUserInstagram(user.getInstagram());
            }
            notif.setRelatedUserName(user.getFirstName()+" "+user.getLastName());
            notificationDTOS.add(notif);
        });


        NotificationDTO notif1 = new NotificationDTO();
        notif1.setPending(false);
        notif1.setAvatar("");
        notif1.setRelatedUserInstagram("paasho_com");
        notif1.setRelatedUserInstagram("paasho_com");
        notif1.setRelatedUserId("4601");
        notif1.setText("به پاشو خوش آمدید");
        notificationDTOS.add(notif1);
//        return ResponseEntity.ok(new PageImpl<>(notificationDTOS, new PageRequest(pageable.getPageNumber(), list.getSize() + 1), list.getTotalElements() + 1));
        return ResponseEntity.ok(new PageImpl<>(notificationDTOS,list.getPageable(),list.getTotalElements()));
    }


}
