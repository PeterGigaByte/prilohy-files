package fei.stuba.bp.rigo.preteky.online.repo;

import fei.stuba.bp.rigo.preteky.models.sql.QualificationSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface QualificationSettingsDisciplineRepositoryOnline extends JpaRepository<QualificationSettings, Integer>, JpaSpecificationExecutor<QualificationSettings> {
    QualificationSettings findQualificationSettingsByDisciplineId(int id);
    List<QualificationSettings> findAllByDisciplineRaceId(int id);
}