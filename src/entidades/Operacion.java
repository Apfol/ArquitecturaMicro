package entidades;

public class Operacion {
    String instruccion;
    String operacion;
    String comentario;

    public Operacion(String instruccion, String operacion, String comentario) {
        this.instruccion = instruccion;
        this.operacion = operacion;
        this.comentario = comentario;
    }

    public String getInstruccion() {
        return instruccion;
    }

    public void setInstruccion(String instruccion) {
        this.instruccion = instruccion;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
