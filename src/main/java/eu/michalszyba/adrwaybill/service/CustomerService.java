package eu.michalszyba.adrwaybill.service;

import eu.michalszyba.adrwaybill.exception.CustomerIsNotForCompanyException;
import eu.michalszyba.adrwaybill.model.Company;
import eu.michalszyba.adrwaybill.model.Customer;
import eu.michalszyba.adrwaybill.model.User;
import eu.michalszyba.adrwaybill.repository.CompanyRepository;
import eu.michalszyba.adrwaybill.repository.CustomerRepository;
import eu.michalszyba.adrwaybill.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final UserService userService;

    public CustomerService(CustomerRepository customerRepository, UserRepository userRepository, CompanyRepository companyRepository, UserService userService) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.userService = userService;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void save(Customer customer) {
        // check sig-in user and company of user
        User currentUser = userService.getCurrentUser();
        Company currentUserCompany = currentUser.getCompany();
        customer.getCompanies().add(currentUserCompany);

        customerRepository.save(customer);
    }

    public Customer getByIdForCurrentUser(Long id) {
        Company companyForCurrentUser = userService.getCompanyForCurrentUser();
        Optional<Customer> customer = customerRepository.findByIdAndCompaniesEquals(id, companyForCurrentUser);
        return customer.orElseThrow(
                () -> new CustomerIsNotForCompanyException("We can't find customer with id: " + id + " for " + companyForCurrentUser.getEmail()));
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

    public List<Customer> getCustomersOfCompany(Company company) {
        return customerRepository.findAllByCompaniesEquals(company);
    }

    public List<Customer> getCustomersOfCompanyCurrentUser() {
        User user = userService.getCurrentUser();
        Optional<Company> optionalCompany = companyRepository.findByUsersEquals(user);

        return customerRepository.findAllByCompaniesEquals(optionalCompany.get());
    }
}
