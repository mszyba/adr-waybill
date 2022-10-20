package eu.michalszyba.adrwaybill.service;

import eu.michalszyba.adrwaybill.exception.CustomerIsNotForCompanyException;
import eu.michalszyba.adrwaybill.model.Company;
import eu.michalszyba.adrwaybill.model.Customer;
import eu.michalszyba.adrwaybill.model.User;
import eu.michalszyba.adrwaybill.repository.CompanyRepository;
import eu.michalszyba.adrwaybill.repository.CustomerRepository;
import eu.michalszyba.adrwaybill.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

//    public List<Customer> getAutocomplete(String term) {
//        return customerRepository.findAll().stream().filter(
//                s -> s
//                        .getCustomerName()
//                        .toLowerCase(Locale.ROOT)
//                        .contains(term.toLowerCase(Locale.ROOT))).collect(Collectors.toList());
//    }
//
//    public List<Customer> getAutocomplete2(String term) {
//        return customerRepository.findCustomersByCustomerNameContains(term);
//    }

    public List<Customer> getAutocompleteForCurrentUser(String term) {
        Company companyCurrentUser = companyService.getCompanyCurrentUser();
        return customerRepository
                .findCustomersByCustomerNameContainsAndCompaniesEquals(term, companyCurrentUser);

    }

    public List<Customer> getCustomersOfCompany(Company company) {
        return customerRepository.findAllByCompaniesEquals(company);
    }

    public List<Customer> getCustomersOfCompanyCurrentUser() {
        Company companyCurrentUser = companyService.getCompanyCurrentUser();

//        User user = userService.getCurrentUser();
//        Company company = companyRepository
//                .findByUsersEquals(user)
//                .orElseThrow(
//                        () -> new NoSuchElementException("We can't find Company for username: " + user.getEmail()));

        return customerRepository.findAllByCompaniesEquals(companyCurrentUser);
    }
}
