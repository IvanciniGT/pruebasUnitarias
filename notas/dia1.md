# Metodologías ágiles

## ¿Qué es una metodología ágil?

Es una forma de trabajo para ayudarnos a desarrollar un producto de forma más eficaz... y con ciertas garantías de éxito.

Antiguamente usábamos metodologías (tradicionales) en cascada, que consistían en una serie de fases que se iban sucediendo una tras otra. 

    Toma de requisitos -> Diseño -> Implementación -> Pruebas -> Entrega -> Mantenimiento

El problema de las metodologías tradicionales es que no se adaptan bien a los cambios. No había sitios (momentos) en los que el cliente pudiera dar un feedback sobre el producto. El cliente solamente veía el producto cuando se lo entregábamos. Y si no era lo que él quería, había que cambiar un huevo de cosas.

Para solventar éste y otros problemas, se crearon las metodologías ágiles por parte de los desarrolladores.

La principal característica de una metodología ágil es la entrega incremental de mi producto al cliente.

    Día 20 de comenzar un proyecto: ENTREGA 1 en producción. 
        De esa forma el cliente puede ver el producto y darnos feedback.
        + 10% del producto ... 100% funcional
    Día 35 de comenzar un proyecto: ENTREGA 2 en producción.
        De esa forma el cliente puede ver el producto y darnos feedback.
        + 5% del producto ... 100% funcional
    Día 50 de comenzar un proyecto: ENTREGA 3 en producción.
        De esa forma el cliente puede ver el producto y darnos feedback.
        + 15% del producto ... 100% funcional
    ....
    Día 100 de comenzar un proyecto: ENTREGA 10 en producción.

## Extraído del manifiesto ágil

> "El software funcionando es la MEDIDA principal de progreso." Aquí se define un INDICADOR

La MEDIDA principal de progreso es el software funcionando.
La forma en la que vamos a medir qué tal va nuestro proyecto es mediante el concepto "Software funcionando"
"Software funcionando": Un software que funciona... que hace lo que tiene que hacer.

- Quién dice que el software funciona? LAS PRUEBAS !

# Pruebas de software

## Vocabulario que utilizamos en el mundo del testing

- Error     Los humanos cometemos errores (errar es humano). Motivos variados: Estar cansado, no haber dormido bien, no haber entendido bien el problema, haber estado hasta las 6.00 jugando al Lol...Haber discutido con la parienta o el pariente.
- Defecto   Al cometer un error, los humanos, introducimos un defecto en un producto
- Fallo     Denominamos fallo a la manifestación de un defecto.

## ¿Para qué sirven las pruebas?

- Objetivo principal: Asegurar el cumplimiento de unos requisitos
- Encontrar la máxima cantidad posible de defectos en el código antes de que llegue al cliente (paso a producción)
  - Poner el producto a funcionar... Al hacerlo podemos identificar Fallos.
    Desde esos fallos, nos tocaría buscar el defecto que los ha provocado: Depuración o Debugging (1)
      - Aportar toda la información que podamos para facilitar la depuración
  - Revisión del producto -> Identificar defectos
- Ayudarnos a entender qué tal llevamos el desarrollo: 
  - Cuántas pruebas se han superado la semana pasada de las 100 que había definidas?
  - Y ésta semana 
- Ayudarnos a enfrentar un desarrollo:
  - Test first: Escribir las pruebas antes que el código
  - TDD:  Test Driven Development: Test first + Refactorización en cada iteración
  - BDD:  Behavior Driven Development
  - ATDD: Acceptance Test Driven Development
- Asegurar la calidad de un producto
    - Un producto puede funcionar... llevar a cabo su cometido... pero tener una mala calidad.

## Tipos de pruebas. Formas en la que clasificamos las pruebas

Hay muchos tipos de pruebas... y muchas formas de clasificar las pruebas.
Todas las pruebas, con independencia del tipo de prueba, se deben centrar en un único aspecto del producto.
    ¿Por qué? para que si falla la prueba, tenga claro qué es lo que ha fallado. (1)

### Clasificar las pruebas en base al objeto de la prueba:

- Pruebas funcionales       Se centran en la funcionalidad del producto
- Pruebas no funcionales    Se centran en otros aspectos del producto
  - Rendimiento
  - Seguridad
  - Usabilidad / UX 
  - Accesibilidad
  - Alta disponibilidad
  - Estrés
  - Smoke test
  - Carga

### Clasificar las pruebas en base al nivel de la prueba (scope):

- Pruebas unitarias: Se centran en una característica de un componente AISLADO del sistema.

    TREN / FERROCARRIL

       Motor        -> Pruebas... Lo que me permite esta prueba es dar el siguiente paso con confianza
       Ruedas       -> Pruebas... Las monto en un bastidor -> Le pego un viaje con la mano... a ver si gira?
       Frenos       -> Pruebas... Los monto en un bastidor -> Le meto corriente... a ver si cierran las pinzas de freno
       Asientos
       Sistema de transmisión (motor -> ruedas)

    UN MICROSERVICIO BACKEND por REST para exponer operaciones CRUD sobre unos expedientes que se almacenan en una BBDD Relacional.

            <------------- Frontal ----------> <----------------------------------- Backend ------------------------------------->

            Formulario   --->  Servicio -----> RestController   ---> ExpedientesServicio    --->    Repositorio          ---> BBDD
            ComponenteWeb                      Lógica de exposición  Lógica de negocio              Lógica de persitencia
                               Lógica de comunicaciónes                                                             Entidad/Modelo
                               con backend                              - altaDeExpediente()
                                                                        -> Crear un expediente en la BBDD
                                                                        -> Mandamos un email a una dirección  ----> EmailsService
                                                                        - bajaDeExpediente()
                                                                        - modificacionDeExpediente()

    Esto es lo que montamos si seguimos una arquitectura moderna (fuera de lo que eran las arquitecturas monolíticas):
    - Clean Architecture
    - Hexagonal Architecture
    - Onion Architecture
    Esto es diseñar un sistema siguiendo unos principio SOLID de desarrollo de software:
        - S: Single Responsability Principle
        - O: Open/Closed Principle
        - L: Liskov Substitution Principle
        - I: Interface Segregation Principle
        - D: Dependency Inversion Principle *** Extraordinariamente importante  de cara a hacer pruebas unitarias y de integración ***
                Sin un diseño que posibilite la inyección de dependencias, no podemos hacer pruebas unitarias ni de integración, en la mayor parte de los casos,

    ¿Querré hacer pruebas unitarias a mi ExpedientesServicio? Nos ha jodido que quiero ! A todo quiero hacerle pruebas unitarias.
    Pero... voy a tener un problemón al hacer la prueba unitaria: Necesito aislar al componente del resto de componentes con que se conecta:
        - Con el repositorio BBDD
        - Con el servicio de envío de emails
    Y tendré que montar código (que luego tiraré a la basura, igual que los bastidores donde ponía las ruedas del tren o el motor para probarlo) para aislar al componente del resto de componentes con que se conecta: Test doubles

        ExpedientesServicio -> TestDouble Repositorio 
                            -> TestDouble EmailsService

- Pruebas de integración: Se centran en la COMUNICACIÓN entre 2 componentes.

    Sistema de Frenos -> Rueda
          Monto las ruedas y el sistema de frenos en un bastidor, pongo la rueda a girar, meto corriente al sistema de frenos y miro a ver si las ruedas frenan.

    ExpedientesServicio -> Repositorio 
                        -> TestDouble EmailsService

    ExpedientesServicio -> TestDouble Repositorio 
                        -> EmailsService


- Pruebas de sistema (End2End)  Se centra en el COMPORTAMIENTO del sistema en su conjunto

    Monto el tren, lo enciendo y ... que va pa'tras!

    Si todas las pruebas de sistema se superan... el tren queda listo para su entrega.
    Si todas las pruebas de sistema se superan ... necesito entonces hacer pruebas unitarias o de integración? NO
        Entonces dónde está el truco? Por qué me dicen que he de hacer pruebas unitarias?
            - Y si alguna prueba de sistema no se supera... dónde esta el problema? NPI... a volverte loco buscando y desmontando el tren.
            - Cuándo puedo hacer éstas pruebas? Cuando tengo el sistema acabado.. y mientras? no hago pruebas? 

    Las pruebas, en función de su nivel, las debo hacer en el momento adecuado.

  - Pruebas de Componente: Cuando trato un componente como un sistema en sí mismo.

- Pruebas de aceptación
  Habitualmente éstas son un subconjunto de las pruebas de sistema que yo haya hecho. 

### Clasificar las pruebas en base a la forma de ejecutarlas:

- Pruebas dinámicas: Requieren la puesta del código en ejecución        -> Ayudan a identificar FALLOS
- Pruebas estáticas: No requieren la puesta del código en ejecución     -> Ayudan a identificar DEFECTOS
  - SonarQube: Pruebas estáticas de calidad de código

### Clasificación en baser al conocimiento previo dell objeto de prueba:

- Pruebas de caja negra: No se conoce el funcionamiento interno del objeto de prueba
- Pruebas de caja blanca: Se conoce el funcionamiento interno del objeto de prueba

### Definición de pruebas

Al definir una prueba siempre establecemos 3 cosas:
- Escenario del que partimos
- Acción que vamos a llevar a cabo
- Resultado esperado
Hay lenguajes que me ayudan a definir esos 3 aspectos de una prueba: Given, When, Then (En español: Dado, Cuando, Entonces)

Además, al definir una prueba se siguen los principios FIRST, igual que los desarrolladores de software siguen los prinicipios SOLID:
- F: Fast
- I: Independent
- R: Repeatable
- S: Self-validating
- T: Timely (Oportuna, en el momento adecuado)

---

## Junit

Framework para pruebas en Java:
- Unitarias
- Integración
- Sistema
- Rendimiento
- Funcionales
- ...

##### Nota al margen: Spring 

Qué es Spring? Framework de desarrollo en Java basado en el concepto de IoC: Inversión de Control:
Ésto nos evita el controlar nosotros el flujo de nuestro programa, que pasa a estar bajo control del framework...
Y ésto por qué es importante: Porque facilita la inyección de dependencias.

###### Inyección de dependencias

Un patrón de desarrollo de software por el cuál las clases no crean instancias de los objetos que necesitan, sino que le son suministradas.

Y para qué quiero hacer software siguiendo este patrón para asegurar el complimiento del ppo de Inversión de Dependencias.

###### Ppo de inversión de dependencias

Un componente de alto nivel del sistema no debe depender de implementaciones concretas de componentes de bajo nivel. 
Sino que ambos deben depender de abstracciones (interfaces).

---

## Dobles de prueba (Muy bien explicaditos por nuestro amigo Martin Fowler)

Son componentes de código que usamos para aislar a otros componentes cuando queremos desarrollar pruebas de software (en concreto pruebas unitarias y de integración).
Hay muchos tipos:
- Dummy
- Stub

    ExpedientesServicioImpl
        RepositorioDeExpedientes
        EmailsService
    Si trabajo con las implementaciones reales de los componentes con que se conecta mi componente, no puedo hacer pruebas unitarias.
    Si uno de esos componentes no está bien implementado, la prueba fallará dando un falso positivo: El error no está en mi componente, sino en el componente con que se conecta.
    Además tengo otro problema.. Si uso las implementaciones reales de esos componentes, cuándo podría hacer la prueba? Cuando tenga esas implementaciones acabadas... Y si las está haciendo otro equipo? me espero a que acaben... me pongo con el Lol?
    Y aquí nos salen los test doubles: Voy a montar mi propia implementación de mentirijilla de esos componentes con que se conecta mi componente: 
    ```java
        public class RepositorioDeExpedientesStub implements RepositorioDeExpedientes {
            public Expediente save(Expediente expediente){
                expediente.setId(33L);
                return expediente;
            }
        }
    ```
    
    Esa clase:
    1. La creo yo. Dejo de depender de nadie... es decir, de otras personas que estén desarrollando la implementación REAL de ese componente.
    2. Tiene una lógica tan simple que sé que no va a fallar nunca. No va a afectar a mis pruebas unitarias.
        ExpedientesServicioImpl
            -> RepositorioDeExpedientesStub
            Si le paso unos datos X, que estén bien... a la función altaDeExpediente() del ExpedientesServicioImpl, sé que me va a devolver un ID 33.
            Ya puedo hacer la prueba. Ya puedo avanzar en mi desarrollo.Ya tengo garantías si se supera la prueba de que mi componente es capaz de leer bien el dato que le devuelve el repositorio.... y de que el repositorio está siendo invocado.
    Lo que hemos definido arriba es un STUB.
    Un Stub es una implementación de un componente que devuelve siempre un valor fijo.
    Para muchos casos, los stub son suficientes. Pero hay casos en los que necesitamos algo más potente: Los fakes.
- Fake
    Un fake es como un stub... pero que devuelve valores en función de los datos que le pasemos.

        ```java
        public class LoginServiceFake implements LoginService {

            public boolean doLogin(String userName, String password){
                return userName.equals(password);
            }
        }
        ```

    El fake me sirve no para 1 prueba, sino para muchas pruebas.
    Un fake llevado al extremo sería la implementación REAL !

    TANTO Fakes como Stubs se centran en el valor devuelto por el objeto que está siendo sustituido.
    Pero eso no siempre es lo que me interesa.

- Spy

    Los Spy, a diferencia de los Fakes y los stubs se centran en los datos que se reciben.
    De hecho en muchas ocasiones montamos Spies para funciones de tipo void, que no devuelven nada.

    ```java
    public class EmailsServiceSpy implements EmailsService {

        private boolean emailEnviado = false;
        private Long idExpediente;

        public boolean isEmailEnviado(){
            return emailEnviado;
        }

        public Long getIdExpediente(){
            return idExpediente;
        }

        public void enviarEmailDeAltaDeExpediente(Expediente expediente){
            emailEnviado = true;
            idExpediente = expediente.getId();
        }
        public void enviarEmailDeAltaDeUsuario(String nombre, String email){
            emailEnviado = true;
        }
        // En este escenario solo podría saber si se ha llamado a esta función o no.. pero no sabría si se ha llamado a la función con los datos correctos
    }
    ```
    En ocasiones los spy se me quedan cortos... y necesito algo más potente: Los mocks.

- Mock
    Un mock es a un spy lo que un fake es a un stub.

    El mock es un spy que valida los datos de entrada y que da por fallido en automático la prueba si los datos de entrada no son los esperados.
    ```java
    public class EmailsServiceMock implements EmailsService {

        private boolean emailEnviado = false;
        private Long idExpediente;

        public boolean isEmailEnviado(){
            return emailEnviado;
        }

        public void asignarElIdDelExpedienteQueSeDebeRecibir(Long idExpediente){
            this.idExpediente = idExpediente;
        }

        @Override
        public void enviarEmailDeAltaDeExpediente(Expediente expediente){
            if(expediente.getId() != idExpediente){
                Assertions.fail("El expediente no tiene id"); // Similar a lanzar una excepción
            }
            emailEnviado = true;
        }
    }
    public interface EmailsService {
        void enviarEmailDeAltaDeExpediente(Expediente expediente);
    }
    ```


Muchas veces (especialmente en el mundo Java) se usa el término Mock como genérico para referirse a todos los tipos de dobles de prueba.
La culpa de esto la tiene el gran framework de test doubles que existe en java, llamado Mockito.

---

```java

package com.curso.expedientes.service;

import java.time.LocalDate;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.curso.expedientes.domain.Expediente;
import com.curso.expedientes.dto.DatosDeNuevoExpedienteDTO;
import com.curso.expedientes.mapper.ExpedientesMapper;
import com.curso.expedientes.repository.RepositorioDeExpedientes;
import com.curso.emails.service.EmailsService;

// vvv  ESTOS 3 imports son la muerte del proyecto. LA ACABO DE CARGAR HASTA LO MAS PROFUNDO!
// Acabo de cagarme en el Ppo de inversión de dependencias
//import com.curso.expedientes.mapper.ExpedientesMapperImpl;
//import com.curso.expedientes.repository.RepositorioDeExpedientesImpl;
//import com.curso.emails.service.EmailsServiceImpl;

@Service
@Validated
public class ExpedientesServicioImpl implements ExpedientesServicio {

    private ExpedientesMapper expedientesMapper;
    private RepositorioDeExpedientes repositorioDeExpedientes;
    private EmailsService emailsService;

    public ExpedientesServicioImpl( ExpedientesMapper expedientesMapper, 
                                    RepositorioDeExpedientes repositorioDeExpedientes, 
                                    EmailsService emailsService){
        this.expedientesMapper = expedientesMapper;
        this.repositorioDeExpedientes = repositorioDeExpedientes;
        this.emailsService = emailsService;
    }

    public Optional<Long> altaDeExpediente(@NotNull DatosDeNuevoExpedienteDTO datosDelNuevoExpediente){
        // Validaciones adicionales... que una fecha que viene en los datos sea inferior o igual a la de hoy
        Expediente nuevoExpediente = expedientesMapper.toExpediente(datosDelNuevoExpediente);
        nuevoExpediente.setFechaDeAlta(LocalDate.now());
        nuevoExpediente = repositorioDeExpedientes.save(nuevoExpediente); // Donde se devuelve una instancia con el ID asignado
        emailsService.enviarEmailDeAltaDeExpediente(nuevoExpediente);
        return Optional.of(nuevoExpediente.getId());
    }

    public Optional<DatosDeExpedienteDTO> getExpediente(Long idExpediente){
        // Validaciones adicionales... que una fecha que viene en los datos sea inferior o igual a la de hoy
        Expediente expediente = repositorioDeExpedientes.findById(idExpediente);
        return Optional.of(expedientesMapper.toDatosDeExpedienteDTO(expediente));
    }

}

```

# Vamos a definir una prueba unitaria de la función altaDeExpediente() del ExpedientesServicioImpl

## Alta de expediente con datos GUAYS!

- Dado los datos de un nuevo Expediente... datos que son correctos.
- Y dado una instancia de un ExpedientesServicioImpl
- Y dado que ese ExpedientesServicioImpl está trabajando con un Stub del repositorio que siempre devuelve como id 33
- Y dado que ese ExpedientesServicioImpl está trabajando con un Spy del servicio de emails que no manda emails reales
(- Y dado que ese ExpedientesServicioImpl está trabajando con un mapper de mentirijilla... que no hace nada)
- Cuando altaDeExpediente() es invocado con esos datos
- Entonces me devuelve de id 33
    `Assertion.assertEquals(33, idDevueltoPorLaFuncionAltaDeExpediente)`

Me parece que lo de self-validating me lo he pasado por el forro.
Cómo lo resolvemos? Montar un Spy que me permita asegurar que se ha llamado a la función enviarEmailDeAltaDeExpediente() del servicio de emails.
- Y entonces si pregunto al servicio de emails si ha enviado un email, me dice que sí
    `Assertion.assertTrue(emailsServiceSpy.isEmailEnviado())`
- Y entonces si pregunto al servicio de emails cuál es el id del expediente que ha recibido, me dice que es 33
    `Assertion.assertEquals(33, emailsServiceSpy.getIdExpediente())`

Y AHORA SI ! YA TENGO UNA PRUEBA UNITARIA DE LA FUNCIÓN altaDeExpediente() DEL ExpedientesServicioImpl

## Alta de expediente con datos GUAYS!v2... con un mock del servicio de emails

- Dado los datos de un nuevo Expediente... datos que son correctos.
- Y dado una instancia de un ExpedientesServicioImpl
- Y dado que ese ExpedientesServicioImpl está trabajando con un Stub del repositorio que siempre devuelve como id 33
- Y dado que ese ExpedientesServicioImpl está trabajando con un Mock del servicio de emails al que le digo que se le va solicitar el envío de un email para el expediente con id 33
(- Y dado que ese ExpedientesServicioImpl está trabajando con un mapper de mentirijilla... que no hace nada)
- Cuando altaDeExpediente() es invocado con esos datos
- Entonces me devuelve de id 33
    `Assertion.assertEquals(33, idDevueltoPorLaFuncionAltaDeExpediente)`
- Y entonces si pregunto al mock del servicio de emails si ha enviado un email, me dice que sí
    `Assertion.assertTrue(emailsServiceSpy.isEmailEnviado())`
//- Y entonces si pregunto al servicio de emails cuál es el id del expediente que ha recibido, me dice que es 33
//    `Assertion.assertEquals(33, emailsServiceSpy.getIdExpediente())`
// En el caso de un mock esta condición me sobra... ya lo valida el propio mock

Y AHORA SI ! YA TENGO UNA PRUEBA UNITARIA DE LA FUNCIÓN altaDeExpediente() DEL ExpedientesServicioImpl

En muchos escenarios, voy a montar mis propios Stubs, Fakes, Spies y Mocks.
Pero... en muchos otros podré usar librerías como mockito, que montarán esas clases por mi.

# Vamos a definir una prueba de integración de la función altaDeExpediente() del ExpedientesServicioImpl con el repositorio de expedientes

## Alta de expediente con datos GUAYS!

- Dado los datos de un nuevo Expediente... datos que son correctos.
- Y dado una instancia de un ExpedientesServicioImpl
- Y dado que ese ExpedientesServicioImpl está trabajando con la implementación real del repositorio
- Y dado que ese ExpedientesServicioImpl está trabajando con un Mock del servicio de emails al que le digo que se le va solicitar el envío de un email para el expediente con id 33
(- Y dado que ese ExpedientesServicioImpl está trabajando con un mapper de mentirijilla... que no hace nada)
- Cuando altaDeExpediente() es invocado con esos datos
- Entonces me devuelve de id mayor que cero
    `Assertion.assertTrue(idDevueltoPorLaFuncionAltaDeExpediente>0, "El id devuelto debe ser mayor que cero")`
- Y entonces si pregunto al mock del servicio de emails si ha enviado un email, me dice que sí
    `Assertion.assertTrue(emailsServiceSpy.isEmailEnviado())`

Vamos a definir una prueba de integración de la función altaDeExpediente() del ExpedientesServicioImpl con el servicio de emails

## Alta de expediente con datos GUAYS!

- Dado los datos de un nuevo Expediente... datos que son correctos.
- Y dado una instancia de un ExpedientesServicioImpl
- Y dado que ese ExpedientesServicioImpl está trabajando con un Stub del repositorio que siempre devuelve como id 33
- Y dado que ese ExpedientesServicioImpl está trabajando con la implementación real del servicio de emails
(- Y dado que ese ExpedientesServicioImpl está trabajando con un mapper de mentirijilla... que no hace nada)
- Cuando altaDeExpediente() es invocado con esos datos
- Entonces me devuelve de id 33
    `Assertion.assertEquals(33, idDevueltoPorLaFuncionAltaDeExpediente)`
- Y entonces si voy a la bandeja de entrada pop3 o imap de la dirección de email de turno, tendré un correo recibido en los últimos 30 segundos, indicando que se ha dado de alta un expediente con el id 33.

# Vamos a hacer una prueba de sistema de esa función:

## Alta de expediente con datos GUAYS!

- Dado los datos de un nuevo Expediente... datos que son correctos.
- Y dado una instancia de un ExpedientesServicioImpl
- Y dado que ese ExpedientesServicioImpl está trabajando con la implementación real del repositorio
- Y dado que ese ExpedientesServicioImpl está trabajando con la implementación real del servicio de emails
- Cuando altaDeExpediente() es invocado con esos datos
- Entonces me devuelve de id mayor que cero
    `Assertion.assertTrue(idDevueltoPorLaFuncionAltaDeExpediente>0, "El id devuelto debe ser mayor que cero")`
- Y entonces si voy a la bandeja de entrada pop3 o imap de la dirección de email de turno, tendré un correo recibido en los últimos 30 segundos, indicando que se ha dado de alta un expediente con el id devuelto por la función.
- Y entonces si hago una consulta a la BBDD con el id devuelto por la función, tendré un expediente con los datos de los que partía.

## Prueba unitaria de la función getExpediente() pasando un id de expediente que SI existe en el repositorio

Dado: una instancia de un ExpedientesServicioImpl
Y dado que ese ExpedientesServicioImpl está trabajando con un Stub del repositorio que siempre devuelve 
los datos de un expediente X cuando se solicita el expediente con id 33
Cuando se solicita el expediente con id 33
Entonces se devuelve un objeto con los datos del expediente X

## Prueba de integración de la función getExpediente() pasando un id de expediente que SI existe en el repositorio

Dado: una instancia de un ExpedientesServicioImpl
Y dado que ese ExpedientesServicioImpl está trabajando con el repositorio real
Y dado que se ha solicitado el alta del expediente X... y se conoce el ID del mismo (se ha obtenido de vuelta)
Cuando se solicita el expediente con el id anterior
Entonces se devuelve un objeto con los datos del expediente X


Y en este caso, estamos llamado dentro de la prueba a 2 funciones del servicio de expedientes:
- altaDeExpediente()
- getExpediente()

Eso lo podemos hacer? No habíamos dicho que una prueba SOLO se centra en un único aspecto del producto?
Si... solo me centro en getExpediente()... pero necesito poder dar de alta expedientes.
Mi prueba no comprueba que la función altaDeExpediente() funcione bien... 
Ya habré hecho otra prueba para eso... para verificar altaDeExpediente().
Si esa prueba se supera y esta no... tengo claro que el problema está aquí: getExpediente() 

PRINCIPIOS FIRST DE DESARROLLO DE PRUEBAS:
- Independiente del entorno de ejecución
- Repetible... Si la ejecuto 5 veces... en cada prueba se crea su propio expediente... (y normalmente se borra al acabar la prueba... pero si no... no pasa nada... porque la prueba es independiente del entorno de ejecución)