package fei.stuba.bp.rigo.preteky.web.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import fei.stuba.bp.rigo.preteky.models.sql.Athlete;
import fei.stuba.bp.rigo.preteky.models.sql.Club;
import fei.stuba.bp.rigo.preteky.models.sql.ClubTransfer;
import fei.stuba.bp.rigo.preteky.service.service.ClubParticipantsService;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/athletes")
public class AthletesRest {
    private ClubParticipantsService clubParticipantsService;
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public AthletesRest(ClubParticipantsService clubParticipantsService) {
        this.clubParticipantsService = clubParticipantsService;
    }
    @ModelAttribute("activePage")
    public String activePage(){
        return "database";
    }
    @PostMapping("/save")
    public String saveAthlete(@RequestBody JsonNode jsonNode) throws ParseException {
        if(jsonNode.get("id").asText().equals("")){
            Date date = new Date(format.parse(jsonNode.get("birth").asText()).getTime());
            Athlete athlete = new Athlete();
            athlete.setFirstName(jsonNode.get("firstName").asText());
            athlete.setSurname(jsonNode.get("surname").asText());
            athlete.setDateBirth(date);
            athlete.setSex(jsonNode.get("gender").asText());
            clubParticipantsService.saveAthlete(athlete);
            if(!jsonNode.get("club").asText().equals("") && jsonNode.get("club").asText() != null && !jsonNode.get("club").asText().equals("null")){
                ClubTransfer clubTransfer = new ClubTransfer();
                clubTransfer.setAthlete(athlete);
                clubTransfer.setClub(clubParticipantsService.findClubById(jsonNode.get("club").asInt()));
                long millis=System.currentTimeMillis();
                clubTransfer.setSince(new Date(millis));
                clubTransfer.setReason("Zaregistrovanie atléta");
                clubParticipantsService.saveClubTransfer(clubTransfer);
            }
        }
        return "Successs";
    }
    @PostMapping("/getAll")
    public List<Athlete> getAllAthletes(){
        return clubParticipantsService.findAllAthletes();
    }
    @PostMapping("/delete")
    public String deleteAthlete(@RequestBody JsonNode jsonNode){
        clubParticipantsService.deleteAthlete(jsonNode.get("id").asInt());
        return "Successs";
    }
    @GetMapping("/getAllClubs")
    public List<Club> getAllClubs(){
        return clubParticipantsService.getAllClubs();
    }
    @GetMapping("/getTransfers")
    public Map<Athlete,ClubTransfer> getTransfers(){
        long millis=System.currentTimeMillis();
        Date date = new Date(millis);
       return clubParticipantsService.findRealClubs(date,date,date);
    }
    @PostMapping("/{id}/saveTransfer")
    public String saveTransfer(@PathVariable int id,@RequestBody JsonNode jsonNode) throws ParseException {
       //načítať údaje
        Club club = clubParticipantsService.findClubById(jsonNode.get("idClub").asInt());
        String reason = jsonNode.get("reason").asText();
        String dateTo = jsonNode.get("to").asText();
        if(club!=null){
            Athlete athlete = clubParticipantsService.findAthlete(id);
            Date since = new Date(format.parse(jsonNode.get("since").asText()).getTime());
            Map<Athlete,ClubTransfer> validation = clubParticipantsService.findRealClubsOfAthlete(since,since,id,since,id);
            if(validation.size()==0){
                ClubTransfer clubTransfer = new ClubTransfer();
                clubTransfer.setAthlete(athlete);
                clubTransfer.setClub(club);
                clubTransfer.setSince(since);
                if(!dateTo.equals("")){
                    clubTransfer.setTo(new Date(format.parse(dateTo).getTime()));
                }

                clubTransfer.setReason(reason);
                clubParticipantsService.saveClubTransfer(clubTransfer);
            }
        }

        return "Successs";
    }
    @PostMapping("/{id}/deleteTransfer")
    public String deleteTransfer(@PathVariable int id,@RequestBody JsonNode jsonNode) {
        clubParticipantsService.deleteTransfer(jsonNode.get("id").asInt());
        return "success";
    }

}
