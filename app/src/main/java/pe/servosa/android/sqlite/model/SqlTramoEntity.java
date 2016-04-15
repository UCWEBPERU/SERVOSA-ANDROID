package pe.servosa.android.sqlite.model;

import com.orm.SugarRecord;

/**
 * Created by ucweb02 on 14/04/2016.
 */
public class SqlTramoEntity extends SugarRecord {

    private Integer IDTramo;
    private Integer IDRuta;
    private String nombreTramo;
    private String velocidad;

    public SqlTramoEntity() {
    }

    public SqlTramoEntity(Integer IDTramo, Integer IDRuta, String nombreTramo, String velocidad) {
        this.IDTramo = IDTramo;
        this.IDRuta = IDRuta;
        this.nombreTramo = nombreTramo;
        this.velocidad = velocidad;
    }

    public Integer getIDTramo() {
        return IDTramo;
    }

    public void setIDTramo(Integer IDTramo) {
        this.IDTramo = IDTramo;
    }

    public Integer getIDRuta() {
        return IDRuta;
    }

    public void setIDRuta(Integer IDRuta) {
        this.IDRuta = IDRuta;
    }

    public String getNombreTramo() {
        return nombreTramo;
    }

    public void setNombreTramo(String nombreTramo) {
        this.nombreTramo = nombreTramo;
    }

    public String getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(String velocidad) {
        this.velocidad = velocidad;
    }

}
