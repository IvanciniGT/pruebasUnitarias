package com.curso.microservicio;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaServiceImpl implements PersonaService{
    private final PersonaRepository repositorioPersonas;
    private final EmailsService emailsService;

    public PersonaServiceImpl(PersonaRepository repositorioPersonas, EmailsService emailsService) {
        this.repositorioPersonas = repositorioPersonas;
        this.emailsService = emailsService;
    }

    @Override
    public Persona crearPersona(Persona persona) {
        // Validación de los datos de entrada
        // Se persisten en BBDD mediante el repositorio
        persona = repositorioPersonas.save(persona);
        // Otras operaciones
        emailsService.mandarUnEmail("Nueva persona creada", "Se ha creado una nueva persona con id: " + persona.getId()+ "y nombre: " + persona.getNombre());
        return persona;
    }

    @Override
    public Optional<Persona> obtenerPersona(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Persona> obtenerPersonas() {
        return null;
    }
}
