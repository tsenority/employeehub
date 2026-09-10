package project.employeehub.repository;

import project.employeehub.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


    public interface EmployeeRepository
        extends JpaRepository<Employee, Long>  {
        //inheriting functions from jpaRepository  <entity,primary key type>
        // By using JpaRepository, we are asking spring to give all the functionality(database tools like hibernate)
        // for Employee enitity where the ID is a long.
        // and Spring will automatically gives us methods like save, findbyId,findAll,deleteById

    }

