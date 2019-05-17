package ir.redmind.paasho.service.impl;

import ir.redmind.paasho.service.SettingService;
import ir.redmind.paasho.domain.Setting;
import ir.redmind.paasho.repository.SettingRepository;
import ir.redmind.paasho.service.dto.SettingDTO;
import ir.redmind.paasho.service.mapper.SettingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing Setting.
 */
@Service
@Transactional
public class SettingServiceImpl implements SettingService {

    private final Logger log = LoggerFactory.getLogger(SettingServiceImpl.class);

    private final SettingRepository settingRepository;

    private final SettingMapper settingMapper;


    public SettingServiceImpl(SettingRepository settingRepository, SettingMapper settingMapper) {
        this.settingRepository = settingRepository;
        this.settingMapper = settingMapper;
    }

    /**
     * Save a setting.
     *
     * @param settingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SettingDTO save(SettingDTO settingDTO) {
        log.debug("Request to save Setting : {}", settingDTO);
        Setting setting = settingMapper.toEntity(settingDTO);
        setting = settingRepository.save(setting);
        SettingDTO result = settingMapper.toDto(setting);
        return result;
    }

    /**
     * Get all the settings.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SettingDTO> findAll() {
        log.debug("Request to get all Settings");
        return settingRepository.findAll().stream()
            .map(settingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one setting by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SettingDTO> findOne(Long id) {
        log.debug("Request to get Setting : {}", id);
        return settingRepository.findById(id)
            .map(settingMapper::toDto);
    }

    /**
     * Delete the setting by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Setting : {}", id);
        settingRepository.deleteById(id);
    }

    /**
     * Search for the setting corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SettingDTO> search(String query) {
        log.debug("Request to search Settings for query {}", query);
        return null;
    }
}
