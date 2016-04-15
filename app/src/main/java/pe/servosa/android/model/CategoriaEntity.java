package pe.servosa.android.model;

/**
 * Created by ucweb02 on 14/04/2016.
 */
public class CategoriaEntity {

    private Integer ID;
    private Integer IDEvento;
    private String codigoCategoria;
    private String nombreCategoria;

    public CategoriaEntity(Integer ID, Integer IDEvento, String codigoCategoria, String nombreCategoria) {
        this.ID = ID;
        this.IDEvento = IDEvento;
        this.codigoCategoria = codigoCategoria;
        this.nombreCategoria = nombreCategoria;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
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
