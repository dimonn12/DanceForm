package by.danceform.app.repository.system;

import by.danceform.app.domain.system.SystemSetting;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the SystemSetting entity.
 */
public interface SystemSettingRepository extends JpaRepository<SystemSetting, Long> {

    SystemSetting findByName(String name);
}
