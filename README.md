# Proyecto CRUD de Automóviles usando Spring Boot

Este proyecto es una aplicación Spring Boot que proporciona operaciones CRUD para manejar datos de automóviles. Utiliza una base de datos para almacenar y recuperar la información de los automóviles.

## Requisitos

- Java Development Kit (JDK) 8 o superior
- Maven (para compilar y construir el proyecto)
- IDE (como IntelliJ IDEA, Eclipse) o cualquier editor de texto para desarrollo

## Tecnologías Utilizadas

- Spring Boot
- Spring Data JPA (para la capa de persistencia)
- PostgreSQL
- Maven (para la gestión de dependencias y construcción del proyecto)

## Instalacion y ejecucion

Este es un proyecto bakend de Spring boot, el cual no requeire de ningun servidor, lo unico que se necesita un el jdk y el maven para la compilacion

- El primer paso es hacer la compilacion del proyecto
```bash
mvn clean install
```
-  Despues viene la ejecucion del archivo jar generado
java -jar unow-vehicles-0.0.1-SNAPSHOT.jar

## NOTAS

Este proyecto solo contiene el back de la aplicacio nel front sera desplegado en otro repositorio que consumira a este back