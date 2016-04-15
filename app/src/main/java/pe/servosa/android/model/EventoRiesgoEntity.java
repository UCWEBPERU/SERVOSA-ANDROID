package pe.servosa.android.model;

/**
 * Created by ucweb02 on 08/04/2016.
 */
public class EventoRiesgoEntity {

    String operacion;
    String ruta;
    String tramo;
    String evento;
    String categoria;
    String tipo;
    String numPlaca;
    String descripcion;
    String fechaHora;

    public EventoRiesgoEntity(String operacion, String ruta, String tramo, String evento, String categoria, String tipo, String numPlaca, String descripcion, String fechaHora) {
        this.operacion = operacion;
        this.ruta = ruta;
        this.tramo = tramo;
        this.evento = evento;
        this.categoria = categoria;
        this.tipo = tipo;
        this.numPlaca = numPlaca;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getTramo() {
        return tramo;
    }

    public void setTramo(String tramo) {
        this.tramo = tramo;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumPlaca() {
        return numPlaca;
    }

    public void setNumPlaca(String numPlaca) {
        this.numPlaca = numPlaca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }
}
