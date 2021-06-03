package fei.stuba.bp.rigo.preteky.repository;

import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface DisciplineRepository extends JpaRepository<Discipline, Integer>, JpaSpecificationExecutor<Discipline> {
    List<Discipline> findDisciplinesByRaceIdOrderByDisciplineTime(Integer id);
    List<Discipline> findDisciplinesByDisciplineNameAndCategoryAndRaceIdAndIdIsNot(String disciplineName,String category,int idRace, int idDiscipline);
    List<Discipline> findDisciplinesByDisciplineNameAndCategoryAndRaceIdAndIdIsNotAndPhaseNameAndPhaseNumber(String disciplineName,String category,int idRace, int idDiscipline,String phaseName,int phaseNumber);
    List<Discipline> findDisciplinesByRaceIdOrderByCameraIdDesc(int id);
    List<Discipline> findDisciplinesByRaceIdAndDisciplineTypeOrderByCameraIdDesc(int id,String type);
    List<Discipline> findDisciplinesByDisciplineDateAndRaceIdOrderByDisciplineTime(Date date, int id);
    List<Discipline> findDisciplinesByDisciplineDateAndRaceIdAndDisciplineNameOrderByDisciplineTime(Date disciplineDate, int race_id, String disciplineName);
    List<Discipline> findDisciplinesByDisciplineDateAndRaceIdAndCategoryOrderByDisciplineTime(Date disciplineDate, int race_id, String category);
    List<Discipline> findDisciplinesByDisciplineDateAndRaceIdAndCategoryAndDisciplineNameOrderByDisciplineTime(Date disciplineDate, int race_id, String category, String disciplineName);
    void deleteDisciplineByRaceIdAndParticipantsEquals(int raceId,int participants);
    Discipline findDisciplinesById(int id);
    List<Discipline> findDisciplinesByRaceIdAndDisciplineTypeOrderByDisciplineTime(Integer id,String type);
    List<Discipline> findDisciplinesByRaceIdAndCategoryAndPhaseNameAndDisciplineNameOrderByPhaseNumberDesc(int raceId, String category, String phaseName, String disciplineName);
    @Query("SELECT DISTINCT d.disciplineName FROM Discipline d WHERE d.race.id = (:activeRace)")
    List<String> disciplineNames(int activeRace);
    @Query("SELECT DISTINCT d.category FROM Discipline d WHERE d.race.id = (:activeRace)")
    List<String> categories(int activeRace);
    @Query("SELECT DISTINCT d.phaseName FROM Discipline d WHERE d.race.id = (:activeRace)")
    List<String> phases(int activeRace);
}