package edu.eci.ieti.ProjectIeti.persistence;

import edu.eci.ieti.ProjectIeti.model.Producto;
import edu.eci.ieti.ProjectIeti.model.Tienda;
import edu.eci.ieti.ProjectIeti.model.TipoComercio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda,Long> {
    List<Tienda> findAllByTipoComercio(TipoComercio tipoComercio);

    Optional<Tienda> findByNombre(String nombre);

}
