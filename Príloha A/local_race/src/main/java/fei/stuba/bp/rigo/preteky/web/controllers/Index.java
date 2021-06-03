package fei.stuba.bp.rigo.preteky.web.controllers;

import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@Controller
public class Index {
    private RaceService raceService;

    public Index(RaceService raceService,DisciplineService disciplineService){
        super();
        this.raceService = raceService;
    }


    @ModelAttribute("activePage")
    public String activePage(){
        return "races";
    }
    @ModelAttribute("races")
    public List<Race> races(){
        return  raceService.listRaces();
    }

    @ModelAttribute("activeRace")
    public Race activeRace(){
        if(raceService.getActiveRace().size()>0){
            return raceService.getActiveRace().get(0);
        }else{
            return raceService.getFakeRace();
        }
    }
    @GetMapping("/contact")
    public String contact(){

        return "contact/contact";
    }
    @GetMapping("/")
    public  String index(@ModelAttribute("activeRace")
                                     Race activeRace){
        return "index/index";
    }
    @GetMapping("/activeRace/{id}")
    public String activeRace(@PathVariable Integer id) {
        Optional<Race> race = raceService.getRace(id);
        race.ifPresent(value -> raceService.changeActivity(value));
        return "redirect:/";
    }



}
