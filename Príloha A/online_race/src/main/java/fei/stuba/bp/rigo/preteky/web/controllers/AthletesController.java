package fei.stuba.bp.rigo.preteky.web.controllers;

import fei.stuba.bp.rigo.preteky.models.sql.Athlete;
import fei.stuba.bp.rigo.preteky.models.sql.Club;
import fei.stuba.bp.rigo.preteky.models.sql.ClubTransfer;
import fei.stuba.bp.rigo.preteky.service.service.ApResultsService;
import fei.stuba.bp.rigo.preteky.service.service.ClubParticipantsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/athletes")
public class AthletesController {
    private ClubParticipantsService clubParticipantsService;
    private ApResultsService apResultsService;

    public AthletesController(ClubParticipantsService clubParticipantsService,ApResultsService apResultsService) {
        this.clubParticipantsService = clubParticipantsService;
        this.apResultsService = apResultsService;
    }
    @ModelAttribute("activePage")
    public String activePage(){
        return "database";
    }
    @GetMapping(value = "/{id}")
    public String athleteDetails(@PathVariable Integer id, Model model) {
        Athlete athlete = clubParticipantsService.findAthlete(id);
        model.addAttribute("athlete",athlete);
        model.addAttribute("actualClub",getClubForAthlete(athlete));
        model.addAttribute("clubs",clubParticipantsService.findClubTransferByAthleteId(id));
        model.addAttribute("results",apResultsService.findAllByAthleteIdOrderByDisciplineDisciplineDateAsc(id));
        return "participants&clubs/athlete";
    }
    @PostMapping(value = "/{id}/saveAthlete")
    public String saveAthlete(@ModelAttribute Athlete athlete, Model model) {
        clubParticipantsService.saveAthlete(athlete);
        model.addAttribute("actualClub",getClubForAthlete(athlete));
        model.addAttribute("clubs",clubParticipantsService.findClubTransferByAthleteId(athlete.getId()));
        return "participants&clubs/athlete";
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
