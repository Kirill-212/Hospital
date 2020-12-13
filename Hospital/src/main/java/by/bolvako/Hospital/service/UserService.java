package by.bolvako.Hospital.service;

import by.bolvako.Hospital.dto.SearchPatientDto;
import by.bolvako.Hospital.model.User;

import java.util.List;

public interface UserService {
    User register(User user);

    List<User> getAll();

    User findByEmail(String username);
    List<User> GetByFandL(String f, String l);
    User findById(Long id);
    Long getRoleForId(Long id);
    void delete(Long id);

}
