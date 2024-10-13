package org.com.hrm.repository;

import org.com.hrm.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentInterface extends JpaRepository<Department, String> {
}
