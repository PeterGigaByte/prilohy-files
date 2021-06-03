package fei.stuba.bp.rigo.preteky.online.repo;

import fei.stuba.bp.rigo.preteky.models.sql.Athlete;
import fei.stuba.bp.rigo.preteky.models.sql.ResultStartList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ResultStartListRepositoryOnline extends JpaRepository<ResultStartList, Integer>, JpaSpecificationExecutor<ResultStartList> {
    ResultStartList findById(int id);
    List<ResultStartList> findAllByDisciplineRaceId(int id);
    List<ResultStartList> findAllByDisciplineIdOrderByResultPerformanceAsc(int id);
    List<ResultStartList> findResultStartListByDisciplineIdOrderByStartPerformanceAsc(int id);
    List<ResultStartList> findByAthleteIdAndDisciplineId(int idAthlete, int idDiscipline);
    List<ResultStartList> findAllByDisciplineRaceIdAndDisciplineId(int idRace, int idDiscipline);
    default Map<Athlete, ResultStartList> findAllByDisciplineRaceIdMap(int id) {
        return findAllByDisciplineRaceId(id).stream().collect(Collectors.toMap(ResultStartList::getAthlete, v -> v));
    }
    List<ResultStartList> findAllByAthleteIdOrderByDisciplineDisciplineDateAsc(int athleteId);
}