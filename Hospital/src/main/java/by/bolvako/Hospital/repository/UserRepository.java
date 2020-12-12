package by.bolvako.Hospital.repository;

import by.bolvako.Hospital.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Optional<User> findById(Long id);
    List<User> findByFirstNameAndLastName(String first,String last);
}

