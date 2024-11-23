package iuh.course.hpt.controller;

import iuh.course.hpt.entity.Category;
import iuh.course.hpt.service.interfaces.CategoryService;
import iuh.course.hpt.utility.Utils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Data
@Controller
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/category")
    public String category(Model model, @ModelAttribute("error") String error, @ModelAttribute("success") String success) {
        model.addAttribute("title", "Quản lý danh mục");

        // get all categories
        model.addAttribute("categories", categoryService.getAll());

        // create new category
        model.addAttribute("category", new Category());

        return "admin/category";
    }

    // Create new category
    @RequestMapping(value = "/admin/category", method = RequestMethod.POST)
    public String createCategory(@ModelAttribute("category") Category category, Model model) {

        // check category is existed
        boolean isCategoryExisted = categoryService.isCategoryExisted(category.getCategoryName());

        if (isCategoryExisted) {
            return "redirect:/admin/category?error=" + Utils.getInstance().encodeUrlSafe("Danh mục đã tồn tại");
        }

        Category result = categoryService.save(category);
        if (result != null) {
            return "redirect:/admin/category?success=" + Utils.getInstance().encodeUrlSafe("Tạo danh mục thành công");
        } else {
            return "redirect:/admin/category?error=" + Utils.getInstance().encodeUrlSafe("Tạo danh mục thất bại");
        }

    }

    // Edit category
    @RequestMapping(value = "/admin/category/update", method = RequestMethod.POST)
    public String editCategory(@ModelAttribute("category") Category category, Model model) {

        // check category is existed
        boolean isCategoryExisted = categoryService.isCategoryExisted(category.getCategoryName());

        if (isCategoryExisted) {
            model.addAttribute("error", "Danh mục đã tồn tại");
            return "admin/category";
        }

        Category result = categoryService.save(category);
        if (result != null) {
            model.addAttribute("success", "Cập nhật danh mục thành công");
        } else {
            model.addAttribute("error", "Cập nhật danh mục thất bại");
        }

        return "admin/category";
    }

    // Delete category
    @RequestMapping(value = "/admin/category/delete", method = RequestMethod.GET)
    public String deleteCategory(@ModelAttribute("category") Category category, Model model) {

        categoryService.deleteById(category.getId());

        model.addAttribute("success", "Xóa danh mục thành công");

        return "admin/category";
    }
}
