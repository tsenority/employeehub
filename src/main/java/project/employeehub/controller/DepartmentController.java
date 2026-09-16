package project.employeehub.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.employeehub.dto.DepartmentRequest;
import project.employeehub.dto.DepartmentResponse;
import project.employeehub.entity.Department;
import project.employeehub.service.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public DepartmentResponse createDepartment(
            @RequestBody DepartmentRequest departmentRequest) {

        return departmentService.createDepartment(departmentRequest);

    }


    @GetMapping
    public List<DepartmentResponse> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public DepartmentResponse getDepartmentById(@PathVariable  Long id) {
        return departmentService.getDepartmentById(id);
    }

    @PutMapping("/{id}")
    public DepartmentResponse updateDepartment(
            @PathVariable Long id,
            @RequestBody DepartmentRequest departmentRequest) {
        return departmentService.updateDepartment(id, departmentRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable  Long id)
    {
        departmentService.deleteDepartment(id);
    }
}


