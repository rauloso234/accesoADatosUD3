# Documentación Técnica - Sistema de Gestión de Clínica Veterinaria
## 1. Introducción:
   ### Propósito de la Documentación:
   Esta documentación técnica tiene como objetivo proporcionar una guía exhaustiva sobre el sistema de gestión de clínica veterinaria desarrollado. Se abordan aspectos como la arquitectura del sistema, el modelo de datos, las consultas JPQL, la configuración y despliegue, las pruebas implementadas, así como conclusiones y posibles mejoras.

### Descripción General del Sistema:
El sistema de gestión de clínica veterinaria permite administrar la información de dueños, mascotas y visitas en una clínica veterinaria. Se pueden realizar operaciones como agregar, actualizar, eliminar y mostrar información relacionada con estos elementos. La aplicación está implementada en Java utilizando el framework de persistencia JPA para interactuar con la base de datos.

## 2. Arquitectura del Sistema:
   Estructura del Proyecto:
   El proyecto sigue una arquitectura de tres capas: presentación, lógica de negocios y persistencia. Se utiliza el patrón MVC (Modelo-Vista-Controlador) para separar las responsabilidades. Las clases están organizadas de manera modularizada para facilitar el mantenimiento y la escalabilidad.

Diagrama de Clases:
![diagrama.png](..%2Fdiagrama.png)

## 3. Modelo de Datos:
   ### Dueño:

   #### Atributos: 
id, nombre, telefono, direccion.
   #### Relaciones: 
One-to-Many con Mascota.

  ### Mascota:

 ####  Atributos: 
id, nombre, raza.
  #### Relaciones: 
Many-to-One con Dueño, One-to-Many con Visita.
 ###  Visita:
  #### Atributos: 
id, fecha, motivoVacunacion.
  #### Relaciones: 
Many-to-One con Mascota.
 ###  Clases Detalladas:
  #### MainApp:

Clase principal que inicia el programa y maneja la interacción con el usuario.
Contiene métodos para agregar, mostrar, actualizar y eliminar dueños, mascotas y visitas.
#### Owner:

Representa a un dueño de mascota.

**Atributos**: 

id, nombre, telefono, direccion.
**Relación:**

One-to-Many con Mascota.
Métodos para gestionar mascotas asociadas.
#### Mascota:

Representa a una mascota.

**Atributos:**

id, nombre, raza.
**Relaciones:**

Many-to-One con Dueño, One-to-Many con Visita.
Métodos para gestionar dueño y visitas asociadas.
#### Visita:

Representa una visita de mascota a la clínica.

**Atributos:**

id, fecha, motivoVacunacion.
**Relación:**

Many-to-One con Mascota.
Método prePersist para obtener el nombre del dueño antes de persistir.
## 4. Consultas JPQL:
En el sistema de gestión de clínica veterinaria, se han establecido relaciones entre las entidades mediante anotaciones JPA que definen las cardinalidades y la naturaleza de dichas relaciones. A continuación, se explica cada una de las relaciones presentes en las entidades Dueño, Mascota y Visita:

### Dueño - Mascota (One-to-Many):

#### Descripción: 
La relación entre Dueño y Mascota es de tipo One-to-Many. Esto significa que un dueño puede tener varias mascotas, pero cada mascota pertenece a un único dueño.

#### Anotaciones JPA en la Clase Dueño:

@OneToMany(mappedBy = "dueño", cascade = CascadeType.ALL)
private List<Mascota> mascotas;
#### Explicación: 
El atributo "mascotas" en la clase Dueño establece la relación One-to-Many con la clase Mascota. La anotación @OneToMany indica que un dueño puede tener múltiples mascotas, y el atributo "mappedBy" especifica que la relación está mapeada por el atributo "dueño" en la clase Mascota.

### Mascota - Dueño (Many-to-One):

#### Descripción: 
La relación entre Mascota y Dueño es de tipo Many-to-One. Esto significa que muchas mascotas pueden pertenecer a un mismo dueño, pero cada mascota tiene un único dueño.

#### Anotaciones JPA en la Clase Mascota:

@ManyToOne

@JoinColumn(name = "dueño_id")

private Owner dueño;
#### Explicación: 
El atributo "dueño" en la clase Mascota establece la relación Many-to-One con la clase Dueño. La anotación @ManyToOne indica que muchas mascotas pueden tener el mismo dueño, y la anotación @JoinColumn especifica la columna en la base de datos que se utilizará para almacenar la relación.

### Mascota - Visita (One-to-Many):

#### Descripción: 
La relación entre Mascota y Visita es de tipo One-to-Many. Esto significa que una mascota puede tener varias visitas, pero cada visita está asociada a una única mascota.

#### Anotaciones JPA en la Clase Mascota:

@OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL)
private List<Visita> visitas;
#### Explicación: 
El atributo "visitas" en la clase Mascota establece la relación One-to-Many con la clase Visita. La anotación @OneToMany indica que una mascota puede tener varias visitas, y el atributo "mappedBy" especifica que la relación está mapeada por el atributo "mascota" en la clase Visita.

###  Visita - Mascota (Many-to-One):

#### Descripción:
La relación entre Visita y Mascota es de tipo Many-to-One. Esto significa que muchas visitas pueden estar asociadas a una misma mascota, pero cada visita está vinculada a una única mascota.

#### Anotaciones JPA en la Clase Visita:

@ManyToOne

@JoinColumn

(name = "mascota_id")

private Mascota mascota;

#### Explicación: 
El atributo "mascota" en la clase Visita establece la relación Many-to-One con la clase Mascota. La anotación @ManyToOne indica que muchas visitas pueden tener la misma mascota, y la anotación @JoinColumn especifica la columna en la base de datos que se utilizará para almacenar la relación.
## 5. Configuración y Despliegue:

### Configuración:

Clonar el repositorio desde [enlace al repositorio].
Configurar la conexión a la base de datos en el archivo de configuración.
Importar el proyecto en el entorno de desarrollo.
Despliegue:

Configurar una base de datos según las especificaciones del sistema.
Ejecutar la aplicación en el servidor de aplicaciones.
.

## 6. Conclusiones y Posibles Mejoras:
   ### Reflexiones:
   El desarrollo se llevó a cabo de manera eficiente, siguiendo las mejores prácticas de diseño y desarrollo. La modularidad y la separación de responsabilidades facilitan la comprensión y el mantenimiento del código.

### Posibles Mejoras:
#### 1. Validación de Entrada:

Implementar validaciones más robustas para la 
entrada del usuario. Actualmente, el código asume 
que el usuario ingresará datos válidos. Sería útil agregar validaciones para asegurar que los datos ingresados sean coherentes y cumplan con los requisitos esperados.
#### 2. Manejo de Excepciones Mejorado:

Mejorar el manejo de excepciones. Actualmente, hay bloques de código que manejan excepciones, pero podría ser beneficioso proporcionar mensajes de error más descriptivos y manejar situaciones específicas de manera más precisa.
#### 3. Seguridad:

Introducir medidas de seguridad, especialmente al trabajar con la capa de persistencia. Considerar la posibilidad de implementar parámetros de consulta seguros para evitar ataques de inyección SQL.
#### 4. Logging:

Implementar un sistema de registro más robusto para rastrear eventos importantes y facilitar la solución de problemas en caso de errores o problemas inesperados.
#### 5. Mejoras en la Interfaz de Usuario:

Si la aplicación tiene una interfaz de usuario, considerar mejorar la presentación y la usabilidad. Podría ser útil implementar una interfaz gráfica de usuario (GUI) si la aplicación actualmente se ejecuta en la línea de comandos.
#### 6. Pruebas Unitarias Adicionales:

Añadir más pruebas unitarias para aumentar la cobertura y garantizar un mejor control de calidad del código.
#### 7. Manejo de Transacciones:

Revisar el manejo de transacciones en la aplicación. Asegurarse de que las transacciones se gestionen adecuadamente para evitar posibles problemas de concurrencia y para garantizar la integridad de los datos.
