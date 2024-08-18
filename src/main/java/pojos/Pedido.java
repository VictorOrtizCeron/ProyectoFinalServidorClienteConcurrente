package pojos;

public class Pedido {
    private long id_pedido;
    private long id_cliente;
    private long id_restaurante;
    private float precio;
    private String factura;



    public long getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(long id_pedido) {
        this.id_pedido = id_pedido;
    }

    public long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public long getId_restaurante() {
        return id_restaurante;
    }

    public void setId_restaurante(long id_restaurante) {
        this.id_restaurante = id_restaurante;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }
}
