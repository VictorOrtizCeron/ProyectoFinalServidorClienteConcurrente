package servidor;

import pojos.Cuenta;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionCuenta {
    ConexionBD conexion = new ConexionBD();
    ResultSet resultado = null;

    public boolean registrarCuenta (String nom, String contra,String email, String ciudad)
    {
        try
        {
            conexion.setConexion();
            conexion.setConsulta("Insert into octobereats.cuentas(nombre, contra, email,ciudad) values(?,?,?,?)");
            conexion.getConsulta().setString(1, nom);
            conexion.getConsulta().setString(2, contra);
            conexion.getConsulta().setString(3, email);
            conexion.getConsulta().setString(4, ciudad);

            if(conexion.getConsulta().executeUpdate() > 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(SQLException error)
        {
            error.printStackTrace();
            return false;
        }

    }
    public  boolean iniciarSesion(String email, String password)
    {
        try
        {
            conexion.setConexion();
            conexion.setConsulta("SELECT * FROM octobereats.cuentas WHERE email = ?" + "AND contra = ?");
            conexion.getConsulta().setString(1, email);
            conexion.getConsulta().setString(2, password);
            resultado = conexion.getResultado();


            if(resultado.next()){



                conexion.cerrar();
                return true;
            }
            else{
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
