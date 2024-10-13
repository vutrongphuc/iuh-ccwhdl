public interface Interface {
    // Department
    void addDepartment(Department dep);
    void updateDepartment(Department dep);
    void deleteDepartment(Department dep);
    void searchDepartmentById(String department_id);
    void searchDepartmentByName(String department_name);

    // Employee
    void addEmployee(Employee emp);
    void updateEmployee(Employee emp);
    void deleteEmployee(String employee_id);
    void searchEmployeeById(String employee_id);
    void searchEmployeeByName(String name);
}
