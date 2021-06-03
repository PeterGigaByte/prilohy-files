package fei.stuba.bp.rigo.preteky.repository;

import fei.stuba.bp.rigo.preteky.models.sql.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClubRepository extends JpaRepository<Club, Integer>, JpaSpecificationExecutor<Club> {
    Club findClubById(Integer id);
}