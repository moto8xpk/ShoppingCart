package com.shoppingcart.server.repo;

import com.shoppingcart.server.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
    public Customer findByUsername(String username);
}
