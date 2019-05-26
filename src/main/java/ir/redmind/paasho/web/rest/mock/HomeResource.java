package ir.redmind.paasho.web.rest.mock;


import io.micrometer.core.annotation.Timed;
import ir.redmind.paasho.domain.Event;
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

        Page<Event> events = eventRepository.findAll(pageable);

        List<MyEventDTO> myEventDTOS = new ArrayList<>();

        events.getContent().forEach(event -> {
            MyEventDTO event1 = new MyEventDTO();
            event1.setCode(event.getCode());
            event1.setTitle(event.getTitle());
            event1.setId(event.getId());
            event1.setScore(event.getCreator().getScore().floatValue());
            event1.setEditable(event.getCreator().getLogin().equalsIgnoreCase(SecurityUtils.getCurrentUserLogin().get()));

//            event1.setPic(event.getp);
            myEventDTOS.add(event1);
        });
        return ResponseEntity.ok(myEventDTOS);

    }

    @GetMapping(value = "events")
    @Timed
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<EventDTO>> events(@RequestParam("type") EventType eventType, Pageable pageable) {
        List<EventDTO> eventDTOS = new ArrayList<>();
        Page<Event> events = eventRepository.findAll(pageable);
        events.getContent().forEach(event -> {
            EventDTO event1 = new EventDTO();
            event1.setCode(event.getCode());
//            event1.setPic(event.getMedias().iterator().);
            event1.setTitle(event.getTitle());
            event1.setId(event.getId());
            event1.setPricing(PriceType.FREE);
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
