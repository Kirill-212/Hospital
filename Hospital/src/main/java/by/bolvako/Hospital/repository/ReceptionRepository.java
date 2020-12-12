package by.bolvako.Hospital.repository;

import by.bolvako.Hospital.model.Doctor;
import by.bolvako.Hospital.model.Patient;
import by.bolvako.Hospital.model.Reception;
import by.bolvako.Hospital.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceptionRepository  extends JpaRepository<Reception, Long> {
   List<Reception> findByDoctor(Doctor doctor);
   List<Reception> findByPatient(Patient patient);
   List<Reception> findByPatientAndDoctor(Patient patient,Doctor doctor);
}
