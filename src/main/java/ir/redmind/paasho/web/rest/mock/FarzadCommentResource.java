package ir.redmind.paasho.web.rest.mock;


import io.micrometer.core.annotation.Timed;
import ir.redmind.paasho.domain.Comment;
import ir.redmind.paasho.domain.Event;
import ir.redmind.paasho.domain.Notification;
import ir.redmind.paasho.domain.User;
import ir.redmind.paasho.domain.enumeration.NotificationStatus;
import ir.redmind.paasho.domain.enumeration.NotificationType;
import ir.redmind.paasho.security.SecurityUtils;
import ir.redmind.paasho.service.CommentService;
import ir.redmind.paasho.service.EventService;
import ir.redmind.paasho.service.NotificationService;
import ir.redmind.paasho.service.UserService;
import ir.redmind.paasho.service.dto.mock.CommentDTO;
import ir.redmind.paasho.service.mapper.NotificationMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class FarzadCommentResource {

    private final CommentService commentService;
    private final NotificationMapper notificationMapper;
    private final NotificationService notificationService;
    private final EventService eventService;
    private final UserService userService;

    public FarzadCommentResource(CommentService commentService, NotificationMapper notificationMapper, NotificationService notificationService, EventService eventService, UserService userService) {
        this.commentService = commentService;
        this.notificationMapper = notificationMapper;
        this.notificationService = notificationService;
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping(value = "{eventCode}")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<CommentDTO>> commentList(@PathVariable("eventCode") String eventId) {
        List<Comment> comment = commentService.findAllByCode(eventId);

        List<CommentDTO> commentDTOS = new ArrayList<>();

        comment.forEach(c -> {
            CommentDTO c1 = new CommentDTO();
            c1.setAvatar(c.getUser().getAvatar());
            c1.setText(c.getDescription());
            c1.setId(c.getId());
            commentDTOS.add(c1);
        });
        return ResponseEntity.ok(commentDTOS);

    }


    @PostMapping(value = "{eventCode}")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<HttpStatus> comment(@PathVariable("eventCode") String eventId, @RequestParam("comment") String comment) {
        Event event = eventService.findByCode(eventId);
        User user = userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        ir.redmind.paasho.service.dto.CommentDTO c= new ir.redmind.paasho.service.dto.CommentDTO();
        c.setDescription(comment);
        c.setUserId(user.getId());
        c.setEventId(event.getId());
        commentService.save(c);


        if(!c.getUserId().equals(event.getCreator().getId())) {
            Notification notification = new Notification();
            notification.setDescription(user.getFirstName() + " " + user.getLastName() + " پیام در رویداد : " + event.getTitle());
            notification.addUsers(event.getCreator());
            notification.setRelatedEvent(event);
            notification.setFrom(user);
            notification.setStatus(NotificationStatus.ACCEPTED);
            notification.setType(NotificationType.NEWS);
            notificationService.save(notificationMapper.toDto(notification));
        }
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @DeleteMapping(value = "{commentId}")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("commentId") Long commentId) {

        commentService.delete(commentId);
        return ResponseEntity.ok(HttpStatus.OK);

    }


}
