package ir.redmind.paasho.web.rest.mock;


import io.micrometer.core.annotation.Timed;
import ir.redmind.paasho.domain.Comment;
import ir.redmind.paasho.domain.Event;
import ir.redmind.paasho.security.SecurityUtils;
import ir.redmind.paasho.service.CommentService;
import ir.redmind.paasho.service.EventService;
import ir.redmind.paasho.service.UserService;
import ir.redmind.paasho.service.dto.mock.CommentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentResource {

    private final CommentService commentService;
    private final EventService eventService;
    private final UserService userService;

    public CommentResource(CommentService commentService, EventService eventService, UserService userService) {
        this.commentService = commentService;
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
        ir.redmind.paasho.service.dto.CommentDTO c= new ir.redmind.paasho.service.dto.CommentDTO();
        c.setDescription(comment);
        c.setId(userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId());
        c.setEventId(event.getId());
        commentService.save(c);
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
