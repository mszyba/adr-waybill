package eu.michalszyba.adrwaybill.controller;

import eu.michalszyba.adrwaybill.model.Company;
import eu.michalszyba.adrwaybill.model.Customer;
import eu.michalszyba.adrwaybill.service.CompanyService;
import eu.michalszyba.adrwaybill.service.CustomerService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class HomeController {

    private final CustomerService customerService;
    private final CompanyService companyService;

    public HomeController(CustomerService customerService, CompanyService companyService) {
        this.customerService = customerService;
        this.companyService = companyService;
    }

    @ModelAttribute("customers")
    public List<Customer> populateCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(path = {"/", "/home"})
    public String home(Authentication authentication, Model model) {
        System.out.println(authentication.getName() + "========");
        System.out.println(authentication.getAuthorities() + "0000000========");
        System.out.println(authentication.getDetails() + "+++++++++++++++");
        Company company = companyService.getById(1L);
        for (Customer customer : customerService.getCustomersOfCompany(company)) {
            System.out.println(customer.getCustomerName() + "********");
        }

        List<Customer> customersOfCompanyCurrentUser = customerService.getCustomersOfCompanyCurrentUser();

        model.addAttribute("customers2", customersOfCompanyCurrentUser);

        return "home";
    }

    public String currentUser(Authentication authentication) {
        System.out.println(authentication.getDetails() + "===========");
        return authentication.getName();
    }
}
