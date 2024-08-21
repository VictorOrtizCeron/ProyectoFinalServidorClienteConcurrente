package Controller;

import Model.Producto;
import Model.Restaurante;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GestionRestaurantes {
    ConexionBD conexion = new ConexionBD();
    ResultSet resultado = null;

    public Restaurante[] getRestaurantes() {
        try {
            conexion.setConexion();
            conexion.setConsulta("SELECT * FROM octobereats.restaurantes;");


            resultado = conexion.getResultado();

            ArrayList<Restaurante> restaurantes = new ArrayList<>();


            while (resultado.next()) {

                String nombre = resultado.getString("nombre");
                String ciudad = resultado.getString("ciudad");
                String desc = resultado.getString("descripcion");

                Restaurante temp = new Restaurante(nombre, ciudad, desc);
                restaurantes.add(temp);
            }

            Restaurante[] ArregloRestaurantes = new Restaurante[restaurantes.size()];
            int i = 0;
            for (Restaurante rest : restaurantes) {
                ArregloRestaurantes[i] = rest;
                i++;
            }
            return ArregloRestaurantes;

        } catch (SQLException error) {
            error.printStackTrace();
            return null;

        }
    }

    public Producto[] getProductos(String restaurante) {
        try {
            conexion.setConexion();
            conexion.setConsulta(
                    "SELECT p.nombre,p.precio,p.descripcion " +
                            "FROM octobereats.productos p " +
                            "JOIN octobereats.restaurantes r ON id_restaurante = r.id " +
                            "WHERE r.nombre = ?;");

            conexion.getConsulta().setString(1, restaurante);


            resultado = conexion.getResultado();

            ArrayList<Producto> productos = new ArrayList<>();


            while (resultado.next()) {

                String nombre = resultado.getString("nombre");
                Float precio = resultado.getFloat("precio");
                String desc = resultado.getString("descripcion");

                Producto temp = new Producto(nombre, precio, desc);
                productos.add(temp);
            }

            Producto[] ArregloProductos = new Producto[productos.size()];
            int i = 0;
            for (Producto prod : productos) {
                ArregloProductos[i] = prod;
                i++;
            }
            return ArregloProductos;

        } catch (SQLException error) {
            error.printStackTrace();
            return null;

        }
    }
}
