package service;

import model.Customer;

import java.util.*;

public class CustomerService {
    public static final CustomerService instance = new  CustomerService();
    public static Map<String, Customer> allCustomers = new HashMap<>();
    private CustomerService() {}
    public static CustomerService getInstance(){
        return instance;
    }
    public void createACustomer(String firstName, String lastName, String email){
        Customer customer = new Customer(firstName,lastName,email);
        allCustomers.put(email, customer);
    }
    public void addCustomer(Customer customer){
        String email =  customer.getEmail();
        allCustomers.put(email,customer);
    }
    public Collection<Customer> getAllCustomers () {
        return allCustomers.values();
    }
    public Customer getACustomer(String customerEmail){
        return(allCustomers.get(customerEmail));

    }

}
