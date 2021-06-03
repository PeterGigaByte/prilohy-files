package fei.stuba.bp.rigo.preteky.web.controllers;

import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.models.sql.ResultStartList;
import fei.stuba.bp.rigo.preteky.service.service.ApResultsService;
import fei.stuba.bp.rigo.preteky.service.service.ClubParticipantsService;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/applications")
public class Applications {
    private RaceService raceService;
    private DisciplineService disciplineService;
    private ApResultsService apResultsService;
    private ClubParticipantsService clubParticipantsService;

    public Applications(RaceService raceService,DisciplineService disciplineService, ApResultsService apResultsService,ClubParticipantsService clubParticipantsService){
        super();
        this.raceService = raceService;
        this.disciplineService = disciplineService;
        this.apResultsService = apResultsService;
        this.clubParticipantsService = clubParticipantsService;
    }
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

    @GetMapping("")
    public String disciplines(Model model){
        List<Discipline> disciplineList = disciplineService.findDisciplinesByRaceId(activeRace().getId());
        Map<Discipline,List<ResultStartList>> map = new LinkedHashMap<>();
        for (Discipline discipline:disciplineList) {
            map.put(discipline,apResultsService.findAllByDisciplineRaceIdAndDisciplineId(activeRace().getId(),discipline.getId()));
        }
        model.addAttribute("disciplines",disciplineList);
        model.addAttribute("startListMap",map);
        model.addAttribute("bibMap",apResultsService.findByRaceIdMap(activeRace().getId()));
        model.addAttribute("clubs",clubParticipantsService.findRealClubs(activeRace().getStartDate(),activeRace().getStartDate(),activeRace().getStartDate()));

        return "applications/applications";
    }
    @GetMapping(value = "/open")
    public String openRace(){
        Race race = activeRace();
        race.setStatus("OPENED");
        raceService.save(race);
        return "redirect:/applications";
    }
    @GetMapping(value = "/close")
    public String closeRace(){
        Race race = activeRace();
        race.setStatus("CLOSED");
        raceService.save(race);
        return "redirect:/applications";
    }


}
