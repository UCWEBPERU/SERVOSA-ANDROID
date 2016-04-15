package pe.servosa.android.model;

/**
 * Created by ucweb02 on 14/04/2016.
 */
public class PlacaEntity {

    private Integer ID;
    private Integer IDOperacion;
    private String placa;

    public PlacaEntity(Integer ID, Integer IDOperacion, String placa) {
        this.ID = ID;
        this.IDOperacion = IDOperacion;
        this.placa = placa;
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

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

}
