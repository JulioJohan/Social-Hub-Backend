# Social-Hub-Backend

Este repositorio contiene el backend de servicios que sirve para las publicaciones y comentarios
## Requisitos previos

- Java JDK 8: Asegúrate de tener instalado Java Development Kit (JDK) en tu sistema. Puedes descargarlo desde [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html) o [OpenJDK](https://openjdk.java.net/install/).

- Maven 3.8.6 o superior: Necesitarás tener Maven instalado para gestionar las dependencias y construir el proyecto. Puedes descargarlo desde [el sitio oficial de Apache Maven](https://maven.apache.org/download.cgi).

- Git: Debes tener Git instalado para clonar este repositorio en tu máquina. Puedes descargarlo desde [git-scm.com](https://git-scm.com/downloads).

## Configuración

1. **Clona el repositorio:** Abre una terminal y ejecuta el siguiente comando para clonar este repositorio:

   ```sh
   https://github.com/JulioJohan/Social-Hub-Backend.git
2. Accede al directorio del proyecto.
   ```sh
   cd Social-Hub-Backend
4. Construye el archivo JAR: Utiliza Maven para construir el archivo JAR del proyecto.
   ```sh
   mvn clean package
5. Encuentra el archivo JAR en la capeta target: Una vez que la construcción sea exitosa, podrás encontrar el archivo JAR en la carpeta target del proyecto.
6. Descarga el archivo de claves [KEYS](https://drive.google.com/file/d/1_JWXu6t4zi4mEW9JLJLeC6-qrFnzthy1/view?usp=sharing) a la carpeta target.
7. Ejecuta el archivo JAR: Utiliza el siguiente comando para ejecutar el archivo JAR:
   ```sh
   java -jar target/Social-Hub-0.0.2-SNAPSHOT.jar
8. Verifica que se haya iniciado correctamente
   ```sh
   http://localhost:8081/swagger-ui/index.html#/
