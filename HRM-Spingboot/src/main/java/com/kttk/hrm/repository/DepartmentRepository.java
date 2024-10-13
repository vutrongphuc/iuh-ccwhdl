package com.kttk.hrm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kttk.hrm.model.Department;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {

}
