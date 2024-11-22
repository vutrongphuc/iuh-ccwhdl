package iuh.course.hpt.service.interfaces;

import iuh.course.hpt.entity.Category;

import java.util.List;

public interface CategoryService {

    public List<Category> getAll();

    public Category getById(Long id);

    public Category save(Category category);

    public void deleteById(Long id);

    boolean isCategoryExisted(String categoryName);
}
