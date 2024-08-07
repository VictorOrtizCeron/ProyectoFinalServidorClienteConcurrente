package servidor;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestionCuenta {
    ConexionBD conexion = new ConexionBD();
    ResultSet resultado = null;

    public void registrarCuenta (String nom, String contra)
    {
        try
        {
            conexion.setConexion();
            conexion.setConsulta("Insert into octobereats.personaTest(nombre, contra) values(?,?)");
            conexion.getConsulta().setString(1, nom);
            conexion.getConsulta().setString(2, contra);

            if(conexion.getConsulta().executeUpdate() > 0)
            {
                JOptionPane.showMessageDialog(null, "Nueva cuenta registrada",
                        "Registro Nuevo", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Error en el registro!",
                        "Registro Nuevo", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(SQLException error)
        {
            error.printStackTrace();
        }
    }
}
