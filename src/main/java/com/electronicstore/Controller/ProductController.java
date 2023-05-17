package com.electronicstore.Controller;

import com.electronicstore.dtos.ProductDto;
import com.electronicstore.services.ProductService;
import com.electronicstore.services.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ProductDto>createProduct(@RequestBody ProductDto productDto){
        ProductDto productCreated = productService.createProduct(productDto);
        return  new ResponseEntity<>(productCreated, HttpStatus.CREATED);

    }

}
