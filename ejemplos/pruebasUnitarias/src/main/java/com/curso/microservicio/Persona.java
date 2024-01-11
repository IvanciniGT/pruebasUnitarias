package com.curso.microservicio;

import lombok.Data;

import javax.persistence.*;

@Data
// Genera getter, setter, toString decente, equals, constructor vacio y constructor con todos los atributos
@Entity
@Table(name = "persona")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String dni;

    @Column(updatable = false, nullable = false, unique = true)
    private String email;

    private Integer edad;
}
