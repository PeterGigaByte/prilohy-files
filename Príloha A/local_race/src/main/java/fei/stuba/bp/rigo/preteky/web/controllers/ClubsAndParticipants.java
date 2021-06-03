package fei.stuba.bp.rigo.preteky.web.controllers;

import fei.stuba.bp.rigo.preteky.models.sql.Club;

import fei.stuba.bp.rigo.preteky.service.service.ClubParticipantsService;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.ArrayUtils;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import static javax.xml.bind.DatatypeConverter.parseInt;

@Controller
public class ClubsAndParticipants {
    private RaceService raceService;
    private DisciplineService disciplineService;
    private ClubParticipantsService clubParticipantsService;
    public ClubsAndParticipants(RaceService raceService,DisciplineService disciplineService, ClubParticipantsService clubParticipantsService){
        super();
        this.raceService = raceService;
        this.disciplineService = disciplineService;
        this.clubParticipantsService = clubParticipantsService;
    }
    @ModelAttribute("activePage")
    public String activePage(){
        return "database";
    }
    @ModelAttribute("newClub")
    public Club newClub(){
        return new Club();
    }
    @ModelAttribute("clubs")
    public List<Club> listClubs(){
        return clubParticipantsService.getAllClubs();
    }

    @GetMapping("/clubs")
    public String clubs()  {
        return "participants&clubs/clubs";
    }
    @GetMapping("/athletes")
    public String participants(Model model){
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        model.addAttribute("athletes",clubParticipantsService.findAllAthletes());
        model.addAttribute("clubTransfer",clubParticipantsService.findRealClubs(date,date,date));
        return "participants&clubs/participants";
    }

    @PostMapping("/clubs/create")
    public String clubCreated(
                              @RequestParam("id") String  id,
                              @RequestParam("clubName") String  clubName,
                              @RequestParam("shortcutClubName") String shortcutClubName,
                              @RequestParam("responsiblePerson") String  responsiblePerson,
                              @RequestParam("residence") String  residence,
                              @RequestParam("logoImage") MultipartFile multiPartFile,
                              RedirectAttributes re) throws IOException{
        if(id.equals("")){
        String logoName = StringUtils.cleanPath(Objects.requireNonNull(multiPartFile.getOriginalFilename()));
        Club club = new Club();
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        club.setLogo(logoName);
        club.setClubName(clubName);
        club.setDateCreated(date);
        club.setResponsiblePerson(responsiblePerson);
        club.setResidence(residence);
        club.setShortcutClubName(shortcutClubName);
        clubParticipantsService.clubSave(club);
        String uploadDir = "./src/main/resources/static/logos/" + club.getId();
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }try{
            InputStream inputStream = multiPartFile.getInputStream();
            Path filePath = uploadPath.resolve(logoName);
            System.out.println(filePath.toString());
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw new IOException("Logo sa nepodarilo uložiť" + logoName);
        }
        re.addFlashAttribute("message","Klub bol úspešne uložený.");
        }else{
            Club club = clubParticipantsService.findClubById(parseInt(id));
            club.setClubName(clubName);
            club.setShortcutClubName(shortcutClubName);
            club.setResidence(residence);
            club.setResponsiblePerson(responsiblePerson);
            clubParticipantsService.clubSave(club);
        }
        return "redirect:/clubs";
    }


}
