package eu.michalszyba.adrwaybill.controller;

import com.lowagie.text.DocumentException;
import eu.michalszyba.adrwaybill.model.Company;
import eu.michalszyba.adrwaybill.model.Customer;
import eu.michalszyba.adrwaybill.model.Un;
import eu.michalszyba.adrwaybill.model.Waybill;
import eu.michalszyba.adrwaybill.service.CompanyService;
import eu.michalszyba.adrwaybill.service.CustomerService;
import eu.michalszyba.adrwaybill.service.UnService;
import eu.michalszyba.adrwaybill.service.WaybillService;
import eu.michalszyba.adrwaybill.util.PDFGenerator;
import eu.michalszyba.adrwaybill.util.PDFGeneratorWaybill;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/waybill")
public class WaybillController {

    private final CompanyService companyService;
    private final CustomerService customerService;
    private final WaybillService waybillService;
    private final UnService unService;

    public WaybillController(CompanyService companyService, CustomerService customerService, WaybillService waybillService, UnService unService) {
        this.companyService = companyService;
        this.customerService = customerService;
        this.waybillService = waybillService;
        this.unService = unService;
    }

    @ModelAttribute("customers")
    public List<Customer> populateCustomer() {
        return customerService.getAllCustomers();
    }

    @ModelAttribute("companies")
    public List<Company> populateCompany() {
        return companyService.getAllCompany();
    }

    @ModelAttribute("waybills")
    public List<Waybill> populateWaybill() {
        return waybillService.getAllWaybill();
    }

    @ModelAttribute("uns")
    public List<Un> populateUn() {
        return unService.getAllUn();
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
        return "redirect:/waybill/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteWaybillById(@PathVariable Long id) {
        if (waybillService.getById(id) == null) {
            return "redirect:/waybill/list";
        } else {
            waybillService.deleteById(id);
            return "redirect:/waybill/list";
        }
    }

    @GetMapping("/pdf/{id}")
    public void generatePdf(HttpServletResponse response, @PathVariable Long id) throws DocumentException, IOException {

        response.setContentType("application/pdf");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");

        String currentDateTime = dateFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        Waybill waybill = waybillService.getById(id);

        PDFGeneratorWaybill pdfGeneratorWaybill = new PDFGeneratorWaybill();

        pdfGeneratorWaybill.setWaybill(waybill);
        pdfGeneratorWaybill.generate(response);
    }
}
