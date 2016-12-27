package by.danceform.app.repository.system;

import by.danceform.app.domain.system.SystemSetting;
import by.danceform.app.repository.AbstractEntityRepository;

/**
 * Spring Data JPA repository for the SystemSetting entity.
 */
public interface SystemSettingRepository extends AbstractEntityRepository<SystemSetting, Long> {

    SystemSetting findByName(String name);
}
