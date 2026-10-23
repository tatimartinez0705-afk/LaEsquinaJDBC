
package com.laesquina.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Comprueba que el proyecto puede conectarse a la base de datos MySQL.
 */
public class PruebaConexion {

    public static void main(String[] args) {

        // Solicitar una conexión a la base de datos.
        try (Connection conexion = ConexionBD.obtenerConexion()) {

            if (conexion != null && !conexion.isClosed()) {
                // Confirmar que la conexión está disponible.
                System.out.println("Prueba de conexión exitosa.");
            }

        } catch (SQLException e) {
            // Informar cualquier error al cerrar o verificar la conexión.
            System.out.println("Error durante la prueba: " + e.getMessage());
        }
    }
}
