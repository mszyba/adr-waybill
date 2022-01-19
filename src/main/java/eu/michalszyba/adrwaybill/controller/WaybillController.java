package eu.michalszyba.adrwaybill.controller;

import eu.michalszyba.adrwaybill.model.Company;
import eu.michalszyba.adrwaybill.model.Customer;
import eu.michalszyba.adrwaybill.model.Waybill;
import eu.michalszyba.adrwaybill.service.CompanyService;
import eu.michalszyba.adrwaybill.service.CustomerService;
import eu.michalszyba.adrwaybill.service.WaybillService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/waybill")
public class WaybillController {

    private final CompanyService companyService;
    private final CustomerService customerService;
    private final WaybillService waybillService;

    public WaybillController(CompanyService companyService, CustomerService customerService, WaybillService waybillService) {
        this.companyService = companyService;
        this.customerService = customerService;
        this.waybillService = waybillService;
    }

    @ModelAttribute("customers")
    public List<Customer> populateCustomer() {
        return customerService.getAllCustomers();
    }

    @ModelAttribute("companies")
    public List<Company> populateCompany() {
        return companyService.getAllCompany();
    }

    @GetMapping("/list")
    public String listWaybill() {
        return "waybill/waybill-list";
    }

    @GetMapping("/add")
    public String getAddWaybillForm(Model model) {
        model.addAttribute("waybill", new Waybill());
        return "waybill/waybill-details";
    }

    @RequestMapping(value = "/add", params = {"addShippedItem"})
    public String addShippedItem(Waybill waybill) {
        waybillService.addShippedItem(waybill);
        return "/waybill/waybill-details";
    }

    @PostMapping(value = "/add", params = {"saveForm"})
    public String postAddWaybill(@ModelAttribute Waybill waybill) {
        waybillService.save(waybill);
        return "redirect:/home";
    }
}
