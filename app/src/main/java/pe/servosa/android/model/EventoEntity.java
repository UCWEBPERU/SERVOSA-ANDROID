package pe.servosa.android.model;

/**
 * Created by ucweb02 on 14/04/2016.
 */
public class EventoEntity {

    private Integer ID;
    private String codigoEvento;
    private String nombreEvento;

    public EventoEntity(Integer ID, String codigoEvento, String nombreEvento) {
        this.ID = ID;
        this.codigoEvento = codigoEvento;
        this.nombreEvento = nombreEvento;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getCodigoEvento() {
        return codigoEvento;
    }

    public void setCodigoEvento(String codigoEvento) {
        this.codigoEvento = codigoEvento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }
}
