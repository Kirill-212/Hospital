package by.bolvako.Hospital.repository;

import by.bolvako.Hospital.model.Doctor;
import by.bolvako.Hospital.model.User;
import org.springframework.data.repository.CrudRepository;

public interface DoctorCrudRepository  extends CrudRepository<Doctor,Long> {
}
