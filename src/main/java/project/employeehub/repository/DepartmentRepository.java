package project.employeehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.employeehub.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    }
