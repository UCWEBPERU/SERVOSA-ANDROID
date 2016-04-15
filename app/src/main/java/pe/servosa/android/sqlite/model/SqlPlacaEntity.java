package pe.servosa.android.sqlite.model;

import com.orm.SugarRecord;

/**
 * Created by ucweb02 on 14/04/2016.
 */
public class SqlPlacaEntity extends SugarRecord {

    private Integer IDPlaca;
    private Integer IDOperacion;
    private String placa;

    public SqlPlacaEntity() {
    }

    public SqlPlacaEntity(Integer IDPlaca, Integer IDOperacion, String placa) {
        this.IDPlaca = IDPlaca;
        this.IDOperacion = IDOperacion;
        this.placa = placa;
    }

    public Integer getIDPlaca() {
        return IDPlaca;
    }

    public void setIDPlaca(Integer IDPlaca) {
        this.IDPlaca = IDPlaca;
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
