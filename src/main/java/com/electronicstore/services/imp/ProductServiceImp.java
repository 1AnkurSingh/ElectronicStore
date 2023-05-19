package com.electronicstore.services.imp;

import com.electronicstore.dtos.PageableResponse;
import com.electronicstore.dtos.ProductDto;
import com.electronicstore.entity.Category;
import com.electronicstore.entity.Product;
import com.electronicstore.exception.ResourceNotFoundException;
import com.electronicstore.helper.Helper;
import com.electronicstore.repository.CategoryRepository;
import com.electronicstore.repository.ProductRepository;
import com.electronicstore.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;



    @Override
    public ProductDto createProduct(ProductDto productDto) {

        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);



        Product product = mapper.map(productDto, Product.class);
        product.setAddedDate(new Date());
        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct, ProductDto.class);

//        Product product=dtoToEntity(productDto);
//        Product savedUser = productRepository.save(product);
//        ProductDto newDto=entityToDto(savedUser);
//
//        return newDto;

    }


    @Override
    public ProductDto updateProduct(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product id not found!!"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProductImageName(productDto.getProductImageName());

        Product updatedProduct = productRepository.save(product);

        return entityToDto(updatedProduct);
    }

    @Override
    public void deleteProduct(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product id not found !!"));
        productRepository.delete(product);

    }

    @Override
    public ProductDto getProductById(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product id not found!!"));
        return entityToDto(product);
    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending()) ;

        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);

        Page<Product> page = productRepository.findAll(pageable);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(page, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto>  getAllLiveProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending()) ;

        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);

        Page<Product> page = productRepository.findByLiveTrue(pageable);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(page, ProductDto.class);
        return pageableResponse;


    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(int pageNumber, int pageSize, String sortBy, String sortDir, String subTitle) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending()) ;

        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);

        Page<Product> page = productRepository.findByTitleContaining(subTitle,pageable);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(page, ProductDto.class);
        return pageableResponse;
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category id not found !!"));

        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);

        Product product = mapper.map(productDto, Product.class);


        product.setAddedDate(new Date());
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct, ProductDto.class);



    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
        // product fetch operation

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product id not found!!"));

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category id not found!!"));

        product.setCategory(category);
        Product savedProduct = productRepository.save(product);

        return mapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category id not found!!"));

        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending()) ;


        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);


        Page<Product> page=productRepository.findByCategory(category,pageable);


        return  Helper.getPageableResponse(page,ProductDto.class);
    }


    private Product dtoToEntity(ProductDto productDto) {
        Product product = Product.builder()
                .productId(productDto.getProductId())
                .title(productDto.getTitle())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .discountedPrice(productDto.getDiscountedPrice())
                .quantity(productDto.getQuantity())
                .addedDate(productDto.getAddedDate())
                .live(productDto.isLive())
                .stock(productDto.isStock()).build();
        return product;


    }


    private ProductDto entityToDto(Product savedUser) {
        ProductDto productDto=ProductDto.builder()
                .productId(savedUser.getProductId())
                .title(savedUser.getTitle())
                .description(savedUser.getDescription())
                .price(savedUser.getPrice())
                .discountedPrice(savedUser.getDiscountedPrice())
                .quantity(savedUser.getQuantity())
                .addedDate(savedUser.getAddedDate())
                .live(savedUser.isLive())
                .stock(savedUser.isStock()).build();
        return productDto;

    }


}
