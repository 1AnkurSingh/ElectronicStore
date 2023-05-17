package com.electronicstore.services;

import com.electronicstore.dtos.PageableResponse;
import com.electronicstore.dtos.ProductDto;

import java.util.List;

public interface ProductService {
    //create
    ProductDto createProduct(ProductDto productDto);


    // update

    ProductDto product(ProductDto productDto,String productId);


    //delete
    void deleteProduct(String productId);


    //getBy id

    ProductDto getProductById(String productId);


    // getAll

    PageableResponse<ProductDto>getAllProduct(int pageNumber,int pageSize,String sortBy, String sortDir);


   // get All live

    List<ProductDto> getAllLiveProduct();


    //search by title

    List<ProductDto>searchByTitle(String title);
}
