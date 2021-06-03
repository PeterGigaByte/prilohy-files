package fei.stuba.bp.rigo.preteky.web.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.models.sql.Settings;
import fei.stuba.bp.rigo.preteky.models.sql.Track;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/races")
public class RaceRestController {
    private RaceService raceService;
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public RaceRestController(RaceService raceService) {
        this.raceService = raceService;
    }
    @ModelAttribute("activePage")
    public String activePage(){
        return "races";
    }
    @GetMapping(value = "/all")
    public List<Race> getRacesResource(){return raceService.listRaces();
    }


    @PostMapping(value="/save")
    public String saveRace(@RequestBody ObjectNode jsonNodes) throws Exception {
        Date startDate = new Date(format.parse(jsonNodes.get("startDate").asText()).getTime());
        Date endDate = new Date(format.parse(jsonNodes.get("endDate").asText()).getTime());
        int comparator = startDate.compareTo(endDate);
        System.out.println(comparator);
        if(comparator>0){
            return "Post failed because startDate is after endDate";
        }
        if(!jsonNodes.get("id").asText().equals("")){
            Race race = raceService.findByIdFromRepository(jsonNodes.get("id").asInt());
            race.setRaceName(jsonNodes.get("raceName").asText());
            race.setPlace(jsonNodes.get("place").asText());
            race.setOrganizer(jsonNodes.get("organizer").asText());
            race.setResultsManager(jsonNodes.get("resultsManager").asText());
            race.setPhone(jsonNodes.get("phone").asText());
            race.setStartDate(startDate);
            race.setEndDate(endDate);
            race.setNote(jsonNodes.get("note").asText());
            race.setDirector(jsonNodes.get("director").asText());
            race.setArbitrator(jsonNodes.get("arbitrator").asText());
            race.setTechnicalDelegate(jsonNodes.get("technicalDelegate").asText());
            race.getSettings().setCameraType(jsonNodes.get("cameraType").asText());
            race.getSettings().setTypeScoring(jsonNodes.get("typeScoring").asText());
            race.getSettings().setTypeRace(jsonNodes.get("raceType").asInt());
            race.getSettings().setOutCompetition(jsonNodes.get("outCompetition").asInt());
            race.getSettings().setReactions(jsonNodes.get("reactions").asInt());
            race.getSettings().getTrack().setNumberOfTracks(jsonNodes.get("numberOfTracks").asInt());
            raceService.save(race);
            return "Post update Successfully";
        }
        else {
            Track track = new Track(jsonNodes.get("numberOfTracks").asInt());
            Settings settings = new Settings(
                    jsonNodes.get("cameraType").asText(),
                    jsonNodes.get("raceType").asInt(),
                    jsonNodes.get("typeScoring").asText(),
                    jsonNodes.get("outCompetition").asInt(),
                    jsonNodes.get("reactions").asInt(),
                    track
            );
            Race race = new Race(
                    0,
                    jsonNodes.get("raceName").asText(),
                    jsonNodes.get("place").asText(),
                    jsonNodes.get("organizer").asText(),
                    jsonNodes.get("resultsManager").asText(),
                    jsonNodes.get("phone").asText(),
                    startDate,
                    endDate,
                    jsonNodes.get("raceType").asInt(),
                    jsonNodes.get("note").asText(),
                    jsonNodes.get("director").asText(),
                    jsonNodes.get("arbitrator").asText(),
                    jsonNodes.get("technicalDelegate").asText(),
                    settings
            );
            raceService.save(race);
            return "Post create Successfully";
        }

    }

    @PostMapping(value="/findRace")
    public Race getRaceResource (@RequestBody ObjectNode jsonNodes){
        return raceService.findByIdFromRepository(jsonNodes.get("id").asInt());
    }
    @PostMapping(value="/delete")
    public String deleteRace (@RequestBody ObjectNode jsonNodes){
        raceService.deleteRace(jsonNodes.get("id").asInt());
        return "Post delete Successfully";
    }
}
