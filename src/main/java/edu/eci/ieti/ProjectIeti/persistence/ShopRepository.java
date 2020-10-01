package edu.eci.ieti.ProjectIeti.persistence;

import edu.eci.ieti.ProjectIeti.model.Shop;
import edu.eci.ieti.ProjectIeti.model.Storekeeper;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends MongoRepository<Shop,String> {

    List<Shop> findAllByType(String type);

    Optional<Shop> findByName(String name);

    Optional<Shop> findByOwner(Storekeeper owner);

}
