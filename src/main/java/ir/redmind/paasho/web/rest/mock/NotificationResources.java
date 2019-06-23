package ir.redmind.paasho.web.rest.mock;


import io.micrometer.core.annotation.Timed;
import ir.redmind.paasho.domain.Event;
import ir.redmind.paasho.domain.Notification;
import ir.redmind.paasho.domain.User;
import ir.redmind.paasho.domain.enumeration.NotificationStatus;
import ir.redmind.paasho.repository.UserRepository;
import ir.redmind.paasho.security.SecurityUtils;
import ir.redmind.paasho.service.EventService;
import ir.redmind.paasho.service.NotificationService;
import ir.redmind.paasho.service.UserService;
import ir.redmind.paasho.service.dto.EventDTO;
import ir.redmind.paasho.service.dto.mock.NotificationDTO;
import ir.redmind.paasho.service.mapper.EventMapper;
import ir.redmind.paasho.service.mapper.NotificationMapper;
import ir.redmind.paasho.service.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/notifications/")
public class NotificationResources {


    private final NotificationService notificationService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final NotificationMapper notificationMapper;
    private final UserMapper userMapper;

    public NotificationResources(NotificationService notificationService, UserService userService, UserRepository userRepository, EventService eventService, EventMapper eventMapper, NotificationMapper notificationMapper, UserMapper userMapper) {
        this.notificationService = notificationService;
        this.userService = userService;
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

        user.setPoint(user.getPoint() + 1);
        userRepository.save(user);

        User user1 = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get());

        user1.setPoint(user.getPoint() + 1);
        userRepository.save(user1);
        Event ev = eventService.findOne(n.getRelatedEvent().getId()).get();
        ev.addParticipants(n.getFrom());
        n.setStatus(NotificationStatus.ACCEPTED);
        eventService.save(eventMapper.toDto(ev));
        notificationService.save(notificationMapper.toDto(n));
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
            notif.setAvatar(userService.getUserWithAuthorities(l.getFromId()).get().getAvatar());
            notificationDTOS.add(notif);
        });


        NotificationDTO notif1 = new NotificationDTO();
        notif1.setPending(false);
        notif1.setText("به پاشو خوش آمدید");
        notificationDTOS.add(notif1);
//        return ResponseEntity.ok(new PageImpl<>(notificationDTOS, new PageRequest(pageable.getPageNumber(), list.getSize() + 1), list.getTotalElements() + 1));
        return ResponseEntity.ok(notificationDTOS);
    }


}
