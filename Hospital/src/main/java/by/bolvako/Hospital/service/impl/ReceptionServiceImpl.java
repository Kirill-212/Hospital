package by.bolvako.Hospital.service.impl;

import by.bolvako.Hospital.model.Reception;
import by.bolvako.Hospital.repository.ReceptionCrudRepository;
import by.bolvako.Hospital.repository.ReceptionRepository;
import by.bolvako.Hospital.service.ReceptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReceptionServiceImpl implements ReceptionService {
    private static final org.slf4j.Logger log =
            org.slf4j.LoggerFactory.getLogger(ReceptionServiceImpl.class);
    private ReceptionCrudRepository receptionCrudRepository;
    private ReceptionRepository receptionRepository;
    @Autowired
    public ReceptionServiceImpl(ReceptionCrudRepository receptionCrudRepository, ReceptionRepository receptionRepository) {
        this.receptionCrudRepository = receptionCrudRepository;
        this.receptionRepository = receptionRepository;
    }

    @Override
    public Reception Add(Reception reception) {

        return null;
    }

    @Override
    public List<Reception> getAll() {
        return null;
    }

    @Override
    public List<Reception> getByIdDoctor(Long id) {
        return null;
    }

    @Override
    public List<Reception> getByIdPatient(Long id) {
        return null;
    }

    @Override
    public List<Reception> getByDate(String date) {
        return null;
    }

    @Override
    public List<Reception> getByTime(String time) {
        return null;
    }

    @Override
    public List<Reception> getByTimeAndDate(String time, String date) {
        return null;
    }
}
