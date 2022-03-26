package eu.michalszyba.adrwaybill.repository;

import eu.michalszyba.adrwaybill.model.Un;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnRepository extends JpaRepository<Un, Long> {
}
