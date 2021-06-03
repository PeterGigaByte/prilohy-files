package fei.stuba.bp.rigo.preteky.online.repo;

import fei.stuba.bp.rigo.preteky.models.sql.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AthleteRepositoryOnline extends JpaRepository<Athlete, Integer>, JpaSpecificationExecutor<Athlete> {
    Athlete findAthleteById(int id);
}