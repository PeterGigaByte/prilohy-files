package fei.stuba.bp.rigo.preteky.service.service;

import fei.stuba.bp.rigo.preteky.models.sql.Athlete;
import fei.stuba.bp.rigo.preteky.models.sql.Club;
import fei.stuba.bp.rigo.preteky.models.sql.ClubTransfer;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface ClubParticipantsService {
    void clubSave(Club club);
    List<Club> getAllClubs();
    Club findClubById(Integer id);
    void deleteClub(int id);
    void saveAthlete(Athlete athlete);
    Athlete findAthlete(int id);
    List<Athlete> findAllAthletes();
    void deleteAthlete(int id);
    void saveClubTransfer(ClubTransfer clubTransfer);
    Map<Athlete,ClubTransfer> findAllTransfersToMap();
    Map<Athlete,ClubTransfer> findRealClubs(Date since,Date to,Date to2);
    List<ClubTransfer> findClubTransferByAthleteId(int id);
    Map<Athlete, ClubTransfer> findRealClubsOfAthlete(Date since,Date to,int id2,Date to2,int id);
    void deleteTransfer(int id);
    List<ClubTransfer> findRealAthletesOfClub(Date since, Date to, int id2, String sex, Date to2, int id, String sex2);
}
