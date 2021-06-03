package fei.stuba.bp.rigo.preteky.models.sql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "qualification_settings_discipline")
public class QualificationSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "id_discipline_where")
    private Integer disciplineWhere;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_discipline",referencedColumnName = "id")
    private Discipline discipline;

    @Column(name = "Q_by_place")
    private Integer qByPlace;

    @Column(name = "q_by_time")
    private Integer qByTime;

    public QualificationSettings() {
    }

    public QualificationSettings(Integer disciplineWhere, Integer qByPlace, Integer qByTime) {
        this.disciplineWhere = disciplineWhere;
        this.qByPlace = qByPlace;
        this.qByTime = qByTime;
    }

    public QualificationSettings(Integer qByPlace, Integer qByTime) {
        this.qByPlace = qByPlace;
        this.qByTime = qByTime;
    }
}
