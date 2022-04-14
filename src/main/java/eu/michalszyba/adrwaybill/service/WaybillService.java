package eu.michalszyba.adrwaybill.service;

import eu.michalszyba.adrwaybill.model.*;
import eu.michalszyba.adrwaybill.repository.CompanyRepository;
import eu.michalszyba.adrwaybill.repository.CustomerRepository;
import eu.michalszyba.adrwaybill.repository.WaybillRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class WaybillService {

    private final WaybillRepository waybillRepository;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final CompanyService companyService;
    private final CustomerService customerService;
    private final UnService unService;

    public WaybillService(WaybillRepository waybillRepository, CompanyRepository companyRepository, CustomerRepository customerRepository, CompanyService companyService, CustomerService customerService, UnService unService) {
        this.waybillRepository = waybillRepository;
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.companyService = companyService;
        this.customerService = customerService;
        this.unService = unService;
    }

    public List<Waybill> getAllWaybill() {
        return waybillRepository.findAll();
    }

    public void save(Waybill waybill) {

        /*
        * first start with 0 points
        * */
        var ref = new Object() {
            int pointsAll = 0;
        };

        /*
        * check each shippedItems and add fixed data from Un
        * can't use relation table, need fix data
        * */
        waybill.getShippedItems()
                .forEach(shippedItem -> {
                    // get points for each Shipped Items
                    int shippedItemPoints = shippedItem.getPoints();

                    // add to all waybill points
                    ref.pointsAll += shippedItemPoints;

                    // and save to waybill
                    waybill.setPoints(ref.pointsAll);

                    /*
                    * save Shipped Item to Waybill
                    * */
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
