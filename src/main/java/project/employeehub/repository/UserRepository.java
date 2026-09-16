package project.employeehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.employeehub.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
