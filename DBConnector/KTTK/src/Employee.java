public class Employee {
    private String employee_id;
    private String name;
    private boolean sex;
    private String email;
    private String phone;
    private String address;
    private String department_id;
    private String department_name;

    public Employee(String employee_id, String name, boolean sex, String email, String phone, String address, String department_id, String department_name) {
        super();
        this.employee_id = employee_id;
        this.name = name;
        this.sex = sex;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.department_id = department_id;
        this.department_name = department_name;
    }

    public String getEmployeeId() {
        return employee_id;
    }

    public void setEmployeeId(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getDepartmentId() {
        return department_id;
    }

    public void setDepartmentId(String department_id) {
        this.department_id = department_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String mobile) {
        this.phone = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartmentName() {
        return department_name;
    }

    public void setDepartmentName(String department_name) {
        this.department_name = department_name;
    }

    @Override
    public String toString() {
        return "Employee {" +
                "id='" + employee_id + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address +
                ", department='" + department_name +
                '}';
    }
}
