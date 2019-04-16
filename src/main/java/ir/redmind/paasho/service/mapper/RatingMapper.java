package ir.redmind.paasho.service.mapper;

import ir.redmind.paasho.domain.*;
import ir.redmind.paasho.service.dto.RatingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Rating and its DTO RatingDTO.
 */
@Mapper(componentModel = "spring", uses = {EventMapper.class, User1Mapper.class})
public interface RatingMapper extends EntityMapper<RatingDTO, Rating> {

    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "user.id", target = "userId")
    RatingDTO toDto(Rating rating);

    @Mapping(source = "eventId", target = "event")
    @Mapping(source = "userId", target = "user")
    Rating toEntity(RatingDTO ratingDTO);

    default Rating fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rating rating = new Rating();
        rating.setId(id);
        return rating;
    }
}
