 # Buildear proyecto
 > Se necesita java 8. No hace falta configurar un tomcat.
 1. Ver versiones instaladas de java: `/usr/libexec/java_home -V`
 2. Usando la versión de java 8 instalada que se listó, generar war. Ejemplo: 
 ```bash
 JAVA_HOME=`/usr/libexec/java_home -v 1.8.0_05, x86_64` mvn clean install
 ```
 3. Crear usuario pedidos y password pedidos en mysql
 ```sql
 create database pedidos
 GRANT ALL PRIVILEGES ON pedidos.* TO 'pedidos'@'localhost' IDENTIFIED BY 'pedidos';
 ```
 4. Correr los tests: Se puede skippear porque Tambussi no puso tests (una Tambusseada).
 5. Levantar la aplicación (usa un tomcat embebido, no es neceario tener levantado otro). Usará el puerto 8090 (ver application.properties).
 ```
  JAVA_HOME=`/usr/libexec/java_home -v 1.8.0_05, x86_64` java -jar target/pedidos-api-0.0.1-SNAPSHOT.jar
 ```

 6. Generar datos en db `GET http://localhost:8090/pedidos-api/initDB`. Salida: DB initialization with test data was successful