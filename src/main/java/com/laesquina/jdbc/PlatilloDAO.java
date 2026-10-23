
package com.laesquina.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object).
 * Esta clase centraliza las operaciones CRUD sobre la tabla platillos.
 */
public class PlatilloDAO {

    /**
     * Registra un platillo nuevo en la base de datos.
     *
     * @param platillo objeto con los datos que se van a guardar.
     * @return true si se insertó correctamente.
     */
    public boolean insertar(Platillo platillo) {
        String sql = "INSERT INTO platillos "
                + "(nombre, descripcion, precio, categoria, estado) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia =
                     conexion == null ? null : conexion.prepareStatement(sql)) {

            // Si la conexión falla, no se intenta ejecutar la consulta.
            if (conexion == null) {
                return false;
            }

            // Asignar los valores del objeto a los parámetros SQL.
            sentencia.setString(1, platillo.getNombre());
            sentencia.setString(2, platillo.getDescripcion());
            sentencia.setBigDecimal(3, platillo.getPrecio());
            sentencia.setString(4, platillo.getCategoria());
            sentencia.setBoolean(5, platillo.isEstado());

            // Ejecutar la inserción y verificar si se creó un registro.
            return sentencia.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar el platillo: "
                    + e.getMessage());
            return false;
        }
    }

    /**
     * Consulta todos los platillos registrados.
     *
     * @return lista de platillos encontrados.
     */
    public List<Platillo> consultarTodos() {
        List<Platillo> platillos = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion, precio, "
                + "categoria, estado FROM platillos ORDER BY id";

        try (Connection conexion = ConexionBD.obtenerConexion()) {

            // Detener la consulta si no se pudo establecer la conexión.
            if (conexion == null) {
                return platillos;
            }

            try (Statement sentencia = conexion.createStatement();
                 ResultSet resultado = sentencia.executeQuery(sql)) {

                // Recorrer los resultados y convertir cada fila en un objeto.
                while (resultado.next()) {
                    Platillo platillo = new Platillo(
                            resultado.getInt("id"),
                            resultado.getString("nombre"),
                            resultado.getString("descripcion"),
                            resultado.getBigDecimal("precio"),
                            resultado.getString("categoria"),
                            resultado.getBoolean("estado")
                    );

                    platillos.add(platillo);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar los platillos: "
                    + e.getMessage());
        }

        return platillos;
    }

    /**
     * Actualiza los datos de un platillo existente.
     *
     * @param platillo objeto que contiene el identificador y los nuevos datos.
     * @return true si se modificó un registro.
     */
    public boolean actualizar(Platillo platillo) {
        String sql = "UPDATE platillos SET nombre = ?, descripcion = ?, "
                + "precio = ?, categoria = ?, estado = ? WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion()) {

            // Verificar la conexión antes de preparar la consulta.
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement sentencia =
                         conexion.prepareStatement(sql)) {

                // Asignar los nuevos valores y el ID del platillo.
                sentencia.setString(1, platillo.getNombre());
                sentencia.setString(2, platillo.getDescripcion());
                sentencia.setBigDecimal(3, platillo.getPrecio());
                sentencia.setString(4, platillo.getCategoria());
                sentencia.setBoolean(5, platillo.isEstado());
                sentencia.setInt(6, platillo.getId());

                // Indicar si se actualizó alguna fila.
                return sentencia.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar el platillo: "
                    + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un platillo mediante su identificador.
     *
     * @param id identificador del platillo que se desea eliminar.
     * @return true si se eliminó un registro.
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM platillos WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion()) {

            // Detener la operación si no hay conexión disponible.
            if (conexion == null) {
                return false;
            }

            try (PreparedStatement sentencia =
                         conexion.prepareStatement(sql)) {

                // Establecer el identificador del registro por eliminar.
                sentencia.setInt(1, id);

                // Ejecutar la eliminación y verificar el resultado.
                return sentencia.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar el platillo: "
                    + e.getMessage());
            return false;
        }
    }
}
