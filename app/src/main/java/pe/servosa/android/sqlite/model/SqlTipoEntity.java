package pe.servosa.android.sqlite.model;

import com.orm.SugarRecord;

/**
 * Created by ucweb02 on 14/04/2016.
 */
public class SqlTipoEntity extends SugarRecord {

    private Integer IDTipo;
    private Integer IDCategoria;
    private String codigoTipo;
    private String nombreTipo;

    public SqlTipoEntity() {
    }

    public SqlTipoEntity(Integer IDTipo, Integer IDCategoria, String codigoTipo, String nombreTipo) {
        this.IDTipo = IDTipo;
        this.IDCategoria = IDCategoria;
        this.codigoTipo = codigoTipo;
        this.nombreTipo = nombreTipo;
    }

    public Integer getIDTipo() {
        return IDTipo;
    }

    public void setIDTipo(Integer IDTipo) {
        this.IDTipo = IDTipo;
    }

    public Integer getIDCategoria() {
        return IDCategoria;
    }

    public void setIDCategoria(Integer IDCategoria) {
        this.IDCategoria = IDCategoria;
    }

    public String getCodigoTipo() {
        return codigoTipo;
    }

    public void setCodigoTipo(String codigoTipo) {
        this.codigoTipo = codigoTipo;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

}
