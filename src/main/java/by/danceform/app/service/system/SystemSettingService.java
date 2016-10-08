package by.danceform.app.service.system;

import by.danceform.app.converter.system.SystemSettingConverter;
import by.danceform.app.domain.system.SystemSetting;
import by.danceform.app.dto.system.SystemSettingDTO;
import by.danceform.app.repository.system.SystemSettingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Service Implementation for managing SystemSetting.
 */
@Service
@Transactional
public class SystemSettingService {

    private final Logger log = LoggerFactory.getLogger(SystemSettingService.class);

    @Inject
    private SystemSettingRepository systemSettingRepository;

    @Inject
    private SystemSettingConverter systemSettingConverter;

    /**
     * Save a systemSetting.
     *
     * @param systemSettingDTO the entity to save
     * @return the persisted entity
     */
    public SystemSettingDTO save(SystemSettingDTO systemSettingDTO) {
        log.debug("Request to save SystemSetting : {}", systemSettingDTO);
        SystemSetting systemSetting = systemSettingConverter.convertToEntity(systemSettingDTO);
        systemSetting = systemSettingRepository.save(systemSetting);
        SystemSettingDTO result = systemSettingConverter.convertToDto(systemSetting);
        return result;
    }

    /**
     * Get all the systemSettings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SystemSettingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SystemSettings");
        Page<SystemSetting> result = systemSettingRepository.findAll(pageable);
        return result.map(systemSetting -> systemSettingConverter.convertToDto(systemSetting));
    }

    /**
     * Get one systemSetting by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SystemSettingDTO findOne(Long id) {
        log.debug("Request to get SystemSetting : {}", id);
        SystemSetting systemSetting = systemSettingRepository.findOne(id);
        SystemSettingDTO systemSettingDTO = systemSettingConverter.convertToDto(systemSetting);
        return systemSettingDTO;
    }

    @Transactional(readOnly = true)
    public SystemSetting findByName(String name) {
        log.debug("Request to get SystemSetting by name: name={}", name);
        return systemSettingRepository.findByName(name);
    }

    /**
     * Delete the  systemSetting by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SystemSetting : {}", id);
        systemSettingRepository.delete(id);
    }
}
