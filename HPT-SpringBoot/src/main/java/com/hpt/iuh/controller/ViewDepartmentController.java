package com.hpt.iuh.controller;

import com.hpt.iuh.model.Department;
import com.hpt.iuh.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller // This means that this class is a Controller that creates Views
@RequestMapping("/department")
public class ViewDepartmentController {
    public static final Logger logger = LoggerFactory.getLogger(ViewDepartmentController.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    // View show all departments
    @GetMapping("/")
    public String allDepartment() {
        return "departments/all";
    }

    // View add a department
    @GetMapping("/add")
    public String addDepartment(Model model) {
        model.addAttribute("department", new Department());
        return "departments/add";
    }

    // View Method add department
    @PostMapping("/add")
    public String addDepartment(@ModelAttribute("department") Department department, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "departments/add";
        }
        try {
            departmentRepository.save(department);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error saving department: " + e.getMessage());
            return "departments/add";
        }
        return "redirect:/";
    }

    // View update a department
    @GetMapping("/update/{id}")
    public String updateDepartment(@PathVariable("id") String id, Model model) {
        logger.info("Updating User with id {}", id);
        Department dp = departmentRepository.findById(id).orElse(null);
        if (dp == null) {
            logger.error("Unable to update. User with id {} not found.", id);
            throw new RuntimeException("Unable to update. User with id " + id + " not found.");
        }
        model.addAttribute("department", dp);
        return "departments/update";
    }
}
