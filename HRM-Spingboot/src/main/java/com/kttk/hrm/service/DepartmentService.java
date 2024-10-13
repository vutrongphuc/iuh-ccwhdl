package com.kttk.hrm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kttk.hrm.model.Department;
import com.kttk.hrm.repository.DepartmentRepository;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }

    // add department
    public void addDepartment(Department department) {
        departmentRepository.save(department);
    }

    // delete department
    public void deleteDepartment(Department department) {
        departmentRepository.delete(department);
    }

    public void updateDepartment(Department department) {
        departmentRepository.save(department);
    }

    // find by id
    public Department findById(String department_id) {
        Optional<Department> result = departmentRepository.findById(department_id);
        return result.orElse(null); // Trả về phòng ban nếu tìm thấy, ngược lại trả về null
    }
}
