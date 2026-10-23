
package com.laesquina.jdbc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal para probar las operaciones CRUD del módulo JDBC.
 */
public class PruebaCRUD {

    public static void main(String[] args) {
        // Crear el DAO que administra las operaciones de base de datos.
        PlatilloDAO dao = new PlatilloDAO();

        // Leer las opciones y los datos introducidos por el usuario.
        Scanner teclado = new Scanner(System.in);
        int opcion;

        do {
            // Mostrar las operaciones disponibles.
            System.out.println("\n===== LA ESQUINA DEL SABOR =====");
            System.out.println("1. Registrar platillo");
            System.out.println("2. Consultar platillos");
            System.out.println("3. Actualizar platillo");
            System.out.println("4. Eliminar platillo");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");

            opcion = leerEntero(teclado);

            // Ejecutar la operación seleccionada.
            switch (opcion) {
                case 1 -> registrar(teclado, dao);
                case 2 -> consultar(dao);
                case 3 -> actualizar(teclado, dao);
                case 4 -> eliminar(teclado, dao);
                case 0 -> System.out.println("Programa finalizado.");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);

        teclado.close();
    }

    // Solicita los datos y registra un nuevo platillo.
    private static void registrar(Scanner teclado, PlatilloDAO dao) {
        System.out.println("\n--- Registrar platillo ---");

        System.out.print("Nombre: ");
        String nombre = teclado.nextLine();

        System.out.print("Descripción: ");
        String descripcion = teclado.nextLine();

        System.out.print("Precio: ");
        BigDecimal precio = leerPrecio(teclado);

        System.out.print("Categoría: ");
        String categoria = teclado.nextLine();

        System.out.print("¿Está disponible? (s/n): ");
        boolean estado = teclado.nextLine().trim().equalsIgnoreCase("s");

        // Crear el objeto y enviarlo al DAO para su almacenamiento.
        Platillo platillo = new Platillo(
                nombre, descripcion, precio, categoria, estado);

        if (dao.insertar(platillo)) {
            System.out.println("Platillo registrado correctamente.");
        } else {
            System.out.println("No fue posible registrar el platillo.");
        }
    }

    // Consulta y muestra todos los platillos guardados.
    private static void consultar(PlatilloDAO dao) {
        System.out.println("\n--- Lista de platillos ---");

        List<Platillo> platillos = dao.consultarTodos();

        if (platillos.isEmpty()) {
            System.out.println("No hay platillos registrados.");
            return;
        }

        // Recorrer la lista para presentar cada registro.
        for (Platillo platillo : platillos) {
            System.out.println(
                    "ID: " + platillo.getId()
                    + " | Nombre: " + platillo.getNombre()
                    + " | Descripción: " + platillo.getDescripcion()
                    + " | Precio: $" + platillo.getPrecio()
                    + " | Categoría: " + platillo.getCategoria()
                    + " | Disponible: " + platillo.isEstado()
            );
        }
    }

    // Modifica los datos de un platillo identificado por su ID.
    private static void actualizar(Scanner teclado, PlatilloDAO dao) {
        System.out.println("\n--- Actualizar platillo ---");

        System.out.print("ID del platillo: ");
        int id = leerEntero(teclado);

        System.out.print("Nuevo nombre: ");
        String nombre = teclado.nextLine();

        System.out.print("Nueva descripción: ");
        String descripcion = teclado.nextLine();

        System.out.print("Nuevo precio: ");
        BigDecimal precio = leerPrecio(teclado);

        System.out.print("Nueva categoría: ");
        String categoria = teclado.nextLine();

        System.out.print("¿Está disponible? (s/n): ");
        boolean estado = teclado.nextLine().trim().equalsIgnoreCase("s");

        // Construir el objeto con el ID y los datos actualizados.
        Platillo platillo = new Platillo(
                id, nombre, descripcion, precio, categoria, estado);

        if (dao.actualizar(platillo)) {
            System.out.println("Platillo actualizado correctamente.");
        } else {
            System.out.println(
                    "No se actualizó ningún registro. Revisa el ID.");
        }
    }

    // Elimina un platillo a partir de su identificador.
    private static void eliminar(Scanner teclado, PlatilloDAO dao) {
        System.out.println("\n--- Eliminar platillo ---");

        System.out.print("ID del platillo que deseas eliminar: ");
        int id = leerEntero(teclado);

        if (dao.eliminar(id)) {
            System.out.println("Platillo eliminado correctamente.");
        } else {
            System.out.println(
                    "No se eliminó ningún registro. Revisa el ID.");
        }
    }

    // Lee un número entero y evita que el programa termine por formato inválido.
    private static int leerEntero(Scanner teclado) {
        while (true) {
            String entrada = teclado.nextLine().trim();

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.print("Ingresa un número entero válido: ");
            }
        }
    }

    // Lee un precio admitiendo coma o punto como separador decimal.
    private static BigDecimal leerPrecio(Scanner teclado) {
        while (true) {
            String entrada = teclado.nextLine().trim().replace(',', '.');

            try {
                BigDecimal precio = new BigDecimal(entrada);

                if (precio.signum() > 0) {
                    return precio;
                }

                System.out.print("El precio debe ser mayor que cero: ");
            } catch (NumberFormatException e) {
                System.out.print("Ingresa un precio válido: ");
            }
        }
    }
}
