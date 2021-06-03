package fei.stuba.bp.rigo.preteky.web.controllers;


import fei.stuba.bp.rigo.preteky.models.sql.Race;

import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


@Controller
public class Disciplines {
    private RaceService raceService;
    private DisciplineService disciplineService;

    public Disciplines(RaceService raceService,DisciplineService disciplineService){
        super();
        this.raceService = raceService;
        this.disciplineService = disciplineService;
    }
    @ModelAttribute("activePage")
    public String activePage(){
        return "disciplines";
    }
    @ModelAttribute("activeRace")
    public Race activeRace(){
        if(raceService.getActiveRace().size()>0){
            return raceService.getActiveRace().get(0);
        }else{
           return raceService.getFakeRace();
        }
    }
    @GetMapping("/disciplines")
    public String disciplines(@ModelAttribute("activeRace") Race activeRace){
        return "disciplines/disciplines"; }

}
