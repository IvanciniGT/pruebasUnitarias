package com.curso.microservicio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {MicroservicioAplicacionTest.class})
@ExtendWith(SpringExtension.class)
class PersonaServicePersonaRepositoryIntegrationTest {

    private PersonaService personaService;

    @MockBean
    private EmailsService emailsServiceSpy;
    @Captor
    private ArgumentCaptor<String> emailCaptorAsunto;
    @Captor
    private ArgumentCaptor<String> emailCaptorCuerpo;

    private PersonaRepository personaRepository;

    public PersonaServicePersonaRepositoryIntegrationTest(@Autowired PersonaService personaService, @Autowired PersonaRepository personaRepository){
        this.personaService=personaService;
        this.personaRepository=personaRepository;
    }

    @Test
    @DisplayName("Probar a crear una persona con datos guays")
    void crearPersonaConDatosGuaysEnIntegracionConElRepositorio(){
        Persona persona= new Persona();
        persona.setNombre("Juan");
        persona.setApellido("Perez");
        persona.setDni("12345678T");
        persona.setEmail("juan@perez.com");
        persona.setEdad(30);

        Persona personaRecuperada = personaService.crearPersona(persona);
        assertNotNull(personaRecuperada);
        assertNotNull(personaRecuperada.getId());
        Assertions.assertEquals("Juan", personaRecuperada.getNombre());
        Assertions.assertEquals("Perez", personaRecuperada.getApellido());
        Assertions.assertEquals("12345678T", personaRecuperada.getDni());
        Assertions.assertEquals("juan@perez.com", personaRecuperada.getEmail());
        Assertions.assertEquals(30, personaRecuperada.getEdad());
        Assertions.assertTrue(personaRecuperada.getId() >= 1);

        verify(emailsServiceSpy).mandarUnEmail(emailCaptorAsunto.capture(), emailCaptorCuerpo.capture());
        assertEquals("Nueva persona creada", emailCaptorAsunto.getValue());
        assertTrue(emailCaptorCuerpo.getValue().contains(""+personaRecuperada.getId()));
        assertTrue(emailCaptorCuerpo.getValue().contains("Juan"));

        // Mecesito asegurarme que en el repo se ha creado la persona
        Persona personaRecuperadaDelRepo = personaRepository.findById(personaRecuperada.getId()).orElse(null);
        assertNotNull(personaRecuperadaDelRepo);
        assertEquals(personaRecuperada.getId(), personaRecuperadaDelRepo.getId());
        assertEquals(personaRecuperada.getNombre(), personaRecuperadaDelRepo.getNombre());
        assertEquals(personaRecuperada.getApellido(), personaRecuperadaDelRepo.getApellido());
        assertEquals(personaRecuperada.getDni(), personaRecuperadaDelRepo.getDni());
        assertEquals(personaRecuperada.getEmail(), personaRecuperadaDelRepo.getEmail());
        assertEquals(personaRecuperada.getEdad(), personaRecuperadaDelRepo.getEdad());
        // Es una prueba de integración porque estoy probando que el servicio y el repositorio funcionan bien juntos
        // Los reales, no los mocks
    }
}
