package com.priyanshu.ecomproject.service;

import com.priyanshu.ecomproject.model.Product;
import com.priyanshu.ecomproject.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(Integer id) {
        return productRepo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile file) throws IOException {
        product.setImageName(file.getOriginalFilename());
        product.setImageType(file.getContentType());
        product.setImageData(file.getBytes());
        return productRepo.save(product);
    }
}
