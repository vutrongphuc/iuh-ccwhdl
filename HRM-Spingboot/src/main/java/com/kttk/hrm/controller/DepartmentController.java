package com.kttk.hrm.controller;

import com.kttk.hrm.model.Department;
import com.kttk.hrm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DepartmentController {
    @Autowired

    private DepartmentService departmentService;

    // fetch all departments
    @GetMapping("/departments")
    public String getAllDepartment(Model model) {
        List<Department> departments = departmentService.getAllDepartment();
        model.addAttribute("departments", departments);
        return "departments";
    }

    // map to add department


    // post department
    @PostMapping("/departments/add")
    public String addDepartment(@ModelAttribute Department department) {
        departmentService.addDepartment(department);
        return "redirect:/departments";
    }


    // map to edit with para id
    @GetMapping("/departments/update/{id}")
    public String editDepartmentForm(@PathVariable String id, Model model) {
        Department department = departmentService.findById(id);
        model.addAttribute("department", department);
        return "editDepartment";
    }

    // post update department
    @PostMapping("/departments/update/{id}")
    public String updateDepartment(@PathVariable("id") String id, @ModelAttribute Department department) {
        // Tìm phòng ban dựa trên ID và cập nhật nếu tìm thấy
        Department existingDepartment = departmentService.findById(id);
        if (existingDepartment != null) {
            existingDepartment.setDepartment_name(department.getDepartment_name());
            departmentService.updateDepartment(existingDepartment);
        }
        return "redirect:/departments";
    }
}
