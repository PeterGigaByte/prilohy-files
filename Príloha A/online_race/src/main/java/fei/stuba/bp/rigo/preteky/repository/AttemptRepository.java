package fei.stuba.bp.rigo.preteky.repository;

import fei.stuba.bp.rigo.preteky.models.sql.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AttemptRepository extends JpaRepository<Attempt, Integer>, JpaSpecificationExecutor<Attempt> {
    List<Attempt> findAllByResultStartListId(Integer resultId);
    Integer countByResultStartListId(Integer resultId);
    List<Attempt> deleteAllByResultStartListId(Integer resultId);
    Integer countByResultStartListDisciplineId(Integer disciplineId);
    List<Attempt> findAllByResultStartListIdOrderByIdAttemptDesc(Integer resultId);
    List<Attempt> findAllByResultStartListIdOrderByIdAttemptAsc(Integer resultId);
    List<Attempt> findAllByResultStartListIdOrderByPerformanceDesc(Integer resultId);
    Attempt findByIdAttempt(Integer id);

}