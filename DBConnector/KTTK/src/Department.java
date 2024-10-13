public class Department {
    private String department_id;
    private String department_name;

    public Department(String department_id, String department_name) {
        super();
        this.department_id = department_id;
        this.department_name = department_name;
    }

    public String getDepartmentId() {
        return department_id;
    }

    public void setDepartmentId(String department_id) {
        this.department_id = department_id;
    }

    public String getDepartmentName() {
        return department_name;
    }

    public void setDepartmentName(String department_name) {
        this.department_name = department_name;
    }

    @Override
    public String toString() {
        return "Department {" +
                "id='" + department_id + '\'' +
                ", name='" + department_name +
                '}';
    }
}
