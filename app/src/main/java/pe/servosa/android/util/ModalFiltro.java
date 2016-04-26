package pe.servosa.android.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.servosa.android.GraficoCompSeguroActivity;
import pe.servosa.android.GraficoPiramideBridActivity;
import pe.servosa.android.R;
import pe.servosa.android.sqlite.model.SqlOperacionEntity;

/**
 * Created by ucweb02 on 15/04/2016.
 */
public abstract class ModalFiltro {

    private String[] seleccionePresentacionDatos;
    private String[] filtroGerente;
    private String[] filtroSupervisor;
    protected String[] filtroRegion = new String[]{"Sur", "Norte", "Centro"};
    private List<String> filtroOperacion;

    private Activity activity;
    private Bundle bundle;
    private Intent intent;

    private List<SqlOperacionEntity> sqlOperacionEntities;

    protected ModalFiltro(Activity activity) {
        this.activity = activity;
        bundle = new Bundle();
    }

    abstract public void showModal();
    abstract public void destroy();

    protected void showModalOpciones(int tipoGrafico) {
        String[] filtroPrincipal = mostrarFiltroPorTipoUsuario();

        if (tipoGrafico == 0) {
            bundle.putBoolean("isPiramideBrid", true);
        } else {
            bundle.putBoolean("isPiramideBrid", false);
        }

        new AlertDialog.Builder(activity)
                .setTitle((tipoGrafico == 0) ? seleccionePresentacionDatos[0] : seleccionePresentacionDatos[1])
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
        if (MyPreferences.getInstance().getString("id_tipo_usuario", "").toLowerCase().equals("2")) { // Por Gerente
            return filtroGerente;
        } else if (MyPreferences.getInstance().getString("id_tipo_usuario", "").toLowerCase().equals("1")) { // Por Supervisor
            return filtroSupervisor;
        }
        return new String[]{};
    }

    private void validarAccesoPorTipoUsuario(int posicionFiltroPrincipal) {
        if (MyPreferences.getInstance().getString("id_tipo_usuario", "").toLowerCase().equals("2")) { // Por Gerente
            if (posicionFiltroPrincipal == 0) {
                bundle.putString("filtro", "nacional");
                openActivity();
            } else if (posicionFiltroPrincipal == 1) {
                bundle.putString("filtro", "region");
                new AlertDialog.Builder(activity)
                        .setTitle(filtroGerente[1])
                        .setSingleChoiceItems(filtroRegion, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                bundle.putString("id_region", which + 1 + "");
                                openActivity();
                            }
                        })
                        .create().show();
            } else if (posicionFiltroPrincipal == 2) {
                cargarOperaciones();
                seleccionarOperaciones();
            }
        } else if (MyPreferences.getInstance().getString("id_tipo_usuario", "").toLowerCase().equals("1")) { // Por Supervisor
            if (posicionFiltroPrincipal == 0) {
                bundle.putString("filtro", "region");
                openActivity();
            } else if (posicionFiltroPrincipal == 1) {
//                bundle.putString("filtro", "operacion");
                cargarOperaciones();
                seleccionarOperaciones();
            }
        }
    }

    private void openActivity() {
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

    private void seleccionarOperaciones() {
        if (filtroOperacion.size() > 0) {
            new AlertDialog.Builder(activity)
                    .setTitle(filtroGerente[2])
                    .setSingleChoiceItems(filtroOperacion.toArray(new String[0]), -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            bundle.putString("id_operacion", sqlOperacionEntities.get(which).getIDOperacion().toString());
                            bundle.putString("filtro", "operacion");
                            openActivity();
                        }
                    })
                    .create().show();
        }
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void setSeleccionePresentacionDatos(String[] seleccionePresentacionDatos) {
        this.seleccionePresentacionDatos = seleccionePresentacionDatos;
    }

    public void setFiltroGerente(String[] filtroGerente) {
        this.filtroGerente = filtroGerente;
    }

    public void setFiltroSupervisor(String[] filtroSupervisor) {
        this.filtroSupervisor = filtroSupervisor;
    }

    public String[] getSeleccionePresentacionDatos() {
        return seleccionePresentacionDatos;
    }

    public String[] getFiltroGerente() {
        return filtroGerente;
    }

    public String[] getFiltroSupervisor() {
        return filtroSupervisor;
    }
}
