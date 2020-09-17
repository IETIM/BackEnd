package edu.eci.ieti.ProjectIeti.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "tienda",cascade=CascadeType.ALL)
    private List<Producto> productos;

    @Column(nullable = false)
    private String ubicacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoComercio tipoComercio;


}
