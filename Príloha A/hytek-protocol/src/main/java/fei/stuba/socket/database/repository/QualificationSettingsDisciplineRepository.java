package fei.stuba.socket.database.repository;

import fei.stuba.socket.database.models.QualificationSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QualificationSettingsDisciplineRepository extends JpaRepository<QualificationSettings, Integer>, JpaSpecificationExecutor<QualificationSettings> {
    QualificationSettings findQualificationSettingsByDisciplineId(int id);
}