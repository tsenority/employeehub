package project.employeehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.employeehub.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);
    //findByUsername
    //     ↓
    //"Find User where username = ?"--spring does it naturally.

}
