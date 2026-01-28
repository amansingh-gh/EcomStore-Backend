package com.example.demoecom.service;

import com.example.demoecom.model.Product;
import com.example.demoecom.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo prodRepo;

    public List<Product> getAllProd() {
        return prodRepo.findAll();
    }

    public Product getProductById(int id) {
        return prodRepo.findById(id).orElse(new Product());
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return prodRepo.save(product);
    }

    public void updateProd(int id, Product product, MultipartFile imageFile) throws IOException {
        product.setImageData(imageFile.getBytes());
        product.setImageType(imageFile.getContentType());
        product.setImageName(imageFile.getOriginalFilename());
        prodRepo.save(product);
    }

    public void deleteProduct(int prodId) {
        prodRepo.deleteById(prodId);
    }

    public List<Product> serachProducts(String keyword){
        return prodRepo.serachProducts(keyword);
    }
}
