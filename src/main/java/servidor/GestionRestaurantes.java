package servidor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionRestaurantes {
    ConexionBD conexion = new ConexionBD();
    ResultSet resultado = null;

    public boolean getRestaurantes(){
        try
        {
            conexion.setConexion();
            conexion.setConsulta("SELECT * FROM octobereats.restaurantes;");


            resultado = conexion.getResultado();

            while(resultado.next()){
                for (int i = 1; i <= 4; i++) {
                    System.out.print(resultado.getString(i) + "\t");
                }
                System.out.println();
            }
            return true;


        }
        catch(SQLException error)
        {
            error.printStackTrace();
            return false;
        }
    }
}
