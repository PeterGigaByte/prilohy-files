package fei.stuba.bp.rigo.preteky.models.sql;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * kluby
 */
@Entity
@Table(name = "club")
@Data
@NoArgsConstructor
public class Club implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "club_name")
    private String clubName;

    @Column(name = "responsible_person")
    private String responsiblePerson;

    @Column(name = "shortcut_club_name")
    private String shortcutClubName;

    @Column(name = "residence")
    private String residence;

    @Column(name = "logo")
    private String logo;

    @Column(name = "date_created")
    private Date dateCreated;


    @Transient
    public String getLogoImage(){
        if(logo==null || id == null){return null;}
        return "/logos/" +id+"/"+logo;
    }



}
