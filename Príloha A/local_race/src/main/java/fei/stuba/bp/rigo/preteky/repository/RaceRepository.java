package fei.stuba.bp.rigo.preteky.repository;

import fei.stuba.bp.rigo.preteky.models.sql.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RaceRepository extends JpaRepository<Race, Integer>, JpaSpecificationExecutor<Race> {
    List<Race> findRegisteredUserByActivity(Integer activity);
    Race findRaceById(Integer id);
}