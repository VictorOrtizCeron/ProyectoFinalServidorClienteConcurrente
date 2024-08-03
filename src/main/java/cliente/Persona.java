package cliente;

public class Persona {

    private String contra;
    private String nombre;

    public Persona(String nombre, String contra) {

        this.nombre = nombre;
        this.contra = contra;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", contraseña='" + contra + '\'' +
                '}';
    }
}
