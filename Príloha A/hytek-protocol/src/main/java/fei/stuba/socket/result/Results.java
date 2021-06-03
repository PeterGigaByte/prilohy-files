package fei.stuba.socket.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Results {
    private String idRace;
    private String status;
    private String wind;
    private List<Result> resultArrayList = new ArrayList<>();
}
