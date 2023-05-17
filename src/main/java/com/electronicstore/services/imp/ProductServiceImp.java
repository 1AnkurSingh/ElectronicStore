package com.electronicstore.services.imp;

import com.electronicstore.dtos.PageableResponse;
import com.electronicstore.dtos.ProductDto;
import com.electronicstore.entity.Product;
import com.electronicstore.exception.ResourceNotFoundException;
import com.electronicstore.repository.ProductRepository;
import com.electronicstore.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    ProductRepository productRepository;



    @Override
    public ProductDto createProduct(ProductDto productDto) {

        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);

        Product product = mapper.map(productDto, Product.class);
        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct, ProductDto.class);

//        Product product=dtoToEntity(productDto);
//        Product savedUser = productRepository.save(product);
//        ProductDto newDto=entityToDto(savedUser);
//
//        return newDto;

    }


    @Override
    public ProductDto product(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product id not found!!"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setAddedDate(productDto.getAddedDate());
        product.setLive(product.isLive());
        product.setStock(product.isStock());
        Product updatedProduct = productRepository.save(product);

        return entityToDto(updatedProduct);
    }

    @Override
    public void deleteProduct(String productId) {

    }

    @Override
    public ProductDto getProductById(String productId) {
        return null;
    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {
        return null;
    }

    @Override
    public List<ProductDto> getAllLiveProduct() {
        return null;
    }

    @Override
    public List<ProductDto> searchByTitle(String title) {
        return null;
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
