package fei.stuba.bp.rigo.preteky.online.repo;

import fei.stuba.bp.rigo.preteky.models.sql.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AttemptRepositoryOnline extends JpaRepository<Attempt, Integer>, JpaSpecificationExecutor<Attempt> {
    List<Attempt> findAllByResultStartListDisciplineRaceId(int id);
    Attempt findByIdAttempt(int id);
}