package edu.eci.ieti.ProjectIeti.persistence;

import edu.eci.ieti.ProjectIeti.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> getUserByCorreo(String correo);

    Optional<User> getUserById(Long id);

    User save(User user);
}
