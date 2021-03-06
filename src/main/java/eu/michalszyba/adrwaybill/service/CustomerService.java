package eu.michalszyba.adrwaybill.service;

import eu.michalszyba.adrwaybill.model.Customer;
import eu.michalszyba.adrwaybill.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer getById(Long id) {
        return customerRepository.findById(id)
                .orElse(null);
    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}
