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
}
