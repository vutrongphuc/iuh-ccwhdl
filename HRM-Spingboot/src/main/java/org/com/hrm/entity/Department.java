package org.com.hrm.entity;

import jakarta.persistence.*;


@Entity // Định nghĩa đây là lớp entity
public class Department {
    @Id // định nghĩa cho các id
    private String department_id;
    private String department_name;

    /*  constructor không tham số (no-arg constructor)
    JPA dùng để tạo entity.
    nó cần một cách để khởi tạo các đối tượng entity mà không cần biết trước các giá trị của các thuộc tính
    */
    public Department() {

    }
    public Department(String department_id, String department_name) {
        this.department_id = department_id;
        this.department_name = department_name;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    @Override
    public String toString() {
        return "Department{" +
                "department_id='" + department_id + '\'' +
                ", department_name='" + department_name + '\'' +
                '}';
    }
}
