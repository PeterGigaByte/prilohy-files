package fei.stuba.bp.rigo.preteky.web.controllers;
import com.fasterxml.jackson.databind.JsonNode;
import fei.stuba.bp.rigo.preteky.models.sql.*;
import fei.stuba.bp.rigo.preteky.repository.DisciplineRepository;
import fei.stuba.bp.rigo.preteky.service.service.ClubParticipantsService;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import fei.stuba.bp.rigo.preteky.web.controllers.AthletesController;
import fei.stuba.bp.rigo.preteky.models.login.User;
import fei.stuba.bp.rigo.preteky.repository.UsersRepository;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ApplicationRegistration {
    private RaceService raceService;
    private UsersRepository usersRepository;
    private ClubParticipantsService clubParticipantsService;
    private DisciplineService disciplineService;

    public ApplicationRegistration(RaceService raceService, UsersRepository usersRepository, ClubParticipantsService clubParticipantsService, DisciplineService disciplineService) {
        this.raceService = raceService;
        this.usersRepository = usersRepository;
        this.clubParticipantsService = clubParticipantsService;
        this.disciplineService = disciplineService;
    }

    @ModelAttribute("activePage")
    public String activePage(){
        return "appRegistration";
    }
    @ModelAttribute("races")
    public List<Race> activeRace(){
        return raceService.findAllByStatus(Race.Status.OPENED);
    }

    @GetMapping("/appRegistration")
    public String appRegistration(){
        return "appRegistration";
    }

    @GetMapping("/appRegistration/{id}")
    public String appRegistration(@PathVariable int id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        //user
        User user = usersRepository.findUserByUsername(currentPrincipalName);
        //zoznam disciplín
        model.addAttribute("disciplines",disciplineService.findDisciplinesByRaceId(id));
        //zoznam atlétov v klube
        Race race = raceService.getRaceById(id);
        model.addAttribute("club",user.getClub());
        model.addAttribute("race",race);
        model.addAttribute("athletesMen",getAthletes(user.getClub().getId(),"male",race));
        model.addAttribute("athletesWomen",getAthletes(user.getClub().getId(),"female",race));
        return "appRegistrationRegister";
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
    private List<ClubTransfer> getAthletes(int clubId,String gender,Race race) {
        Date date = race.getStartDate();
        return clubParticipantsService.findRealAthletesOfClub(date, date,clubId,gender, date,clubId,gender);
    }
}
