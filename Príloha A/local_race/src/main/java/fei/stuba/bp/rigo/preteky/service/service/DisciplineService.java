package fei.stuba.bp.rigo.preteky.service.service;

import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import fei.stuba.bp.rigo.preteky.models.sql.QualificationSettings;

import java.sql.Date;
import java.util.List;

public interface DisciplineService {
    List<Discipline> findDisciplinesByRaceId(Integer id);
    List<Discipline> findDisciplinesByDisciplineNameAndCategoryAndRaceIdAndIdIsNot(String disciplineName,String category,int idRace, int idDiscipline);
    List<Discipline> findDisciplinesByDisciplineNameAndCategoryAndRaceIdAndIdIsNotAndPhaseNameAndPhaseNumber(String disciplineName,String category,int idRace, int idDiscipline,String phaseName,int phaseNumber);
    List<Discipline> findDisciplinesByRaceIdOrderByCameraIdDesc(int id);
    List<Discipline> findDisciplinesByDisciplineDateAndRaceIdOrderByDisciplineTime(Date date, int id);
    List<Discipline> findDisciplinesByDisciplineDateAndRaceIdAndDisciplineNameOrderByDisciplineTime(Date disciplineDate, int race_id, String disciplineName);
    List<Discipline> findDisciplinesByDisciplineDateAndRaceIdAndCategoryOrderByDisciplineTime(Date disciplineDate, int race_id, String category);
    List<Discipline> findDisciplinesByDisciplineDateAndRaceIdAndCategoryAndDisciplineNameOrderByDisciplineTime(Date disciplineDate, int race_id, String category, String disciplineName);
    void saveDiscipline(Discipline discipline);
    QualificationSettings findQualificationSettingsByDisciplineId(int id);
    void saveQualificationSettings(QualificationSettings qualificationSettings);
    Discipline findDisciplineById(int id);
    void deleteDiscipline(int id);
    void deleteDisciplineByRaceIdAndParticipantsEquals(int raceId,int participants);
    void changeCameraNumbering(int raceId);
    List<Discipline> findDisciplinesRaceIdTypeASC(int id,String type);
    List<Discipline> findDisciplinesByRaceIdAndCategoryAndPhaseNameAndDisciplineNameOrderByPhaseNumberDesc(int raceId, String category, String phaseName, String disciplineName);

}
