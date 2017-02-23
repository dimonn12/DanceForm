package by.danceform.app.repository.system;

import by.danceform.app.domain.system.LanguageString;
import by.danceform.app.domain.system.Message;
import by.danceform.app.repository.AbstractEntityRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the LanguageString entity.
 */
@SuppressWarnings("unused")
public interface LanguageStringRepository extends AbstractEntityRepository<LanguageString, Long> {

    @Query("SELECT langStr FROM LanguageString langStr WHERE langStr.lang = :lang AND langStr.name = :name")
    List<LanguageString> findByNameAndLang(String name, String lang);

    List<LanguageString> findByName(String name);
}
