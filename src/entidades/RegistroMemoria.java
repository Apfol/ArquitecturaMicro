package entidades;


import javafx.scene.control.TextField;

public class RegistroMemoria {

    String direccion;
    String contenido;

    public RegistroMemoria(String direccion, String contenido) {
        this.direccion = direccion;
        this.contenido = contenido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
