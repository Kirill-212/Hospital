package by.bolvako.Hospital.service;

import by.bolvako.Hospital.model.Doctor;
import by.bolvako.Hospital.model.Patient;
import by.bolvako.Hospital.model.Reception;

import java.util.List;

public interface ReceptionService {
    Reception Add(Reception reception);
    List<Reception> getAll();
    List<Reception> getByIdDoctor(Doctor doctor);
    List<Reception> getByIdPatient(Patient patient);
    Reception findById(Long id);
    List<Reception> getByDate(String date);
    List<Reception> getByTime(String time);
    List<Reception> getByTimeAndDate(String time,String date);
    Boolean CheckDate(String date);
    void delete();
}
