package org.com.hrm.entity;

import jakarta.persistence.*;

@Entity
public class Employee {

    @Id
    private String employee_id;
    private String employee_name;
    private Boolean isSex;
    private String employee_phone;
    private String employee_email;
    private String department_name;

    public Employee() {

    }
    public Employee(String department_name, String employee_email, String employee_phone, Boolean isSex, String employee_name, String employee_id) {
        super();
        this.department_name = department_name;
        this.employee_email = employee_email;
        this.employee_phone = employee_phone;
        this.isSex = isSex;
        this.employee_name = employee_name;
        this.employee_id = employee_id;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public Boolean getSex() {
        return isSex;
    }

    public void setSex(Boolean sex) {
        isSex = sex;
    }

    public String getEmployee_phone() {
        return employee_phone;
    }

    public void setEmployee_phone(String employee_phone) {
        this.employee_phone = employee_phone;
    }

    public String getEmployee_email() {
        return employee_email;
    }

    public void setEmployee_email(String employee_email) {
        this.employee_email = employee_email;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employee_id='" + employee_id + '\'' +
                ", employee_name='" + employee_name + '\'' +
                ", isSex=" + isSex +
                ", employee_phone='" + employee_phone + '\'' +
                ", employee_email='" + employee_email + '\'' +
                ", department_name='" + department_name + '\'' +
                '}';
    }
}
