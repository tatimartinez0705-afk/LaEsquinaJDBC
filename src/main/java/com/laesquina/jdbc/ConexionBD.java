
package com.laesquina.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Administra la conexión entre Java y la base de datos MySQL.
 * La contraseña se solicita al ejecutar el programa y no se guarda
 * directamente en el código fuente.
 */
public class ConexionBD {

    // Dirección del servidor y nombre de la base de datos.
    private static final String URL =
            "jdbc:mysql://localhost:3306/laesquina_jdbc";

    // Usuario de MySQL utilizado para la conexión local.
    // Usuario de MySQL creado exclusivamente para este proyecto.
    private static final String USUARIO = "laesquina_app";

    /**
     * Establece una conexión con MySQL.
     *
     * @return conexión activa o null si ocurre un error.
     */
    public static Connection obtenerConexion() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingresa la contraseña de MySQL: ");
        String contrasena = scanner.nextLine();

        try {
            // Intentar establecer la conexión mediante JDBC.
            Connection conexion = DriverManager.getConnection(
                    URL, USUARIO, contrasena);

            System.out.println("Conexión con MySQL establecida correctamente.");
            return conexion;

        } catch (SQLException e) {
            // Informar el error sin mostrar la contraseña.
            System.out.println("No fue posible conectar con MySQL.");
            System.out.println("Detalle: " + e.getMessage());
            return null;
        }
    }
}
