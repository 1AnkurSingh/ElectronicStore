package com.electronicstore.services;

import com.electronicstore.dtos.CategoryDto;
import com.electronicstore.dtos.PageableResponse;

import java.util.List;

public interface CategoryService {
    // create

    CategoryDto crateCategory(CategoryDto categoryDto);


    // update
    CategoryDto updateCategory(CategoryDto categoryDto,String categoryId);


    // getAll
    PageableResponse<CategoryDto>getAllCategory(int pageNumber,int pageSize,String sortBy, String sortDir);

    // delete

    void deleteCategory(String categoryId);


    // get single category by id
    CategoryDto getCategoryById(String categoryId);


    //search

//    CategoryDto searchCategoryById()
    List<CategoryDto> searchByTitle(String title);
}
