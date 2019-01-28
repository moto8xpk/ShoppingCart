package com.shoppingcart.server.controller;


import com.shoppingcart.server.model.CustomProduct;
import com.shoppingcart.server.model.Product;
import com.shoppingcart.server.repo.CustomProductRepository;
import com.shoppingcart.server.repo.ProductRepository;
import com.shoppingcart.server.repo.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ProductController {

    public static List<String> files = new ArrayList<String>();

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StorageService storageService;

    @Autowired
    CustomProductRepository customProductRepository;

    @GetMapping("/products")
    public List<Product> getAllCustomers() {
        System.out.println("Get all products...");

        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);

        return products;
    }

    @PostMapping(value = "/products/create")
    public Product postCategory(@RequestBody Product product) {

        Product _product = productRepository.save(
                new Product(product.getName(),product.getProductCategory(),
                            product.getPrice(),product.getDesc(),product.getImagelink()));
        return _product;
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") long id) {
        System.out.println("Delete product with ID = " + id + "...");

        productRepository.deleteById(id);

        return new ResponseEntity<>("Product has been deleted!", HttpStatus.OK);
    }

    @GetMapping(value = "products/id/{id}")
    public List<Product> findById(@PathVariable long id) {
        List<Product> products=new ArrayList<Product>();
        try {
            Optional<Product> product = productRepository.findById(id);
            Product _product = product.get();
            products.add(_product);
        }
        catch (Exception e){
            //send error if id not in db
            System.out.println(e);
        }
        return products;
    }

    @GetMapping(value = "products/cate/{id}")
    public List<Product> findByCategory(@PathVariable long id) {
        List<Product> products=new ArrayList<Product>();
        try {
            products  = productRepository.findByProductCategory(id);
        }
        catch (Exception e){
            //send error if id not in db
            System.out.println(e);
        }
        return products;
    }

    @PutMapping("/products/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
        System.out.println("Update product with ID = " + id + "...");
//        product.setImagelink("");
        Optional<Product> productData = productRepository.findById(id);
        Optional<CustomProduct> CustomProductData = customProductRepository.findById(id);

        if (productData.isPresent()) {
            Product _product = productData.get();

//            CustomProduct _customProduct=CustomProductData.get();
            _product.setName(product.getName());
            _product.setProductCategory(product.getProductCategory());
            _product.setDesc(product.getDesc());
            _product.setPrice(product.getPrice());
            _product.setImagelink(product.getImagelink());

//            _customProduct.setName(product.getName());
//            _customProduct.setCateid(product.getProductCategory().getId());
//            _customProduct.setDesc(product.getDesc());
//            _customProduct.setPrice(product.getPrice());
//            _customProduct.setImagelink(product.getImagelink());

            return new ResponseEntity<>(productRepository.save(_product), HttpStatus.OK);
//            return new ResponseEntity<>(customProductRepository.save(_customProduct), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }




}
