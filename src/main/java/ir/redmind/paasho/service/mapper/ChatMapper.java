package ir.redmind.paasho.service.mapper;

import ir.redmind.paasho.domain.Chat;
import ir.redmind.paasho.service.dto.ChatDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Chat and its DTO ChatDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ChatMapper extends EntityMapper<ChatDTO, Chat> {

    @Mapping(source = "first.id", target = "firstId")
    @Mapping(source = "first.avatar", target = "firstAvatar")
    @Mapping(source = "second.avatar", target = "secondAvatar")
    @Mapping(source = "second.id", target = "secondId")
    ChatDTO toDto(Chat chat);

    @Mapping(source = "firstId", target = "first")
    @Mapping(source = "secondId", target = "second")
    Chat toEntity(ChatDTO chatDTO);

    default Chat fromId(Long id) {
        if (id == null) {
            return null;
        }
        Chat chat = new Chat();
        chat.setId(id);
        return chat;
    }
}
