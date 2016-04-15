package pe.servosa.android.model;

/**
 * Created by ucweb02 on 14/04/2016.
 */
public class RutaEntity {

    private Integer ID;
    private Integer IDOperacion;
    private String nombreRuta;

    public RutaEntity(Integer ID, Integer IDOperacion, String nombreRuta) {
        this.ID = ID;
        this.IDOperacion = IDOperacion;
        this.nombreRuta = nombreRuta;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
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
