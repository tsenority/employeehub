package project.employeehub.service;

import project.employeehub.dto.DepartmentRequest;
import project.employeehub.dto.DepartmentResponse;
import project.employeehub.entity.Department;

import java.util.List;

public interface DepartmentService {

    //CRUD METHODS --same as employee service class.
    DepartmentResponse createDepartment(DepartmentRequest departmentRequest);

    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse getDepartmentById(Long id);

    DepartmentResponse updateDepartment(Long id, DepartmentRequest departmentRequest);


    void deleteDepartment(Long id);
}
