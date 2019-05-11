package ir.redmind.paasho.service.mapper;

import ir.redmind.paasho.domain.*;
import ir.redmind.paasho.service.dto.EventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Event and its DTO EventDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CategoryMapper.class, TitlesMapper.class})
public interface EventMapper extends EntityMapper<EventDTO, Event> {

    @Mapping(source = "creator.id", target = "creatorId")
    @Mapping(source = "titles.id", target = "titlesId")
    EventDTO toDto(Event event);

    @Mapping(source = "creatorId", target = "creator")
    @Mapping(target = "medias", ignore = true)
    @Mapping(target = "rates", ignore = true)
    @Mapping(target = "factors", ignore = true)
    @Mapping(source = "titlesId", target = "titles")
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
