package by.bolvako.Hospital.repository;

import by.bolvako.Hospital.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    Role findRolesById(Long id);

}

