package edu.eci.ieti.ProjectIeti.persistence;

import edu.eci.ieti.ProjectIeti.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role,String> {

    Role findRoleByRole(String role);
}
