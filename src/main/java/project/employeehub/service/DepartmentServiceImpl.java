package project.employeehub.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.employeehub.dto.DepartmentRequest;
import project.employeehub.dto.DepartmentResponse;
import project.employeehub.entity.Department;
import project.employeehub.repository.DepartmentRepository;

import java.util.List;


@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;


    @Override
    //This is a very important method because it shows the complete DTO → Entity → Database → Response DTO flow.
    public DepartmentResponse createDepartment(DepartmentRequest departmentRequest) {

        Department department = new Department();
        //Create = make a new object → save it
        //Update = find existing object → change it → save it

        department.setName(departmentRequest.getName());

        Department savedDepartment = departmentRepository.save(department);

        DepartmentResponse response = new DepartmentResponse();

        response.setId(savedDepartment.getId());
        response.setName(savedDepartment.getName());

        return response;

    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {

        List<Department> departments = departmentRepository.findAll();

        List<DepartmentResponse> responses = new java.util.ArrayList<>();

        for (Department department : departments) {

            DepartmentResponse response = new DepartmentResponse();

            response.setId(department.getId());
            response.setName(department.getName());

            responses.add(response);
        }

        return responses;
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow();

        DepartmentResponse response = new DepartmentResponse();

        response.setId(department.getId());
        response.setName(department.getName());

        return response;
    }

    @Override
    public DepartmentResponse updateDepartment(
            Long id,
            DepartmentRequest departmentRequest) {

        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow();

        existingDepartment.setName(departmentRequest.getName());

        Department savedDepartment =
                departmentRepository.save(existingDepartment);

        DepartmentResponse response = new DepartmentResponse();

        response.setId(savedDepartment.getId());
        response.setName(savedDepartment.getName());

        return response;
    }
    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);

    }
}
