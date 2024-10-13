package org.com.hrm.DataTransferObject.request;

public class DepartmentRequest {

    private  String department_id;
    private String department_name;

    public String getDepartment_id() {
        return department_id;
    }
    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment(String department_id, String department_name) {
        this.department_id = department_id;
        this.department_name = department_name;
    }
}
