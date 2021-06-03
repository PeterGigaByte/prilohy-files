package fei.stuba.bp.rigo.preteky.web.controllers;
import com.fasterxml.jackson.databind.JsonNode;
import fei.stuba.bp.rigo.preteky.csvFilesImplementation.ExportStartList;
import fei.stuba.bp.rigo.preteky.models.sql.*;
import fei.stuba.bp.rigo.preteky.service.service.ApResultsService;
import fei.stuba.bp.rigo.preteky.service.service.ClubParticipantsService;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/applications")
public class ApplicationsRest {
    private RaceService raceService;
    private DisciplineService disciplineService;
    private ApResultsService apResultsService;
    private ClubParticipantsService clubParticipantsService;
    private ExportStartList exportStartList = new ExportStartList();
    private AtomicInteger at = new AtomicInteger(0);

    @ModelAttribute("activeRace")
    public Race activeRace(){
        if(raceService.getActiveRace().size()>0){
            return raceService.getActiveRace().get(0);
        }else{
            return raceService.getFakeRace();
        }
    }
    @ModelAttribute("activePage")
    public String activePage(){
        return "applications";
    }
    public ApplicationsRest(RaceService raceService,DisciplineService disciplineService, ApResultsService apResultsService,ClubParticipantsService clubParticipantsService){
        super();
        this.raceService = raceService;
        this.disciplineService = disciplineService;
        this.apResultsService = apResultsService;
        this.clubParticipantsService = clubParticipantsService;
        exportStartList.createDisciplinesLength();
    }
    @PostMapping(value = "/athletes")
    public List<ClubTransfer> getAthletes(@RequestBody JsonNode jsonNode) {
        int clubId = jsonNode.get("idClub").asInt();
        String gender = jsonNode.get("gender").asText();
        Date date = activeRace().getStartDate();
        return clubParticipantsService.findRealAthletesOfClub(date, date,clubId,gender, date,clubId,gender);
    }
    @PostMapping(value = "/save")
    public String saveAthletes(@RequestBody JsonNode jsonNode) {
        int disciplineId = jsonNode.get(jsonNode.size()-1).get("id").asInt();
        for (int i = 0; i<jsonNode.size()-1; i++){
            int line = jsonNode.get(i).get("Dráha").asInt();
            int bib = jsonNode.get(i).get("Číslo").asInt();
            int idAthlete = jsonNode.get(i).get("Zmazať").asInt();
            //TODO
            String startPerformance = jsonNode.get(i).get("Štartový výkon").asText();
            if(apResultsService.findByAthleteIdAndDisciplineId(idAthlete,disciplineId).isEmpty()){
                ResultStartList resultStartList = new ResultStartList();
                resultStartList.setAthlete(clubParticipantsService.findAthlete(idAthlete));
                Discipline discipline = disciplineService.findDisciplineById(disciplineId);
                resultStartList.setDiscipline(discipline);
                discipline.setParticipants(discipline.getParticipants()+1);
                disciplineService.saveDiscipline(discipline);
                resultStartList.setLine(line);
                //resultStartList.setStartPerformance(startPerformance);
                apResultsService.saveResultStartList(resultStartList);
                Bib bibR = apResultsService.findByRaceIdAndAthleteId(activeRace().getId(),idAthlete);
                Bib bibCheck = apResultsService.findByRaceIdAndBib(activeRace().getId(),bib);
                if (bibR!=null){
                    if(bibCheck!=null && !bibCheck.getId().equals(bibR.getId())){
                        System.out.println("faiulure");
                    }else{
                        bibR.setBib(bib);
                    }
                }else{
                    bibR = new Bib();
                    if(bibCheck!=null && !bibCheck.getId().equals(bibR.getId())){
                        bibR.setBib(0);
                    }else{
                        bibR.setBib(bib);
                    }
                    bibR.setAthlete(resultStartList.getAthlete());
                    bibR.setRace(activeRace());
                }
                apResultsService.saveBib(bibR);
            }
        }
        return "success" ;
    }
    @GetMapping(value = "/bibNumbers")
    public Map<Athlete, Bib> getBibs(){
        return apResultsService.findByRaceIdMap(activeRace().getId());
    }
    @GetMapping("/exportStartList")
    public String exportCsv(){
        int activeRace = activeRace().getId();
        Date date = activeRace().getStartDate();
        exportStartList.setStartList(apResultsService.findAllByDisciplineRaceAndTypeIdMap(activeRace,"run"));
        exportStartList.setBibs(apResultsService.findByRaceIdMap(activeRace));
        exportStartList.setClubs(clubParticipantsService.findRealClubs(date,date,date));
        return exportStartList.createCsv(activeRace);
    }
    @PostMapping(value = "/edit")
    public String editApplications(@RequestBody JsonNode jsonNode){
        for (int i = 0; i<jsonNode.size()-1; i++){
            Integer line=null;
            if(!jsonNode.get(i).get("Line").asText().equals("")){
                line = jsonNode.get(i).get("Line").asInt();
            }
            Integer bib=null;
            if(!jsonNode.get(i).get("Bib").asText().equals("")){
                bib = jsonNode.get(i).get("Bib").asInt();
            }
            int idStartResult = jsonNode.get(i).get("Meno").asInt();
            Double startPerformance = jsonNode.get(i).get("Štartový výkon").asDouble();
            ResultStartList resultStartList = apResultsService.findById(idStartResult);
            if(resultStartList!=null){
                resultStartList.setLine(line);
                if(!resultStartList.getDiscipline().getDisciplineType().equals("run")){
                    resultStartList.setStartPerformance(startPerformance);
                }
                else if(!jsonNode.get(i).get("Štartový výkon").asText().equals(resultStartList.getTimeStartPerformance())){
                    resultStartList.setStartPerformance(startPerformance);
                }
                apResultsService.saveResultStartList(resultStartList);
                Bib bibR = apResultsService.findByRaceIdAndAthleteId(activeRace().getId(),resultStartList.getAthlete().getId());
                Bib bibCheck=null;
                if(bib !=null){
                    bibCheck = apResultsService.findByRaceIdAndBib(activeRace().getId(),bib);
                }
                if (bibR!=null){
                    if(bibCheck!=null && !bibCheck.getId().equals(bibR.getId())){
                        System.out.println("faiulure");
                    }
                    else{
                        bibR.setBib(bib);
                    }
                }else{
                    bibR = new Bib();
                    if(bibCheck!=null && !bibCheck.getId().equals(bibR.getId())){
                        bibR.setBib(0);
                    }else{
                        bibR.setBib(bib);
                    }
                    bibR.setAthlete(resultStartList.getAthlete());
                    bibR.setRace(activeRace());
                }
                apResultsService.saveBib(bibR);
            }
        }
        return "success";
    }
    @PostMapping(value = "/delete/StartList/")
    public String deleteStartList(@RequestBody JsonNode jsonNode){
        apResultsService.deleteStartList(jsonNode.get("id").asInt());
        return "success";
    }
    @PostMapping("/splitAthletes")
    public String splitAthletes(@RequestBody JsonNode jsonNode){
        int disciplineId = jsonNode.get("id").asInt();
        List<ResultStartList> resultStartList = apResultsService.findResultStartListByDisciplineId(disciplineId);
        Discipline discipline = disciplineService.findDisciplineById(disciplineId);
        QualificationSettings qualificationSettings = disciplineService.findQualificationSettingsByDisciplineId(disciplineId);
        Track track = discipline.getRace().getSettings().getTrack();
        List<Discipline> disciplines= disciplineService.findDisciplinesRaceIdTypeASC(activeRace().getId(),"run");
        List<Discipline> disciplinesNumber = disciplineService.findDisciplinesByRaceIdAndCategoryAndPhaseNameAndDisciplineNameOrderByPhaseNumberDesc(discipline.getRace().getId(),discipline.getCategory(),discipline.getPhaseName(),discipline.getDisciplineName());
        List<Discipline> disciplineList = new ArrayList<>();
        disciplineList.add(discipline);
        int maxNum = disciplinesNumber.get(0).getPhaseNumber();
        at.set(disciplines.get(0).getCameraId());
        if(resultStartList.size() > track.getNumberOfTracks()){
            discipline.setParticipants(0);
            int generateDisciplines = resultStartList.size()/track.getNumberOfTracks();
            for (int i = 0; i<generateDisciplines;i++){
                Discipline generatedDiscipline = new Discipline();
                generatedDiscipline.setRace(discipline.getRace());
                generatedDiscipline.setDisciplineType(discipline.getDisciplineType());
                generatedDiscipline.setDisciplineName(discipline.getDisciplineName());
                generatedDiscipline.setDisciplineDate(discipline.getDisciplineDate());
                generatedDiscipline.setCategory(discipline.getCategory());
                generatedDiscipline.setDisciplineTime(discipline.getDisciplineTime());
                generatedDiscipline.setPhaseName(discipline.getPhaseName());
                generatedDiscipline.setCameraId(at.incrementAndGet());
                generatedDiscipline.setParticipants(0);
                generatedDiscipline.setNote(discipline.getNote());
                maxNum++;
                generatedDiscipline.setPhaseNumber(maxNum);
                disciplineService.saveDiscipline(generatedDiscipline);
                QualificationSettings settings = new QualificationSettings();
                settings.setDiscipline(generatedDiscipline);
                settings.setQByTime(qualificationSettings.getQByTime());
                settings.setQByPlace(qualificationSettings.getQByPlace());
                settings.setDisciplineWhere(qualificationSettings.getDisciplineWhere());
                disciplineService.saveQualificationSettings(settings);
                disciplineList.add(generatedDiscipline);
            }
            int counter = 0;
            for (ResultStartList resultStart: resultStartList) {
                if(counter>generateDisciplines){
                    counter = 0;
                }
                resultStart.setDiscipline(disciplineList.get(counter));
                disciplineList.get(counter).setParticipants(disciplineList.get(counter).getParticipants()+1);
                apResultsService.saveResultStartList(resultStart);
                counter++;

            }
            for (Discipline discipline1: disciplineList){
                sortByStarPerformance(track,apResultsService.findResultStartListByDisciplineId(discipline1.getId()));
            }
            //TODO split resultStartList

            System.out.println("success");
        }else{
            sortByStarPerformance(track,resultStartList);
        }
        return "success";
    }
    private void sortByStarPerformance(Track track, List<ResultStartList> resultStartList){
        int i=1;
        for (ResultStartList resultStart: resultStartList) {
            Map<Integer,Integer> lines = track.returnMapOfTracks();
            resultStart.setLine(lines.get(i));
            apResultsService.saveResultStartList(resultStart);
            i++;
        }
    }
}
