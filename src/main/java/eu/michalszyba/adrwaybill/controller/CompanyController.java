package eu.michalszyba.adrwaybill.controller;

import eu.michalszyba.adrwaybill.model.Company;
import eu.michalszyba.adrwaybill.model.Customer;
import eu.michalszyba.adrwaybill.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @ModelAttribute("companies")
    public List<Company> populateCompanies() {
        return companyService.getAllCompany();
    }

    @GetMapping("/list")
    public String listCompany() {
        return "company/company-list";
    }

    @GetMapping("/add")
    public String getAddCompanyForm(Model model) {
        model.addAttribute("company", new Company());
        return "company/company-details";
    }

    @PostMapping("/add")
    public String postAddCompany(@ModelAttribute Company company, Model model) {
        model.addAttribute("company", company);
        companyService.save(company);
        return "redirect:/admin/company/list";
    }
}
