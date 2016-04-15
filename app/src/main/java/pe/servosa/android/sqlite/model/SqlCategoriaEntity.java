package pe.servosa.android.sqlite.model;

import com.orm.SugarRecord;

/**
 * Created by ucweb02 on 14/04/2016.
 */

public class SqlCategoriaEntity extends SugarRecord {

    private Integer IDCategoria;
    private Integer IDEvento;
    private String codigoCategoria;
    private String nombreCategoria;

    public SqlCategoriaEntity(){}

    public SqlCategoriaEntity(Integer IDCategoria, Integer IDEvento, String codigoCategoria, String nombreCategoria) {
        this.IDCategoria = IDCategoria;
        this.IDEvento = IDEvento;
        this.codigoCategoria = codigoCategoria;
        this.nombreCategoria = nombreCategoria;
    }

    public Integer getIDCategoria() {
        return IDCategoria;
    }

    public void setIDCategoria(Integer IDCategoria) {
        this.IDCategoria = IDCategoria;
    }

    public Integer getIDEvento() {
        return IDEvento;
    }

    public void setIDEvento(Integer IDEvento) {
        this.IDEvento = IDEvento;
    }

    public String getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(String codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

}
