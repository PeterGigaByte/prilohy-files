package fei.stuba.bp.rigo.preteky.models;

import fei.stuba.bp.rigo.preteky.models.sql.Attempt;
import fei.stuba.bp.rigo.preteky.models.sql.ResultStartList;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class FormAttempts {
    private Map<Integer, List<Attempt>> mappedAttempts;
    private Map<Integer, Double> attemptMap;
    private List<ResultStartList> startList;

    public FormAttempts(Map<Integer, List<Attempt>> mappedAttempts, Map<Integer, Double> attemptMap, List<ResultStartList> startList) {
        this.mappedAttempts = mappedAttempts;
        this.attemptMap = attemptMap;
        this.startList = startList;
    }
}
