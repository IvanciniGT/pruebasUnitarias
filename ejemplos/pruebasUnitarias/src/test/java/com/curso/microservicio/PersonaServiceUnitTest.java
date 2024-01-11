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
class PersonaServiceUnitTest {

    private PersonaService personaService;

    @MockBean
    private EmailsService emailsServiceSpy;
    @Captor
    private ArgumentCaptor<String> emailCaptorAsunto;
    @Captor
    private ArgumentCaptor<String> emailCaptorCuerpo;
    @MockBean
    private PersonaRepository personaRepositoryStub;

    public PersonaServiceUnitTest(@Autowired PersonaService personaService){
        this.personaService=personaService;
    }

    @Test
    @DisplayName("Probar a crear una persona con datos guays")
    void crearPersonaConDatosGuaysUnitaria(){
        // Stubear el repositorio
        Persona personaQueDevolveraElRepositorio= new Persona();
        personaQueDevolveraElRepositorio.setNombre("Juan");
        personaQueDevolveraElRepositorio.setApellido("Perez");
        personaQueDevolveraElRepositorio.setDni("12345678T");
        personaQueDevolveraElRepositorio.setEmail("juan@perez.com");
        personaQueDevolveraElRepositorio.setEdad(30);
        personaQueDevolveraElRepositorio.setId(33L);

        Persona persona= new Persona();
        persona.setNombre("Juan");
        persona.setApellido("Perez");
        persona.setDni("12345678T");
        persona.setEmail("juan@perez.com");
        persona.setEdad(30);

        // Cuando se llame al método .save del repositorio, devolverá la persona que he creado
        Mockito.when(personaRepositoryStub.save(persona)).thenReturn(personaQueDevolveraElRepositorio);

        Persona personaRecuperada = personaService.crearPersona(persona);
        assertNotNull(personaRecuperada);
        assertNotNull(personaRecuperada.getId());
        Assertions.assertEquals("Juan", personaRecuperada.getNombre());
        Assertions.assertEquals("Perez", personaRecuperada.getApellido());
        Assertions.assertEquals("12345678T", personaRecuperada.getDni());
        Assertions.assertEquals("juan@perez.com", personaRecuperada.getEmail());
        Assertions.assertEquals(30, personaRecuperada.getEdad());
        Assertions.assertTrue(personaRecuperada.getId() == 33L);

        verify(emailsServiceSpy).mandarUnEmail(emailCaptorAsunto.capture(), emailCaptorCuerpo.capture());
        assertEquals("Nueva persona creada", emailCaptorAsunto.getValue());
        assertTrue(emailCaptorCuerpo.getValue().contains("33"));
        assertTrue(emailCaptorCuerpo.getValue().contains("Juan"));

        // Es una prueba unitaria porque el PersonasService no está hablando con el PersonasRepository real, ni con el EmailsService real
        // Sino con con un Stub y un Spy que he generado de ellos... gracias a Mockito

        // Falñtaría refactorizar el código para :
        // Tener la creación de personas en una función
        // Tener la comprobación de los datos de la persona en otra función... ya que la usaríamos en distintas pruebas
        // Tener varaibles para los datos de la persona... ya que la usaríamos en distintas pruebas... Desde un CSV idealmente
    }


}
