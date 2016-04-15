package pe.servosa.android.sqlite.model;

import com.orm.SugarRecord;

/**
 * Created by ucweb02 on 14/04/2016.
 */
public class SqlRutaEntity extends SugarRecord {

    private Integer IDRuta;
    private Integer IDOperacion;
    private String nombreRuta;

    public SqlRutaEntity() {
    }

    public SqlRutaEntity(Integer IDRuta, Integer IDOperacion, String nombreRuta) {
        this.IDRuta = IDRuta;
        this.IDOperacion = IDOperacion;
        this.nombreRuta = nombreRuta;
    }

    public Integer getIDRuta() {
        return IDRuta;
    }

    public void setIDRuta(Integer IDRuta) {
        this.IDRuta = IDRuta;
    }

    public Integer getIDOperacion() {
        return IDOperacion;
    }

    public void setIDOperacion(Integer IDOperacion) {
        this.IDOperacion = IDOperacion;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }

}
