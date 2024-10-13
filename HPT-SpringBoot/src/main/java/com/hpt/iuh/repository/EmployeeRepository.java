package com.hpt.iuh.repository;

import com.hpt.iuh.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
