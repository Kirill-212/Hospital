package by.bolvako.Hospital.service.impl;

import by.bolvako.Hospital.model.Doctor;
import by.bolvako.Hospital.model.Role;
import by.bolvako.Hospital.model.Status;
import by.bolvako.Hospital.model.User;
import by.bolvako.Hospital.repository.DoctorRepository;
import by.bolvako.Hospital.repository.UserRepository;
import by.bolvako.Hospital.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DoctorServiceImpl  implements DoctorService {
    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(DoctorServiceImpl.class);
    private DoctorRepository doctorRepository;
    private UserRepository userRepository;
    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository,UserRepository userRepository){
        this.doctorRepository=doctorRepository;
        this.userRepository=userRepository;
    }

    @Override
    public Doctor register(Doctor doctor) {


        Doctor registeredDoctor = doctorRepository.save(doctor);

        log.info("IN register - Doctor: {} successfully registered", registeredDoctor);

        return registeredDoctor;
    }

    @Override
    public List<Doctor> getAll() {
        List<Doctor> result =doctorRepository.findAll();
        log.info("IN getAll - {} Doctor found", result.size());
        return result;
    }

    @Override
    public Boolean GetUserInDoctor(User user) {

        if(doctorRepository.findByUser(user).size()>0){
            return false;
        }
        return  true;
    }

    @Override
    public Doctor findById(Long id) {
    Doctor result =doctorRepository.findById(id).orElse(null);
        if (result == null) {
            log.warn("IN findById - no Doctor found by id: {}", id);
            return null;
        }

        log.info("IN findById - Doctor: {} found by id: {}", result);
        return result;
    }

    @Override
    public Long getRoleForId(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {
       doctorRepository.deleteById(id);
        log.info("IN delete - doctor with id: {} successfully deleted");
    }
    @Override
    public  void Update(Doctor doctor){
        doctorRepository.save(doctor);
        log.info("IN patient - doctor with id: {} successfully update");
    }
}
