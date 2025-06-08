package com.priyanshu.ecomproject.controller;

import com.priyanshu.ecomproject.model.Product;
import com.priyanshu.ecomproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/")
    public String testing(){
        return "testing";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        return new ResponseEntity<>(productService.getAllProducts(),HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id){
        Product product =   productService.getProductById(id);
        if(product == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product,HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestPart Product product,
                                              @RequestPart MultipartFile file){
        try{
            Product addproduct = productService.addProduct(product, file);
            return new ResponseEntity<>(addproduct,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Integer id){
        Product product = productService.getProductById(id);
        if(product == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            byte[] imageFile = product.getImageData();
            if(imageFile == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                return ResponseEntity
                        .ok()
                        .contentType(
                                MediaType.valueOf(product.getImageType())
                        )
                        .body(imageFile);
            }
        }
    }
}
