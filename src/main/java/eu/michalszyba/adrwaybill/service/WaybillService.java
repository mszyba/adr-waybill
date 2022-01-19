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

    public WaybillService(WaybillRepository waybillRepository, CompanyRepository companyRepository, CustomerRepository customerRepository) {
        this.waybillRepository = waybillRepository;
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
    }

    public List<Waybill> getAllWaybill() {
        return waybillRepository.findAll();
    }

    public void save(Waybill waybill) {
        waybill.getShippedItems()
                .forEach(shippedItem -> {
                    shippedItem.setWaybill(waybill);
                });

        waybillRepository.save(waybill);
    }

    public void addShippedItem(Waybill waybill) {
        waybill.getShippedItems().add(new ShippedItem());
    }
}
