package pe.servosa.android.sqlite.model;

import com.orm.SugarRecord;

/**
 * Created by ucweb02 on 15/04/2016.
 */
public class SqlEventoRiesgoEntity extends SugarRecord {

    private String operacion;
    private String ruta;
    private String tramo;
    private String evento;
    private String categoria;
    private String tipo;
    private String numero_placa;
    private String descripcion;
    private String fecha_registro;

    public SqlEventoRiesgoEntity() {
    }

    public SqlEventoRiesgoEntity(String operacion, String ruta, String tramo, String evento, String categoria, String tipo, String numero_placa, String descripcion, String fecha_registro) {
        this.operacion = operacion;
        this.ruta = ruta;
        this.tramo = tramo;
        this.evento = evento;
        this.categoria = categoria;
        this.tipo = tipo;
        this.numero_placa = numero_placa;
        this.descripcion = descripcion;
        this.fecha_registro = fecha_registro;
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

    public String getNumero_placa() {
        return numero_placa;
    }

    public void setNumero_placa(String numero_placa) {
        this.numero_placa = numero_placa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

}
