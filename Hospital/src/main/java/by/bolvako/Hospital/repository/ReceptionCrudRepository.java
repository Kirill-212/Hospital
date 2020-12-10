package by.bolvako.Hospital.repository;

import by.bolvako.Hospital.model.Reception;
import by.bolvako.Hospital.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceptionCrudRepository extends JpaRepository<Reception, Long> {

}
