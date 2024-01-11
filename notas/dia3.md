# JUnit platform engine

Junit no solo es un framework que me ofrece una sintaxis para definir pruebas en JAVA.

Junit es un integrador de frameworks de pruebas. Eso se conoce como el proyecto junit platform.

Si quiero ejecutar pruebas de otro framework con maven... el esquema será el siguiente:

    maven -> surefire -> junit platform -> cucumber junit platform -> cucumber