package eu.michalszyba.adrwaybill.service;

import eu.michalszyba.adrwaybill.model.Company;
import eu.michalszyba.adrwaybill.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
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
}
