package project.employeehub.dto;

public class DepartmentResponse {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/* Next  step:  — Update DepartmentService to use DTOs

Now we'll change the service interface so create and update use the same DTO pattern as Employee.

 */
