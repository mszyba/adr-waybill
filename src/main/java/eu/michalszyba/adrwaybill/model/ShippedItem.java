package eu.michalszyba.adrwaybill.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "shipped_items")
@Getter @Setter @NoArgsConstructor
public class ShippedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: in the future split by UnBaseEntity
    private Long unId;
    private String unNumber;
    private String unNameAndDescription;
    private String unClass;
    private String unPackingGroup;
    private String unLabels;

    private Long packagingId;
    private String packagingCode;
    private String packagingDescription;

    private Integer quantity;
    private Integer volume;
    private Integer points;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "waybill_id")
    private Waybill waybill;
}
