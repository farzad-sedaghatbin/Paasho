package ir.redmind.paasho.service.mapper;

import ir.redmind.paasho.domain.*;
import ir.redmind.paasho.service.dto.SettingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Setting and its DTO SettingDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SettingMapper extends EntityMapper<SettingDTO, Setting> {



    default Setting fromId(Long id) {
        if (id == null) {
            return null;
        }
        Setting setting = new Setting();
        setting.setId(id);
        return setting;
    }
}
