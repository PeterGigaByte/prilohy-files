package fei.stuba.socket.database.repository;

import fei.stuba.socket.database.models.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AthleteRepository extends JpaRepository<Athlete, Integer>, JpaSpecificationExecutor<Athlete> {
    Athlete findAthleteById(int id);

}