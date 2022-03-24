package eu.michalszyba.adrwaybill.controller;

import eu.michalszyba.adrwaybill.model.Company;
import eu.michalszyba.adrwaybill.model.Customer;
import eu.michalszyba.adrwaybill.service.CompanyService;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/edit/{id}")
    public String editCompanyById(@PathVariable Long id, Model model) {
        if (companyService.getById(id) == null) {
            return "redirect:/admin/company/list";
        } else {
            model.addAttribute("company", companyService.getById(id));
            return "company/company-details";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteCompanyById(@PathVariable Long id) {
        if (companyService.getById(id) == null) {
            return "redirect:/admin/company/list";
        } else {
            companyService.deleteById(id);
            return "redirect:/admin/company/list";
        }
    }
}
