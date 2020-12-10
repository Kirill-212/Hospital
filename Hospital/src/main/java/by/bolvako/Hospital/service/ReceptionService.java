package by.bolvako.Hospital.service;

import by.bolvako.Hospital.model.Reception;

import java.util.List;

public interface ReceptionService {
    Reception Add(Reception reception);
    List<Reception> getAll();
    List<Reception> getByIdDoctor(Long id);
    List<Reception> getByIdPatient(Long id);
    List<Reception> getByDate(String date);
    List<Reception> getByTime(String time);
    List<Reception> getByTimeAndDate(String time,String date);
    
}
