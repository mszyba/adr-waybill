package eu.michalszyba.adrwaybill.repository;

import eu.michalszyba.adrwaybill.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
