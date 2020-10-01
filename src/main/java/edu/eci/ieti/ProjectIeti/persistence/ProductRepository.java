package edu.eci.ieti.ProjectIeti.persistence;

import edu.eci.ieti.ProjectIeti.model.Product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {

}
