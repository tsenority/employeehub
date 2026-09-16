package project.employeehub.dto;

public class DepartmentRequest {
    /* DepartmentRequest — represents what the client sends:
    Request DTO = data the client needs to provide.
    Entity = data the database needs to store.
     */

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
