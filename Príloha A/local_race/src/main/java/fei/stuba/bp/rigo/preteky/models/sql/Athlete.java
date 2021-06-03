package fei.stuba.bp.rigo.preteky.models.sql;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

/**
 * pretekár
 */
@Entity
@Table(name = "athlete")
@Data
public class Athlete implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "date_birth")
    private Date dateBirth;

    @Column(name = "sex")
    private String sex;

    public String returnBirth(){
        return returnDate(this.dateBirth);
    }

    private String returnDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day= cal.get(Calendar.DAY_OF_MONTH);
        int month= cal.get(Calendar.MONTH)+1;
        int year= cal.get(Calendar.YEAR);
        return day +"."+ month +"."+ year;
    }



}
