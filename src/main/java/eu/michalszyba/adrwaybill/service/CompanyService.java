package eu.michalszyba.adrwaybill.service;

import eu.michalszyba.adrwaybill.model.Company;
import eu.michalszyba.adrwaybill.model.User;
import eu.michalszyba.adrwaybill.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserService userService;

    public CompanyService(CompanyRepository companyRepository, UserService userService) {
        this.companyRepository = companyRepository;
        this.userService = userService;
    }

    public List<Company> getAllCompany() {
        return companyRepository.findAll();
    }

    public void save(Company company) {
        companyRepository.save(company);
    }

    public Company getById(Long id) {
        return companyRepository.findById(id)
                .orElse(null);
    }

    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }

    public Company getCompanyCurrentUser() {
        User user = userService.getCurrentUser();
        return companyRepository
                .findByUsersEquals(user)
                .orElseThrow(
                        () -> new NoSuchElementException("We can't find Company for username: " + user.getEmail()));
    }
}
