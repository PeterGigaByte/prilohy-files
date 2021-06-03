package fei.stuba.bp.rigo.preteky.service.service;


import fei.stuba.bp.rigo.preteky.models.sql.Race;
import fei.stuba.bp.rigo.preteky.models.sql.Settings;
import fei.stuba.bp.rigo.preteky.models.sql.Track;


import java.util.List;
import java.util.Optional;

public interface RaceService {
    void save(Race race);
    void edit(Race race);
    List<Race> listRaces();
    Optional<Race> getRace(int id);
    List<Race> getActiveRace();
    void deleteRace(Integer id);
    void changeActivity(Race race);
    Race getRaceById(Integer id);
    Race getFakeRace();
    Race findByIdFromRepository(Integer id);
    void editRealRace(Race race, Settings settings, Track track);

}
