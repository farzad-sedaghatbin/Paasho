package ir.redmind.paasho.service.mapper;

import ir.redmind.paasho.domain.*;
import ir.redmind.paasho.service.dto.NotificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Notification and its DTO NotificationDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, EventMapper.class})
public interface NotificationMapper extends EntityMapper<NotificationDTO, Notification> {

    @Mapping(source = "from.id", target = "fromId")
    @Mapping(source = "from.login", target = "fromLogin")
    @Mapping(source = "relatedEvent.id", target = "relatedEventId")
    NotificationDTO toDto(Notification notification);

    @Mapping(source = "fromId", target = "from")
    @Mapping(source = "relatedEventId", target = "relatedEvent")
    Notification toEntity(NotificationDTO notificationDTO);

    default Notification fromId(Long id) {
        if (id == null) {
            return null;
        }
        Notification notification = new Notification();
        notification.setId(id);
        return notification;
    }
}
