package fei.stuba.bp.rigo.preteky.web.controllers;

import fei.stuba.bp.rigo.preteky.models.sql.*;
import fei.stuba.bp.rigo.preteky.online.repo.*;
import fei.stuba.bp.rigo.preteky.repository.*;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DatabaseRestController {
    @Autowired
    private AthleteRepositoryOnline athleteRepositoryOnline;
    @Autowired
    private ClubRepositoryOnline clubRepositoryOnline;
    @Autowired
    private ClubTransferRepositoryOnline clubTransferRepositoryOnline;
    //Online repositories
    //race
    @Autowired
    private RaceRepositoryOnline raceRepositoryOnline;
    @Autowired
    private SettingsRepositoryOnline settingsRepositoryOnline;
    @Autowired
    private TrackRepositoryOnline trackRepositoryOnline;
    //discipline
    @Autowired
    private DisciplineRepositoryOnline disciplineRepositoryOnline;
    @Autowired
    private QualificationSettingsDisciplineRepositoryOnline qualificationSettingsDisciplineRepositoryOnline;
    //startList bibs results
    @Autowired
    private ResultStartListRepositoryOnline resultStartListRepositoryOnline;
    @Autowired
    private BibRepositoryOnline bibRepositoryOnline;
    @Autowired
    private AttemptRepositoryOnline attemptRepositoryOnline;
    @Autowired
    private RelayRepositoryOnline relayRepositoryOnline;

    //Offline repositories
    //race
    @Autowired
    private RaceRepository raceRepository;
    @Autowired
    private SettingsRepository settingsRepository;
    @Autowired
    private TrackRepository trackRepository;
    //discipline
    @Autowired
    private DisciplineRepository disciplineRepository;
    @Autowired
    private QualificationSettingsDisciplineRepository qualificationSettingsDisciplineRepository;
    //startList bibs results
    @Autowired
    private ResultStartListRepository resultStartListRepository;
    @Autowired
    private BibRepository bibRepository;
    @Autowired
    private AttemptRepository attemptRepository;
    @Autowired
    private RelayRepository relayRepository;

    @Autowired
    private AthleteRepository athleteRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private ClubTransferRepository clubTransferRepository;
    private RaceService raceService;

    public DatabaseRestController(RaceService raceService, AthleteRepositoryOnline athleteRepositoryOnline, ClubRepositoryOnline clubRepositoryOnline, ClubTransferRepositoryOnline clubTransferRepositoryOnline, RaceRepositoryOnline raceRepositoryOnline, SettingsRepositoryOnline settingsRepositoryOnline, TrackRepositoryOnline trackRepositoryOnline, DisciplineRepositoryOnline disciplineRepositoryOnline, QualificationSettingsDisciplineRepositoryOnline qualificationSettingsDisciplineRepositoryOnline, ResultStartListRepositoryOnline resultStartListRepositoryOnline, BibRepositoryOnline bibRepositoryOnline, AttemptRepositoryOnline attemptRepositoryOnline, RelayRepositoryOnline relayRepositoryOnline, RaceRepository raceRepository, SettingsRepository settingsRepository, TrackRepository trackRepository, DisciplineRepository disciplineRepository, QualificationSettingsDisciplineRepository qualificationSettingsDisciplineRepository, ResultStartListRepository resultStartListRepository, BibRepository bibRepository, AttemptRepository attemptRepository, RelayRepository relayRepository, AthleteRepository athleteRepository, ClubRepository clubRepository, ClubTransferRepository clubTransferRepository) {
        this.athleteRepositoryOnline = athleteRepositoryOnline;
        this.clubRepositoryOnline = clubRepositoryOnline;
        this.clubTransferRepositoryOnline = clubTransferRepositoryOnline;
        this.raceRepositoryOnline = raceRepositoryOnline;
        this.settingsRepositoryOnline = settingsRepositoryOnline;
        this.trackRepositoryOnline = trackRepositoryOnline;
        this.disciplineRepositoryOnline = disciplineRepositoryOnline;
        this.qualificationSettingsDisciplineRepositoryOnline = qualificationSettingsDisciplineRepositoryOnline;
        this.resultStartListRepositoryOnline = resultStartListRepositoryOnline;
        this.bibRepositoryOnline = bibRepositoryOnline;
        this.attemptRepositoryOnline = attemptRepositoryOnline;
        this.relayRepositoryOnline = relayRepositoryOnline;
        this.raceRepository = raceRepository;
        this.settingsRepository = settingsRepository;
        this.trackRepository = trackRepository;
        this.disciplineRepository = disciplineRepository;
        this.qualificationSettingsDisciplineRepository = qualificationSettingsDisciplineRepository;
        this.resultStartListRepository = resultStartListRepository;
        this.bibRepository = bibRepository;
        this.attemptRepository = attemptRepository;
        this.relayRepository = relayRepository;
        this.athleteRepository = athleteRepository;
        this.clubRepository = clubRepository;
        this.clubTransferRepository = clubTransferRepository;
        this.raceService = raceService;
    }
    @ModelAttribute("activeRace")
    public Race activeRace(){
        if(raceService.getActiveRace().size()>0){
            return raceService.getActiveRace().get(0);
        }else{
            return raceService.getFakeRace();
        }
    }

    @PostMapping("/databases/download/dbs/{id}/deleteOld")
    public String downloadDatabase(@PathVariable int id){
        //race
        raceRepository.deleteAll();
        settingsRepository.deleteAll();
        trackRepository.deleteAll();
        downloadAthletes();
        raceRepository.save(raceRepositoryOnline.findRaceById(id));
        //Disciplines
       //disciplineRepository.saveAll(disciplineRepositoryOnline.findAllByRaceId(id));

        qualificationSettingsDisciplineRepository.saveAll(qualificationSettingsDisciplineRepositoryOnline.findAllByDisciplineRaceId(id));
        //startList bibs results
        resultStartListRepository.saveAll(resultStartListRepositoryOnline.findAllByDisciplineRaceId(id));

        bibRepository.saveAll(bibRepositoryOnline.findByRaceId(id));

        attemptRepository.saveAll(attemptRepositoryOnline.findAllByResultStartListDisciplineRaceId(id));
        relayRepository.saveAll(relayRepositoryOnline.findAllByResultStartListDisciplineRaceId(id));

        return String.valueOf(id);
    }
    @PostMapping("/databases/upload/dbs")
    public String uploadDatabase(){
        Race raceOnline = raceRepositoryOnline.findRaceById(activeRace().getId());
        if(raceOnline.getId() == null){
            return "false";
        }
        //race upload
        Race raceLocal = activeRace();
        raceOnline.setRaceEdit(raceLocal);
        raceRepositoryOnline.save(raceOnline);

        uploadApplications();
        uploadResults();

        return String.valueOf(activeRace().getId());
    }

    public void downloadAthletes(){
        clubTransferRepository.deleteAll();
        clubRepository.deleteAll();
        athleteRepository.deleteAll();
        athleteRepository.saveAll(athleteRepositoryOnline.findAll());
        clubRepository.saveAll(clubRepositoryOnline.findAll());
        clubTransferRepository.saveAll(clubTransferRepositoryOnline.findAll());
    }
    @GetMapping("/results/upload/dbs")
    public String uploadOnlyResults(){
        Race raceLocal = activeRace();
        if( raceLocal.getId() == -1){
            return "false";
        }
        uploadResults();
        return "redirect:/results";
    }
    public void uploadResults(){
        //result settings upload
        List<ResultStartList> resultStartLists = resultStartListRepositoryOnline.findAllByDisciplineRaceId(activeRace().getId());
        for (ResultStartList r :
                resultStartLists) {
            ResultStartList localR = resultStartListRepository.findResultStartListById(r.getId());
            if(localR!=null){
                r.editResultStartList(localR);
                resultStartListRepositoryOnline.save(r);
            }
        }
        //bib  upload
        List<Bib> bibList = bibRepositoryOnline.findAllByRaceId(activeRace().getId());
        for (Bib b :
                bibList) {
            Bib localB = bibRepository.findBibById(b.getId());
            if(localB!=null){
                b.setBib(localB.getBib());
                bibRepositoryOnline.save(b);
            }
        }
        //relay  upload
        List<Relay> relayList = relayRepositoryOnline.findAllByResultStartListDisciplineRaceId(activeRace().getId());
        for (Relay r :
                relayList) {
            Relay localR = relayRepository.findRelayById(r.getId());
            if(localR!=null){
                r.setSector(localR.getSector());
                relayRepositoryOnline.save(r);
            }
        }
        //attempt settings upload
        List<Attempt> attemptList = attemptRepositoryOnline.findAllByResultStartListDisciplineRaceId(activeRace().getId());
        for (Attempt a :
                attemptList) {
            Attempt localA = attemptRepository.findByIdAttempt(a.getIdAttempt());
            if(localA!=null){
                a.setPerformance(localA.getPerformance());
                a.setNum(localA.getNum());
                attemptRepositoryOnline.save(a);
            }
        }
    }
    @GetMapping("/applications/upload/dbs")
    public String uploadOnlyApplications(){
        Race raceOnline = raceRepositoryOnline.findRaceById(activeRace().getId());
        if(raceOnline.getId() == null){
            return "false";
        }
        //race upload
        Race raceLocal = activeRace();
        raceOnline.setRaceEdit(raceLocal);
        raceRepositoryOnline.save(raceOnline);
        uploadApplications();
        uploadResults();
        return "redirect:/applications";
    }
    public void uploadApplications() {
        //disciplines upload
        List<Discipline> disciplineList = disciplineRepositoryOnline.findDisciplinesByRaceIdOrderByCameraIdDesc(activeRace().getId());
        for (Discipline d :
                disciplineList) {
            Discipline localD = disciplineRepository.findDisciplinesById(d.getId());
            if(localD!=null){
                d.editDiscipline(localD);
                disciplineRepositoryOnline.save(d);
            }
        }
        //qualification settings upload
        List<QualificationSettings> qualificationSettingsList = qualificationSettingsDisciplineRepositoryOnline.findAllByDisciplineRaceId(activeRace().getId());
        for (QualificationSettings q :
                qualificationSettingsList) {
            QualificationSettings localQ = qualificationSettingsDisciplineRepository.findQualificationSettingsById(q.getId());
            if(localQ!=null){
                q.editQualificationSettings(localQ);
                qualificationSettingsDisciplineRepositoryOnline.save(q);
            }
        }
    }
}
