package fei.stuba.bp.rigo.preteky.csvFilesImplementation;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import fei.stuba.bp.rigo.preteky.models.sql.Athlete;
import fei.stuba.bp.rigo.preteky.models.sql.Bib;
import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import fei.stuba.bp.rigo.preteky.models.sql.ResultStartList;
import fei.stuba.bp.rigo.preteky.service.service.ApResultsService;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ImportResults {
    private Map<Discipline,List<ResultStartList>> startList;
    private Map<Athlete, Bib> bibs;
    private ApResultsService apResultsService;
    public void readLSTRslt(int activeRace) {
        try{
            String pathToJar = new File("").getAbsolutePath();
            String path = pathToJar+"\\camera\\"+activeRace+"\\LSTRslt.txt";

            CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build(); // custom separator
            try(CSVReader reader = new CSVReaderBuilder(
                    new FileReader(path))
                    .withCSVParser(csvParser)   // custom CSV parser
                    .withSkipLines(1)           // skip the first line, header info
                    .build()){
                List<String[]> r = reader.readAll();
                r.forEach(x -> System.out.println(Arrays.toString(x)));
                r.forEach(x -> {
                    int idCamera = Integer.parseInt(x[2].replaceAll("\\s+",""));
                    int bibNo = Integer.parseInt(x[4].replaceAll("\\s+",""));
                    int lane = Integer.parseInt(x[5].replaceAll("\\s+",""));
                    //int status = Integer.parseInt(x[6].replaceAll("\\s+",""));
                    String time = x[8].replaceAll("\\s+","");
                    int result = Integer.parseInt(x[9].replaceAll("\\s+",""));
                    for (Discipline discipline : startList.keySet()) {
                        System.out.println(idCamera+"; "+discipline.getCameraId());
                        if(idCamera==discipline.getCameraId()){
                            for (ResultStartList resultStartList : startList.get(discipline)){
                                Athlete athlete = resultStartList.getAthlete();
                                if(bibNo==bibs.get(athlete).getBib() && lane==resultStartList.getLine()){
                                    resultStartList.setPlace(result+".");
                                     resultStartList.setResultPerformance(stringTimeToDouble(time));
                                    //resultStartList.setStatus(status);
                                    apResultsService.saveResultStartList(resultStartList);
                                    System.out.println("success");
                                }
                            }
                        }
                    }
                });
            }
        }catch (Exception e){
            System.out.println("function readLSTRslt threw exception: "+"'"+e.getMessage()+"'");
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
