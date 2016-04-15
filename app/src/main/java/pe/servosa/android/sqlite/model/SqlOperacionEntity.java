package pe.servosa.android.sqlite.model;

import com.orm.SugarRecord;

/**
 * Created by ucweb02 on 14/04/2016.
 */
public class SqlOperacionEntity extends SugarRecord {

    private Integer IDOperacion;
    private Integer IDRegion;
    private String nombreOperacion;

    public SqlOperacionEntity() { }

    public SqlOperacionEntity(Integer IDOperacion, Integer IDRegion, String nombreOperacion) {
        this.IDOperacion = IDOperacion;
        this.IDRegion = IDRegion;
        this.nombreOperacion = nombreOperacion;
    }

    public Integer getIDOperacion() {
        return IDOperacion;
    }

    public void setIDOperacion(Integer IDOperacion) {
        this.IDOperacion = IDOperacion;
    }

    public Integer getIDRegion() {
        return IDRegion;
    }

    public void setIDRegion(Integer IDRegion) {
        this.IDRegion = IDRegion;
    }

    public String getNombreOperacion() {
        return nombreOperacion;
    }

    public void setNombreOperacion(String nombreOperacion) {
        this.nombreOperacion = nombreOperacion;
    }

}
