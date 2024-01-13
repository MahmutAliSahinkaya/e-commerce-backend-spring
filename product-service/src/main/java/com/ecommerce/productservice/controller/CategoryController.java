package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.CategoryDto;
import com.ecommerce.productservice.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto category) {
        CategoryDto addedCategory = categoryService.addCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedCategory);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long categoryId,
                                                      @RequestBody CategoryDto category) {
        CategoryDto updatedCategory = categoryService.updateCategory(categoryId, category);
        return ResponseEntity.ok(updatedCategory);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long categoryId) {
        CategoryDto fetchedCategory = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(fetchedCategory);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CategoryDto>> listCategories() {
        List<CategoryDto> categories = categoryService.listCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/{productId}/assign/{categoryId}")
    public ResponseEntity<Void> assignProductToCategory(@PathVariable Long productId,
                                                        @PathVariable Long categoryId) {
        categoryService.assignProductToCategory(productId, categoryId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}/remove/{categoryId}")
    public ResponseEntity<Void> removeProductFromCategory(@PathVariable Long productId,
                                                          @PathVariable Long categoryId) {
        categoryService.removeProductFromCategory(productId, categoryId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }
}
