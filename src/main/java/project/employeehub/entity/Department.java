package project.employeehub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // ---Table name
public class Department {

    @Id //id is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //automatically creates ascending order IDS.
    private Long id;

    private String name;

    //JPA needs a no-argument constructor so it can create Department objects when reading data from the database.
    public Department(){

    }

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
