package eu.michalszyba.adrwaybill.service;

import eu.michalszyba.adrwaybill.model.ShippedItem;
import eu.michalszyba.adrwaybill.model.Un;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShippedItemService {

    private final UnService unService;

    List<ShippedItem> shippedItemList = new ArrayList<>();

    public ShippedItemService(UnService unService) {
        this.unService = unService;
    }

    public void addToList(ShippedItem shippedItem) {

        Un unById = unService.getUnById(shippedItem.getUnId());
        /*
         * set fixed un variables
         * */
        shippedItem.setUnId(unById.getId());
        shippedItem.setUnNameAndDescription(unById.getUnNameAndDescription());
        shippedItem.setUnNumber(unById.getUnNumber());
        shippedItem.setUnLabels(unById.getUnLabels());
        shippedItem.setUnPackingGroup(unById.getUnPackingGroup());

        shippedItemList.add(shippedItem);
    }

    public List<ShippedItem> getShippedItems() {
        return shippedItemList;
    }
}
