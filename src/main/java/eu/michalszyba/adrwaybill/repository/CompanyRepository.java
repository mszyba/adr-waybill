package eu.michalszyba.adrwaybill.repository;

import eu.michalszyba.adrwaybill.model.Company;
import eu.michalszyba.adrwaybill.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByUsersEquals(User user);
}
