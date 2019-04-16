package ir.redmind.paasho.service.mapper;

import ir.redmind.paasho.domain.*;
import ir.redmind.paasho.service.dto.EventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Event and its DTO EventDTO.
 */
@Mapper(componentModel = "spring", uses = {User1Mapper.class, CategoryMapper.class})
public interface EventMapper extends EntityMapper<EventDTO, Event> {

    @Mapping(source = "creator.id", target = "creatorId")
    EventDTO toDto(Event event);

    @Mapping(source = "creatorId", target = "creator")
    @Mapping(target = "participants", ignore = true)
    @Mapping(target = "medias", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "events", ignore = true)
    Event toEntity(EventDTO eventDTO);

    default Event fromId(Long id) {
        if (id == null) {
            return null;
        }
        Event event = new Event();
        event.setId(id);
        return event;
    }
}
