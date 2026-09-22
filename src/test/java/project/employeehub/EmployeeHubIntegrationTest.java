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

@SpringBootTest  // "Start the Spring application context to this class.temporarily so I can test it." just for testing purpose.
@AutoConfigureMockMvc(addFilters = false) // "Ignore the security filters and let me test whether my controller works."
// temporarily so I can test it." just for testing purpose.
@Transactional //Transaction starts, test runs, transaction rolls back and the test data is removed.

public class EmployeeHubIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;
    // For Mockito, @mock created a fake repository. Here, we are using the real repository

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc; //A fake browser/Postman for testing your Spring controller.


    @Test
        //For this method, we are testing “Can Spring create and use my EmployeeRepository?”
    void repositoryLoads() {

        List<Employee> employees = employeeRepository.findAll();
        //Test the repository against your database. "Can my real EmployeeRepository successfully retrieve data?"

        assertNotNull(employees);
        assertFalse(employees.isEmpty());

    }

    @Test
    void findEmployeeById() { //test a specific employee using the real repository. ID exists
        Optional<Employee> employee = employeeRepository.findById(2L);
        //Optional: "There might be an Employee here, or there might not be."

        assertTrue(employee.isPresent());
        //"Did the database actually give us an employee?"
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

        EmployeeRequest request = new EmployeeRequest();

        request.setFirstName("Integration");
        request.setLastName("Service");
        request.setEmail("Integration@test.com");
        request.setPhone("801-522-344");
        request.setSalary(55000);
        request.setDepartmentId(1L);

        EmployeeResponse response = employeeService.createEmployee(request);

        assertNotNull(response.getId());
        assertEquals("Integration", response.getFirstName());
        assertEquals("Service", response.getLastName());

    }

    @WithMockUser //"For this test, pretend a user is already authenticated.
    @Test
    void getEmployees() throws Exception { //To test the Controller


            mockMvc.perform(get("/api/employees")) //Means GET http://localhost:8080/api/employees
                    .andExpect(status().isOk()) //  //"I expect my controller to return HTTP 200."
                    .andExpect(jsonPath("$").isArray())//verify that the controller actually returns an array of employees.
                    .andExpect(jsonPath("$[0].id").exists());//verify that the first employee in the returned array has an id
        }

    @Test
    @WithMockUser
    void createEmployee() throws Exception {

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON) //Tells "This request body is JSON."
                        .content("""
                    {
                        "firstName": "Mock",
                        "lastName": "Employee",
                        "email": "mock@example.com",
                        "phone": "801-555-1234",
                        "salary": 50000,
                        "departmentId": 1
                    }
                    """))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getEmployeeById() throws Exception {

        mockMvc.perform(get("/api/employees/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.firstName").exists())
                .andExpect(jsonPath("$.email").exists());
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

