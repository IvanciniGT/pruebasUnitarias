package com.curso.microservicio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest(classes = {MicroservicioAplicacionTest.class}) // Con esta anotación, le indico a JUNIT que esta clase es un test de Spring Boot
@ExtendWith(SpringExtension.class)
//@ActiveProfiles("EmailServiceDummy") // Con esta anotación, le indico a Spring que cargue el fichero application-test.properties
// Con esta anotación, le indico a JUNIT que esta clase puede requerir datros que sean suministrador por otros frameworks
// En mi caso, le estoy diciendo que esta clase necesita que Spring le inyecte dependencias
public class RepositorioTest {

    private PersonaRepository personaRepository ;
    @MockBean
    // Esta anotación no es de mockito, es de Spring
    // Mockito ofrece la anotación @Mock
    // Con esa anotacion genera una instancia de una clase que él monta por mi implementando una interfaz de la forma más sencilla posible
    // Si tengo un método en mi interfaz que devuelve un int: 0
    // Si tengo un método en mi interfaz que devuelve un boolean: false
    // Si tengo un método en mi interfaz que devuelve un objeto: null
    // Me interesa tener esa instancia de la clase... pero que Spring la utilice para inyectarla en otras clases
    // Eso hace la anotación @MockBean
    private EmailsService emailsServiceDummy;

    public RepositorioTest(@Autowired PersonaRepository personaRepository) {// Inyeccion de dependencias
        this.personaRepository = personaRepository;
    }

    @Test
    public void testCrearPersona() {
        // Dado que tengo una persona
        Persona persona = new Persona();
        persona.setNombre("Juan");
        persona.setApellido("Perez");
        persona.setDni("12345678T");
        persona.setEmail("juan@perez.com");
        persona.setEdad(30);
        // Dado que tengo un repositorio de personas
        Persona personaRecuperada = personaRepository.save(persona);
        // Me aseguro de que se ha hecho bien
        Assertions.assertNotNull(personaRecuperada);
        Assertions.assertNotNull(personaRecuperada.getId());
        Assertions.assertEquals("Juan", personaRecuperada.getNombre());
        Assertions.assertEquals("Perez", personaRecuperada.getApellido());
        Assertions.assertEquals("12345678T", personaRecuperada.getDni());
        Assertions.assertEquals("juan@perez.com", personaRecuperada.getEmail());
        Assertions.assertEquals(30, personaRecuperada.getEdad());
        Assertions.assertTrue(personaRecuperada.getId() >= 1);
    }

    @Test
    public void tratarDeDarDeAltaUnaPersonaSinEmail() {
        // Dado que tengo una persona
        Persona persona = new Persona();
        persona.setNombre("Juan");
        persona.setApellido("Perez");
        persona.setDni("12345678T");
        persona.setEdad(30);
        // Cuando intento guardarla en el repositorio
        //asegurarQueSeLanzaException(()->personaRepository.save(persona));
        Assertions.assertThrows(Exception.class, ()->personaRepository.save(persona));
    }
/*
    private void asegurarQueSeLanzaException(Runnable funcionQueDebesEjecutar) {
        try {
            funcionQueDebesEjecutar.run();
        } catch (Exception e) {
            return;
        }
        Assertions.fail("No se ha lanzado la excepcion esperada");
    }*/
}
