package by.bolvako.Hospital.service;

import by.bolvako.Hospital.model.Doctor;
import by.bolvako.Hospital.model.Patient;
import by.bolvako.Hospital.model.User;

import java.util.List;

public interface PatientService  {
    Patient register(Patient patient);

    List<Patient> getAll();
    Boolean GetUserInPatient(User user);
    Patient findById(Long id);
    Long getRoleForId(Long id);
    void delete(Long id);
    void Update(Patient patient);
    Long findbyUser(User user);
}