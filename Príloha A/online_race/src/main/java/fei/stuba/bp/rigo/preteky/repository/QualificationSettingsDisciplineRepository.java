package fei.stuba.bp.rigo.preteky.repository;

import fei.stuba.bp.rigo.preteky.models.sql.QualificationSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface QualificationSettingsDisciplineRepository extends JpaRepository<QualificationSettings, Integer>, JpaSpecificationExecutor<QualificationSettings> {
    QualificationSettings findQualificationSettingsByDisciplineId(int id);
    List<QualificationSettings> findAllByDisciplineRaceId(int id);
}