package fei.stuba.bp.rigo.preteky.web.controllers;

import fei.stuba.bp.rigo.preteky.models.sql.*;
import fei.stuba.bp.rigo.preteky.service.service.ApResultsService;
import fei.stuba.bp.rigo.preteky.service.service.ClubParticipantsService;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/spectator")
public class SpectatorController {
    private RaceService raceService;
    private DisciplineService disciplineService;
    private ApResultsService apResultsService;
    private ClubParticipantsService clubParticipantsService;


    public SpectatorController(RaceService raceService, DisciplineService disciplineService, ApResultsService apResultsService, ClubParticipantsService clubParticipantsService) {
        this.raceService = raceService;
        this.disciplineService = disciplineService;
        this.apResultsService = apResultsService;
        this.clubParticipantsService = clubParticipantsService;
    }
    @ModelAttribute("activePage")
    public String activePage(){
        return "spectator";
    }

    @GetMapping(value = "/races")
    public String showRaces(Model model){
        model.addAttribute("races",raceService.listRaces());
        return "spectator/listRaces";
    }
    @GetMapping(value = "/races/app/{id}")
    public String showApplications(@PathVariable int id,Model model){
        List<Discipline> disciplineList = disciplineService.findDisciplinesByRaceId(id);
        Map<Discipline,List<ResultStartList>> map = new LinkedHashMap<>();
        for (Discipline discipline:disciplineList) {
            map.put(discipline,apResultsService.findAllByDisciplineRaceIdAndDisciplineId(id,discipline.getId()));
        }
        model.addAttribute("disciplines",disciplineList);
        model.addAttribute("startListMap",map);
        model.addAttribute("bibMap",apResultsService.findByRaceIdMap(id));
        Race race = raceService.getRaceById(id);
        model.addAttribute("race",race);
        model.addAttribute("clubs",clubParticipantsService.findRealClubs(race.getStartDate(),race.getStartDate(),race.getStartDate()));
        return "spectator/app";
    }
    @GetMapping(value = "/races/res/{id}")
    public String showResults(@PathVariable int id,Model model){
        List<Discipline> disciplineList = disciplineService.findDisciplinesByRaceId(id);
        Map<Discipline,List<ResultStartList>> map = new LinkedHashMap<>();
        for (Discipline discipline:disciplineList) {
            map.put(discipline,apResultsService.findAllByDisciplineRaceIdAndDisciplineId(id,discipline.getId()));
        }
        Race race = raceService.getRaceById(id);
        int span = 4;
        if(race.getSettings().getTypeScoring().equals("club_competition")){
            span++;
        }
        if(race.getSettings().getReactions()==1){
            span++;
        }
        model.addAttribute("span",span);
        model.addAttribute("disciplines",disciplineList);
        model.addAttribute("startListMap",map);
        model.addAttribute("bibMap",apResultsService.findByRaceIdMap(id));

        model.addAttribute("race",race);
        model.addAttribute("clubs",clubParticipantsService.findRealClubs(race.getStartDate(),race.getStartDate(),race.getStartDate()));
        return "spectator/res";
    }
    @GetMapping(value = "/athlete/{id}")
    public String showAthlete(@PathVariable int id,Model model){
            Athlete athlete = clubParticipantsService.findAthlete(id);
            model.addAttribute("athlete",athlete);
            model.addAttribute("actualClub",getClubForAthlete(athlete));
            model.addAttribute("clubs",clubParticipantsService.findClubTransferByAthleteId(id));
            model.addAttribute("results",apResultsService.findAllByAthleteIdOrderByDisciplineDisciplineDateAsc(id));
            return "spectator/athlete";
    }
    private ClubTransfer getClubForAthlete(Athlete athlete){
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        ClubTransfer clubTransfer = clubParticipantsService.findRealClubs(date,date,date).get(athlete);
        if(clubTransfer==null){
            clubTransfer = new ClubTransfer();
            Club club = new Club();
            club.setClubName("Bez klubu");
            clubTransfer.setClub(club);
        }
        return clubTransfer;
    }
}

