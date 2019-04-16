package ir.redmind.paasho.service.mapper;

import ir.redmind.paasho.domain.*;
import ir.redmind.paasho.service.dto.User1DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity User1 and its DTO User1DTO.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, EventMapper.class, NotificationMapper.class})
public interface User1Mapper extends EntityMapper<User1DTO, User1> {

    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "notification.id", target = "notificationId")
    User1DTO toDto(User1 user1);

    @Mapping(target = "contacts", ignore = true)
    @Mapping(target = "rates", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "event", ignore = true)
    @Mapping(source = "eventId", target = "event")
    @Mapping(source = "notificationId", target = "notification")
    @Mapping(target = "factors", ignore = true)
    User1 toEntity(User1DTO user1DTO);

    default User1 fromId(Long id) {
        if (id == null) {
            return null;
        }
        User1 user1 = new User1();
        user1.setId(id);
        return user1;
    }
}
