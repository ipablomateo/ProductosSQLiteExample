package greenway.com.gt.productos;

/**
 * Created by Pablo on 08/04/2016.
 */
public class Producto {

    private int codigo;
    private String nombre;
    private String descripcion;
    private float precio;

    public Producto(String nombre, String descripcion, float precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public Producto(int codigo, String nombre, String descripcion, float precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public float getPrecio() {
        return precio;
    }
}
