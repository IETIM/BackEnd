package edu.eci.ieti.ProjectIeti.persistence;

import edu.eci.ieti.ProjectIeti.model.Storekeeper;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StorekeeperRepository extends MongoRepository<Storekeeper,String> {

    Optional<Storekeeper> getStorekeeperByEmail (String email);
}
