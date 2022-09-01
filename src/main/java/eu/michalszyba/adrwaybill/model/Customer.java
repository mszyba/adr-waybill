package eu.michalszyba.adrwaybill.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customers")
@Getter @Setter @NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String VatNumber;
    private String address;
    private String postcode;
    private String city;
    private String country;
    private String email;
    private String phone;
    private boolean isActive = true;

    @ManyToMany
    @JoinTable(
            name = "customers_companies",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id"))
    private Set<Company> companies = new HashSet<>();
}
