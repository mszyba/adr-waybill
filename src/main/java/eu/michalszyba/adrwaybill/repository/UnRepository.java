package eu.michalszyba.adrwaybill.repository;

import eu.michalszyba.adrwaybill.model.Un;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnRepository extends JpaRepository<Un, Long> {

    List<Un> findAllByUnNumberIsStartingWith(String unNumber);
}
