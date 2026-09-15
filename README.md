# TP Catan - Paradigmas de Programación

Reinterpretación del juego Catan desarrollada en Java 21 como proyecto Maven, aplicando modelado orientado a objetos y patrones de diseño.

## Integrantes (Grupo Cyberlek)

* **Ian Alabornoz**
* **Jonathan Galvan Perez**
* **Osvaldo Grasso**
* **Tomas Cilia**

---

## Requisitos Previos

* **Java JDK 21** o superior.
* **Apache Maven 3.8+** configurado en las variables de entorno.

---

## Compilación e Instalación

Para descargar las dependencias y compilar el proyecto desde cero, ejecuta en la raíz del repositorio:

```bash
mvn clean compile
```

## Ejecución
Para ejecutar el juego una vez fue compilado:
```bash
mvn exec:java -Dexec.mainClass="fiuba.cyberlek.App"
```

## Pruebas
Para ejecutar las pruebas unitarias:
```bash
mvn test
```

## Formato
Para verificar que el código fuente cumple con el formato:
```bash
mvn spotless:check
```

Para aplicar el formato de código definido en el proyecto:
```bash
mvn spotless:apply
```