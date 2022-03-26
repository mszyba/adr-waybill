package eu.michalszyba.adrwaybill.service;

import eu.michalszyba.adrwaybill.model.Company;
import eu.michalszyba.adrwaybill.model.Customer;
import eu.michalszyba.adrwaybill.model.ShippedItem;
import eu.michalszyba.adrwaybill.model.Waybill;
import eu.michalszyba.adrwaybill.repository.CompanyRepository;
import eu.michalszyba.adrwaybill.repository.CustomerRepository;
import eu.michalszyba.adrwaybill.repository.WaybillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaybillService {

    private final WaybillRepository waybillRepository;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final CompanyService companyService;
    private final CustomerService customerService;

    public WaybillService(WaybillRepository waybillRepository, CompanyRepository companyRepository, CustomerRepository customerRepository, CompanyService companyService, CustomerService customerService) {
        this.waybillRepository = waybillRepository;
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.companyService = companyService;
        this.customerService = customerService;
    }

    public List<Waybill> getAllWaybill() {
        return waybillRepository.findAll();
    }

    public void save(Waybill waybill) {
        waybill.getShippedItems()
                .forEach(shippedItem -> {
                    shippedItem.setWaybill(waybill);
                });

        Company companyById = companyService.getById(waybill.getCompanyId());
        Customer customerById = customerService.getById(waybill.getCustomerId());

        waybill.setCompanyAddress(companyById.getAddress());
        waybill.setCompanyCity(companyById.getCity());
        waybill.setCompanyCountry(companyById.getCountry());
        waybill.setCompanyName(companyById.getCompanyName());
        waybill.setCompanyPostcode(companyById.getPostcode());

        waybill.setCustomerAddress(customerById.getAddress());
        waybill.setCustomerCity(customerById.getCity());
        waybill.setCustomerCountry(customerById.getCountry());
        waybill.setCustomerName(customerById.getCustomerName());
        waybill.setCustomerPostcode(customerById.getPostcode());

        waybillRepository.save(waybill);
    }

    public void addShippedItem(Waybill waybill) {
        waybill.getShippedItems().add(new ShippedItem());
    }

    public Waybill getById(Long id) {
        return waybillRepository.findById(id)
                .orElse(null);
    }

    public void deleteById(Long id) {
        waybillRepository.deleteById(id);
    }
}
