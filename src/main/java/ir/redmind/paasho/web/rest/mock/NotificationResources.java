package ir.redmind.paasho.web.rest.mock;


import io.micrometer.core.annotation.Timed;
import ir.redmind.paasho.domain.Notification;
import ir.redmind.paasho.domain.enumeration.NotificationStatus;
import ir.redmind.paasho.service.EventService;
import ir.redmind.paasho.service.NotificationService;
import ir.redmind.paasho.service.UserService;
import ir.redmind.paasho.service.dto.mock.NotificationDTO;
import ir.redmind.paasho.service.mapper.EventMapper;
import ir.redmind.paasho.service.mapper.NotificationMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications/")
public class NotificationResources {


    private final NotificationService notificationService;
    private final UserService userService;
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final NotificationMapper notificationMapper;

    public NotificationResources(NotificationService notificationService, UserService userService, EventService eventService, EventMapper eventMapper, NotificationMapper notificationMapper) {
        this.notificationService = notificationService;
        this.userService = userService;
        this.eventService = eventService;
        this.eventMapper = eventMapper;
        this.notificationMapper = notificationMapper;
    }

    @PostMapping(value = "{requestId}/approved")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<HttpStatus> approved(@PathVariable("requestId") String requestId) {

        // todo join user to participants

        Notification n = notificationMapper.toEntity(notificationService.findOne(Long.valueOf(requestId)).get());
        n.getRelatedEvent().addParticipants(n.getFrom());
        n.setStatus(NotificationStatus.ACCEPTED);
        eventService.save(eventMapper.toDto(n.getRelatedEvent()));
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
        Page<ir.redmind.paasho.service.dto.NotificationDTO> list = notificationService.findAllWithEagerRelationships(pageable);
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        list.getContent().forEach(l -> {
            NotificationDTO notif = new NotificationDTO();
            notif.setRequestId(String.valueOf(l.getId()));
            notif.setPending(true);
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
