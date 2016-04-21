package pe.servosa.android.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.servosa.android.R;
import pe.servosa.android.sqlite.model.SqlOperacionEntity;

/**
 * Created by ucweb02 on 15/04/2016.
 */
public class ModalFiltroPiramide {

    private String[] filtroGerente = new String[]{"Piramide Nacional", "Piramide Regional", "Piramide por Operacion"};
    private String[] filtroSupervisor = new String[]{"Piramide Regional", "Piramide Operacion"};
    private String[] filtroRegion = new String[]{"Sur", "Norte", "Centro"};
    private List<String> filtroOperacion;

    private static ModalFiltroPiramide modalFiltroPiramide;

    private Activity activity;
    private Bundle bundle;
    private Intent intent;

    private List<SqlOperacionEntity> sqlOperacionEntities;

    private ModalFiltroPiramide() {

    }

    public static ModalFiltroPiramide getInstance() {
        if (modalFiltroPiramide == null) {
            modalFiltroPiramide = new ModalFiltroPiramide();
            return modalFiltroPiramide;
        }
        return modalFiltroPiramide;
    }

    public ModalFiltroPiramide init(Activity activity){
        this.activity = activity;
        bundle = new Bundle();
        return modalFiltroPiramide;
    }

    public void show(Intent intent) {
        this.intent = intent;
        String[] filtroPrincipal = mostrarFiltroPorTipoUsuario();
        new AlertDialog.Builder(activity)
                .setTitle("Piramide de Accidentabilidad")
                .setSingleChoiceItems(filtroPrincipal, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        validarAccesoPorTipoUsuario(which);
                    }
                })
                .create().show();
    }

    private String[] mostrarFiltroPorTipoUsuario() {
        if (MyPreferences.getInstance().getString("tipo_usuario", "").toLowerCase().equals("gerente")) { // Por Gerente
            return filtroGerente;
        } else if (MyPreferences.getInstance().getString("tipo_usuario", "").toLowerCase().equals("supervisor")) { // Por Supervisor
            return filtroSupervisor;
        }
        return new String[]{};
    }

    private void validarAccesoPorTipoUsuario(int posicionFiltroPrincipal) {
        if (MyPreferences.getInstance().getString("tipo_usuario", "").toLowerCase().equals("gerente")) { // Por Gerente
            if (posicionFiltroPrincipal == 0) {
                bundle.putString("filtro", "nacional");
                openPiramideActivity();
            } else if (posicionFiltroPrincipal == 1) {
                bundle.putString("filtro", "region");
                new AlertDialog.Builder(activity)
                        .setTitle(filtroGerente[1])
                        .setSingleChoiceItems(filtroRegion, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                bundle.putString("id_region", which + 1 + "");
                                openPiramideActivity();
                            }
                        })
                        .create().show();
            } else if (posicionFiltroPrincipal == 2) {
                cargarOperaciones();
                if (filtroOperacion.size() > 0) {
                    new AlertDialog.Builder(activity)
                            .setTitle(filtroGerente[2])
                            .setSingleChoiceItems(filtroOperacion.toArray(new String[0]), -1, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    bundle.putString("id_operacion", sqlOperacionEntities.get(which).getIDOperacion().toString());
                                    bundle.putString("filtro", "operacion");
                                    openPiramideActivity();
                                }
                            })
                            .create().show();
                }
            }
        } else if (MyPreferences.getInstance().getString("tipo_usuario", "").toLowerCase().equals("supervisor")) { // Por Supervisor
            if (posicionFiltroPrincipal == 0) {
                bundle.putString("filtro", "region");
            } else if (posicionFiltroPrincipal == 1) {
                bundle.putString("filtro", "operacion");
            }
            openPiramideActivity();
        }
    }

    private void openPiramideActivity() {
        intent.putExtra("filtros", bundle);
        activity.startActivity(intent);
    }

    private void cargarOperaciones() {
        filtroOperacion = new ArrayList<>();
        sqlOperacionEntities = SqlOperacionEntity.listAll(SqlOperacionEntity.class);

        if (sqlOperacionEntities.size() > 0) {
            for (int c = 0; c < sqlOperacionEntities.size(); c++) {
                filtroOperacion.add(sqlOperacionEntities.get(c).getNombreOperacion());
            }
        } else {
            Toast.makeText(activity, activity.getString(R.string.modal_filtro_piramide_error_registro_operaciones), Toast.LENGTH_LONG).show();
        }
    }

}
