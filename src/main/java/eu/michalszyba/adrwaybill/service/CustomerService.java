package eu.michalszyba.adrwaybill.service;

import eu.michalszyba.adrwaybill.exception.CustomerIsNotForCompanyException;
import eu.michalszyba.adrwaybill.model.Company;
import eu.michalszyba.adrwaybill.model.Customer;
import eu.michalszyba.adrwaybill.model.User;
import eu.michalszyba.adrwaybill.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final CompanyService companyService;

    public CustomerService(CustomerRepository customerRepository, UserService userService, CompanyService companyService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
        this.companyService = companyService;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void save(Customer customer) {
        // check sing-in user and company of user
        User currentUser = userService.getCurrentUser();
        Company currentUserCompany = currentUser.getCompany();
        customer.getCompanies().add(currentUserCompany);

        customerRepository.save(customer);
    }

    public Customer getByIdForCurrentUser(Long id) {
        Company companyForCurrentUser = companyService.getCompanyCurrentUser();
        Optional<Customer> customer = customerRepository.findByIdAndCompaniesEquals(id, companyForCurrentUser);
        return customer.orElseThrow(
                () -> new CustomerIsNotForCompanyException("We can't find customer with id: " + id + " for " + companyForCurrentUser.getEmail()));
    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    public List<Customer> getAutocompleteForCurrentUser(String term) {
        Company companyCurrentUser = companyService.getCompanyCurrentUser();

        return customerRepository
                .findCustomersByCustomerNameContainsAndCompaniesEquals(term, companyCurrentUser)
                .stream()
                .limit(10) // return max 10 results
                .collect(Collectors.toList());
    }

    public List<Customer> getCustomersOfCompanyCurrentUser() {
        Company companyCurrentUser = companyService.getCompanyCurrentUser();
        return customerRepository.findAllByCompaniesEquals(companyCurrentUser);
    }
}
