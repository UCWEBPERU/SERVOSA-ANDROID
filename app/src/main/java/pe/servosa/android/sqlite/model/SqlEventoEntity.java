package pe.servosa.android.sqlite.model;

import com.orm.SugarRecord;

/**
 * Created by ucweb02 on 14/04/2016.
 */
public class SqlEventoEntity extends SugarRecord {

    private Integer IDEvento;
    private String codigoEvento;
    private String nombreEvento;

    public SqlEventoEntity() {}

    public SqlEventoEntity(Integer IDEvento, String codigoEvento, String nombreEvento) {
        this.IDEvento = IDEvento;
        this.codigoEvento = codigoEvento;
        this.nombreEvento = nombreEvento;
    }

    public Integer getIDEvento() {
        return IDEvento;
    }

    public void setIDEvento(Integer IDEvento) {
        this.IDEvento = IDEvento;
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
