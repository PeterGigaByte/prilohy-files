package fei.stuba.socket.database.repository;


import fei.stuba.socket.database.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Integer>, JpaSpecificationExecutor<Track> {
}
