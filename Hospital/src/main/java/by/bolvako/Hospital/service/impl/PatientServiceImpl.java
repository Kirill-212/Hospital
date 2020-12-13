package by.bolvako.Hospital.service.impl;

import by.bolvako.Hospital.model.Doctor;
import by.bolvako.Hospital.model.Patient;
import by.bolvako.Hospital.model.User;
import by.bolvako.Hospital.repository.DoctorRepository;
import by.bolvako.Hospital.repository.PatientRepository;
import by.bolvako.Hospital.service.DoctorService;
import by.bolvako.Hospital.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PatientServiceImpl implements PatientService {
    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(PatientServiceImpl.class);
    private PatientRepository patientRepository;
    @Autowired
    public  PatientServiceImpl(PatientRepository patientRepository){
        this.patientRepository=patientRepository;
    }


    @Override
    public Patient register(Patient patient) {
        Patient registeredPatient = patientRepository.save(patient);

        log.info("IN register - Patient: {} successfully registered", registeredPatient);

        return registeredPatient;

    }
    @Override
    public Long findbyUser(User user) {
        List<Patient> doctors=patientRepository.findByUser(user);
        System.out.println(doctors.size()+" |size patient");
        if(doctors.size()>0){
            log.info("IN findbyUser - patient: {} ", doctors.get(0).getId());
            return  doctors.get(0).getId();
        }
        log.info("IN findbyUser - patient: not found");
        return -2L;
    }
    @Override
    public List<Patient> getAll() {
        List<Patient> result =patientRepository.findAll();
        log.info("IN getAll - {} patient ", result);
        return result;
    }

    @Override
    public Boolean GetUserInPatient(User user) {
        if(patientRepository.findByUser(user).size()>0){
            log.info("IN GetUserInPatient - {} patient", false);
            return false;
        }
        log.info("IN GetUserInPatient - {} patient", true);
        return  true;
    }

    @Override
    public Patient findById(Long id) {
        Patient result =patientRepository.findById(id).orElse(null);
        if (result == null) {
            log.info("IN findById - no patient: found by id: not found");
            return null;
        }

        log.info("IN findById - patient:  found by id: {}", result);
        return result;
    }

    @Override
    public Long getRoleForId(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {
        patientRepository.deleteById(id);
        log.info("IN delete - patient:  successfully deleted");
    }

    @Override
    public  void Update(Patient patient){
        patientRepository.save(patient);
        log.info("IN update - patient :  successfully update");
    }
}
