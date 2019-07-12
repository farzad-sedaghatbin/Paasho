package ir.redmind.paasho.web.rest.mock;


import io.micrometer.core.annotation.Timed;
import ir.redmind.paasho.domain.Event;
import ir.redmind.paasho.domain.enumeration.EventStatus;
import ir.redmind.paasho.domain.enumeration.PriceType;
import ir.redmind.paasho.repository.EventRepository;
import ir.redmind.paasho.security.SecurityUtils;
import ir.redmind.paasho.service.dto.mock.EventDTO;
import ir.redmind.paasho.service.dto.mock.EventType;
import ir.redmind.paasho.service.dto.mock.MyEventDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/home/")
public class HomeResource {

    private final EventRepository eventRepository;

    public HomeResource(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    @GetMapping(value = "my-event")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<MyEventDTO>> myEvents(Pageable pageable) {

        List<Event> events = eventRepository.findByStatus(EventStatus.APPROVED);

        List<MyEventDTO> myEventDTOS = new ArrayList<>();

        events.stream().filter(e -> e.getCreator().getLogin().equalsIgnoreCase(SecurityUtils.getCurrentUserLogin().get())).forEach(event -> {
            MyEventDTO event1 = new MyEventDTO();
            event1.setCode(event.getCode());
            event1.setTitle(event.getTitle());
            event1.setId(event.getId());
            event1.setScore(event.getCreator().getScore().floatValue());
            event1.setEditable(event.getCreator().getLogin().equalsIgnoreCase(SecurityUtils.getCurrentUserLogin().get()));
            if (event.getMedias().iterator().hasNext())
                event1.setPic(event.getMedias().iterator().next().getId());
            myEventDTOS.add(event1);
        });
        return ResponseEntity.ok(myEventDTOS);

    }

    @GetMapping(value = "events")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<EventDTO>> events(@RequestParam("type") EventType eventType, Pageable pageable) {
        List<EventDTO> eventDTOS = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        if (eventType.equals(EventType.WEEK))
            events = eventRepository.findByStatusAndEventTimeIsBetween(EventStatus.APPROVED, ZonedDateTime.now(), ZonedDateTime.now().plusDays(7));
        else if (eventType.equals(EventType.TODAY))
            events = eventRepository.findByStatusAndEventTime(EventStatus.APPROVED, ZonedDateTime.now());
        else if (eventType.equals(EventType.POPULAR))
            events = eventRepository.findByStatus(EventStatus.APPROVED);


        events.forEach(event -> {
            EventDTO event1 = new EventDTO();
            event1.setCode(event.getCode());
            if (event.getMedias().iterator().hasNext())
                event1.setPic(event.getMedias().iterator().next().getId());
            event1.setTitle(event.getTitle());
            event1.setId(event.getId());
            event1.setPricing(event.getPriceType());
            event1.setScore(event.getCreator().getScore().floatValue());
            event1.setTime(event.getTimeString());
            event1.setDate(event.getDateString());
            event1.setCategoryId(event.getCategories().iterator().next().getId().intValue());
            event1.setCreator(event.getCreator().getFirstName() + " " + event.getCreator().getLastName());
            event1.setEditable(event.getCreator().getLogin().equalsIgnoreCase(SecurityUtils.getCurrentUserLogin().get()));
            event1.setView(event.getVisitCount());
            event1.setEditable(event.getCreator().getLogin().equalsIgnoreCase(SecurityUtils.getCurrentUserLogin().get()));
            eventDTOS.add(event1);
        });
        return ResponseEntity.ok(eventDTOS);

    }
}
