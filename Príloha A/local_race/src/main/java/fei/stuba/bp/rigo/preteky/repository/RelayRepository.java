package fei.stuba.bp.rigo.preteky.repository;

import fei.stuba.bp.rigo.preteky.models.sql.Relay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RelayRepository extends JpaRepository<Relay, Integer>, JpaSpecificationExecutor<Relay> {
    Relay findRelayById(int id);
}