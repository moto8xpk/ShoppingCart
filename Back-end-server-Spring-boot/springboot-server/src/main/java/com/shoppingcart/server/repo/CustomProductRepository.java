package com.shoppingcart.server.repo;

import com.shoppingcart.server.model.CustomProduct;
import org.springframework.data.repository.CrudRepository;

public interface CustomProductRepository extends CrudRepository<CustomProduct,Long> {

}
