package fei.stuba.bp.rigo.preteky.csvFilesImplementation;

import fei.stuba.bp.rigo.preteky.models.sql.*;

import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Data
@NoArgsConstructor
public class ExportStartList {
    private Map<Discipline,List<ResultStartList>> startList;
    private Map<Athlete, ClubTransfer> clubs;
    private Map<Athlete, Bib> bibs;
    private Map<String,String > length;

    public void createDisciplinesLength(){
        String[] disciplines = {"40 m", "50 m", "60 m","100 m","150 m","200 m","400 m",
                "500 m", "600 m", "800 m", "1500 m", "2000 m", "3000 m", "5000 m", "10 000 m",
                "5 km","10 km", "15 km", "20 km", "50 km", "polmaratón", "maratón", "hodinovka",
                "60 m pr. 106,7", "60 y pr.","50 m pr. 83,8", "50 m p 76,2-7,5", "60 m pr. 106,7",
                "60 m pr. 99,1", "60 m pr. 91,4", "60 m pr. 83,8", "60 m p. 76,2-8,5", "chôdza 3000 m",
                "chôdza 5000 m", "5 km chôdza", "10 km chôdza","20 km chôdza", "35 km chôdza","50 km chôdza"};
        String[] lengths = {"40", "50", "60","100","150","200","400",
                "500", "600", "800", "1500", "2000", "3000", "5000", "10000",
                "5000","10000", "15000", "20000", "50000", "21000", "42000", "10000",
                "60", "60","50", "50", "60",
                "60", "60", "60", "60", "3000",
                "5000", "5000", "10000","20000", "35000","50000"};
        length = new LinkedHashMap<>();
        int i = 0;
        for (String discipline:disciplines) {
            length.put(discipline,lengths[i]);
            i++;
        }
    }
    public String createCsv(int activeRace){
        try{
            String pathToJar = new File("").getAbsolutePath();
            Path direct = Paths.get(pathToJar+"\\camera\\"+activeRace);

            //java.nio.file.Files;
            Files.createDirectories(direct);

            String path = pathToJar+"\\camera\\"+activeRace+"\\STARTLIST.csv";
            FileOutputStream file = new FileOutputStream(path);
            OutputStreamWriter fileWriter = new OutputStreamWriter(file,"Cp1250");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Event Code;Date;Time;Lane/order;Bib No\r\n");
            for (Discipline discipline : startList.keySet()){
                stringBuilder.append(discipline.getCameraId()).append(";");
                stringBuilder.append(discipline.getDateForCSV()).append(";");
                stringBuilder.append(discipline.getDisciplineTime()).append(";;;;;;");
                stringBuilder.append(length.get(discipline.getDisciplineName())).append(";");
                stringBuilder.append(discipline.getDisciplineName()).append(' ').append(discipline.getCategory()).append(' ').append(discipline.getPhaseName()).append(' ').append(discipline.getPhaseNumber()).append(";;");
                stringBuilder.append("Peter Rigo").append("\r\n");

                for (ResultStartList resultStartList : startList.get(discipline)){
                    Athlete athlete = resultStartList.getAthlete();
                    stringBuilder.append(";;;");
                    stringBuilder.append(resultStartList.getLine()).append(";");
                    stringBuilder.append(bibs.get(athlete).getBib()).append(";");
                    stringBuilder.append(athlete.getSurname()).append(";");
                    stringBuilder.append(athlete.getFirstName()).append(";");
                    stringBuilder.append(clubs.get(athlete).getClub().getShortcutClubName()).append("\r\n");
                }
            }
            fileWriter.write(stringBuilder.toString());
            fileWriter.close();
            return "Success";
        }catch (Exception e){
            System.out.println("function createCsv threw exception: "+"'"+e.getMessage()+"'");
            return "Failure";
        }
    }
}
