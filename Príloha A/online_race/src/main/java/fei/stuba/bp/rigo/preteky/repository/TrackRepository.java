package fei.stuba.bp.rigo.preteky.repository;


import fei.stuba.bp.rigo.preteky.models.sql.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Integer>, JpaSpecificationExecutor<Track> {
}
