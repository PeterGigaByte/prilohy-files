package fei.stuba.bp.rigo.preteky.models.sql;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * nastavenia pretekov
 */
@Entity
@Table(name = "settings")
@Data
@NoArgsConstructor
public class Settings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * nastavenia dráh - radenie do dráh a počet dráh
     */
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_Track",referencedColumnName = "id")
    private Track track;

    @Column(name = "camera_type", nullable = false)
    private String cameraType = "OMEGA";

    /**
     * Pretek je v hale alebo vonku?
     * 0 neni
     * 1 je
     */
    @Column(name = "type_race", nullable = false)
    private Integer typeRace = 1;

    /**
     * typ bodovania, môže byť napríklad
     * súťaž družstiev
     * kde získava pretekár body pre klub
     */
    @Column(name = "type_scoring", nullable = false)
    private String typeScoring = "žiadne";

    /**
     * ak bude mimo súťaž, čo s ním?
     * TRUE - bude posledný vždy 0
     * FALSE - bude rátaný ako normálny pretekár 1
     */
    @Column(name = "out_competition", nullable = false)
    private Integer outCompetition = 1;

    /**
     * merajú sa aj reakcie?
     * TRUE - ano 1
     * FALSE - nie 0
     */
    @Column(name = "reactions", nullable = false)
    private Integer reactions = 0;


    public Settings(String cameraType, Integer typeRace, String typeScoring, Integer outCompetition, Integer reactions, Track track) {
        this.cameraType = cameraType;
        this.typeRace = typeRace;
        this.typeScoring = typeScoring;
        this.outCompetition = outCompetition;
        this.reactions = reactions;
        this.track=track;
    }

}
