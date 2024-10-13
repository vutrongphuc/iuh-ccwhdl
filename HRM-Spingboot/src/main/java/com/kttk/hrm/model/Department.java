package com.kttk.hrm.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "department")
public class Department {
    @Id
    private String department_id;

    @Column(name = "department_name", nullable = false)
    private String department_name;

    public Department(String department_id, String department_name) {
        this.department_id = department_id;
        this.department_name = department_name;
    }

    public Department() {

    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }
}
