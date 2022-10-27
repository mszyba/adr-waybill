package eu.michalszyba.adrwaybill.controller;

import com.lowagie.text.DocumentException;
import eu.michalszyba.adrwaybill.model.*;
import eu.michalszyba.adrwaybill.service.*;
import eu.michalszyba.adrwaybill.util.PDFGeneratorWaybill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/waybill")
public class WaybillController {

    private final CompanyService companyService;
    private final CustomerService customerService;
    private final WaybillService waybillService;
    private final UnService unService;
    private final ShippedItemService shippedItemService;

    public WaybillController(CompanyService companyService, CustomerService customerService, WaybillService waybillService, UnService unService, ShippedItemService shippedItemService) {
        this.companyService = companyService;
        this.customerService = customerService;
        this.waybillService = waybillService;
        this.unService = unService;
        this.shippedItemService = shippedItemService;
    }

//    ToDo: need add only waybills for Company
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
        model.addAttribute("shippedItem", new ShippedItem());
        model.addAttribute("filledShippedItem", shippedItemService.getShippedItems());
        model.addAttribute("company", companyService.getCompanyCurrentUser());
        return "waybill/waybill-details";
    }

    @GetMapping(value = "/customer/autocomplete")
    @ResponseBody
    public List<Customer> customerAutocomplete(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
        return customerService.getAutocompleteForCurrentUser(term);
    }

    @GetMapping(value = "/un/autocomplete")
    @ResponseBody
    public List<Un> unAutocomplete(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
        return unService.getAllUnByUnNumber(term);
    }

    @PostMapping(value = "/addShippedItem", params = {"addShippedItem"})
    public String addShippedItem(ShippedItem shippedItem) {
        shippedItemService.addToList(shippedItem);
        return "redirect:/waybill/add";
    }

    @PostMapping(value = "/add", params = {"saveForm"})
    public String postAddWaybill(@ModelAttribute Waybill waybill) {
        Company companyCurrentUser = companyService.getCompanyCurrentUser();

        // get List of ShippedItems from form
        List<ShippedItem> shippedItems = shippedItemService.getShippedItems();

        // set List of shippedItems to Waybill
        waybill.setShippedItems(shippedItemService.getShippedItems());

        // set only Company for current User
        waybill.setCompanyId(companyCurrentUser.getId());

        // save Waybill form
        waybillService.save(waybill);

        // clear List of Shipped Items to clear form
        shippedItems.clear();
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
