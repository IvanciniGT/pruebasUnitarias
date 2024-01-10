
# Maven

Es una herramienta de automatización de tareas para proyectos de desarrollo de software (principalmente en Java).
Nos permite automatizar tareas habituales del proyecto:
- Compilación
- Empaquetado (generación de un jar, war, ear ,etc)
- Compilar el código de prueba
- Ejecutar las pruebas
- Mandar el código a un sonar
- Generar una imagen de contenedor con mi proyecto

Cuidado porque mucha gente cree que maven es un gestor de dependencias, pero no es así. Maven es una herramienta de automatización de tareas.
Otra cosa es que para poder compilar el proyecto, maven necesita las dependencias, y para ello, maven tiene un repositorio de dependencias, que me ayuda con la gestión de las mismas.

# Estructura típica de un proyecto maven

proyecto/
├── pom.xml
├── src
│   ├── main
│   |    ├── java
│   |    ├── resources 
|   ├── test
│        ├── java
│        └── resources
└── target
    ├── classes
    ├── test-classes

- pom.xml: Es el archivo de configuración de maven para mi proyecto.
           En él se detallan:
                - Las coordenadas del proyecto (sus datos identificativos):
                  - groupId: Identificador del grupo al que pertenece el proyecto
                  - artifactId: Identificador del artefacto (proyecto)
                  - version: La versión del proyecto
                - Dependencias del proyecto
                - Plugins que se van a usar para automatizar las tareas
                - Configuración de esos plugins
                - Y otras variables de configuración que yo necesite definir
                - Otros metadatos del proyecto
            En él indico a maven que tareas son las que quiero automatizar... y cómo quiero que las automatice.
  Por defecto, en maven vienen ya preconfiguradas algunas tareas, las más comunes. A esas tareas les denominamos GOALS:
    - compile          Compila lo que hay dentro de src/main/java y deja el resultado dentro de target/classes
                       Copia lo que hay en src/main/resources dentro de target/classes 
                       NOTA: En este trabajo podemos solicitar a maven que reemplace textos dentro de los archivos de recursos.
    - test-compile     Compila lo que hay dentro de src/test/java y deja el resultado dentro de target/test-classes*
                       Copia lo que hay en src/test/resources dentro de target/test-classes 
    - test             Ejecuta los test que existan en: target/test-classes 
    - package          Empaqueta el proyecto en un jar, war, ear, etc
                        Si es un jar: Comprime la carpeta target/classes en un jar (que es un zip al fin y al cabo)
    - install          Copia el artefacto generado (jar, war, war) dentro de la carpeta ~/.m2
                                                                                        En windows: c:\Usuarios\MiUsuario\.m2 
                       La carpeta .m2 es donde maven va guardando las dependencias que va descargando de internet, o que voy compilando y empaquetando desde mi máquina. Al hacer un instalkl de mi proyecto, hago que mi archivo .jar o .war (generado por el goal package) se copie a esa carpeta, de forma que pueda ser usado como dependencia por otros proyectos, dentro de mi máquina.
    - clean            Borra la carpeta target
    - * En maven no hay ninguna tarea llamada `build`. Ese es un invento del Eclipse
   Ciertas tareas se ejecutan de forma incremental:
    install > package > test > test-compile > compile

  Toda tarea de maven se ejecuta mediante un plugin:
    - compile:      maven-compiler-plugin
    - test-compile: maven-compiler-plugin
    - test:         maven-surefire-plugin
    - package:      maven-jar-plugin
- También indicamos en el pom.xml las dependencias que tiene nuestro proyecto.

---

# Devops

Hoy en día TODAS las empresas abrazan una cultura DEVOPS.

Devops es una cultura, una filosofía, un movimiento en pro de la AUTOMATIZACION.

Automatización de qué? De TODO lo que hay entre el DESARROLLO y la OPERACION de un sistema.

El primer paso para adoptar una cultura DEVOPS es lo que llamamos INTEGRACION CONTINUA.

Integración continua es cuando AUTOMATIZO el proceso de:
- Extraer en un entorno de pruebas (que a lo mejor se ha creado bajo demanda) la última versión de mi código fuente desde un repo de git
- Compilar ese código en el entorno de pruebas
- Ejecutar en ese entorno de pruebas las pruebas automatizadas que se hayan definido
- Generar un informe de pruebas que presentar al equipo de desarrollo

El entregable de un pipeline(flujo de trabajo) de Integración continua es un REPORTE de pruebas.

Y queremos que ese informe se esté generando DE CONTINUO.

En un pipeline de integración continua se hacen muchos tipos de pruebas... no solo las de JUnit funcionales que definamos... por ejemplo, mi código se manda a un SonarQube

Normalmente las empresas tienen una serie de restricciones para poder subir código a producción... muchas de ellas validadas a través de SonarQube.
Hay un proceso que se encarga de mandar el código a un Sonar... y en función de la respuesta que dé el sonar, el código pasa o no a producción... sin mediación humana.

Herramientas como Jenkins, Azure Devops, Travis, Gitlab CI/CD, Github actions, TeamCity, Bamboo, etc... nos permiten crear pipelines de integración continua.

Jenkins... y herramientas similares no saben hacer la O con un canuto.
Lo único que hacen es llamar a otras automatizaciones realizadas con OTRAS herramientas.

En el caso de proyectos java, hay 2 grandes para éste trabajo: MAVEN y GRADLE.
En proyectos de backend, habitualmente usamos maven.

Maven es quien se encargará de ejecutar las pruebas de mi proyecto en el entorno de pruebas y de generar el informe de pruebas.

Maven solicita al plugin SUREFIRE que ejecute las pruebas definidas con JUnit.
El plugin surefire, además de ejecutar las pruebas, genera un fichero en:
target/surefire-reports/TEST-<nombre del test>.xml, con el informe de las pruebas.


---
# Proyecto de pruebas de la librería Diccionario

Quiero una librería que me permita recuperar diccionarios de distintos idiomas,
Y a cada diccionario preguntarle si existe una determinada palabra o no, 
Caso que exista, me de los significados
Caso que no exista, me de un listado de alternativas a esa palabra

1. Diccionario de ES
2. Existe la palabra manzana: SI
3. Significado de manzana: fruta del manzano
4. Existe la palabra manana: NO
5. Alternativas a la palabra manana: banana, mañana, manzana, manzano

---

Vamos a definir las pruebas


## Prueba 1: VEr si existe un diccionario que se que existe en la librería

DADO: Un idioma de un diccionario que existe en mi librería
    String idiomaExistente="ES";
    // Y por que se que existe... porque es mi librería y le he puesto un diccionario de ES
CUANDO: pido a la librería el diccionario del idioma
    SuministradorDeDiccionarios miSuministrador = SuministradorDeDiccionariosFactory.getSuministradorDeDiccionarios();
        // Una variante de Inyección de dependencias, mediante un patrón de diseño llamado FACTORY
    boolean respuesta = miSuministrador.tienesDiccionarioDe(idiomaExistente);
ENTONCES: me da el diccionario
    Assertions.assertTrue(respuesta);

## Prueba 2: VEr si no existe un diccionario que se que no existe en la librería

DADO: Un idioma de un diccionario que existe en mi librería
    String idiomaExistente="RUNAS DE LOS ENANOS DEL BOSQUE";
    // Y por que se que existe... porque es mi librería y le he puesto un diccionario de ES
CUANDO: pido a la librería el diccionario del idioma
    SuministradorDeDiccionarios miSuministrador = SuministradorDeDiccionariosFactory.getSuministradorDeDiccionarios();
        // Una variante de Inyección de dependencias, mediante un patrón de diseño llamado FACTORY
    boolean respuesta = miSuministrador.tienesDiccionarioDe(idiomaExistente);
ENTONCES: me da el diccionario
    Assertions.assertFalse(respuesta);


## Prueba 3: Ver si soy capaz de recuperar un diccionario de un idioma que exista

DADO: Un idioma de un diccionario que existe en mi librería
    String idiomaExistente="ES";
    // Y por que se que existe... porque es mi librería y le he puesto un diccionario de ES
CUANDO: pido a la librería el diccionario del idioma
    SuministradorDeDiccionarios miSuministrador = SuministradorDeDiccionariosFactory.getSuministradorDeDiccionarios();
        // Una variante de Inyección de dependencias, mediante un patrón de diseño llamado FACTORY
    Optional<Diccionario> diccionario = miSuministrador.getDiccionario(idiomaExistente);
ENTONCES: me da el diccionario
    Assertions.assertTrue(diccionario.isPresent());
    Assertions.assertEquals(idiomaExistente, diccionario.get().getIdioma());

## Prueba 4: Ver que pasa si pido un diccionario que no existe

DADO: Un idioma de un diccionario que no existe en mi librería
    String idiomaExistente="RUNAS DE LOS ENANOS DEL BOSQUE";
    // Y por que se que no existe... porque es mi librería y no le he puesto un diccionario de RUNAS DE LOS ENANOS DEL BOSQUE
CUANDO: pido a la librería el diccionario del idioma
    SuministradorDeDiccionarios miSuministrador = SuministradorDeDiccionariosFactory.getSuministradorDeDiccionarios();
        // Una variante de Inyección de dependencias, mediante un patrón de diseño llamado FACTORY
    Optional<Diccionario> diccionario = miSuministrador.getDiccionario(idiomaExistente);
ENTONCES: me no da el diccionario
    Assertions.assertTrue(diccionario.isEmpty());

## Prueba 5: Ver si en el diccionario aparece una palabra que se que existe en ese idioma

DADO: Un diccionario ES
Diccionario miDiccionario = SuministradorDeDiccionariosFactory.getSuministradorDeDiccionarios().getDiccionario("ES").get();
Y una palabra que se que existe en ese diccionario de ES: manzana
CUANDO: le pregunto al diccionario si existe esa palabra
boolean respuesta = miDiccionario.existe("manzana");
ENTONCES: Que si
Assertions.assertTrue(respuesta);

## Prueba 6: Ver si en el diccionario no aparece una palabra que se que no existe en ese idioma

DADO: Un diccionario ES
Diccionario miDiccionario = SuministradorDeDiccionariosFactory.getSuministradorDeDiccionarios().getDiccionario("ES").get();
Y una palabra que se que no existe en ese diccionario de ES: archilococo
CUANDO: le pregunto al diccionario si existe esa palabra
boolean respuesta = miDiccionario.existe("archilococo");
ENTONCES: Que no
Assertions.assertFalse(respuesta);


## Prueba 7: Ver si puedo recuperar los significados de una palabra que se que existe en un diccionario

DADO: Un diccionario ES
Diccionario miDiccionario = SuministradorDeDiccionariosFactory.getSuministradorDeDiccionarios().getDiccionario("ES").get();
Y una palabra que se que existe en ese diccionario de ES: manzana
CUANDO: le pregunto al diccionario si existe esa palabra
Optional<List<String>> significados = miDiccionario.getSignificados("manzana");
ENTONCES: Me dice que "Fruta del manzano"
Assertions.assertTrue(significados.isPresent());
Assertions.assertEquals(1, significados.get().size());
Assertions.assertEquals("Fruta del manzano", significados.get().get(0));


## Prueba 8: Ver si no puedo recuperar los significados de una palabra que se que no existe en un diccionario

DADO: Un diccionario ES
Diccionario miDiccionario = SuministradorDeDiccionariosFactory.getSuministradorDeDiccionarios().getDiccionario("ES").get();
Y una palabra que se que existe en ese diccionario de ES: archilococo
CUANDO: le pregunto al diccionario si existe esa palabra
Optional<List<String>> significados = miDiccionario.getSignificados("archilococo");
ENTONCES: Me dice que nasti de plasti
Assertions.assertTrue(significados.isEmpty());

// Y esto es hacer pruebas guay!
A priori, estas pruebas son de SISTEMA. Toda prueba hecha a través de la interfaz pública (API) de mi librería es una prueba de SISTEMA.

El ponerme a definir las pruebas sin tener CODIGO creado, me ayuda a DEFINIR EL API de mi librería.

---

```java
public interface SuministradorDeDiccionariosFactory{
    static SuministradorDeDiccionarios getSuministradorDeDiccionarios(){
        return new SuministradorDeDiccionariosDesdeFicheros();
    }
}

public interface SuministradorDeDiccionarios {
    boolean tienesDiccionarioDe(String idioma);
    Optional<Diccionario> getDiccionario(String idioma);
    // Desde JAVA 1.8, está considerado una MUY MUY MUY Mala practica el que una función devuelva null!!!
}

public interface Diccionario {
    String getIdioma();
    boolean existe(String palabra);

    Optional<List<String>> getSignificados(String palabra);
    // El problema es que no tengo npi de como se comporta está función viendo su definición.
    // Si le pido la palabra archilococo en un diccionario de ES, qué me devuelve?
    // Lista vacia,... por ejemplo
    // null,... por ejemplo
    // Tengo una función cuya definición es AMBIGUA
    // Para saber cómo se comporta me quedan 2 opciones:
    // - 1. Leer la documentación de la función... en serio???? en el 2023 y seguimos leyendo documentación?????
    // - 2. Mirar el código fuente de la función... en serio???? en el 2023 y seguimos mirando código fuente????? Y si no lo tengo?
    // - 3. Probar a ver... metele horas... a ver como se comporta.., Ein???

    // Para resolver estas ambigüedades se crea la clase Optional (Es una caja que envuelve un tipo de objeto)
    // Java siempre me entrega la caja (el optional) que puede venir vacia o no
    // El compromiso al que llegamos es que a partir de ahora ninguna función debe devolver null.
}


```