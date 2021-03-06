package eu.michalszyba.adrwaybill.service;

import eu.michalszyba.adrwaybill.model.*;
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
        * first start with 0 point for each class
        * */
        var ref = new Object() {
            Integer pointsClass1 = 0;
            Integer pointsClass2 = 0;
            Integer pointsClass3 = 0;
            Integer pointsAll = 0;
        };


        /*
        * check each shippedItems and add fixed data from Un
        * can't use relation table, need fix data
        * */
        waybill.getShippedItems()
                .forEach(shippedItem -> {

                    Un unById = unService.getUnById(shippedItem.getUnId());

                    String unPackingGroup = unById.getUnPackingGroup();
                    Integer quantity = shippedItem.getQuantity();

                    /*
                     * check each Packing Group and add for each result of points
                     * */
                    switch (unPackingGroup) {
                        case "I":
                            ref.pointsClass1 += 6 * quantity;
                            ref.pointsAll += ref.pointsClass1;
                            break;
                        case "II":
                            ref.pointsClass2 += 3 * quantity;
                            ref.pointsAll += ref.pointsClass2;
                            break;
                        case "III":
                            ref.pointsClass3 += quantity;
                            ref.pointsAll += ref.pointsClass3;
                            break;
                    }

                    /*
                    * set points
                    * */
                    waybill.setPointClass1(ref.pointsClass1);
                    waybill.setPointClass2(ref.pointsClass2);
                    waybill.setPointClass3(ref.pointsClass3);
                    waybill.setPoints(ref.pointsAll);


                    /*
                    * set fixed un variables
                    * */
                    shippedItem.setUnId(unById.getId());
                    shippedItem.setUnNameAndDescription(unById.getUnNameAndDescription());
                    shippedItem.setUnNumber(unById.getUnNumber());
                    shippedItem.setUnLabels(unById.getUnLabels());
                    shippedItem.setUnPackingGroup(unById.getUnPackingGroup());

                    /*
                    * save all to shipped_items
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
