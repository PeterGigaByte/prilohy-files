package fei.stuba.bp.rigo.preteky.models.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "club_transfer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubTransfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transfer", nullable = false)
    private Integer idTransfer;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_athlete",referencedColumnName = "id")
    private Athlete athlete;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_club",referencedColumnName = "id")
    private Club club;

    @Column(name = "since")
    private Date since;

    @Column(name = "to_date")
    private Date to;

    @Column(name = "reason")
    private String reason;

}
