package com.electronicstore.Controller;

import com.electronicstore.dtos.*;
import com.electronicstore.services.FileService;
import com.electronicstore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imagePath;

    @PostMapping("/create")
    public ResponseEntity<ProductDto>createProduct(@RequestBody ProductDto productDto){
        ProductDto productCreated = productService.createProduct(productDto);
        return  new ResponseEntity<>(productCreated, HttpStatus.CREATED);

    }
    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDto>updateProduct(@RequestBody ProductDto productDto,@PathVariable("productId") String productId){
        ProductDto updatedProduct = productService.updateProduct(productDto, productId);
        return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ApiResponseMessage>deleteProduct(@PathVariable("productId") String productId){
        productService.deleteProduct(productId);
        ApiResponseMessage deletedProduct = ApiResponseMessage.builder()
                .message("Deleted Successfully!!")
                .success(true)
                .status(HttpStatus.OK).build();
        return new ResponseEntity<>(deletedProduct,HttpStatus.OK);
    }
    @GetMapping("/getProductById/{productId}")
    public ResponseEntity<ProductDto>getProductById(@PathVariable("productId") String productId){
        ProductDto productById = productService.getProductById(productId);
        return new ResponseEntity<>(productById,HttpStatus.OK);
    }

    @GetMapping("getAll")
    public ResponseEntity<PageableResponse<ProductDto>>getAllProduct(@RequestParam(value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
                                                                     @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
                                                                     @RequestParam(value = "sortby",defaultValue = "title",required = false)String sortBy,
                                                                     @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir

    ){
        PageableResponse<ProductDto> allProduct = productService.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allProduct,HttpStatus.OK);
    }

    @GetMapping("/getLiveProduct")
    public ResponseEntity<PageableResponse<ProductDto>>getAllLiveProduct(@RequestParam(value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
                                                                         @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
                                                                         @RequestParam(value = "sortby",defaultValue = "title",required = false)String sortBy,
                                                                         @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir){
        PageableResponse<ProductDto> allLiveProduct = productService.getAllLiveProduct(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allLiveProduct,HttpStatus.OK);

    }
    @GetMapping("/searchByTitie/{title}")
    public ResponseEntity<PageableResponse<ProductDto>>  getProductByTitle(@PathVariable("title") String title,@RequestParam(value = "pageNumber",defaultValue = "0",required = false)int pageNumber,
                                                             @RequestParam(value = "pageSize",defaultValue = "10",required = false)int pageSize,
                                                             @RequestParam(value = "sortby",defaultValue = "title",required = false)String sortBy,
                                                             @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir)
    {

        PageableResponse<ProductDto> productDtoPageableResponse = productService.searchByTitle(pageNumber, pageSize, sortBy, sortDir, title);

        return new ResponseEntity<>(productDtoPageableResponse,HttpStatus.OK);

    }

//    upload image
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse>uploadProductImage(
            @PathVariable String productId, @RequestParam("productImage")MultipartFile image
            ) throws IOException {
        String fileName = fileService.uploadImage(image, imagePath);
        ProductDto productDto = productService.getProductById(productId);
        productDto.setProductImageName(fileName);
        ProductDto updatedProduct = productService.updateProduct(productDto, productId);
        ImageResponse response = ImageResponse.builder()
                .imageName(fileName)
                .message("Product image is successfully uploaded!!")
                .status(HttpStatus.CREATED)
                .success(true).build();
        return new ResponseEntity<>(response,HttpStatus.CREATED);

    }

    // serve product image

    @GetMapping("/image/{productId}")
    public void serveUserImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        ProductDto productDto = productService.getProductById(productId);
        InputStream resource = fileService.getResource(imagePath, productDto.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource,response.getOutputStream());

    }

}
