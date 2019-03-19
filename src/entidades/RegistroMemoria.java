package entidades;


import javafx.scene.control.TextField;

public class RegistroMemoria {

    String direccion;
    TextField contenido;

    public RegistroMemoria(String direccion, String contenido) {
        this.direccion = direccion;
        this.contenido = new TextField(contenido);
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public TextField getContenido() {
        return contenido;
    }

    public void setContenido(TextField contenido) {
        this.contenido = contenido;
    }
}
