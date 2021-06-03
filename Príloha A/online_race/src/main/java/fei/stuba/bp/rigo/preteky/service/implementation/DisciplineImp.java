package fei.stuba.bp.rigo.preteky.service.implementation;

import fei.stuba.bp.rigo.preteky.models.sql.Discipline;
import fei.stuba.bp.rigo.preteky.models.sql.QualificationSettings;
import fei.stuba.bp.rigo.preteky.repository.DisciplineRepository;
import fei.stuba.bp.rigo.preteky.repository.QualificationSettingsDisciplineRepository;
import fei.stuba.bp.rigo.preteky.service.service.DisciplineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

@Service
@Transactional
public class DisciplineImp implements DisciplineService {

    @Autowired
    private DisciplineRepository disciplineRepository;
    @Autowired
    private QualificationSettingsDisciplineRepository qualificationSettingsDisciplineRepository;
    public DisciplineImp(DisciplineRepository disciplineRepository, QualificationSettingsDisciplineRepository qualificationSettingsDisciplineRepository){
        super();
        this.disciplineRepository=disciplineRepository;
        this.qualificationSettingsDisciplineRepository= qualificationSettingsDisciplineRepository;
    }


    @Override
    public List<Discipline> findDisciplinesByRaceId(Integer id) {
        return disciplineRepository.findDisciplinesByRaceIdOrderByDisciplineTime(id);
    }

    @Override
    public List<Discipline> findDisciplinesByDisciplineNameAndCategoryAndRaceIdAndIdIsNot(String disciplineName,String category,int idRace, int idDiscipline) {
        return disciplineRepository.findDisciplinesByDisciplineNameAndCategoryAndRaceIdAndIdIsNot(disciplineName,category,idRace,idDiscipline);
    }

    @Override
    public List<Discipline> findDisciplinesByDisciplineNameAndCategoryAndRaceIdAndIdIsNotAndPhaseNameAndPhaseNumber(String disciplineName, String category, int idRace, int idDiscipline, String phaseName, int phaseNumber) {
        return disciplineRepository.findDisciplinesByDisciplineNameAndCategoryAndRaceIdAndIdIsNotAndPhaseNameAndPhaseNumber(disciplineName,category,idRace,idDiscipline,phaseName,phaseNumber);
    }

    @Override
    public List<Discipline> findDisciplinesByRaceIdOrderByCameraIdDesc(int id) {
        return disciplineRepository.findDisciplinesByRaceIdOrderByCameraIdDesc(id);
    }

    @Override
    public List<Discipline> findDisciplinesByDisciplineDateAndRaceIdOrderByDisciplineTime(Date date, int id) {
        return disciplineRepository.findDisciplinesByDisciplineDateAndRaceIdOrderByDisciplineTime(date,id);
    }

    @Override
    public List<Discipline> findDisciplinesByDisciplineDateAndRaceIdAndDisciplineNameOrderByDisciplineTime(Date disciplineDate, int race_id, String disciplineName) {
        return disciplineRepository.findDisciplinesByDisciplineDateAndRaceIdAndDisciplineNameOrderByDisciplineTime(disciplineDate,race_id,disciplineName);
    }

    @Override
    public List<Discipline> findDisciplinesByDisciplineDateAndRaceIdAndCategoryOrderByDisciplineTime(Date disciplineDate, int race_id, String category) {
        return disciplineRepository.findDisciplinesByDisciplineDateAndRaceIdAndCategoryOrderByDisciplineTime(disciplineDate, race_id, category);
    }

    @Override
    public List<Discipline> findDisciplinesByDisciplineDateAndRaceIdAndCategoryAndDisciplineNameOrderByDisciplineTime(Date disciplineDate, int race_id, String category, String disciplineName) {
        return disciplineRepository.findDisciplinesByDisciplineDateAndRaceIdAndCategoryAndDisciplineNameOrderByDisciplineTime(disciplineDate,race_id,category,disciplineName);
    }

    @Override
    public void saveDiscipline(Discipline discipline) {
        disciplineRepository.save(discipline);
    }

    @Override
    public QualificationSettings findQualificationSettingsByDisciplineId(int id) {
        return qualificationSettingsDisciplineRepository.findQualificationSettingsByDisciplineId(id);
    }

    @Override
    public void saveQualificationSettings(QualificationSettings qualificationSettings){
        qualificationSettingsDisciplineRepository.save(qualificationSettings);
    }
    @Override
    public void deleteDiscipline(int id){
        disciplineRepository.deleteById(id);
    }

    @Override
    public void deleteDisciplineByRaceIdAndParticipantsEquals(int raceId, int participants) {
        disciplineRepository.deleteDisciplineByRaceIdAndParticipantsEquals(raceId,participants);

    }
    @Override
    public Discipline findDisciplineById(int id) {
        return disciplineRepository.findDisciplinesById(id);
    }
    @Override
    public void changeCameraNumbering(int raceId){
        List<Discipline> disciplines = disciplineRepository.findDisciplinesByRaceIdAndDisciplineTypeOrderByDisciplineTime(raceId,"run");
        int i = 1;
        for (Discipline d : disciplines) {
            d.setCameraId(i);
            disciplineRepository.save(d);
            i++;
        }
    }
    public List<Discipline> findDisciplinesRaceIdTypeASC(int id,String type){
        return disciplineRepository.findDisciplinesByRaceIdAndDisciplineTypeOrderByCameraIdDesc(id,type);
    }

    @Override
    public List<Discipline> findDisciplinesByRaceIdAndCategoryAndPhaseNameAndDisciplineNameOrderByPhaseNumberDesc(int raceId, String category, String phaseName, String disciplineName) {
        return disciplineRepository.findDisciplinesByRaceIdAndCategoryAndPhaseNameAndDisciplineNameOrderByPhaseNumberDesc(raceId,category,phaseName,disciplineName);
    }


}
