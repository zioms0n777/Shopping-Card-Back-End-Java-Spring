package com.shopbackend.shoppingcard.controller;


import com.shopbackend.shoppingcard.Model.Category;
import com.shopbackend.shoppingcard.Service.category.CategoryService;
import com.shopbackend.shoppingcard.Service.category.ICategoryService;
import com.shopbackend.shoppingcard.exception.AlreadyExistsException;
import com.shopbackend.shoppingcard.exception.ResourceNotFoundException;
import com.shopbackend.shoppingcard.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService categoryService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories()
    {

        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse(categories, "Found!"));
        }
        catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(INTERNAL_SERVER_ERROR, "Error"));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name)
    {

        try {
            Category category = categoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse(category, "Added!"));
        }
        catch (AlreadyExistsException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(INTERNAL_SERVER_ERROR, "Error"));
        }


    }


    @GetMapping("/category/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id)
    {

        try {
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse(category, "Found!"));
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(NOT_FOUND, "Error"));
        }
            }


    @GetMapping("/category/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name)
    {

        try {
            Category category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse(category, "Found!"));
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(NOT_FOUND, "Error"));
        }
    }

    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id)
    {

        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse(null, "Found!"));
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(NOT_FOUND, "Error"));
        }
    }

@PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category)
    {
        try
        {
            Category updatedCategory = categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new ApiResponse(updatedCategory, "Updated!"));
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(NOT_FOUND, "Error"));
        }
    }







}
