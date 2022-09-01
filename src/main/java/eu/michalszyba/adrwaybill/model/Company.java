package eu.michalszyba.adrwaybill.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "companies")
@Getter @Setter @NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String VatNumber;
    private String address;
    private String postcode;
    private String city;
    private String country;
    private String email;
    private String phone;
    private boolean isActive = true;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    @ManyToMany(mappedBy = "companies")
    private Set<Customer> customers = new HashSet<>();
}
