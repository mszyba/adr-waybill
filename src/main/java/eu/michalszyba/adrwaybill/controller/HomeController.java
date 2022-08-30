package eu.michalszyba.adrwaybill.controller;

import eu.michalszyba.adrwaybill.model.Customer;
import eu.michalszyba.adrwaybill.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class HomeController {

    private final CustomerService customerService;

    public HomeController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ModelAttribute("customers")
    public List<Customer> populateCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(path = {"/", "/home"})
    public String home() {
        return "home";
    }
}
