package ir.redmind.paasho.web.rest.mock;


import io.micrometer.core.annotation.Timed;
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

        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        NotificationDTO notif= new NotificationDTO();
        notif.setRequestId("UBT123");
        notif.setPending(true);
        notif.setText("کاربر فرزاد صداقت بین علاقه دارد به رویداد کوه نوردی ملحق شود");
        notif.setRelatedEventcode("codeEvent");
        notif.setRelatedUserId("userId");
        notificationDTOS.add(notif);
        NotificationDTO notif1= new NotificationDTO();
        notif1.setPending(false);
        notif1.setText("یک متن معمولی");
        notificationDTOS.add(notif1);
        return ResponseEntity.ok(new PageImpl<>(notificationDTOS,new PageRequest(0,2),2));

    }


}
