package eu.michalszyba.adrwaybill.service;

import eu.michalszyba.adrwaybill.model.Customer;
import eu.michalszyba.adrwaybill.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

    public List<Customer> getAutocomplete(String term) {

        return customerRepository.findAll().stream().filter(
                s -> s
                        .getCustomerName()
                        .toLowerCase(Locale.ROOT)
                        .contains(term.toLowerCase(Locale.ROOT))).collect(Collectors.toList());

    }
}
