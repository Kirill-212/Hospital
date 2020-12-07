package by.bolvako.Hospital.service;

import by.bolvako.Hospital.model.Doctor;
import by.bolvako.Hospital.model.User;

import java.util.List;

public interface DoctorService {
    Doctor register(Doctor doctor);

    List<Doctor> getAll();
    Boolean GetUserInDoctor(User user);
    Doctor findById(Long id);
    Long getRoleForId(Long id);
    void delete(Long id);
    void Update(Doctor doctor);
}
