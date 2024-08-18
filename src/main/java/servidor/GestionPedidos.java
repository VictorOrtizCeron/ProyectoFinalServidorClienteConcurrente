package servidor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionPedidos {
    ConexionBD conexion = new ConexionBD();
    ResultSet resultado = null;

    public boolean crearPedido (String emailCliente, String restaurante,String factura,Float precio)
    {
        String id_cliente ="";
        String id_restaurante = "";
        try
        {
            conexion.setConexion();

            //obtiene id del cliente
            conexion.setConsulta("SELECT id FROM octobereats.cuentas WHERE email = ?;");
            conexion.getConsulta().setString(1,emailCliente);
            resultado = conexion.getResultado();

            while(resultado.next()){
                id_cliente = resultado.getString("id");
            }

            //obtiene id del restaurante
            conexion.setConsulta("SELECT id FROM octobereats.restaurantes WHERE nombre = ? ;");
            conexion.getConsulta().setString(1,restaurante);
            resultado = conexion.getResultado();

            while(resultado.next()){
                id_restaurante = resultado.getString("id");

            }

            //inserta pedido en la base de datos
            conexion.setConsulta("INSERT INTO octobereats.pedidos(id_cliente, id_restaurante, precio,factura) VALUES(?,?,?,?);");
            conexion.getConsulta().setString(1, id_cliente);
            conexion.getConsulta().setString(2, id_restaurante);
            conexion.getConsulta().setFloat(3, precio);
            conexion.getConsulta().setString(4, factura);



            if(conexion.getConsulta().executeUpdate() > 0)
            {
                conexion.cerrar();
                return true;
            }
            else
            {
                conexion.cerrar();
                return false;
            }
        }
        catch(SQLException error)
        {
            error.printStackTrace();
            return false;
        }

    }
    public boolean actualizarPedido (String emailCliente, String restaurante,String factura,Float precio)
    {

        String id_cliente ="";
        String id_restaurante = "";
        try
        {
            conexion.setConexion();

            //obtiene id del cliente
            conexion.setConsulta("SELECT id FROM octobereats.cuentas WHERE email = ?;");
            conexion.getConsulta().setString(1,emailCliente);
            resultado = conexion.getResultado();

            while(resultado.next()){
                id_cliente = resultado.getString("id");
            }

            //obtiene id del restaurante
            conexion.setConsulta("SELECT id FROM octobereats.restaurantes WHERE nombre = ? ;");
            conexion.getConsulta().setString(1,restaurante);
            resultado = conexion.getResultado();

            while(resultado.next()){
                id_restaurante = resultado.getString("id");

            }

            //inserta pedido en la base de datos
            conexion.setConsulta("UPDATE octobereats.pedidos SET isEntregado = TRUE " +
                    "WHERE id_cliente =? AND id_restaurante =? ORDER BY id DESC LIMIT 1;");
            conexion.getConsulta().setString(1, id_cliente);
            conexion.getConsulta().setString(2, id_restaurante);




            if(conexion.getConsulta().executeUpdate() > 0)
            {
                conexion.cerrar();
                return true;
            }
            else
            {
                conexion.cerrar();
                return false;
            }
        }
        catch(SQLException error)
        {
            error.printStackTrace();
            return false;
        }

    }

}
