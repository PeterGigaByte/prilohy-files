package fei.stuba.bp.rigo.preteky.web.controllers;

import fei.stuba.bp.rigo.preteky.csvFilesImplementation.ExportStartList;
import fei.stuba.bp.rigo.preteky.csvFilesImplementation.ImportResults;
import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.service.service.ApResultsService;
import fei.stuba.bp.rigo.preteky.service.service.ClubParticipantsService;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/results")
public class ResultsRest {
    private RaceService raceService;
    private DisciplineService disciplineService;
    private ApResultsService apResultsService;
    private ClubParticipantsService clubParticipantsService;
    private ImportResults importResults = new ImportResults();

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
        return "results";
    }
    public ResultsRest(RaceService raceService,DisciplineService disciplineService, ApResultsService apResultsService,ClubParticipantsService clubParticipantsService){
        super();
        this.raceService = raceService;
        this.disciplineService = disciplineService;
        this.apResultsService = apResultsService;
        this.clubParticipantsService = clubParticipantsService;
    }
    @GetMapping(value = "/importResultsFromCsv")
    public String importResults(){
        int activeRace = activeRace().getId();
        importResults.setStartList(apResultsService.findAllByDisciplineRaceAndTypeIdMap(activeRace,"run"));
        importResults.setBibs(apResultsService.findByRaceIdMap(activeRace));
        importResults.readLSTRslt(activeRace);
        importResults.setApResultsService(apResultsService);
        return "success";
    }
    @PutMapping(value = "/absoluteOrder")
    public String absoluteOrder(){
        int activeRace = activeRace().getId();
        apResultsService.absoluteOrderRun(activeRace);
        return "success";
    }
}
