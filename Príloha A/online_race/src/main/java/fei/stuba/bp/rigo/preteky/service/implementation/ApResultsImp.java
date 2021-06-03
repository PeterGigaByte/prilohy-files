package fei.stuba.bp.rigo.preteky.service.implementation;

import fei.stuba.bp.rigo.preteky.models.sql.*;
import fei.stuba.bp.rigo.preteky.repository.*;
import fei.stuba.bp.rigo.preteky.service.service.ApResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ApResultsImp implements ApResultsService {
    @Autowired
    private ResultStartListRepository resultStartListRepository;
    @Autowired
    private BibRepository bibRepository;
    @Autowired
    private DisciplineRepository disciplineRepository;
    @Autowired
    private RaceRepository raceRepository;
    @Autowired
    private AttemptRepository attemptRepository;

    public ApResultsImp(ResultStartListRepository resultStartListRepository,BibRepository bibRepository,RaceRepository raceRepository, AttemptRepository attemptRepository) {
        this.resultStartListRepository = resultStartListRepository;
        this.bibRepository=bibRepository;
        this.raceRepository=raceRepository;
        this.attemptRepository=attemptRepository;
    }
    @Override
    public List<ResultStartList> findResultStartListByRaceId(int id){
        return resultStartListRepository.findAllByDisciplineRaceId(id);
    }

    @Override
    public Map<Athlete, Bib> findByRaceIdMap(int id) {
        return bibRepository.findByRaceIdMap(id);
    }

    @Override
    public List<ResultStartList> findByAthleteIdAndDisciplineId(int idAthlete, int idDiscipline) {
        return resultStartListRepository.findByAthleteIdAndDisciplineId(idAthlete,idDiscipline);
    }

    @Override
    public Bib findByRaceIdAndAthleteId(int raceId, int athleteId) {
        return bibRepository.findByRaceIdAndAthleteId(raceId,athleteId);
    }
    @Override
    public void saveBib(Bib bib) {
        bibRepository.save(bib);
    }
    @Override
    public void saveResultStartList(ResultStartList resultStartList) {
        resultStartListRepository.save(resultStartList);
    }

    @Override
    public List<ResultStartList> findAllByDisciplineRaceIdAndDisciplineId(int idRace, int idDiscipline) {
        return resultStartListRepository.findAllByDisciplineRaceIdAndDisciplineId(idRace,idDiscipline);
    }

    @Override
    public Map<Athlete, ResultStartList> findAllByDisciplineRaceIdMap(int id) {
       return resultStartListRepository.findAllByDisciplineRaceIdMap(id);
    }

    @Override
    public Map<Discipline, List<ResultStartList>> mapResultsToDiscipline(int activeRace) {
        List<Discipline> disciplines = disciplineRepository.findDisciplinesByRaceIdOrderByDisciplineTime(activeRace);
        Map<Discipline,List<ResultStartList>> map = new LinkedHashMap<>();
        for (Discipline discipline:disciplines) {
            map.put(discipline,findAllByDisciplineRaceIdAndDisciplineId(activeRace,discipline.getId()));
        }
        return map;
    }

    @Override
    public Map<Discipline, List<ResultStartList>> findAllByDisciplineRaceAndTypeIdMap(int idRace, String type) {
        List<Discipline> disciplines = disciplineRepository.findDisciplinesByRaceIdAndDisciplineTypeOrderByDisciplineTime(idRace,type);
        Map<Discipline, List<ResultStartList>> map = new LinkedHashMap<>();
        for (Discipline discipline:disciplines) {
            map.put(discipline,findAllByDisciplineRaceIdAndDisciplineId(idRace,discipline.getId()));
        }
        return map;
    }

    @Override
    public ResultStartList findById(int id) {
       return resultStartListRepository.findById(id);
    }

    @Override
    public Bib findByRaceIdAndBib(int id, int bib) {
        return bibRepository.findByRaceIdAndBib(id,bib);
    }
    @Override
    public void deleteStartList(int id) {
        Discipline discipline = resultStartListRepository.findById(id).getDiscipline();
        discipline.setParticipants(discipline.getParticipants()-1);
        disciplineRepository.save(discipline);
        resultStartListRepository.deleteById(id);
    }
    @Override
    public List <ResultStartList> findResultStartListByDisciplineId(int disciplineId){
        return resultStartListRepository.findResultStartListByDisciplineIdOrderByStartPerformanceAsc(disciplineId);
    }

    @Override
    public List<ResultStartList> findAllByDisciplineIdOrderByResultPerformanceAsc(int id) {
        return resultStartListRepository.findAllByDisciplineIdOrderByResultPerformanceAsc(id);
    }

    @Override
    public List<ResultStartList> findAllByAthleteIdOrderByDisciplineDisciplineDateAsc(int athleteId) {
        return resultStartListRepository.findAllByAthleteIdOrderByDisciplineDisciplineDateAsc(athleteId);
    }
    @Override
    public void absoluteOrderRun (int activeRace) {
        List<String> allDisciplines = disciplineRepository.disciplineNames(activeRace);
        List<String> allCategories = disciplineRepository.categories(activeRace);
        List<String> allPhases = disciplineRepository.phases(activeRace);
        Race race = raceRepository.findRaceById(activeRace);
        for (String discipline : allDisciplines) {
            for (String category : allCategories) {
                for (String phase : allPhases) {
                    List<ResultStartList> resultStartLists = resultStartListRepository.findAllByDisciplineRaceIdAndDisciplineCategoryAndDisciplineDisciplineNameAndDisciplinePhaseNameOrderByResultPerformanceAsc(activeRace,category,discipline,phase);
                    if(!resultStartLists.isEmpty()){
                        String type = resultStartLists.get(0).getDiscipline().getDisciplineType();
                        if(!type.equals("run")){
                            resultStartLists = resultStartListRepository.findAllByDisciplineRaceIdAndDisciplineCategoryAndDisciplineDisciplineNameAndDisciplinePhaseNameOrderByResultPerformanceDesc(activeRace,category,discipline,phase);
                        }
                        int order = 1;
                        ResultStartList previous = null;
                        int points = 11;
                        for(ResultStartList result: resultStartLists){
                            if(previous != null && previous.getResultPerformance()!=null && previous.getResultPerformance()!=0 && result.getResultPerformance()!=null && result.getResultPerformance()!=0 && previous.getResultPerformance().equals(result.getResultPerformance())){
                                order--;
                                result.setAbsoluteOrder("="+order +".");
                                if(race.getSettings().getTypeScoring().equals("club_competition") && points >= 1){
                                    result.setPoints(points);
                                    points = pointsDown(points);
                                }
                                resultStartListRepository.save(result);
                                order++;
                            }
                            else if(result.getResultPerformance()!=null && result.getResultPerformance()!=0){
                                result.setAbsoluteOrder(order +".");
                                order++;
                                if(race.getSettings().getTypeScoring().equals("club_competition") && points >= 1){
                                    result.setPoints(points);
                                    points = pointsDown(points);
                                }
                                resultStartListRepository.save(result);
                            }else{
                                if(race.getSettings().getTypeScoring().equals("club_competition") && points >= 1){
                                    result.setPoints(null);
                                }
                                result.setAbsoluteOrder(null);
                                resultStartListRepository.save(result);
                            }
                            previous = result;
                        }
                    }
                }
            }
        }
    }
    @Override
    public void clearResults(ResultStartList resultStartList){
        resultStartList.setResultPerformance(null);
        resultStartList.setPlace(null);
        resultStartList.setAbsoluteOrder(null);
        resultStartList.setPoints(null);
        resultStartListRepository.save(resultStartList);
    }
    private int pointsDown(int points){
        if (points==11){
            points=points-2;
        }
        else{
            points--;
        }
        return points;
    }
    @Override
    public List<ResultStartList> findResultStartListByDisciplineIdOrderByResultPerformanceDesc(int id) {
        return resultStartListRepository.findResultStartListByDisciplineIdOrderByResultPerformanceDesc(id);
    }
    @Override
    public Integer getAttemptsNumber(Discipline discipline){
        Integer attempts = attemptRepository.countByResultStartListDisciplineId(discipline.getId());
        Integer athletes = resultStartListRepository.countByDisciplineId(discipline.getId());
        if(athletes == 0){
            return 0;
        }
        return attempts/athletes;
    }
    @Override
    public void createAttempts (Discipline discipline, Integer numAttempts){
        if (numAttempts == null || numAttempts>10 || numAttempts < 0){
            return;
        }
        if(discipline != null && !discipline.getDisciplineType().equals("run")){
            Integer attempts = attemptRepository.countByResultStartListDisciplineId(discipline.getId());
            Integer athletes = resultStartListRepository.countByDisciplineId(discipline.getId());
            if(athletes == 0){
                return;
            }
            if(attempts/athletes!=numAttempts){
                List <ResultStartList> resultStartList = resultStartListRepository.findAllResultStartListByDisciplineId(discipline.getId());
                if(!resultStartList.isEmpty()){
                    for (ResultStartList resultStartListContent : resultStartList){
                        Integer attemptsOfResult = attemptRepository.countByResultStartListId(resultStartListContent.getId());
                        if(!attemptsOfResult.equals(numAttempts)){
                            if(numAttempts>attemptsOfResult){
                                while (!numAttempts.equals(attemptsOfResult)){
                                    attemptRepository.save(new Attempt(resultStartListContent));
                                    attemptsOfResult++;
                                }
                            }else{
                                List<Attempt> attemptList = attemptRepository.findAllByResultStartListIdOrderByIdAttemptDesc(resultStartListContent.getId());
                                while (!numAttempts.equals(attemptsOfResult)){
                                    attemptRepository.delete(attemptList.get(0));
                                    attemptList.remove(0);
                                    attemptsOfResult--;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @Override
    public Map<Integer,List<Attempt>> getAttemptMapping(List<ResultStartList> resultStartLists){
        if(resultStartLists.isEmpty()){
            return null;
        }
        Map<Integer,List<Attempt>> map = new LinkedHashMap<>();
        for (ResultStartList resultStartList: resultStartLists){
            map.put(resultStartList.getId(),attemptRepository.findAllByResultStartListIdOrderByIdAttemptAsc(resultStartList.getId()));
        }
        return map;
    }
    @Override
    public Attempt getAttempt(Integer id){
        return attemptRepository.findByIdAttempt(id);
    }
    @Override
    public void saveAttempt(Attempt attempt){
        attemptRepository.save(attempt);
    }
    @Override
    public void orderTechnicalDiscipline(Integer disciplineId){
        List<ResultStartList> resultStartList = resultStartListRepository.findAllResultStartListByDisciplineId(disciplineId);
        for (ResultStartList resultStartList1 : resultStartList){
            resultStartList1.setResultPerformance( attemptRepository.findAllByResultStartListIdOrderByPerformanceDesc(resultStartList1.getId()).get(0).getPerformance());
            resultStartListRepository.save(resultStartList1);
        }
        resultStartList = this.findResultStartListByDisciplineIdOrderByResultPerformanceDesc(disciplineId);
        orderPlace(resultStartList);
    }
    @Override
    public void orderPlace(List<ResultStartList> resultStartLists){
        int order = 1;
        ResultStartList previous = null;
        for (ResultStartList resultStartList: resultStartLists) {
            if(previous != null && previous.getResultPerformance()!=null && resultStartList.getResultPerformance()!=null && previous.getResultPerformance().equals(resultStartList.getResultPerformance()) && resultStartList.getResultPerformance()!=null && resultStartList.getResultPerformance()!=0.0){
                order--;
                resultStartList.setPlace(+order +"=.");
                order++;
            }
            else if(resultStartList.getResultPerformance()!=null && resultStartList.getResultPerformance()!=0.0){
                resultStartList.setPlace(order+".");
                order++;
            }else{
                resultStartList.setPlace(null);
            }

            previous = resultStartList;
            this.saveResultStartList(resultStartList);
        }
    }

}
