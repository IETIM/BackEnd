package edu.eci.ieti.ProjectIeti.persistence;

import edu.eci.ieti.ProjectIeti.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order,String> {
    @Override
    Optional<Order> findById(String s);

    @Override
    void deleteById(String s);

    @Override
    <S extends Order> S insert(S s);
    
}
