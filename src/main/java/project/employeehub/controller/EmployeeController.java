package project.employeehub.controller;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.employeehub.dto.EmployeeRequest;
import project.employeehub.dto.EmployeeResponse;
import project.employeehub.entity.Employee;
import project.employeehub.service.EmployeeService;

import java.util.List;


@RestController // "This class is a Controller that handles HTTP requests and returns data such as JSON."
@RequestMapping("/api/employees") //It establishes the base URL for all the endpoints inside this Controller.
@SecurityRequirement(name = "Bearer Authentication") //for jwt verification purpose
public class EmployeeController {

    @Autowired  //"Find the appropriate implementation of EmployeeService and provide it to this variable."
    private EmployeeService employeeService; // Connects to service class and creates employeeService variable;+

    //CRUD OPERATIONS:
    @GetMapping //READ ALL METHOD
    public List<EmployeeResponse> getAllEmployees() { //method that returns collection/list
        return employeeService.getAllEmployees(); //calling employeeService method from its class.
    }

    /*  Change POST to use the DTO, instead of entity

    In EmployeeController.java, find:
    @PostMapping  //CREATE METHOD
    public Employee createEmployee(@Valid @RequestBody Employee employee) { // @Validation for creating employee
         @RequestBody --We want Spring to take the JSON sent in Postman's body and turn it into an Employee object.
        return employeeService.createEmployee(employee);
        Change it to:
            */

    @PostMapping  //CREATE METHOD
    public EmployeeResponse createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        return employeeService.createEmployee(employeeRequest);
    }



    @GetMapping("/{id}") //READ BY ID METHOD
    public EmployeeResponse getEmployeeBYId(@PathVariable Long id) {
        //@PathVariable ----"Take the value from {id} in the URL and put it into this id variable."
        return employeeService.getEmployeeById(id);

    }


    @PutMapping("/{id}") //UPDATE METHOD,  (@PathVariable: Take the id and @RequestBody: return update info for employee
    public EmployeeResponse updateEmployee(@PathVariable Long id,
                                   @Valid @RequestBody EmployeeRequest employeeRequest) { // @Valid = for update method
        return employeeService.updateEmployee(id, employeeRequest);
    }

    @DeleteMapping("/{id}") //DELETE METHOD
    public void deleteEmployee(@PathVariable Long id) { //void coz not returning anything
        employeeService.deleteEmployee (id);
    }


}
