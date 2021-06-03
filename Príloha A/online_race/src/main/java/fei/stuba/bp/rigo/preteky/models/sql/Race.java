package fei.stuba.bp.rigo.preteky.models.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

/**
 * zoznam pretekov
 */
@Entity
@Table(name = "race")
@Data
@NoArgsConstructor
public class Race implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_settings", referencedColumnName = "id")
    private Settings settings;

    /**
     * aktívny pretek
     */
    @Column(name = "activity", nullable = false)
    private Integer activity = 0;

    @Column(name = "race_name", nullable = false)
    private String raceName;

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "organizer")
    private String organizer;

    @Column(name = "results_manager", nullable = false)
    private String resultsManager;

    @Column(name = "phone")
    private String phone;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;
    public enum Status {
        OPENED, CLOSED
    }
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.CLOSED;

    public void setStatus(String statusIn){
        this.status= Status.valueOf(statusIn);
    }
    public String getStatus(){
        return this.status.name();
    }
    /**
     * typ preteku-
     * True - vonku
     * False - hala
     * default- vonku
     */

    @Column(name = "note")
    private String note;

    @Column(name = "director")
    private String director;

    @Column(name = "arbitrator")
    private String arbitrator;

    @Column(name = "technical_delegate")
    private String technicalDelegate;

    public Race(Integer activity, String raceName, String place, String organizer,
                String resultsManager, String phone, Date startDate, Date endDate,
                Integer raceType, String note, String director, String arbitrator,
                String technicalDelegate,Settings settings) {
        super();
        this.activity = activity;
        this.raceName = raceName;
        this.place = place;
        this.organizer = organizer;
        this.resultsManager = resultsManager;
        this.phone = phone;
        this.startDate = startDate;
        this.endDate = endDate;

        this.note = note;
        this.director = director;
        this.arbitrator = arbitrator;
        this.technicalDelegate = technicalDelegate;
        this.settings=settings;
    }
    public String returnStartDate(){
        return returnDate(this.startDate);
    }
    public String returnEndDate(){
        return returnDate(this.endDate);
    }
    public String returnDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day= cal.get(Calendar.DAY_OF_MONTH);
        int month= cal.get(Calendar.MONTH)+1;
        int year= cal.get(Calendar.YEAR);
        return String.valueOf(day)+"."+String.valueOf(month)+"."+String.valueOf(year);
    }
    public void setRaceEdit(Race race) {
        this.activity = race.activity;
        this.raceName = race.raceName;
        this.place = race.place;
        this.organizer = race.organizer;
        this.resultsManager = race.resultsManager;
        this.phone = race.phone;
        this.startDate = race.startDate;
        this.endDate = race.endDate;

        this.note = race.note;
        this.director = race.director;
        this.arbitrator = race.arbitrator;
        this.technicalDelegate = race.technicalDelegate;
        this.settings=race.settings;
    }


    @Override
    public String toString() {
        return "Race{" +
                "id=" + id +
                ", settings=" + settings +
                ", activity=" + activity +
                ", raceName='" + raceName + '\'' +
                ", place='" + place + '\'' +
                ", organizer='" + organizer + '\'' +
                ", resultsManager='" + resultsManager + '\'' +
                ", phone='" + phone + '\'' +
                ", startDate=" + returnStartDate() +
                ", endDate=" + returnEndDate() +
                ", note='" + note + '\'' +
                ", director='" + director + '\'' +
                ", arbitrator='" + arbitrator + '\'' +
                ", technicalDelegate='" + technicalDelegate + '\'' +
                '}';
    }
}
