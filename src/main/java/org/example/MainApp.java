package org.example;
import org.example.Mascota;
import org.example.Owner;
import org.example.Visita;
import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    // Inicialización de las instancias de EntityManagerFactory, EntityManager y Scanner
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ClinicaVeterinaria");
    private static final EntityManager em = emf.createEntityManager();
    private static final Scanner scanner = new Scanner(System.in);

    // Método principal del programa
    public static void main(String[] args) {
        // Bucle principal del programa
        while (true) {
            // Mostrar el menú de opciones
            mostrarMenu();

            // Leer la opción seleccionada por el usuario
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea después de nextInt()

            // Realizar acciones según la opción seleccionada
            switch (opcion) {
                case 1:
                    agregarDueño();
                    break;
                case 2:
                    agregarMascota();
                    break;
                case 3:
                    agregarVisita();
                    break;
                case 4:
                    mostrarDueños();
                    break;
                case 5:
                    mostrarMascotas();
                    break;
                case 6:
                    mostrarVisitas();
                    break;
                case 7:
                    actualizarDueño();
                    break;
                case 8:
                    actualizarMascota();
                    break;
                case 9:
                    actualizarVisita();
                    break;
                case 10:
                    eliminarDueño();
                    break;
                case 11:
                    eliminarMascota();
                    break;
                case 12:
                    eliminarVisita();
                    break;
                case 13:
                    // Salir del programa, cerrar EntityManager y EntityManagerFactory
                    System.out.println("Saliendo del programa. ¡Hasta luego!");
                    em.close();
                    emf.close();
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Por favor, elige una opción del menú.");
            }
        }
    }

    // Método para mostrar el menú de opciones
    private static void mostrarMenu() {
        System.out.println("\n==== Menú ====");
        System.out.println("1. Agregar Dueño");
        System.out.println("2. Agregar Mascota");
        System.out.println("3. Agregar Visita");
        System.out.println("4. Mostrar Dueños");
        System.out.println("5. Mostrar Mascotas");
        System.out.println("6. Mostrar Visitas");
        System.out.println("7. Actualizar Dueño");
        System.out.println("8. Actualizar Mascota");
        System.out.println("9. Actualizar Visita");
        System.out.println("10. Eliminar Dueño");
        System.out.println("11. Eliminar Mascota");
        System.out.println("12. Eliminar Visita");
        System.out.println("13. Salir");
        System.out.print("Elige una opción: ");
    }


    // Método para agregar un dueño a la base de datos
    private static void agregarDueño() {
        // Iniciar una transacción
        em.getTransaction().begin();

        try {
            // Solicitar al usuario el nombre del dueño
            System.out.print("Ingrese el nombre del dueño: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese el telefono del dueño: ");
            String Telefono = scanner.nextLine();
            System.out.print("Ingrese la direccion del usuarios del dueño(en formato c/.... Nº...:) ");
            String Direccion = scanner.nextLine();
            // Crear una nueva instancia de Owner con el nombre proporcionado
            Owner dueño = new Owner();
           dueño.setTelefono(Telefono);
            dueño.setDireccion(Direccion);
            dueño.setNombre(nombre);

            // Persistir el objeto dueño en la base de datos
            em.persist(dueño);

            // Confirmar la adición del dueño
            System.out.println("Dueño agregado con éxito.");
        } catch (Exception e) {
            // Manejar excepciones, imprimir el rastreo de la pila y realizar rollback en caso de error
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            // Commit de la transacción (se ejecuta independientemente de si hubo una excepción o no)
            em.getTransaction().commit();
        }
    }

    // Método para agregar una mascota a la base de datos
    private static void agregarMascota() {
        // Iniciar una transacción
        em.getTransaction().begin();

        try {
            // Solicitar al usuario el nombre de la mascota
            System.out.print("Ingrese el nombre de la mascota: ");
            String nombreMascota = scanner.nextLine();
            System.out.print("Ingrese la raza de la mascota: ");
            String Raza = scanner.nextLine();

            // Solicitar al usuario el ID del dueño de la mascota
            System.out.print("Ingrese el ID del dueño de la mascota: ");
            Long idDueño = scanner.nextLong();
            scanner.nextLine(); // Consumir la nueva línea después de nextLong()

            // Buscar al dueño en la base de datos
            Owner dueño = em.find(Owner.class, idDueño);

            // Verificar si se encontró al dueño
            if (dueño == null) {
                System.out.println("No se encontró un dueño con el ID proporcionado.");
                return;
            }

            // Crear una nueva instancia de Mascota con el nombre y el dueño proporcionados
            Mascota mascota = new Mascota();
            mascota.setNombre(nombreMascota);
            mascota.setRaza(Raza);
            mascota.setDueño(dueño);

            // Persistir el objeto mascota en la base de datos
            em.persist(mascota);

            // Confirmar la adición de la mascota
            System.out.println("Mascota agregada con éxito.");
        } catch (Exception e) {
            // Manejar excepciones, imprimir el rastreo de la pila y realizar rollback en caso de error
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            // Commit de la transacción (se ejecuta independientemente de si hubo una excepción o no)
            em.getTransaction().commit();
        }
    }


    // Método para agregar una visita a la base de datos
    private static void agregarVisita() {
        // Iniciar una transacción
        em.getTransaction().begin();

        try {
            // Solicitar al usuario el ID de la mascota para la visita
            System.out.print("Ingrese el ID de la mascota para la visita: ");
            Long idMascota = scanner.nextLong();
            scanner.nextLine(); // Consumir la nueva línea después de nextLong()

            // Buscar la mascota en la base de datos
            Mascota mascota = em.find(Mascota.class, idMascota);

            // Verificar si se encontró la mascota
            if (mascota == null) {
                System.out.println("No se encontró una mascota con el ID proporcionado.");
                return;
            }

            // Solicitar al usuario la fecha de la visita en formato dd/MM/yyyy HH:mm
            System.out.print("Ingrese la fecha de la visita (formato dd/MM/yyyy HH:mm): ");
            String fechaStr = scanner.nextLine();
            System.out.print("Ingrese el motivo de su visita: ");
            String motivo = scanner.nextLine();
            Date fecha = parseFecha(fechaStr);

            // Crear una nueva instancia de Visita con la fecha y la mascota proporcionadas
            Visita visita = new Visita();
            visita.setFecha(fecha);
            visita.setMascota(mascota);
            visita.setMotivoVacunacion(motivo);

            // Persistir el objeto visita en la base de datos
            em.persist(visita);

            // Confirmar la adición de la visita
            System.out.println("Visita agregada con éxito.");
        } catch (ParseException | NullPointerException e) {
            // Manejar excepciones, imprimir el rastreo de la pila y realizar rollback en caso de error
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            // Commit de la transacción (se ejecuta independientemente de si hubo una excepción o no)
            em.getTransaction().commit();
        }
    }

    // Método para mostrar todos los dueños en la base de datos
    private static void mostrarDueños() {
        // Obtener la lista de dueños mediante una consulta JPQL
        List<Owner> dueños = em.createQuery("SELECT o FROM Owner o", Owner.class).getResultList();

        // Imprimir encabezado
        System.out.println("\n==== Dueños ====");

        // Iterar sobre cada dueño y mostrar información relevante
        dueños.forEach(owner -> System.out.println("Id Dueño: " + owner.getId() + ", Nombre del Dueño: " +
                owner.getNombre()+ ", Telefono: " + owner.getTelefono() + ", Direccion: " +
                owner.getDireccion()));
    }

    // Método para mostrar todas las mascotas en la base de datos
    private static void mostrarMascotas() {
        // Obtener la lista de mascotas mediante una consulta JPQL
        List<Mascota> mascotas = em.createQuery("SELECT m FROM Mascota m", Mascota.class).getResultList();

        // Imprimir encabezado
        System.out.println("\n==== Mascotas ====");

        // Iterar sobre cada mascota y mostrar información relevante
        mascotas.forEach(mascota -> {
            System.out.println("Id de la Mascota: " + mascota.getId() + ", Nombre de la mascota " + mascota.getNombre() +
                    " (Dueño: " + mascota.getDueño().getNombre() + ")" + ", Raza de la mascota: " + mascota.getRaza());
        });
    }


    // Método para mostrar todas las visitas en la base de datos
    private static void mostrarVisitas() {
        // Obtener la lista de visitas mediante una consulta JPQL
        List<Visita> visitas = em.createQuery("SELECT v FROM Visita v", Visita.class).getResultList();

        // Imprimir encabezado
        System.out.println("\n==== Visitas ====");

        // Iterar sobre cada visita y mostrar información relevante
        visitas.forEach(visita -> {
            System.out.println("Id de la Visita " + visita.getId() + " A fecha de:  " + visita.getFecha() +
                    " se realizará una visita al veterinario siendo el animal a inspeccionar: " +
                    visita.getMascota().getNombre() + ", siendo el dueño de la misma: " + visita.getMascota().getDueño().getNombre() + "con motivo de :" + visita.getMotivoVacunacion());
        });
    }

    // Método para actualizar la información de un dueño en la base de datos
    private static void actualizarDueño() {
        // Iniciar una transacción
        em.getTransaction().begin();

        try {
            // Solicitar al usuario el ID del dueño a actualizar
            System.out.print("Ingrese el ID del dueño a actualizar: ");
            Long idDueño = scanner.nextLong();
            scanner.nextLine(); // Consumir la nueva línea después de nextLong()

            // Buscar al dueño en la base de datos
            Owner dueño = em.find(Owner.class, idDueño);

            // Verificar si se encontró al dueño
            if (dueño == null) {
                System.out.println("No se encontró un dueño con el ID proporcionado.");
                return;
            }

            // Solicitar al usuario el nuevo nombre del dueño
            System.out.print("Ingrese el nuevo nombre del dueño: ");
            String nuevoNombre = scanner.nextLine();
            System.out.print("Ingrese el nuevo telefono del dueño: ");
            String nuevoTelefono = scanner.nextLine();
            System.out.print("Ingrese la nueva Direccion del dueño(en formato c/.... Nº...:): ");
            String nuevaDireccion = scanner.nextLine();
            // Actualizar el nombre del dueño
            dueño.setNombre(nuevoNombre);
            dueño.setTelefono(nuevoTelefono);
            dueño.setDireccion(nuevaDireccion);
            // Fusionar (merge) los cambios en la base de datos
            em.merge(dueño);

            // Confirmar la actualización del dueño
            System.out.println("Dueño actualizado con éxito.");
        } catch (Exception e) {
            // Manejar excepciones, imprimir el rastreo de la pila y realizar rollback en caso de error
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            // Commit de la transacción (se ejecuta independientemente de si hubo una excepción o no)
            em.getTransaction().commit();
        }
    }

    // Método para actualizar una mascota en la base de datos
    private static void actualizarMascota() {
        // Iniciar una transacción
        em.getTransaction().begin();

        try {
            // Solicitar al usuario el ID de la mascota a actualizar
            System.out.print("Ingrese el ID de la mascota a actualizar: ");
            Long idMascota = scanner.nextLong();
            scanner.nextLine(); // Consumir la nueva línea después de nextLong()

            // Buscar la mascota en la base de datos
            Mascota mascota = em.find(Mascota.class, idMascota);

            // Verificar si se encontró la mascota
            if (mascota == null) {
                System.out.println("No se encontró una mascota con el ID proporcionado.");
                return;
            }

            // Solicitar al usuario el nuevo nombre de la mascota
            System.out.print("Ingrese el nuevo nombre de la mascota: ");
            String nuevoNombre = scanner.nextLine();

            System.out.print("Ingrese la nueva Raza de la mascota: ");
            String nuevoRaza = scanner.nextLine();

            // Actualizar el nombre de la mascota
            mascota.setNombre(nuevoNombre);
            mascota.setRaza(nuevoRaza);
            // Fusionar (merge) los cambios en la base de datos
            em.merge(mascota);

            // Confirmar la actualización de la mascota
            System.out.println("Mascota actualizada con éxito.");
        } catch (Exception e) {
            // Manejar excepciones, imprimir el rastreo de la pila y realizar rollback en caso de error
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            // Commit de la transacción (se ejecuta independientemente de si hubo una excepción o no)
            em.getTransaction().commit();
        }
    }

    // Método para eliminar un dueño de la base de datos
    private static void eliminarDueño() {
        // Iniciar una transacción
        em.getTransaction().begin();

        try {
            // Solicitar al usuario el ID del dueño a eliminar
            System.out.print("Ingrese el ID del dueño a eliminar: ");
            Long idDueño = scanner.nextLong();
            scanner.nextLine(); // Consumir la nueva línea después de nextLong()

            // Buscar al dueño en la base de datos
            Owner dueño = em.find(Owner.class, idDueño);

            // Verificar si se encontró al dueño
            if (dueño == null) {
                System.out.println("No se encontró un dueño con el ID proporcionado.");
                return;
            }

            // Eliminar al dueño de la base de datos
            em.remove(dueño);

            // Confirmar la eliminación del dueño
            System.out.println("Dueño eliminado con éxito.");
        } catch (Exception e) {
            // Manejar excepciones, imprimir el rastreo de la pila y realizar rollback en caso de error
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            // Commit de la transacción (se ejecuta independientemente de si hubo una excepción o no)
            em.getTransaction().commit();
        }
    }

    // Método para eliminar una mascota de la base de datos
    private static void eliminarMascota() {
        // Iniciar una transacción
        em.getTransaction().begin();

        try {
            // Solicitar al usuario el ID de la mascota a eliminar
            System.out.print("Ingrese el ID de la mascota a eliminar: ");
            Long idMascota = scanner.nextLong();
            scanner.nextLine(); // Consumir la nueva línea después de nextLong()

            // Buscar la mascota en la base de datos
            Mascota mascota = em.find(Mascota.class, idMascota);

            // Verificar si se encontró la mascota
            if (mascota == null) {
                System.out.println("No se encontró una mascota con el ID proporcionado.");
                return;
            }

            // Eliminar la mascota de la base de datos
            em.remove(mascota);

            // Confirmar la eliminación de la mascota
            System.out.println("Mascota eliminada con éxito.");
        } catch (Exception e) {
            // Manejar excepciones, imprimir el rastreo de la pila y realizar rollback en caso de error
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            // Commit de la transacción (se ejecuta independientemente de si hubo una excepción o no)
            em.getTransaction().commit();
        }
    }

    // Método para parsear una cadena de fecha en un objeto Date
    private static Date parseFecha(String fechaStr) throws ParseException {
        // Utilizar SimpleDateFormat para el parseo de fechas
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.parse(fechaStr);
    }

    // Método para actualizar una visita en la base de datos
    private static void actualizarVisita() {
        // Iniciar una transacción
        em.getTransaction().begin();

        try {
            // Solicitar al usuario el ID de la visita a actualizar
            System.out.print("Ingrese el ID de la visita a actualizar: ");
            Long idVisita = scanner.nextLong();
            scanner.nextLine(); // Consumir la nueva línea después de nextLong()

            // Buscar la visita en la base de datos
            Visita visita = em.find(Visita.class, idVisita);

            // Verificar si se encontró la visita
            if (visita == null) {
                System.out.println("No se encontró una visita con el ID proporcionado.");
                return;
            }

            // Solicitar al usuario la nueva fecha de la visita
            System.out.print("Ingrese la nueva fecha de la visita (formato dd/MM/yyyy HH:mm): ");
            String nuevaFechaStr = scanner.nextLine();
            System.out.print("Ingrese el nuevo motivo: ");
            String nuevoMotivo = scanner.nextLine();
            Date nuevaFecha = parseFecha(nuevaFechaStr);
            visita.setFecha(nuevaFecha);
            visita.setMotivoVacunacion(nuevoMotivo);
            // Confirmar la actualización de la visita
            System.out.println("Visita actualizada con éxito.");
        } catch (ParseException | NullPointerException e) {
            // Manejar excepciones, imprimir el rastreo de la pila y realizar rollback en caso de error
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            // Commit de la transacción (se ejecuta independientemente de si hubo una excepción o no)
            em.getTransaction().commit();
        }
    }

    // Método para eliminar una visita de la base de datos
    private static void eliminarVisita() {
        // Iniciar una transacción
        em.getTransaction().begin();

        try {
            // Solicitar al usuario el ID de la visita a eliminar
            System.out.print("Ingrese el ID de la visita a eliminar: ");
            Long idVisita = scanner.nextLong();
            scanner.nextLine(); // Consumir la nueva línea después de nextLong()

            // Buscar la visita en la base de datos
            Visita visita = em.find(Visita.class, idVisita);

            // Verificar si se encontró la visita
            if (visita == null) {
                System.out.println("No se encontró una visita con el ID proporcionado.");
                return;
            }

            // Eliminar la visita de la base de datos
            em.remove(visita);

            // Confirmar la eliminación de la visita
            System.out.println("Visita eliminada con éxito.");
        } catch (Exception e) {
            // Manejar excepciones, imprimir el rastreo de la pila y realizar rollback en caso de error
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            // Commit de la transacción (se ejecuta independientemente de si hubo una excepción o no)
            em.getTransaction().commit();
        }
    }
}
