package fei.stuba.socket.database.repository;

import fei.stuba.socket.database.models.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, Integer>, JpaSpecificationExecutor<Settings> {
}
