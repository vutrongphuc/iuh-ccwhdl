package org.com.hrm.DataTransferObject.request;

public class EmployeeRequest {
    private String employee_name;
    private Boolean isSex;
    private String employee_phone;
    private String employee_email;
    private String department_name;

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
}
