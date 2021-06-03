package fei.stuba.bp.rigo.preteky.models.sql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "relay")
@Data
public class Relay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_athlete",referencedColumnName = "id")
    private Athlete athlete;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_result_start_list",referencedColumnName = "id")
    private ResultStartList resultStartList;

    @Column(name = "sector")
    private Integer sector;

}
