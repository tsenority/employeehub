package project.employeehub;


import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import project.employeehub.dto.EmployeeRequest;
import project.employeehub.dto.EmployeeResponse;
import project.employeehub.entity.Employee;
import project.employeehub.repository.EmployeeRepository;
import project.employeehub.service.EmployeeService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import project.employeehub.entity.Department;
import project.employeehub.repository.DepartmentRepository;

@SpringBootTest  // "Start the Spring application context to this class.temporarily so I can test it." just for testing purpose.
@AutoConfigureMockMvc(addFilters = false) // "Ignore the security filters and let me test whether my controller works."
// temporarily so I can test it." just for testing purpose.
@Transactional //Transaction starts, test runs, transaction rolls back and the test data is removed.

public class EmployeeHubIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    // For Mockito, @mock created a fake repository. Here, we are using the real repository

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc; //A fake browser/Postman for testing your Spring controller.


    @Test
    void repositoryLoads() {

        List<Employee> employees = employeeRepository.findAll();

        assertNotNull(employees);
    }

    @Test
    void findEmployeeById() {

        Employee employee = new Employee();

        employee.setFirstName("John");
        employee.setLastName("Smith");
        employee.setEmail("john.test@example.com");
        employee.setPhone("1234567890");
        employee.setSalary(50000.0);

        Employee savedEmployee = employeeRepository.save(employee);

        Optional<Employee> foundEmployee =
                employeeRepository.findById(savedEmployee.getId());

        assertTrue(foundEmployee.isPresent());
    }

    @Test
    void findEmployeeByIdNotFound() { // test the "not found" case. ID doesnt exists.

        Optional<Employee> employee =
                employeeRepository.findById(9999L);

        assertTrue(employee.isEmpty());
    }

    @Test
    void saveEmployee() { //integration test for save()

        Employee employee = new Employee();

        employee.setFirstName("Integration");
        employee.setLastName("Test");
        employee.setEmail("integration.test@Example.com");
        employee.setPhone("801-855-8623");
        employee.setSalary(50000);

        Employee savedEmployee = employeeRepository.save(employee);

        assertNotNull(savedEmployee.getId());
    }

    @Test
    void deleteEmployee() {

        Employee employee = new Employee();

        employee.setFirstName("Integration");
        employee.setLastName("Test");
        employee.setEmail("integration.test@example.com");
        employee.setPhone("801-555-0000");
        employee.setSalary(50000.0);

        Employee savedEmployee = employeeRepository.save(employee);

        assertNotNull(savedEmployee.getId()); //till here, same as save method.

        employeeRepository.deleteById(savedEmployee.getId());

        Optional<Employee> deletedEmployee = employeeRepository.findById(savedEmployee.getId());

        assertTrue(deletedEmployee.isEmpty());

    }

    //Now let's test the full Employee service + repository + database together.
    //This is where integration testing becomes more meaningful.
    @Test
    void createEmployeeThroughService() {

        // Step 1: Create a department for this test
        Department department = new Department();
        department.setName("IT");

        // Step 2: Save the department to MySQL
        Department savedDepartment = departmentRepository.save(department);

        EmployeeRequest request = new EmployeeRequest();

        request.setFirstName("Integration");
        request.setLastName("Service");
        request.setEmail("Integration@test.com");
        request.setPhone("801-522-344");
        request.setSalary(55000);
        request.setDepartmentId(savedDepartment.getId());

        EmployeeResponse response = employeeService.createEmployee(request);

        assertNotNull(response.getId());
        assertEquals("Integration", response.getFirstName());
        assertEquals("Service", response.getLastName());

    }

    @WithMockUser //"For this test, pretend a user is already authenticated.
    @Test
    void getEmployees() throws Exception { //To test the Controller

        // Step 1: Create a department
        Department department = new Department();
        department.setName("Human Resources");

// Step 2: Save it to the test database
        Department savedDepartment = departmentRepository.save(department);

// Step 3: Get the generated department ID
        Long departmentId = savedDepartment.getId();


            mockMvc.perform(get("/api/employees")) //Means GET http://localhost:8080/api/employees
                    .andExpect(status().isOk()) //  //"I expect my controller to return HTTP 200."
                    .andExpect(jsonPath("$").isArray())//verify that the controller actually returns an array of employees.
                    .andExpect(jsonPath("$[0].id").exists());//verify that the first employee in the returned array has an id
        }

    @Test
    @WithMockUser
    void createEmployee() throws Exception {

        Department department = new Department();
        department.setName("Human Resources");

        Department savedDepartment = departmentRepository.save(department);

        Long departmentId = savedDepartment.getId();

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "firstName": "Mock",
                                    "lastName": "Employee",
                                    "email": "mock@example.com",
                                    "phone": "801-555-1234",
                                    "salary": 50000,
                                    "departmentId": %d
                                }
                                """.formatted(departmentId)))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser
    void getEmployeeById() throws Exception {

        // Step 1: Create a test employee
        Employee employee = new Employee();

        employee.setFirstName("John");
        employee.setLastName("Smith");
        employee.setEmail("john.integration@example.com");
        employee.setPhone("801-555-1234");
        employee.setSalary(50000.0);

        // Step 2: Save employee to database
        Employee savedEmployee = employeeRepository.save(employee);

        // Step 3: Get the generated employee ID
        Long employeeId = savedEmployee.getId();

        // Step 4: Test GET API using that ID
        mockMvc.perform(get("/api/employees/" + employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employeeId))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.email")
                        .value("john.integration@example.com"));
    }

    @Test
    @WithMockUser
    void getEmployeeByIdNotFound() throws Exception {

        mockMvc.perform(get("/api/employees/9999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void deleteEmployees() throws Exception {

        Employee employee = new Employee();

        employee.setFirstName("Delete");
        employee.setLastName("Test");
        employee.setEmail("delete@test.com");
        employee.setPhone("801-555-1111");
        employee.setSalary(40000);

        Employee savedEmployee = employeeRepository.save(employee);

        mockMvc.perform(
                        delete("/api/employees/" + savedEmployee.getId())
                )
                .andExpect(status().isOk());
    }

    }
    /*  You have essentially learned:

MockMvc
   ↓
GET /api/employees
   ↓
Spring Security
   ↓
No authentication
   ↓
403 Forbidden
*/





    /*  The flow is:

save()
  ↓
Employee gets ID
  ↓
deleteById(ID)
  ↓
findById(ID)
  ↓
Optional.empty()
  ↓
✅ Employee was actually deleted

This is a nice example of integration testing because multiple real repository operations are interacting with the real database.*/

