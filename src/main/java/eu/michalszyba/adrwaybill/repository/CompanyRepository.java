package eu.michalszyba.adrwaybill.repository;

import eu.michalszyba.adrwaybill.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
