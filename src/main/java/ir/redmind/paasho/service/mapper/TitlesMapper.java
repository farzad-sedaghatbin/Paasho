package ir.redmind.paasho.service.mapper;

import ir.redmind.paasho.domain.*;
import ir.redmind.paasho.service.dto.TitlesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Titles and its DTO TitlesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TitlesMapper extends EntityMapper<TitlesDTO, Titles> {



    default Titles fromId(Long id) {
        if (id == null) {
            return null;
        }
        Titles titles = new Titles();
        titles.setId(id);
        return titles;
    }
}
