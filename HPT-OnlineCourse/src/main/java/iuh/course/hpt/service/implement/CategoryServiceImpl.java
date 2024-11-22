package iuh.course.hpt.service.implement;

import iuh.course.hpt.entity.Category;
import iuh.course.hpt.repository.CategoryRepository;
import iuh.course.hpt.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
    
    @Override
    public boolean isCategoryExisted(String categoryName) {
        Category result = categoryRepository.findByCategoryName(categoryName);
        return result != null;
    }
}
