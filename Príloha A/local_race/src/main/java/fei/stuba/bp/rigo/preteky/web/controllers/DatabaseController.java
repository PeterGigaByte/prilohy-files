package fei.stuba.bp.rigo.preteky.web.controllers;

import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.online.repo.RaceRepositoryOnline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/databases")
public class DatabaseController {
    @Autowired
    private RaceRepositoryOnline raceRepositoryOnline;

    public DatabaseController(RaceRepositoryOnline raceRepositoryOnline) {
        this.raceRepositoryOnline = raceRepositoryOnline;
    }
    @ModelAttribute("activePage")
    public String activePage(){
        return "database";
    }
    @ModelAttribute("races")
    public List<Race> races(){
        return  raceRepositoryOnline.findAll();
    }
    @GetMapping("")
    public String contact(){
        return "databases/onlineDatabase";
    }
}
