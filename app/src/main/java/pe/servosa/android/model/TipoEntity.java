package pe.servosa.android.model;

/**
 * Created by ucweb02 on 14/04/2016.
 */
public class TipoEntity {

    private Integer ID;
    private Integer IDCategoria;
    private String codigoTipo;
    private String nombreTipo;

    public TipoEntity(Integer ID, Integer IDCategoria, String codigoTipo, String nombreTipo) {
        this.ID = ID;
        this.IDCategoria = IDCategoria;
        this.codigoTipo = codigoTipo;
        this.nombreTipo = nombreTipo;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
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
