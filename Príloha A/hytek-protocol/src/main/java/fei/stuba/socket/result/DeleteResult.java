package fei.stuba.socket.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeleteResult {
    private String idRace;
    private String bib;
    private String lane;
}
