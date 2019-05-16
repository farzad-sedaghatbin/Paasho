package ir.redmind.paasho.service.mapper;

import ir.redmind.paasho.domain.*;
import ir.redmind.paasho.service.dto.EventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Event and its DTO EventDTO.
 */
@Mapper(componentModel = "spring", uses = {User1Mapper.class, CategoryMapper.class, TitlesMapper.class, UserMapper.class})
public interface EventMapper extends EntityMapper<EventDTO, Event> {

    @Mapping(source = "titles.id", target = "titlesId")
    @Mapping(source = "creator.id", target = "creatorId")
    @Mapping(source = "creator.login", target = "creatorLogin")
    EventDTO toDto(Event event);

    @Mapping(target = "medias", ignore = true)
    @Mapping(target = "rates", ignore = true)
    @Mapping(target = "factors", ignore = true)
    @Mapping(source = "titlesId", target = "titles")
    @Mapping(source = "creatorId", target = "creator")
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
