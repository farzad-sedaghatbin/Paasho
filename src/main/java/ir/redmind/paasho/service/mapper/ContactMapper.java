package ir.redmind.paasho.service.mapper;

import ir.redmind.paasho.domain.*;
import ir.redmind.paasho.service.dto.ContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Contact and its DTO ContactDTO.
 */
@Mapper(componentModel = "spring", uses = {User1Mapper.class})
public interface ContactMapper extends EntityMapper<ContactDTO, Contact> {

    @Mapping(source = "id.id", target = "idId")
    ContactDTO toDto(Contact contact);

    @Mapping(source = "idId", target = "id")
    Contact toEntity(ContactDTO contactDTO);

    default Contact fromId(Long id) {
        if (id == null) {
            return null;
        }
        Contact contact = new Contact();
        contact.setId(id);
        return contact;
    }
}
