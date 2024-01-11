package com.curso.microservicio;

import java.util.List;
import java.util.Optional;

public interface PersonaService {
    Persona crearPersona(Persona persona);
    //DatosDePersona crearPersona(DatosDeUnaNuevaPersona persona); Esatas funcionan TODAS deberían trabajar en entrada y salida con DTOs
    Optional<Persona> obtenerPersona(Long id);
    List<Persona> obtenerPersonas();
}
