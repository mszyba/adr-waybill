package eu.michalszyba.adrwaybill.repository;

import eu.michalszyba.adrwaybill.model.Waybill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaybillRepository extends JpaRepository<Waybill, Long> {
}
