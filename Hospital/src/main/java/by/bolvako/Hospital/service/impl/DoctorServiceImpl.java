package by.bolvako.Hospital.service.impl;

import by.bolvako.Hospital.model.Doctor;
import by.bolvako.Hospital.model.Role;
import by.bolvako.Hospital.model.Status;
import by.bolvako.Hospital.model.User;
import by.bolvako.Hospital.repository.*;
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
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private ReceptionRepository receptionRepository;
    private ReceptionCrudRepository receptionCrudRepository;
    @Autowired
    public DoctorServiceImpl(RoleRepository roleRepository,ReceptionCrudRepository receptionCrudRepository,ReceptionRepository receptionRepository,DoctorRepository doctorRepository,UserRepository userRepository){
        this.doctorRepository=doctorRepository;
        this.userRepository=userRepository;
        this.receptionRepository=receptionRepository;
        this.receptionCrudRepository=receptionCrudRepository;
        this.roleRepository=roleRepository;
    }

    @Override
    public Doctor register(Doctor doctor) {
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleRepository.findByName("ROLE_DOCTOR"));

        doctor.getUser().setRoles(userRoles);
        Doctor registeredDoctor = doctorRepository.save(doctor);

        log.info("IN register - Doctor: {} successfully registered", registeredDoctor);

        return registeredDoctor;
    }

    @Override
    public List<Doctor> getAll() {
        List<Doctor> result =doctorRepository.findAll();
        log.info("IN getAll - {} Doctor found", result);
        return result;
    }

    @Override
    public Boolean GetUserInDoctor(User user) {

        if(doctorRepository.findByUser(user).size()>0){
            log.info("IN GetUserInDoctor - Doctor: {} ", false);
            return false;
        }
        log.info("IN GetUserInDoctor - Doctor: {} ", true);
        return  true;
    }

    @Override
    public Doctor findById(Long id) {
    Doctor result =doctorRepository.findById(id).orElse(null);
        if (result == null) {
            log.info("IN findById - no Doctor found by id: not found");
            return null;
        }

        log.info("IN findById - Doctor found by id: {}", result);
        return result;
    }

    @Override
    public Long findbyUser(User user) {
        List<Doctor> doctors=doctorRepository.findByUser(user);
        System.out.println(doctors.size()+" |size doctor");
        if(doctors.size()>0){
            log.info("IN findbyUser - Doctor: {} ", doctors.get(0).getId());
            return  doctors.get(0).getId();
        }
        log.info("IN findbyUser - Doctor: not found");
        return -2L;
    }

    @Override
    public Long getRoleForId(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

       doctorRepository.deleteById(id);
       doctorRepository.flush();
        log.info("IN delete - doctor with id: successfully deleted");
    }
    @Override
    public  void Update(Doctor doctor){
        doctorRepository.save(doctor);

        log.info("IN update - doctor with id: successfully update");
    }
}
