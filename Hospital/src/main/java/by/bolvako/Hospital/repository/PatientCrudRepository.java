package by.bolvako.Hospital.repository;

import by.bolvako.Hospital.model.Patient;
import by.bolvako.Hospital.model.User;
import org.springframework.data.repository.CrudRepository;

public interface PatientCrudRepository extends CrudRepository<Patient,Long> {
}
