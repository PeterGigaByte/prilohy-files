package fei.stuba.socket.database.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "relay")
@Data
public class Relay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_athlete",referencedColumnName = "id")
    private Athlete athlete;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_result_start_list",referencedColumnName = "id")
    private ResultStartList idResultStartList;

    @Column(name = "sector")
    private Integer sector;

}
