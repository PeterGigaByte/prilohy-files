package fei.stuba.bp.rigo.preteky.web.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fei.stuba.bp.rigo.preteky.models.login.User;
import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.models.sql.ResultStartList;
import fei.stuba.bp.rigo.preteky.service.service.ApResultsService;
import fei.stuba.bp.rigo.preteky.service.service.ClubParticipantsService;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApplicationRegistrationRestController {
     private ClubParticipantsService clubParticipantsService;
     private DisciplineService disciplineService;
     private ApResultsService apResultsService;

    public ApplicationRegistrationRestController(ClubParticipantsService clubParticipantsService, DisciplineService disciplineService, ApResultsService apResultsService) {
        this.clubParticipantsService = clubParticipantsService;
        this.disciplineService = disciplineService;
        this.apResultsService = apResultsService;
    }
    @PostMapping("/appRegistration/{id}/register")
    public String appRegistration(@PathVariable int id, @RequestBody ObjectNode jsonNode){
        int athlete = jsonNode.get("athlete").asInt();
        int discipline = jsonNode.get("discipline").asInt();
        if(athlete == 0 || discipline == 0){
            return "failure";
        }
        Discipline discipline1 = disciplineService.findDisciplineById(discipline);
        if(discipline1.getRace().getId()!=id){
            return "failure";
        }
        if(discipline1.getRace().getStatus().equals("CLOSED")){
            return "failure";
        }
        if(apResultsService.findByAthleteIdAndDisciplineId(athlete,discipline).size()==0){
            ResultStartList resultStartList = new ResultStartList();
            resultStartList.setDiscipline(discipline1);
            resultStartList.setAthlete(clubParticipantsService.findAthlete(athlete));
            apResultsService.saveResultStartList(resultStartList);
            discipline1.setParticipants(discipline1.getParticipants()+1);
            disciplineService.saveDiscipline(discipline1);
            return "success";
        }else{
            return "error-alreadyExist";
        }
    }
    @DeleteMapping("/appRegistration/{id}/delete")
    public String appDelete(@PathVariable int id, @RequestBody ObjectNode jsonNode){
        int athlete = jsonNode.get("athlete").asInt();
        int discipline = jsonNode.get("discipline").asInt();
        //overenie či už neni zaregistrovaný
        if(athlete == 0 || discipline == 0){
            return "failure";
        }
        Discipline discipline1 = disciplineService.findDisciplineById(discipline);
        if(discipline1.getRace().getStatus().equals("CLOSED")){
            return "failure";
        }
        if(discipline1.getRace().getId()!=id){
            return "failure";
        }
        List<ResultStartList> resultStartList = apResultsService.findByAthleteIdAndDisciplineId(athlete,discipline);
        if(resultStartList.size()==1){
            apResultsService.deleteStartList(resultStartList.get(0).getId());
            discipline1.setParticipants(discipline1.getParticipants()-1);
            disciplineService.saveDiscipline(discipline1);
            return "success";
        }
        else{
            return "error-NotExist";
        }
    }
}
