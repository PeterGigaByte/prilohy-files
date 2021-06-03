package fei.stuba.bp.rigo.preteky.web.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import fei.stuba.bp.rigo.preteky.models.sql.Club;
import fei.stuba.bp.rigo.preteky.service.service.ClubParticipantsService;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clubs")
public class ClubsRest {
    private ClubParticipantsService clubParticipantsService;
    public ClubsRest(ClubParticipantsService clubParticipantsService) {

        this.clubParticipantsService = clubParticipantsService;
    }
    @ModelAttribute("activePage")
    public String activePage(){
        return "database";
    }
    @PostMapping("/delete")
    public String deleteClub(@RequestBody JsonNode jsonNode){
        clubParticipantsService.deleteClub(jsonNode.get("id").asInt());
        return "Success";
    }
    @GetMapping("/getAllClubs")
    public List<Club> getAllClubs(){
        return clubParticipantsService.getAllClubs();
    }
    @PostMapping("/getClub")
    public Club getClub(@RequestBody JsonNode jsonNode){
        return clubParticipantsService.findClubById(jsonNode.get("id").asInt());
    }

}
