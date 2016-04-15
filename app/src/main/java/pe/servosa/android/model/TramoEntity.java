package pe.servosa.android.model;

/**
 * Created by ucweb02 on 14/04/2016.
 */
public class TramoEntity {

    private Integer ID;
    private Integer IDRuta;
    private String nombreTramo;
    private String velocida;

    public TramoEntity(Integer ID, Integer IDRuta, String nombreTramo, String velocida) {
        this.ID = ID;
        this.IDRuta = IDRuta;
        this.nombreTramo = nombreTramo;
        this.velocida = velocida;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
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

    public String getVelocida() {
        return velocida;
    }

    public void setVelocida(String velocida) {
        this.velocida = velocida;
    }

}
