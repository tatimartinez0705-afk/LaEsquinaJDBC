
package com.laesquina.jdbc;

import java.math.BigDecimal;

/**
 * Representa un platillo del restaurante.
 * Sus atributos corresponden a las columnas de la tabla MySQL.
 */
public class Platillo {

    // Identificador único generado automáticamente por MySQL.
    private int id;

    // Nombre y descripción del platillo.
    private String nombre;
    private String descripcion;

    // BigDecimal permite manejar precios con decimales correctamente.
    private BigDecimal precio;

    // Categoría a la que pertenece el platillo.
    private String categoria;

    // Indica si el platillo está disponible.
    private boolean estado;

    // Constructor para crear un platillo nuevo sin identificador.
    public Platillo(String nombre, String descripcion,
                    BigDecimal precio, String categoria,
                    boolean estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoria = categoria;
        this.estado = estado;
    }

    // Constructor para representar un platillo ya guardado en MySQL.
    public Platillo(int id, String nombre, String descripcion,
                    BigDecimal precio, String categoria,
                    boolean estado) {
        this(nombre, descripcion, precio, categoria, estado);
        this.id = id;
    }

    // Métodos para consultar y modificar los atributos del platillo.
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isEstado() { return estado; }
    public void setEstado(boolean estado) { this.estado = estado; }
}
