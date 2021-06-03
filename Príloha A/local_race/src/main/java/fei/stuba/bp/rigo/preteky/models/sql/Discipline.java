package fei.stuba.bp.rigo.preteky.models.sql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

/**
 * disciplína
 */
@Entity
@Table(name = "discipline")
@Data
public class Discipline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "race_id",referencedColumnName = "id")
    private Race race;

    @Column(name = "discipline_name")
    private String disciplineName;

    @Column(name = "note")
    private String note;

    @Column(name = "discipline_time")
    private String disciplineTime;

    @Column(name = "category")
    private String category;

    @Column(name = "participants")
    private Integer participants;

    /**
     * 0 - behy
     * 1 - výška,žrď
     * 2 - diaľka,hody atď
     */
    @Column(name = "discipline_type")
    private String disciplineType;

    @Column(name = "camera_id")
    private Integer cameraId;

    @Column(name = "discipline_date")
    private Date disciplineDate;

    @Column(name = "phase_name")
    private String phaseName;

    @Column(name = "phase_number")
    private int phaseNumber;

    @Column(name = "wind")
    private String wind;



    public Discipline() {
    }

    public Discipline(Race race, String disciplineName, String note, String disciplineTime, String category, Integer participants, String disciplineType, Integer cameraId, Date disciplineDate, String phaseName, int phaseNumber) {
        this.race = race;
        this.disciplineName = disciplineName;
        this.note = note;
        this.disciplineTime = disciplineTime;
        this.category = category;
        this.participants = participants;
        this.disciplineType = disciplineType;
            this.cameraId = cameraId;
        this.disciplineDate = disciplineDate;
        this.phaseName = phaseName;
        this.phaseNumber = phaseNumber;
    }
    public String getDayMonth(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(disciplineDate);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        return String.valueOf(day)+'.'+ month +'.';
    }
    public String getDateForCSV(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(disciplineDate);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        int year = cal.get(Calendar.YEAR);
        return String.valueOf(day)+'.'+ month +'.'+String.valueOf(year);
    }
    public void editDiscipline(Discipline discipline){
        this.disciplineName = discipline.getDisciplineName();
        this.note = discipline.getNote();
        this.disciplineTime = discipline.getDisciplineTime();
        this.category = discipline.getCategory();
        this.participants = discipline.getParticipants();
        this.disciplineType = discipline.getDisciplineType();
        this.cameraId = discipline.getCameraId();
        this.disciplineDate = discipline.disciplineDate;
        this.phaseName = discipline.getPhaseName();
        this.phaseNumber = discipline.getPhaseNumber();
        this.wind = discipline.getWind();
    }
}
