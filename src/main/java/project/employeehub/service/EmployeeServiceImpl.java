package project.employeehub.service;

import jakarta.validation.Valid;
import project.employeehub.dto.EmployeeRequest;
import project.employeehub.dto.EmployeeResponse;
import project.employeehub.entity.Department;
import project.employeehub.entity.Employee;
import project.employeehub.exception.EmployeeNotFoundException;
import project.employeehub.repository.DepartmentRepository;
import project.employeehub.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service //tells it is a service class.
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired //creates the connection to Repo class and creates its object.
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository; //we're going to make the service actually use that department ID.


    @Override //overrides method from EmployeeService interface
    public EmployeeResponse createEmployee(@Valid EmployeeRequest employeeRequest) {

        Employee employee = new Employee();//EmployeeRequest is the incoming data, while Employee is the database entity.

        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setEmail(employeeRequest.getEmail());
        employee.setPhone(employeeRequest.getPhone());
        employee.setSalary(employeeRequest.getSalary());

        Department department = departmentRepository.findById( //we'll make the create employee method connect the two entities.
                employeeRequest.getDepartmentId()
        ).orElseThrow();

        employee.setDepartment(department);//creates connection between the 2 jav objects.

        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeResponse response = new EmployeeResponse();

        response.setId(savedEmployee.getId());
        response.setFirstName(savedEmployee.getFirstName());
        response.setLastName(savedEmployee.getLastName());
        response.setEmail(savedEmployee.getEmail());
        response.setPhone(savedEmployee.getPhone());
        response.setSalary(savedEmployee.getSalary());

        response.setDepartmentName(savedEmployee.getDepartment().getName());

        return response;
    }
    /*
 The important pattern you just built
Request DTO
    ↓
Employee entity
    ↓
Database
    ↓
Saved Employee
    ↓
Response DTO
    ↓
Client

This is a very common backend pattern.

        Postman JSON
     ↓
EmployeeRequest
     ↓
EmployeeService
     ↓
Employee entity
     ↓
employeeRepository.save(employee)
     ↓
MySQL

        The important thing to understand is why we did this:

The controller receives a DTO because we don't want the outside API directly controlling our database entity.

Then the service converts the DTO into an Employee entity before saving it.
     */

    @Override
    public List<EmployeeResponse> getAllEmployees() {

        List<Employee> employees = employeeRepository.findAll();

        List<EmployeeResponse> responses = new java.util.ArrayList<>();

        for (Employee employee : employees) {

            EmployeeResponse response = new EmployeeResponse();

            response.setId(employee.getId());
            response.setFirstName(employee.getFirstName());
            response.setLastName(employee.getLastName());
            response.setEmail(employee.getEmail());
            response.setPhone(employee.getPhone());
            response.setSalary(employee.getSalary());

            if (employee.getDepartment() != null) {
                response.setDepartmentName(employee.getDepartment().getName());
            }

            responses.add(response);
        }

        return responses;
    }
    /*  getAllEmployees()
      ↓
    employeeRepository.findAll()
      ↓
    Repository talks to database
      ↓
    Gets all Employee records
      ↓
    Returns List<Employee>  Now, we have Create And ReadALl employee methods.We didn't write Any SQL. Spring Data JPA handles that.
    */

        @Override
        public EmployeeResponse getEmployeeById (Long id){

            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() ->
            new EmployeeNotFoundException(
                    "Employee not found with id: " + id
            ));

            EmployeeResponse response = new EmployeeResponse();

            response.setId(employee.getId());
            response.setFirstName(employee.getFirstName());
            response.setLastName(employee.getLastName());
            response.setEmail(employee.getEmail());
            response.setPhone(employee.getPhone());
            response.setSalary(employee.getSalary());

            if (employee.getDepartment() != null) {
                response.setDepartmentName(employee.getDepartment().getName());
            }

            return response;
        }

                    /*new EmployeeNotFoundException( //Creates an object without referencing a variable.
                            "Employee not found with id: " + id));// 2 brackets because we are inside 2 method calls.
        /*Custom Exception: "If no employee is found, create an EmployeeNotFoundException containing a message that includes
         the employee ID, and throw it." */


        @Override
        public EmployeeResponse updateEmployee (Long id, EmployeeRequest employeeRequest){

            Employee existingEmployee = employeeRepository.findById(id)
                    .orElseThrow();

            existingEmployee.setFirstName(employeeRequest.getFirstName());
            existingEmployee.setLastName(employeeRequest.getLastName());
            existingEmployee.setEmail(employeeRequest.getEmail());
            existingEmployee.setPhone(employeeRequest.getPhone());
            existingEmployee.setSalary(employeeRequest.getSalary());

            Department department = departmentRepository.findById(
                    employeeRequest.getDepartmentId()
            ).orElseThrow();

            existingEmployee.setDepartment(department);

            Employee savedEmployee = employeeRepository.save(existingEmployee);

            EmployeeResponse response = new EmployeeResponse();

            response.setId(savedEmployee.getId());
            response.setFirstName(savedEmployee.getFirstName());
            response.setLastName(savedEmployee.getLastName());
            response.setEmail(savedEmployee.getEmail());
            response.setPhone(savedEmployee.getPhone());
            response.setSalary(savedEmployee.getSalary());
            response.setDepartmentName(savedEmployee.getDepartment().getName());

            return response;
        }

        /*
        So the flow is currently:

PUT JSON
   ↓
EmployeeRequest DTO
   ↓
Service
   ↓
Existing Employee entity
   ↓
Database
   ↓
Employee returned

Whereas POST is:

POST JSON
   ↓
EmployeeRequest
   ↓
Employee entity
   ↓
Database
   ↓
EmployeeResponse
         */


        @Override
        public void deleteEmployee (Long id){
            employeeRepository.deleteById(id);

        }

    }



        // Error: java: implicitly declared classes are not supported in -source 17
//  (use -source 25 or higher to enable implicitly declared classes) ---Bracket issues