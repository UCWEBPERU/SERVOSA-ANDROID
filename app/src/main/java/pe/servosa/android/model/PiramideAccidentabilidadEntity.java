package pe.servosa.android.model;

import com.orm.SugarRecord;

/**
 * Created by ucweb02 on 20/04/2016.
 */
public class PiramideAccidentabilidadEntity {

    private String region;
    private String operacion;
    private String ruta;
    private String tramo;
    private String placa;
    private String observador;
    private String nivel_observador;
    private String fecha;
    private String hora;
    private String evento;
    private String categoria;
    private String tipo;
    private String descripcion;

    public PiramideAccidentabilidadEntity(String region, String operacion, String ruta, String tramo, String placa, String observador, String nivel_observador, String fecha, String hora, String evento, String categoria, String tipo, String descripcion) {
        this.region = region;
        this.operacion = operacion;
        this.ruta = ruta;
        this.tramo = tramo;
        this.placa = placa;
        this.observador = observador;
        this.nivel_observador = nivel_observador;
        this.fecha = fecha;
        this.hora = hora;
        this.evento = evento;
        this.categoria = categoria;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getObservador() {
        return observador;
    }

    public void setObservador(String observador) {
        this.observador = observador;
    }

    public String getNivel_observador() {
        return nivel_observador;
    }

    public void setNivel_observador(String nivel_observador) {
        this.nivel_observador = nivel_observador;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
