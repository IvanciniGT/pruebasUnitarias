
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