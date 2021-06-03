package fei.stuba.bp.rigo.preteky.models.sql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

@Entity
@Table(name = "result_start_list")
@Data
public class ResultStartList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_athlete",referencedColumnName = "id")
    private Athlete athlete;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_discipline",referencedColumnName = "id")
    private Discipline discipline;

    @Column(name = "start_performance")
    private Double startPerformance;

    @Column(name = "result_performance")
    private Double resultPerformance;

    @Column(name = "place")
    private String place;

    @Column(name = "line")
    private Integer line;

    @Column(name = "reaction")
    private Double reaction;

    @Column(name = "absolute_result_performance")
    private String absoluteOrder;

    @Column(name = "points")
    private Integer points;

    @Column(name = "status")
    private String status;

    public String getReactions(){
        if(reaction!=null){
            return String.format("%.3f", reaction);
        }
        else{
            return null;
        }

    }
    public String  getTimeStartPerformance(){
       return timeFormattedReturn(startPerformance);
    }
    public String  getTimeResultPerformance(){
        return timeFormattedReturn(resultPerformance);
    }
    private String timeFormattedReturn(Double input){
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat)nf;
        df.applyPattern("0.00");

        df.setMaximumFractionDigits(2);
        if(input==null || input==0.0){
            return null;
        }else if(input < 86400.0) {
            double timeS = input;
            int hours=0;
            int minutes=0;
            while (timeS>59.99){
                if (timeS>3599.99){
                    hours++;
                    timeS=timeS-3600.0;
                }else if(timeS>59.99){
                    minutes++;
                    timeS = timeS-60.0;
                }
            }
            if(hours!=0){
                if(minutes==0){
                    if(timeS<10.0){
                        return hours+":0"+minutes+":0"+df.format(timeS);
                    }
                    return hours+":0"+minutes+":"+df.format(timeS);
                }
                if(timeS<10.0){
                    return hours+":"+minutes+":0"+df.format(timeS);
                }
                return hours+":"+minutes+":"+df.format(timeS);
            }else if(minutes!=0){
                if(minutes<9){
                    if(timeS<10.0){
                        return "0"+minutes+":0"+df.format(timeS);
                    }
                    return "0"+minutes+":"+df.format(timeS);
                }
                if(timeS<10.0){
                    return minutes+":0"+df.format(timeS);
                }
                return minutes+":"+df.format(timeS);
            }else{
                return String.valueOf(df.format(timeS));
            }
        }else{
            return "Too big";
        }
    }
}
