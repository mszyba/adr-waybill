package eu.michalszyba.adrwaybill.service;

import eu.michalszyba.adrwaybill.model.ShippedItem;
import eu.michalszyba.adrwaybill.model.Un;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShippedItemService {

    private final UnService unService;
    private final static int FACTOR_GROUP_I = 6;
    private final static int FACTOR_GROUP_II = 3;
    private final static int FACTOR_GROUP_III = 1;

    List<ShippedItem> shippedItemList = new ArrayList<>();

    public ShippedItemService(UnService unService) {
        this.unService = unService;
    }

    public void addToList(ShippedItem shippedItem) {

        int points = 0;

        int quantity = shippedItem.getQuantity();
        int volume = shippedItem.getVolume();

        Un unById = unService.getUnById(shippedItem.getUnId());

        // it should be I, II or III
        String unPackingGroup = unById.getUnPackingGroup();

        // check Packing Group and multiply by factor
        switch (unPackingGroup) {
            case "I":
                points += quantity * volume * FACTOR_GROUP_I;
                break;
            case "II":
                points += quantity * volume * FACTOR_GROUP_II;
                break;
            case "III":
                points += quantity * volume * FACTOR_GROUP_III;
                break;
        }

        /*
         * set fixed un variables
         * */
        shippedItem.setUnId(unById.getId());
        shippedItem.setUnNameAndDescription(unById.getUnNameAndDescription());
        shippedItem.setUnNumber(unById.getUnNumber());
        shippedItem.setUnLabels(unById.getUnLabels());
        shippedItem.setUnPackingGroup(unById.getUnPackingGroup());
        shippedItem.setPoints(points);

        shippedItemList.add(shippedItem);
    }

    public List<ShippedItem> getShippedItems() {
        return shippedItemList;
    }
}
