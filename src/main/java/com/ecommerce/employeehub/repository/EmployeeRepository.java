package com.ecommerce.employeehub.repository;

import com.ecommerce.employeehub.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public class EmployeeRepository {
    public interface employeeRepository
            extends JpaRepository<Employee, Long>  {
        //inheriting functions from jpaRepository  <entity,primary key type>
        // By using JpaRepository, we are asking spring to give all the functionality(database tools like hibernate)
        // for Employee enitity where the ID is a long.
        // and Spring will automatically gives us methods like save, findbyId,findAll,deleteById

    }
}
