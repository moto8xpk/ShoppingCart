package com.shoppingcart.server.controller;

import Service.CustomerService;
import com.shoppingcart.server.model.Customer;
import com.shoppingcart.server.repo.CustomerRepository;
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
public class CustomerController {


    @Autowired
    CustomerRepository repository;


    CustomerService customerService=new CustomerService();

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        System.out.println("Get all Customers...");

        List<Customer> customers = new ArrayList<>();
        repository.findAll().forEach(customers::add);

        return customers;
    }

    @PostMapping(value = "/customers/create")
    public Customer postCustomer(@RequestBody Customer customer) {

        Customer _customer = repository.save(
                new Customer(customer.getName(), customer.getUsername(),
                             customer.getPassword(),customer.getAddress(),
                             customer.getPhone(),customer.getEmail()));
        return _customer;
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") long id) {
        System.out.println("Delete Customer with ID = " + id + "...");

        repository.deleteById(id);

        return new ResponseEntity<>("Customer has been deleted!", HttpStatus.OK);
    }

    @DeleteMapping("/customers/delete")
    public ResponseEntity<String> deleteAllCustomers() {
        System.out.println("Delete All Customers...");

        repository.deleteAll();

        return new ResponseEntity<>("All customers have been deleted!", HttpStatus.OK);
    }

    @GetMapping(value = "customers/id/{id}")
    public List<Customer> findById(@PathVariable long id) {
        List<Customer> customers=new ArrayList<Customer>();
        try {
            Optional<Customer> customerChoice = repository.findById(id);
            Customer _customer = customerChoice.get();
            customers.add(_customer);
        }
        catch (Exception e){
            //send error if id not in db
            System.out.println(e);
        }
        return customers;
    }

    @PostMapping(value = "customers/user")
    public Customer findUser(@RequestBody Customer customer) {
        Customer customerChoice =new Customer();
        try {
            customerChoice = repository.findByUsername(customer.getUsername());

            if (customerChoice==null){
                return null;
            }

            if (customerChoice!=null){
                if(this.customerService.compareAccount(customer,customerChoice)){
                    return customerChoice;
                }
            }
        }
        catch (Exception e){
            //send error if user not in db
            System.out.println(e);
        }
        return null;
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
        System.out.println("Update Customer with ID = " + id + "...");

        Optional<Customer> customerData = repository.findById(id);

        if (customerData.isPresent()) {
            Customer _customer = customerData.get();
            _customer.setName(customer.getName());
            _customer.setUsername(customer.getUsername());
            _customer.setAddress(customer.getAddress());
            _customer.setEmail(customer.getEmail());
            _customer.setPhone(customer.getPhone());
            return new ResponseEntity<>(repository.save(_customer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
