package fei.stuba.bp.rigo.preteky.models.sql;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "track")
@Data
@NoArgsConstructor
public class Track implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "number_of_tracks")
    private Integer numberOfTracks = 8;

    @Column(name = "one")
    private Integer one = 6;

    @Column(name = "two")
    private Integer two = 4;

    @Column(name = "three")
    private Integer three = 2;

    @Column(name = "four")
    private Integer four = 1;

    @Column(name = "five")
    private Integer five = 3;

    @Column(name = "six")
    private Integer six = 5;

    @Column(name = "seven")
    private Integer seven = 7;

    @Column(name = "eight")
    private Integer eight = 8;

    @Column(name = "nine")
    private Integer nine;

    @Column(name = "ten")
    private Integer ten;

    @Column(name = "type_track")
    private Integer typeTrack = 1;
    public Map<Integer, Integer> returnMapOfTracks(){
        List<Integer> lines = new ArrayList<>();
        lines.add(one);lines.add(two);lines.add(three);lines.add(four);lines.add(five);lines.add(six);lines.add(seven);lines.add(eight);lines.add(nine);lines.add(ten);
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i<numberOfTracks;i++){
            map.put(lines.get(i),i+1);
        }
        return map;
    }
    public void setNumberOfTracks(Integer numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
        setTracks();
    }

    public Track(Integer numberOfTracks) {
        this.numberOfTracks = numberOfTracks;
        setTracks();
    }
    private void setTracks(){
        switch (numberOfTracks){
            case 1:
                one = 1;
                break;
            case 2:
                one = 1;
                two = 2;
                break;
            case 3:
                one =2;
                two =1;
                three=3;
                break;
            case 4:
                one =3;
                two =2;
                three=1;
                four=4;
                break;
            case 5:
                one =4;
                two =3;
                three=1;
                four=2;
                five=5;
                break;
            case 6:
                one =5;
                two =3;
                three=2;
                four=1;
                five=5;
                six=4;
                break;
            case 7:
                one =6;
                two =5;
                three=3;
                four=1;
                five=2;
                six=4;
                seven=7;
                break;
            case 8:
                one =7;
                two =6;
                three=3;
                four=1;
                five=2;
                six=4;
                seven=5;
                eight=8;
                break;
            case 9:
                one =8;
                two =6;
                three=3;
                four=1;
                five=2;
                six=4;
                seven=5;
                eight=7;
                nine=9;
                break;
            case 10:
                one =8;
                two =7;
                three=3;
                four=2;
                five=1;
                six=4;
                seven=5;
                eight=6;
                nine=9;
                ten=10;
                break;
        }
    }
}
