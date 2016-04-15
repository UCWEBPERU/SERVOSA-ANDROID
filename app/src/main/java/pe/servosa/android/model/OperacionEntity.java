package pe.servosa.android.model;

/**
 * Created by ucweb02 on 14/04/2016.
 */
public class OperacionEntity {

    private Integer ID;
    private Integer IDRegion;
    private String nombreOperacion;

    public OperacionEntity(Integer ID, Integer IDRegion, String nombreOperacion) {
        this.ID = ID;
        this.IDRegion = IDRegion;
        this.nombreOperacion = nombreOperacion;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
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
