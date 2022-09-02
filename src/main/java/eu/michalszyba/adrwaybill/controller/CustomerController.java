package eu.michalszyba.adrwaybill.controller;

import eu.michalszyba.adrwaybill.exception.CustomerIsNotForCompanyException;
import eu.michalszyba.adrwaybill.model.Customer;
import eu.michalszyba.adrwaybill.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ModelAttribute("customers")
    public List<Customer> populateCustomers() {
        return customerService.getCustomersOfCompanyCurrentUser();
    }

    @GetMapping("/list")
    public String listCustomers() {
        return "customer/customer-list";
    }

    @GetMapping("/add")
    public String getAddCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/customer-details";
    }

    @PostMapping("/add")
    public String postAddCustomer(@ModelAttribute Customer customer, Model model) {
        model.addAttribute("customer", customer);
        customerService.save(customer);
        return "redirect:/customer/list";
    }

    @GetMapping("/edit/{id}")
    public String editCustomerById(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("customer", customerService.getByIdForCurrentUser(id));
            return "customer/customer-details";
        } catch (CustomerIsNotForCompanyException e) {
            log.warn(e.getMessage());
            return "redirect:/customer/list";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomerById(@PathVariable Long id) {
        if (customerService.getByIdForCurrentUser(id) == null) {
            return "redirect:/customer/list";
        } else {
            customerService.deleteById(id);
            return "redirect:/customer/list";
        }
    }
}
