package com.hpt.iuh.controller;

import com.hpt.iuh.model.Department;
import com.hpt.iuh.repository.DepartmentRepository;
import com.hpt.iuh.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // This means that this class is a Controller that creates Views
@RequestMapping("department")
public class ViewDepartmentController {
    public static final Logger logger = LoggerFactory.getLogger(ViewDepartmentController.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public String allDepartment() {
        return "departments/all";
    }

    @GetMapping("/update/{id}")
    public String updateDepartment(@PathVariable("id") String id, Model model) {
        logger.info("Updating User with id {}", id);
        Department dp = departmentRepository.findById(id).orElse(null);
        if (dp == null) {
            logger.error("Unable to update. User with id {} not found.", id);
            return "error/404"; // Trang lỗi nếu không tìm thấy department
        }
        model.addAttribute("department", dp);
        return "departments/update";
    }
}
