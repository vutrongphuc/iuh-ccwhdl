package com.hpt.iuh.controller;

import com.hpt.iuh.model.Department;
import com.hpt.iuh.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller // This means that this class is a Controller that creates Views
@RequestMapping("/department")
public class ViewDepartmentController {
    public static final Logger logger = LoggerFactory.getLogger(ViewDepartmentController.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    // View show all departments
    @GetMapping("/")
    public String allDepartment() {
        return "departments/all"; // render all.html on template/departments/all
    }

    // View add a department
    @GetMapping("/add")
    public String addDepartment() {
        return "departments/add"; // template/departments/add
    }

    // View update a department
    @GetMapping("/update/{id}")
    public String updateDepartment(@PathVariable("id") String id, Model model) {
        Department dp = departmentRepository.findById(id).orElse(null);
        if (dp == null) {
            throw new RuntimeException("Unable to update. Department with id " + id + " not found.");
        }
        // Add department data to model
        model.addAttribute("department", dp);
        return "departments/update";
    }
}