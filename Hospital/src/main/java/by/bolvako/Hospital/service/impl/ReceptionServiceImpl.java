package by.bolvako.Hospital.service.impl;

import by.bolvako.Hospital.model.Doctor;
import by.bolvako.Hospital.model.Patient;
import by.bolvako.Hospital.model.Reception;
import by.bolvako.Hospital.repository.ReceptionCrudRepository;
import by.bolvako.Hospital.repository.ReceptionRepository;
import by.bolvako.Hospital.service.ReceptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static java.lang.Integer.parseInt;

@Service
@Slf4j
public class ReceptionServiceImpl implements ReceptionService {
    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(ReceptionServiceImpl.class);
    private ReceptionCrudRepository receptionCrudRepository;
    private ReceptionRepository receptionRepository;
    @Autowired
    public ReceptionServiceImpl(ReceptionCrudRepository receptionCrudRepository, ReceptionRepository receptionRepository) {
        this.receptionCrudRepository = receptionCrudRepository;
        this.receptionRepository = receptionRepository;
    }

    @Override
    public Boolean CheckDate(String date) {
        LocalDate date_now = LocalDate.now(); // получаем текущую дату


        LocalDate date_input=LocalDate.of(parseInt(date.substring(0,4),10),
                parseInt(date.substring(5,7),10),parseInt(date.substring(8,date.length()),10));
        DayOfWeek dayOfWeek = date_input.getDayOfWeek();
        log.info("IN CheckDate Reception");
        return !dayOfWeek.toString().equals("SATURDAY") && !dayOfWeek.toString().equals("SUNDAY") &&
                (date_input.compareTo(date_now) > 0);
    }

    @Override
    public Reception Add(Reception reception) {
        Reception reception1=receptionRepository.save(reception);
        log.info("IN register -Reception: {} successfully registered", reception1);
        return reception1;
    }

    @Override
    public List<Reception> getAll() {
        log.info("IN getAll Reception");
        return receptionRepository.findAll();
    }

    @Override
    public List<Reception> getByIdDoctor(Doctor doctor) {
        List<Reception> res=receptionRepository.findByDoctor(doctor);
        if(res.size()>0){
            log.info("IN getByIdDoctor - Reception  found by id: {}", res);
            return  res;
        }
        log.info("IN getByIdDoctor - Reception  null");
        return null;
    }

    @Override
    public Reception findById(Long id) {
        Reception result =receptionRepository.findById(id).orElse(null);
        if (result == null) {
            log.info("IN findById - no Reception found by id: null");
            return null;
        }

        log.info("IN findById - Reception  found by id: {}", result);
        return result;
    }


    @Override
    public List<Reception> getByIdPatient(Patient patient) {
        List<Reception> res=receptionRepository.findByPatient(patient);
        if(res.size()>0){
            log.info("IN getByIdPatient - Reception  found by id: {}", res);
            return  res;
        }
        log.info("IN getByIdPatient - Reception  null");
        return null;
    }

    @Override
    public List<Reception> getByDate(String date) {
        return null;
    }

    @Override
    public List<Reception> getByTime(String time) {
        return null;
    }
    @Override
    public void delete() {
        List<Reception> rec=receptionRepository.findByPatientAndDoctor(null,null);
        for(int i=0;i<rec.size();i++){
            System.out.println(rec.get(i).getId());
            receptionRepository.deleteById(rec.get(i).getId());

        }

        log.info("IN delete - reception successfully deleted");
    }
    @Override
    public List<Reception> getByTimeAndDate(String time, String date) {
        return null;
    }
}
