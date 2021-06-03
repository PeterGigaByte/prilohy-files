package fei.stuba.socket.database.repository;

import fei.stuba.socket.database.models.Relay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RelayRepository extends JpaRepository<Relay, Integer>, JpaSpecificationExecutor<Relay> {

}