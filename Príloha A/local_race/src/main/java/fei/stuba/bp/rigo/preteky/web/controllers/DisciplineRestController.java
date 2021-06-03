package fei.stuba.bp.rigo.preteky.web.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import fei.stuba.bp.rigo.preteky.models.sql.QualificationSettings;
import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import fei.stuba.bp.rigo.preteky.service.service.RaceService;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.concurrent.atomic.AtomicInteger;



@RestController
@RequestMapping("/disciplines")
public class DisciplineRestController {
    private DisciplineService disciplineService;
    private RaceService raceService;
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private AtomicInteger at = new AtomicInteger(0);

    public DisciplineRestController(DisciplineService disciplineService, RaceService raceService) {
        this.disciplineService = disciplineService;
        this.raceService = raceService;
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

    /**
     *
     * @param activeRace - aktívna atletická súťaž
     * @return - vracia dátumy aktívneho preteku
     */
    @GetMapping(value = "/dates")
    public List<Date> getDates(@ModelAttribute("activeRace") Race activeRace){
        return  getDatesBetweenUsingJava7(activeRace.getStartDate(),activeRace.getEndDate());
    }

    /**
     *
     * @param activeRace - aktívna atletická súťaž
     * @return - vracia disciplíny aktívneho preteku
     */
    @GetMapping(value ="/disciplineTypes")
    public List<String> getDisciplineTypes(@ModelAttribute("activeRace") Race activeRace){
        return resourcesForRefresh("disciplines");
    }

    /**
     *
     * @param activeRace - aktívna atletická súťaž
     * @return - vracia kategórie aktívneho preteku
     */
    @GetMapping(value ="/categories")
    public List<String> getCategories(@ModelAttribute("activeRace") Race activeRace){
        return resourcesForRefresh("categories");
    }

    /**
     *
     * @param jsonNodes - vstupné parametre, názov disciplíny a kategórie, id disciplíny
     * @return - vracia zoznam disciplín, do ktorých je možné postúpiť zo súčasnej disciplíny
     */
    @PostMapping(value ="/disciplines")
    public List<Discipline> getDisciplinesWithSameName(@RequestBody ObjectNode jsonNodes){
        return disciplineService.findDisciplinesByDisciplineNameAndCategoryAndRaceIdAndIdIsNot(jsonNodes.get("disciplineName").asText(),jsonNodes.get("disciplineCategory").asText(),activeRace().getId(),jsonNodes.get("id").asInt());
    }

    /**
     *
     * @param jsonNodes - vstupné parametre z frontendu - požiadavky
     * @return generuje nám tabuľku podľa typu požiadavky
     * @throws ParseException - výnimka pri parsovaní dátumu
     */
    @PostMapping(value = "/disciplines/table")
    public List<Discipline> tableResource(@RequestBody ObjectNode jsonNodes) throws ParseException {
        java.sql.Date date = new java.sql.Date(format.parse(jsonNodes.get("date").asText()).getTime());
        String discipline = jsonNodes.get("discipline").asText();
        String category = jsonNodes.get("category").asText();
        if((discipline.equals("Disciplína") || discipline.equals("discipline")) && (category.equals("Kategória") ||  category.equals("category")) ){
            System.out.println(date);
            System.out.println(jsonNodes.get("date").asText());
            return disciplineService.findDisciplinesByDisciplineDateAndRaceIdOrderByDisciplineTime(date,activeRace().getId());
        }else if(discipline.equals("Disciplína")){
            return disciplineService.findDisciplinesByDisciplineDateAndRaceIdAndCategoryOrderByDisciplineTime(date,activeRace().getId(),category);
        }else if(category.equals("Kategória")){
            return disciplineService.findDisciplinesByDisciplineDateAndRaceIdAndDisciplineNameOrderByDisciplineTime(date,activeRace().getId(),discipline);
        }else{
            return disciplineService.findDisciplinesByDisciplineDateAndRaceIdAndCategoryAndDisciplineNameOrderByDisciplineTime(date,activeRace().getId(),category,discipline);
        }
    }

    /**
     * Táto funkcia slúži na editovanie a ukladanie Disciplíny a jej nastavení
     * @param jsonNodes - vstupný objekt s parametrami
     * @return - vracia nám výsledok tejto funkcie
     * @throws ParseException - výnimka pri parsovaní dátumu
     */
    @PostMapping(value ="/save")
    public String saveDiscipline(@RequestBody ObjectNode jsonNodes) throws ParseException {
        if(jsonNodes.get("id").asText().equals("")){
            if (alreadyExist(jsonNodes.get("disciplineName").asText(), jsonNodes.get("category").asText(), activeRace().getId(), jsonNodes.get("id").asInt(), jsonNodes.get("phaseName").asText(), jsonNodes.get("phaseNumber").asInt())) {
                java.sql.Date date = new java.sql.Date(format.parse(jsonNodes.get("date").asText()).getTime());
                Integer idCamera=null;
                if(jsonNodes.get("disciplineType").asText().equals("run")){
                    List<Discipline> disciplines= disciplineService.findDisciplinesRaceIdTypeASC(activeRace().getId(),"run");
                    if(!disciplines.isEmpty()){
                        at.set(disciplines.get(0).getCameraId());
                    }else {
                        at.set(0);
                    }
                    idCamera=at.incrementAndGet();
                }
                QualificationSettings qualificationSettings = new QualificationSettings(
                        jsonNodes.get("Q").asInt(),
                        jsonNodes.get("q").asInt());
                if(disciplineService.findDisciplineById(jsonNodes.get("aim").asInt()) != null){
                    qualificationSettings.setDisciplineWhere(jsonNodes.get("aim").asInt());
                }
                Discipline discipline = new Discipline(
                        activeRace(),
                        jsonNodes.get("disciplineName").asText(),
                        jsonNodes.get("note").asText(),
                        jsonNodes.get("disciplineTime").asText(),
                        jsonNodes.get("category").asText(),
                        0,
                        jsonNodes.get("disciplineType").asText(),
                        idCamera,
                        date,
                        jsonNodes.get("phaseName").asText(),
                        jsonNodes.get("phaseNumber").asInt()
                );
                disciplineService.saveDiscipline(discipline);qualificationSettings.setDiscipline(discipline);
                disciplineService.saveQualificationSettings(qualificationSettings);
                return "Post create successfully";
            }else{
                return "Already exist";
            }

        }else if(alreadyExist(jsonNodes.get("disciplineName").asText(), jsonNodes.get("category").asText(), activeRace().getId(), jsonNodes.get("id").asInt(), jsonNodes.get("phaseName").asText(), jsonNodes.get("phaseNumber").asInt())){
            Discipline discipline = disciplineService.findDisciplineById(jsonNodes.get("id").asInt());
            discipline.setDisciplineName(jsonNodes.get("disciplineName").asText());
            discipline.setNote(jsonNodes.get("note").asText());
            discipline.setDisciplineTime(jsonNodes.get("disciplineTime").asText());
            discipline.setCategory(jsonNodes.get("category").asText());
            discipline.setDisciplineType(jsonNodes.get("disciplineType").asText());
            discipline.setPhaseName(jsonNodes.get("phaseName").asText());
            discipline.setPhaseNumber(jsonNodes.get("phaseNumber").asInt());

            QualificationSettings qualificationSettings = disciplineService.findQualificationSettingsByDisciplineId(jsonNodes.get("id").asInt());
            qualificationSettings.setQByPlace( jsonNodes.get("Q").asInt());
            qualificationSettings.setQByTime(jsonNodes.get("q").asInt());
            if(disciplineService.findDisciplineById(jsonNodes.get("aim").asInt()) != null){
                qualificationSettings.setDisciplineWhere(jsonNodes.get("aim").asInt());
            }
            disciplineService.saveDiscipline(discipline);
            qualificationSettings.setDiscipline(discipline);
            disciplineService.saveQualificationSettings(qualificationSettings);
            return "Post edit successfully";
        }else {
            return "Already exist";
        }
    }

    /**
     * Funkcia na zmazanie disciplíny spolu s jej nastaveniami
     * @param jsonNode - vstupné parametre - id disciplíny, ktorú chceme zmazať
     * @return vracia nám oznámenie o úspešnom zmazaní discipliny
     */
    @PostMapping(value ="/delete")
    public String deleteDiscipline(@RequestBody JsonNode jsonNode){
        disciplineService.deleteDiscipline(jsonNode.get("id").asInt());
        return "Post delete Successfully";
    }

    /**
     * Funkcia na editáciu disciplíny - získanie údajov - naplnienie formuláru
     * @param jsonNode - vstupné parametre, id disciplíny
     * @return vracia nám disciplínu s parametrami, ktoré naplnia formulár
     */
    @PostMapping(value = "/disciplineEdit")
    public Discipline getDiscipline(@RequestBody JsonNode jsonNode){
        return disciplineService.findDisciplineById(jsonNode.get("id").asInt());
    }

    /**
     * Funkcia na editáciu nastavení disciplíny - získanie údajov - naplnienie formuláru
     * @param jsonNode - vstupné parametre, id disciplíny
     * @return vracia nám nastavenia s parametrami, ktoré naplnia formulár
     */
    @PutMapping(value = "/disciplineEdit/settings")
    public QualificationSettings getQualificationSettings(@RequestBody JsonNode jsonNode){
        return disciplineService.findQualificationSettingsByDisciplineId(jsonNode.get("id").asInt());
    }
    @DeleteMapping(value = "/empty/delete")
    public void deleteEmptyDisciplines(){
        disciplineService.deleteDisciplineByRaceIdAndParticipantsEquals(activeRace().getId(),0);
    }
    @PutMapping(value = "/camera/numbering")
    public void changeCameraNumbering(){
        disciplineService.changeCameraNumbering(activeRace().getId());
    }


    /**
     * Funkcia, ktorá nám vloží do listu dáta, ktoré nám vytvárajú filter
     * @param type - typ požiadavky na dáta
     * @return vracia list s potrebnými dátami kategórie alebo disciplíny
     */
    private List<String> resourcesForRefresh(String type){
        List<String> arrayList = new ArrayList<>();
        List<Discipline> disciplines = disciplineService.findDisciplinesByRaceId(activeRace().getId());
        if(type.equals("disciplines")){
            arrayList.add("Disciplína");
        }else if(type.equals("categories")){
            arrayList.add("Kategória");
        }
        int counter;
        String temp = null;
        for (Discipline discipline : disciplines){
            counter = 0;
            for (String object : arrayList){
                if(type.equals("categories")){
                    if(discipline.getCategory().equals(object)){
                        counter++;
                    }else{
                        temp = discipline.getCategory();
                    }
                }
                else if(type.equals("disciplines")){
                    if(discipline.getDisciplineName().equals(object)){
                        counter++;
                    }else{
                        temp = discipline.getDisciplineName();
                    }
                }
            }
            if(counter==0){ arrayList.add(temp);}
        }
        return arrayList;
    }

    /**
     * Funkcia na získanie listu dátumov, ktoré sa nachádzaju medzi začiatkom a koncom atletickej súťaže
     * @param startDate - začiatok atletickej súťaže
     * @param endDate - koniec atletickej súťaže
     * @return - vracia list dátumov
     */
    private List<java.util.Date> getDatesBetweenUsingJava7(
            java.sql.Date startDate, java.sql.Date endDate) {
        java.util.Date utilStartDate = new java.util.Date(startDate.getTime());
        java.util.Date utilEndDate = new java.util.Date(endDate.getTime());
        List<java.util.Date> datesInRange = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(utilStartDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(utilEndDate);

        while (calendar.before(endCalendar)) {
            java.util.Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        datesInRange.add(utilEndDate);
        return datesInRange;
    }

    /**
     * Táto funkcia nám zisťuje, či už náhodou taká disciplína s rovnakým názvom,
     * kategóriou, názvom fázy a číslom fázy v rovnakom závode už existuje.
     * @param disciplineName - názov disciplíny
     * @param disciplineCategory - kategória disciplíny
     * @param raceId - id atletickej súťaže
     * @param disciplineId - id disciplíny
     * @param phaseName - názov fázy
     * @param phaseNumber - číslo fázy
     * @return vracia true ak je všetko v poriadku(neexistuje zhoeda),
     * false ak nastal problém, že taká disciplína už existuje
     */
    private Boolean alreadyExist(String disciplineName, String disciplineCategory, int raceId, int disciplineId, String phaseName, int phaseNumber){
        List<Discipline> disciplines = disciplineService.findDisciplinesByDisciplineNameAndCategoryAndRaceIdAndIdIsNotAndPhaseNameAndPhaseNumber(disciplineName,
                disciplineCategory,raceId,disciplineId,phaseName,phaseNumber);
        return disciplines.size() == 0;
    }
}
