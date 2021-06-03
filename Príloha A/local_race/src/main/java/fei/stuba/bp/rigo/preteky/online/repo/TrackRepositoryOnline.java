package fei.stuba.bp.rigo.preteky.online.repo;


import fei.stuba.bp.rigo.preteky.models.sql.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepositoryOnline extends JpaRepository<Track, Integer>, JpaSpecificationExecutor<Track> {
}
