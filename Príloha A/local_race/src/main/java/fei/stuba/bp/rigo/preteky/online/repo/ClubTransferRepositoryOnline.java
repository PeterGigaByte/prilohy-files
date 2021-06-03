package fei.stuba.bp.rigo.preteky.online.repo;

import fei.stuba.bp.rigo.preteky.models.sql.Athlete;
import fei.stuba.bp.rigo.preteky.models.sql.ClubTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ClubTransferRepositoryOnline extends JpaRepository<ClubTransfer, Integer>, JpaSpecificationExecutor<ClubTransfer> {
    List<ClubTransfer> findClubTransfersBySinceIsLessThanEqualAndToIsGreaterThanEqualOrToIsNullAndSinceIsLessThanEqual(Date since, Date to, Date to2);
    default Map<Athlete, ClubTransfer> findAllMap() {
        return findAll().stream().collect(Collectors.toMap(ClubTransfer::getAthlete, v -> v));
    }
    default Map<Athlete, ClubTransfer> findRealClubs(Date since, Date to, Date to2) {
        return findClubTransfersBySinceIsLessThanEqualAndToIsGreaterThanEqualOrToIsNullAndSinceIsLessThanEqual( since, to, to2).stream().collect(Collectors.toMap(ClubTransfer::getAthlete, v -> v));
    }
    List<ClubTransfer> findClubTransferByAthleteId(int id);
    List<ClubTransfer> findClubTransfersBySinceIsLessThanEqualAndToIsGreaterThanEqualAndClubIdAndAthleteSexOrToIsNullAndSinceIsLessThanEqualAndClubIdAndAthleteSex(Date since, Date to, int id2, String sex, Date to2, int id, String sex2);

    List<ClubTransfer> findClubTransfersBySinceIsLessThanEqualAndToIsGreaterThanEqualAndAthleteIdOrToIsNullAndSinceIsLessThanEqualAndAthleteId(Date since, Date to, int id2, Date to2, int id);
    default Map<Athlete, ClubTransfer> findRealClubsOfAthlete(Date since, Date to, int id2, Date to2, int id) {
        return findClubTransfersBySinceIsLessThanEqualAndToIsGreaterThanEqualAndAthleteIdOrToIsNullAndSinceIsLessThanEqualAndAthleteId( since, to,id2, to2,id).stream().collect(Collectors.toMap(ClubTransfer::getAthlete, v -> v));
    }


}