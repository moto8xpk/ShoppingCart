package com.shoppingcart.server.repo;

import com.shoppingcart.server.model.ProductCategory;
import org.springframework.data.repository.CrudRepository;

public interface ProductCategoryRepository extends CrudRepository<ProductCategory,Long> {

}
