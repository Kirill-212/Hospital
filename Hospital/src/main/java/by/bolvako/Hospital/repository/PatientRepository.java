package by.bolvako.Hospital.repository;

import by.bolvako.Hospital.model.Doctor;
import by.bolvako.Hospital.model.Patient;
import by.bolvako.Hospital.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> getAllById(Long id);
    List<Patient> getAllByUser(User user);
    List<Patient> findByUser(User user);
}
