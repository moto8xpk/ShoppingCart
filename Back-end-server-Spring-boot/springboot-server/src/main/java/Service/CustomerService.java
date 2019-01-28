package Service;

import com.shoppingcart.server.model.Customer;

public class CustomerService {
    public boolean compareAccount(Customer customer1,Customer customer2){
        return customer1.getUsername().equals(customer2.getUsername())
                &&customer1.getPassword().equals(customer2.getPassword());
    }
}
