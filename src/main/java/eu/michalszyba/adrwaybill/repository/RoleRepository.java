package eu.michalszyba.adrwaybill.repository;

import eu.michalszyba.adrwaybill.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRole(String role);

    Role findRoleByRole(String role);
}
