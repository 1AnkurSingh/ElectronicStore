package com.electronicstore.services;

import com.electronicstore.dtos.PageableResponse;
import com.electronicstore.dtos.ProductDto;
import com.electronicstore.entity.Product;

import java.util.List;

public interface ProductService {
    //create
    ProductDto createProduct(ProductDto productDto);


    // update

    ProductDto updateProduct(ProductDto productDto,String productId);


    //delete
    void deleteProduct(String productId);


    //getBy id

    ProductDto getProductById(String productId);


    // getAll

    PageableResponse<ProductDto>getAllProduct(int pageNumber,int pageSize,String sortBy, String sortDir);


   // get All live

    PageableResponse<ProductDto>getAllLiveProduct(int pageNumber,int pageSize,String sortBy, String sortDir);


    //search by title

    PageableResponse<ProductDto>searchByTitle(int pageNumber,int pageSize,String sortBy, String sortDir,String subTitle);


    // crate product with id
    ProductDto createWithCategory(ProductDto productDto, String CategoryId);


    // update category  of product
    ProductDto updateCategory(String productId,String categoryId);

    //category
    PageableResponse<ProductDto>getAllOfCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir);
}
