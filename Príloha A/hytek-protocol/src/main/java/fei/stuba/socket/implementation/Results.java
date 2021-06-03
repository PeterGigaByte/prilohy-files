package fei.stuba.socket.implementation;

import fei.stuba.socket.database.models.*;
import fei.stuba.socket.database.repository.BibRepository;
import fei.stuba.socket.database.repository.DisciplineRepository;
import fei.stuba.socket.database.repository.RaceRepository;
import fei.stuba.socket.database.repository.ResultStartListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class Results implements fei.stuba.socket.service.Results {
    @Autowired
    private RaceRepository raceRepository;
    @Autowired
    private ResultStartListRepository resultStartListRepository;
    @Autowired
    private BibRepository bibRepository;
    @Autowired
    private DisciplineRepository disciplineRepository;


    public Results(ResultStartListRepository resultStartListRepository, BibRepository bibRepository, RaceRepository raceRepository,DisciplineRepository disciplineRepository) {
        this.resultStartListRepository = resultStartListRepository;
        this.bibRepository = bibRepository;
        this.raceRepository = raceRepository;
        this.disciplineRepository = disciplineRepository;

    }
    @Override
    public void setResults(fei.stuba.socket.result.Results results){
        /**
         * Získanie aktívneho závodu a overenie či nejaký závod vôbec prebieha
         */
        Race race = getActiveRace();
        if(race != null){
            /**
             * bibMap - Mapa s bib číslami atlétov
             * resultStartLists - štartová listina, podľa zadaného ID disciplíny z kamerového softvéra
             */
            int id = Integer.parseInt(results.getIdRace());
            Map<Athlete, Bib> bibMap = bibRepository.findByRaceIdMap(race.getId());
            List<ResultStartList> resultStartLists = resultStartListRepository.findAllByDisciplineIdOrderByResultPerformanceAsc(id);
            /**
             * V tomto cykle si zoberieme načítané výsledky z kamerového softvéru
             * Následne prehľadáve resultStartList(štartovú listinu) a zapíšeme získané údaje z kamerového softvéru
             * Nakoniec uložíme editovaný resultStartList do databázy
             */
            if(resultStartLists.size() != 0){
                for (ResultStartList resultStartList:resultStartLists) {
                    int counter = 0;
                    for (fei.stuba.socket.result.Result result: results.getResultArrayList()) {
                        if(Integer.parseInt(result.getBib()) == (bibMap.get(resultStartList.getAthlete()).getBib()) && Integer.parseInt(result.getLane())==(resultStartList.getLine())){
                            resultStartList.setPlace(result.getOrd()+".");
                            resultStartList.setResultPerformance(stringTimeToDouble(result.getTime()));
                            resultStartListRepository.save(resultStartList);
                            counter++;
                        }
                    }
                    if(counter==0){
                        resultStartList.setResultPerformance(null);
                        resultStartList.setPlace(null);
                        resultStartListRepository.save(resultStartList);
                    }
                }
                Discipline discipline = disciplineRepository.findDisciplinesById(id);
                discipline.setWind(results.getWind());
                disciplineRepository.save(discipline);
            }
        }
    }

    private Race getActiveRace() {
        List<Race> race = raceRepository.findRegisteredUserByActivity(1);
        if(race.size()==1){
            return raceRepository.findRegisteredUserByActivity(1).get(0);
        }else{
            return null;
        }

    }
    private Double stringTimeToDouble(String time){
        String[] array = time.split(":");
        if(array.length==3){
            double hours = Double.parseDouble(array[0]);
            double minutes = Double.parseDouble(array[1]);
            double seconds = Double.parseDouble(array[2]);
            hours = hours*60;
            minutes = minutes+hours;
            seconds = (minutes*60)+seconds;
            return seconds;
        }else if(array.length==2){
            double minutes = Double.parseDouble(array[0]);
            double seconds = Double.parseDouble(array[1]);
            seconds = (minutes*60)+seconds;
            return seconds;
        }else if(array.length==1){
            return Double.parseDouble(array[0]);
        }
        return null;
    }
}
