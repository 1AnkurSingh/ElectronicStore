package com.electronicstore.Controller;

import com.electronicstore.dtos.*;
import com.electronicstore.services.CategoryService;
import com.electronicstore.services.ProductService;
import com.electronicstore.services.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryServiceImp categoryServiceImp;


    @Autowired
    ProductService productService;
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<CategoryDto>createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto cratedCategory = categoryServiceImp.crateCategory(categoryDto);
        return new ResponseEntity<>(cratedCategory,HttpStatus.CREATED);
    }
//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto>updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("categoryId")String categoryId){
        CategoryDto updateCategory = categoryServiceImp.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(updateCategory,HttpStatus.OK);
    }
//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponseMessage>deleteCategory(@PathVariable("categoryId") String categoryId){
        categoryServiceImp.deleteCategory(categoryId);
        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("Category Delete Successfully!!")
                .success(true)
                .status(HttpStatus.OK).build();
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
    @GetMapping("/getCategoryById/{categoryId}")
    public ResponseEntity<CategoryDto>getCategoryById(@PathVariable("categoryId")String categoryId){
        CategoryDto categoryById = categoryServiceImp.getCategoryById(categoryId);
        return new ResponseEntity<>(categoryById,HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<PageableResponse<CategoryDto>>getAllCategory(@RequestParam(value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
                                                                       @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
                                                                       @RequestParam(value = "sortBy" ,defaultValue = "name",required = false)String sortBy,
                                                                       @RequestParam(value = "sortDir" ,defaultValue = "asc",required = false)String sortDir
    )
    {
        PageableResponse<CategoryDto> allCategory = categoryServiceImp.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allCategory,HttpStatus.OK);

    }
    @GetMapping("/getCategoryByTitle/{title}")
    public ResponseEntity<List<CategoryDto>>getCategoryByTitle(@PathVariable("title")String title){
        List<CategoryDto> searchByTitle = categoryServiceImp.searchByTitle(title);
        return new ResponseEntity<>(searchByTitle,HttpStatus.OK);
    }


    // create product with category

    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto>createProductWithCategory(@PathVariable("categoryId")String categoryId, @RequestBody ProductDto productDto){

        ProductDto withCategory = productService.createWithCategory(productDto, categoryId);
        return new ResponseEntity<>(withCategory,HttpStatus.CREATED);
    }


    // update category of product
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto>updateCategoryOfProduct(@PathVariable String categoryId,@PathVariable String productId){
        ProductDto productDto = productService.updateCategory(productId, categoryId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    // get products of product

    @GetMapping("{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDto>>getProductOfCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
            @RequestParam(value = "sortBy" ,defaultValue = "title",required = false)String sortBy,
            @RequestParam(value = "sortDir" ,defaultValue = "asc",required = false)String sortDir
    ){
        PageableResponse<ProductDto> response = productService.getAllOfCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


}
