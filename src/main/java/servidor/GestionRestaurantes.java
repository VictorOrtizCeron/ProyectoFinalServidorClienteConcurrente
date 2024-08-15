package servidor;

import pojos.Restaurante;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GestionRestaurantes {
    ConexionBD conexion = new ConexionBD();
    ResultSet resultado = null;

    public Restaurante[] getRestaurantes(){
        try
        {
            conexion.setConexion();
            conexion.setConsulta("SELECT * FROM octobereats.restaurantes;");


            resultado = conexion.getResultado();

            ArrayList<Restaurante> restaurantes = new ArrayList<>();


            while(resultado.next()){

                String nombre = resultado.getString("nombre");
                String ciudad = resultado.getString("ciudad");
                String desc = resultado.getString("descripcion");

                Restaurante temp = new Restaurante(nombre,ciudad,desc);
                restaurantes.add(temp);
            }

            Restaurante[] ArregloRestaurantes = new Restaurante[restaurantes.size()];
            int i =0;
            for(Restaurante rest: restaurantes){
                ArregloRestaurantes[i] = rest;
                i++;
            }
            return ArregloRestaurantes;

        }
        catch(SQLException error)
        {
            error.printStackTrace();
            return null;

        }
    }
}
