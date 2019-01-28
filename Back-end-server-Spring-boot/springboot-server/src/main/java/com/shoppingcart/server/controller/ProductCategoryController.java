package com.shoppingcart.server.controller;

import com.shoppingcart.server.model.ProductCategory;
import com.shoppingcart.server.repo.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ProductCategoryController {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @GetMapping("/categories")
    public List<ProductCategory> getAllCustomers() {
        System.out.println("Get all Categories...");

        List<ProductCategory> productCategories = new ArrayList<>();
        productCategoryRepository.findAll().forEach(productCategories::add);

        return productCategories;
    }

    @PostMapping(value = "/categories/create")
    public ProductCategory postCategory(@RequestBody ProductCategory productCategory) {

        ProductCategory _productCategory = productCategoryRepository.save(
                new ProductCategory(productCategory.getName()));
        return _productCategory;
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") long id) {
        System.out.println("Delete Category with ID = " + id + "...");

        productCategoryRepository.deleteById(id);

        return new ResponseEntity<>("Category has been deleted!", HttpStatus.OK);
    }

    @GetMapping(value = "categories/id/{id}")
    public List<ProductCategory> findById(@PathVariable long id) {
        List<ProductCategory> productCategories=new ArrayList<ProductCategory>();
        try {
            Optional<ProductCategory> productCategory = productCategoryRepository.findById(id);
            ProductCategory _productCategory = productCategory.get();
            productCategories.add(_productCategory);
        }
        catch (Exception e){
            //send error if id not in db
            System.out.println(e);
        }
        return productCategories;
    }

    @PutMapping("/categories/update/{id}")
    public ResponseEntity<ProductCategory> updateProductCategory(@PathVariable("id") long id, @RequestBody ProductCategory productCategory) {
        System.out.println("Update Customer with ID = " + id + "...");

        Optional<ProductCategory> productCategoryData = productCategoryRepository.findById(id);

        if (productCategoryData.isPresent()) {
            ProductCategory _productCategory = productCategoryData.get();
            _productCategory.setName(productCategory.getName());
            return new ResponseEntity<>(productCategoryRepository.save(_productCategory), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
