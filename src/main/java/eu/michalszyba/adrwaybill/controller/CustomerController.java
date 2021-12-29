package eu.michalszyba.adrwaybill.controller;

import eu.michalszyba.adrwaybill.model.Customer;
import eu.michalszyba.adrwaybill.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ModelAttribute("customers")
    public List<Customer> populateCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/list")
    public String listCustomers() {
        return "customer/customer-list";
    }
}
