package ir.redmind.paasho.web.rest.mock;


import io.micrometer.core.annotation.Timed;
import ir.redmind.paasho.service.NotificationService;
import ir.redmind.paasho.service.UserService;
import ir.redmind.paasho.service.dto.mock.NotificationDTO;
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

    public NotificationResources(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @PostMapping(value = "{requestId}/approved")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<HttpStatus> approved(@PathVariable("requestId") String requestId) {

        // todo join user to participants
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @PostMapping(value = "{requestId}/cancel")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<HttpStatus> cancel(@PathVariable("requestId") String requestId) {
// todo refuse user request
        return ResponseEntity.ok(HttpStatus.OK);

    }


    @GetMapping(value = "")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<Page<NotificationDTO>> listNotification(Pageable pageable) {

        //todo list notifications
        List<ir.redmind.paasho.service.dto.NotificationDTO> list = notificationService.findAll();
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        list.forEach(l->{
            NotificationDTO notif= new NotificationDTO();
            notif.setRequestId(String.valueOf(l.getId()));
            notif.setPending(true);
            notif.setText(l.getDescription());
            notif.setRelatedEventcode(String.valueOf(l.getRelatedEventId()));
            notif.setRelatedUserId(l.getFromId());
            notif.setAvatar(userService.getUserWithAuthorities(Long.valueOf(l.getFromId())).get().getAvatar());
            notificationDTOS.add(notif);
});


        NotificationDTO notif1= new NotificationDTO();
        notif1.setPending(false);
        notif1.setText("به پاشو خوش آمدید");
        notificationDTOS.add(notif1);
        return ResponseEntity.ok(new PageImpl<>(notificationDTOS,new PageRequest(0,list.size()+1),list.size()+1));

    }


}
