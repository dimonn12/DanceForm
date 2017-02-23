package by.danceform.app.service.system;

import by.danceform.app.converter.system.LanguageStringConverter;
import by.danceform.app.domain.system.LanguageString;
import by.danceform.app.dto.system.LanguageStringDTO;
import by.danceform.app.localization.LocalizationHelper;
import by.danceform.app.repository.system.LanguageStringRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing LanguageString.
 */
@Service
@Transactional
public class LanguageStringService {

    private final Logger log = LoggerFactory.getLogger(LanguageStringService.class);

    @Inject
    private LanguageStringRepository languageStringRepository;

    @Inject
    private LanguageStringConverter languageStringConverter;

    @Inject
    private LocalizationHelper localizationHelper;

    /**
     * Save a languageString.
     *
     * @param languageStringDTO the entity to save
     * @return the persisted entity
     */
    public LanguageStringDTO save(LanguageStringDTO languageStringDTO) {
        log.debug("Request to save LanguageString : {}", languageStringDTO);
        LanguageString languageString = languageStringConverter.convertToEntity(languageStringDTO);
        languageString = languageStringRepository.save(languageString);
        LanguageStringDTO result = languageStringConverter.convertToDto(languageString);
        localizationHelper.putIntoCache(languageString.getLang(), languageString.getName(), languageString.getValue());
        return result;
    }

    /**
     * Get all the languageStrings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LanguageStringDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LanguageStrings");
        Page<LanguageString> result = languageStringRepository.findAll(pageable);
        return result.map(languageString -> languageStringConverter.convertToDto(languageString));
    }

    /**
     * Get one languageString by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public LanguageStringDTO findOne(Long id) {
        log.debug("Request to get LanguageString : {}", id);
        LanguageString languageString = languageStringRepository.findOne(id);
        LanguageStringDTO languageStringDTO = languageStringConverter.convertToDto(languageString);
        return languageStringDTO;
    }

    /**
     * Delete the  languageString by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LanguageString : {}", id);
        languageStringRepository.delete(id);
        localizationHelper.clearCache();
    }

    public LanguageString findByLanguage(String name, String lang) {
        List<LanguageString> languageStringList = languageStringRepository.findByNameAndLang(name, lang);
        if(!languageStringList.isEmpty()) {
            return languageStringList.get(0);
        }
        return null;
    }

}
