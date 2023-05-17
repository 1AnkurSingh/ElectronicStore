package com.electronicstore.services.imp;

import com.electronicstore.dtos.CategoryDto;
import com.electronicstore.dtos.PageableResponse;
import com.electronicstore.entity.Category;
import com.electronicstore.exception.ResourceNotFoundException;
import com.electronicstore.helper.Helper;
import com.electronicstore.repository.CategoryRepository;
import com.electronicstore.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public CategoryDto crateCategory(CategoryDto categoryDto) {
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);

        Category category=dtoToEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        CategoryDto newDto=entityToDto(savedCategory);



//        Category category = mapper.map(categoryDto, Category.class);
//        Category savedCategory = categoryRepository.save(category);
//        CategoryDto newDto = mapper.map(savedCategory, CategoryDto.class);


        return newDto;
    }


    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Id not found"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category saveCategory = categoryRepository.save(category);
        return entityToDto(saveCategory);
    }


    @Override
    public void deleteCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Id not found"));
        categoryRepository.delete(category);

    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(int pageNumber,int pageSize,String sortBy, String sortDir) {

        Sort sort=(sortDir.equalsIgnoreCase("desc")) ?(Sort.by(sortBy)):(Sort.by(sortBy).ascending());

        Pageable pageable=PageRequest.of(pageNumber-1,pageSize,sort);

        Page<Category> page = categoryRepository.findAll(pageable);

        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);

        return pageableResponse;
    }



    @Override
    public CategoryDto getCategoryById(String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category Id not found"));

        return entityToDto(category);
    }

    @Override
    public List<CategoryDto> searchByTitle(String title) {
        Optional<Category> byTitle = categoryRepository.findByTitle(title);
        List<CategoryDto> changeToDtoOneByOne = byTitle.stream().map(category -> entityToDto(category)).collect(Collectors.toList());
        return changeToDtoOneByOne;
    }


    private Category dtoToEntity(CategoryDto categoryDto) {
        Category category = Category.builder()
                .categoryId(categoryDto.getCategoryId())
                .title(categoryDto.getTitle())
                .description(categoryDto.getDescription())
                .coverImage(categoryDto.getCoverImage()).build();
        return category;
    }

    private CategoryDto entityToDto(Category savedCategory) {
        CategoryDto categoryDto = CategoryDto.builder()
                .categoryId(savedCategory.getCategoryId())
                .title(savedCategory.getTitle())
                .description(savedCategory.getDescription())
                .coverImage(savedCategory.getCoverImage()).build();
        return categoryDto;

    }


}
