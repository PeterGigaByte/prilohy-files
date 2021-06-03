package fei.stuba.bp.rigo.preteky.service.implementation;

import fei.stuba.bp.rigo.preteky.models.sql.Athlete;
import fei.stuba.bp.rigo.preteky.models.sql.Club;
import fei.stuba.bp.rigo.preteky.models.sql.ClubTransfer;
import fei.stuba.bp.rigo.preteky.repository.AthleteRepository;
import fei.stuba.bp.rigo.preteky.repository.ClubRepository;
import fei.stuba.bp.rigo.preteky.repository.ClubTransferRepository;
import fei.stuba.bp.rigo.preteky.service.service.ClubParticipantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ClubAthletes implements ClubParticipantsService {
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private AthleteRepository athleteRepository;
    @Autowired
    private ClubTransferRepository clubTransferRepository;


    public ClubAthletes(ClubRepository clubRepository, AthleteRepository athleteRepository, ClubTransferRepository clubTransferRepository){
        super();
        this.clubRepository=clubRepository;
        this.athleteRepository=athleteRepository;
        this.clubTransferRepository=clubTransferRepository;
    }

    @Override
    public void clubSave(Club club){
        clubRepository.save(club);
    }

    @Override
    public List<Club> getAllClubs(){
        return clubRepository.findAll();
    }
    @Override
    public  Club findClubById(Integer id){
        return clubRepository.findClubById(id);
    }

    @Override
    public void deleteClub(int id) {
        clubRepository.deleteById(id);
    }

    @Override
    public void saveAthlete(Athlete athlete) {
        athleteRepository.save(athlete);
    }

    @Override
    public Athlete findAthlete(int id) {
        return  athleteRepository.findAthleteById(id);
    }

    @Override
    public List<Athlete> findAllAthletes() {
        return athleteRepository.findAll();
    }
    @Override
    public void deleteAthlete(int id){
        athleteRepository.deleteById(id);
    }
    @Override
    public void saveClubTransfer(ClubTransfer clubTransfer){
        clubTransferRepository.save(clubTransfer);
    }
    @Override
    public Map<Athlete,ClubTransfer> findAllTransfersToMap(){
        return clubTransferRepository.findAllMap();
    }
    @Override
    public Map<Athlete,ClubTransfer> findRealClubs(Date since,Date to,Date to2){
        return clubTransferRepository.findRealClubs(since,to,to2);
    }

    @Override
    public List<ClubTransfer> findClubTransferByAthleteId(int id) {
        return clubTransferRepository.findClubTransferByAthleteId(id);
    }
    @Override
    public Map<Athlete, ClubTransfer> findRealClubsOfAthlete(Date since,Date to,int id2,Date to2,int id){
        return clubTransferRepository.findRealClubsOfAthlete( since,  to,id2,  to2,  id);
    }
    @Override
    public void deleteTransfer(int id){
        clubTransferRepository.deleteById(id);
    }
    @Override
    public List<ClubTransfer> findRealAthletesOfClub(Date since, Date to, int id2, String sex, Date to2, int id, String sex2){
        return clubTransferRepository.findClubTransfersBySinceIsLessThanEqualAndToIsGreaterThanEqualAndClubIdAndAthleteSexOrToIsNullAndSinceIsLessThanEqualAndClubIdAndAthleteSex( since, to, id2, sex, to2, id, sex2);
    }
}
