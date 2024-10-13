package org.com.hrm.service;

import org.com.hrm.entity.Department;
import org.com.hrm.repository.DepartmentInterface;
import org.com.hrm.DataTransferObject.request.DepartmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentInterface departmentInterface; // department repository

    // create department
    public Department createDepartment(DepartmentRequest departmentRequest) {

        Department department = new Department();
        department.setDepartment_id(departmentRequest.getDepartment_id());
        department.setDepartment_name(departmentRequest.getDepartment_name());

        return departmentInterface.save(department);
    }

    // show department
    public List<Department> getAllDepartments() {
        return departmentInterface.findAll();
    }

}
