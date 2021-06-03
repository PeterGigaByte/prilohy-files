package fei.stuba.socket.database.repository;

import fei.stuba.socket.database.models.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RaceRepository extends JpaRepository<Race, Integer>, JpaSpecificationExecutor<Race> {
    List<Race> findRegisteredUserByActivity(Integer activity);
    Race findRaceByActivity(Integer activity);
    Race findRaceById(Integer id);
}