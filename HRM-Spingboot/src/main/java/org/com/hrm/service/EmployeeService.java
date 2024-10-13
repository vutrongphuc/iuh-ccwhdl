package org.com.hrm.service;

import org.com.hrm.entity.Employee;
import org.com.hrm.repository.EmployeeInterface;
import org.com.hrm.DataTransferObject.request.EmployeeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeInterface employeeInterface;

    public Employee createEmployee(EmployeeRequest employeeRequest) {
        Employee emp = new Employee();

        // map data to request
        emp.setEmployee_name(employeeRequest.getEmployee_name());
        emp.setSex(employeeRequest.getSex());
        emp.setEmployee_phone(employeeRequest.getEmployee_phone());
        emp.setEmployee_email(employeeRequest.getEmployee_email());
        emp.setDepartment_name(employeeRequest.getDepartment_name());

        // create row (employee)
        return employeeInterface.save(emp);
    }

}
