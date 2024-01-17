package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.dto.CategoryDto;
import com.ecommerce.productservice.exception.CategoryNotFoundException;
import com.ecommerce.productservice.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@ContextConfiguration(classes = {CategoryController.class})
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryServiceImpl categoryService;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Add Category - Should Create Category")
    void addCategory_ShouldCreateCategory() throws Exception {
        CategoryDto categoryDto = getMockCategoryDto();
        when(categoryService.addCategory(any(CategoryDto.class))).thenReturn(categoryDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/categories/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryId").value(categoryDto.categoryId()));
    }

    @Test
    @DisplayName("Update Category - Should Update Category")
    void updateCategory_ShouldUpdateCategory() throws Exception {
        CategoryDto categoryDto = getMockCategoryDto();
        when(categoryService.updateCategory(anyLong(), any(CategoryDto.class))).thenReturn(categoryDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/categories/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").value(categoryDto.categoryId()));
    }

    @Test
    @DisplayName("Get Category By Id - Should Return Category")
    void getCategoryById_ShouldReturnCategory() throws Exception {
        CategoryDto categoryDto = getMockCategoryDto();
        when(categoryService.getCategoryById(anyLong())).thenReturn(categoryDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").value(categoryDto.categoryId()));
    }

    @Test
    @DisplayName("List Categories - Should Return Categories")
    void listCategories_ShouldReturnCategories() throws Exception {
        List<CategoryDto> categoryDtos = Arrays.asList(getMockCategoryDto(), getMockCategoryDto());
        when(categoryService.listCategories()).thenReturn(categoryDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/categories/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(categoryDtos.size()));
    }

    @Test
    @DisplayName("Assign Product To Category - Should Return Ok")
    void assignProductToCategory_ShouldReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/categories/1/assign/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Remove Product From Category - Should Return Ok")
    void removeProductFromCategory_ShouldReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/1/remove/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete Category - Should Return Ok")
    void deleteCategory_ShouldReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete Category - Should Return Not Found When Category Does Not Exist")
    void deleteCategory_ShouldReturnNotFound_WhenCategoryDoesNotExist() throws Exception {
        doThrow(new CategoryNotFoundException("Category Not Found")).when(categoryService).deleteCategory(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/delete/1"))
                .andExpect(status().isNotFound());
    }


    private CategoryDto getMockCategoryDto() {
        return CategoryDto.builder()
                .categoryId(1L)
                .categoryTitle("Test Category")
                .imageUrl("http://example.com/image.jpg")
                .subCategories(new HashSet<>())
                .productDtos(new HashSet<>())
                .parentCategoryDto(null)
                .build();
    }

}