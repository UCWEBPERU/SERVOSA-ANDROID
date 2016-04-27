package pe.servosa.android.sqlite;

import pe.servosa.android.sqlite.model.SqlCategoriaEntity;
import pe.servosa.android.sqlite.model.SqlEventoEntity;
import pe.servosa.android.sqlite.model.SqlOperacionEntity;
import pe.servosa.android.sqlite.model.SqlPlacaEntity;
import pe.servosa.android.sqlite.model.SqlRutaEntity;
import pe.servosa.android.sqlite.model.SqlTipoEntity;
import pe.servosa.android.sqlite.model.SqlTramoEntity;

/**
 * Created by ucweb02 on 27/04/2016.
 */
public class DB {
    public static void deleteAll() {
        try{
            SqlOperacionEntity.deleteAll(SqlOperacionEntity.class);
            SqlRutaEntity.deleteAll(SqlRutaEntity.class);
            SqlTramoEntity.deleteAll(SqlTramoEntity.class);
            SqlEventoEntity.deleteAll(SqlEventoEntity.class);
            SqlCategoriaEntity.deleteAll(SqlCategoriaEntity.class);
            SqlTipoEntity.deleteAll(SqlTipoEntity.class);
            SqlPlacaEntity.deleteAll(SqlPlacaEntity.class);
        } catch (ExceptionInInitializerError ex) {
            ex.printStackTrace();
        }
    }
}
