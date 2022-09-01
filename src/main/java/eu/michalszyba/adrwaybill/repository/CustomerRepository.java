package eu.michalszyba.adrwaybill.repository;

import eu.michalszyba.adrwaybill.model.Company;
import eu.michalszyba.adrwaybill.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAllByCompaniesEquals(Company company);
}
