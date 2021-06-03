package fei.stuba.socket.database.repository;

import fei.stuba.socket.database.models.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClubRepository extends JpaRepository<Club, Integer>, JpaSpecificationExecutor<Club> {
    Club findClubById(Integer id);
}