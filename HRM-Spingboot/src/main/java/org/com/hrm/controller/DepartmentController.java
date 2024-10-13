package org.com.hrm.controller;

import org.com.hrm.DataTransferObject.request.DepartmentRequest;
import org.com.hrm.entity.Department;
import org.com.hrm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/departments")
    @ResponseBody
    Department createDepartment(@RequestBody DepartmentRequest request) {
        return departmentService.createDepartment(request);
    }

    // Map to department service. show department
    @GetMapping("/departments")
    public String getAllDepartments(Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "departments";
    }
}
