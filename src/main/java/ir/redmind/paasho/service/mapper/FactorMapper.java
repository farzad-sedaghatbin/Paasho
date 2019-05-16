package ir.redmind.paasho.service.mapper;

import ir.redmind.paasho.domain.*;
import ir.redmind.paasho.service.dto.FactorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Factor and its DTO FactorDTO.
 */
@Mapper(componentModel = "spring", uses = {EventMapper.class})
public interface FactorMapper extends EntityMapper<FactorDTO, Factor> {

    @Mapping(source = "event.id", target = "eventId")
    FactorDTO toDto(Factor factor);

    @Mapping(source = "eventId", target = "event")
    Factor toEntity(FactorDTO factorDTO);

    default Factor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Factor factor = new Factor();
        factor.setId(id);
        return factor;
    }
}
