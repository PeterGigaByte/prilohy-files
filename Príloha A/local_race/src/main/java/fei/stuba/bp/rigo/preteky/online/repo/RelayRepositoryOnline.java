package fei.stuba.bp.rigo.preteky.online.repo;

import fei.stuba.bp.rigo.preteky.models.sql.Relay;
import fei.stuba.bp.rigo.preteky.repository.RelayRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RelayRepositoryOnline extends JpaRepository<Relay, Integer>, JpaSpecificationExecutor<Relay> {
    List<Relay> findAllByResultStartListDisciplineRaceId(int id);
}