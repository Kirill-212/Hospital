package by.bolvako.Hospital.repository;

import by.bolvako.Hospital.model.Doctor;
import by.bolvako.Hospital.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> getAllById(Long id);
    List<Doctor> getAllByUser(User user);
    List<Doctor> findByUser(User user);

}
