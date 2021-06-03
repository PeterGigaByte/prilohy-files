package fei.stuba.socket.database.repository;

import fei.stuba.socket.database.models.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AttemptRepository extends JpaRepository<Attempt, Integer>, JpaSpecificationExecutor<Attempt> {

}