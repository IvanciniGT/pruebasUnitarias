package com.curso.microservicio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    List<Persona> findByEdadGreaterThan(int edad);
}
