package com.shoppingcart.server.repo;

import com.shoppingcart.server.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Long> {

    @Query("select p from Product p left join p.productCategory pro where pro.id = ?1")
    public List<Product> findByProductCategory(long id);
}
