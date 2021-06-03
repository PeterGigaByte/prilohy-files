package fei.stuba.bp.rigo.preteky.models.sql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "attempt")
public class Attempt implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_attempt", nullable = false)
    private Integer idAttempt;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_result_start_list",referencedColumnName = "id")
    private ResultStartList resultStartList;

    @Column(name = "performance")
    private Double performance;

    @Column(name = "num")
    private Integer num;

    public Attempt() {
    }

    public Attempt(ResultStartList resultStartList) {
        this.resultStartList = resultStartList;
    }
}
