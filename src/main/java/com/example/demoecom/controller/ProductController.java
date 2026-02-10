package com.example.demoecom.controller;

import com.example.demoecom.model.Product;
import com.example.demoecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService prodSer;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = prodSer.getAllProd();

        if(products!=null) {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable int id){
        return prodSer.getProductById(id);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        try{
            Product product1 = prodSer.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("product/{id}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int id){
        Product product = prodSer.getProductById(id);
        byte[] imageFile = product.getImageData();

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
    }

    @PutMapping("/product/{prodId}")
    public ResponseEntity<String> updateProduct(@PathVariable int prodId, @RequestPart Product product, @RequestPart MultipartFile imageFile){
        try {
            prodSer.updateProd(prodId, product, imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(product!=null) return new ResponseEntity<>("Updated", HttpStatus.OK);
        else return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/product/{prodId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int prodId){
        Product products = prodSer.getProductById(prodId);
        if(products!=null){
            prodSer.deleteProduct(prodId);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/product/search")
    public ResponseEntity <List<Product>> searchProducts(@RequestParam String keyword){
        System.out.println("Searching with keyword : "+keyword);
        List<Product> products = prodSer.serachProducts(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
