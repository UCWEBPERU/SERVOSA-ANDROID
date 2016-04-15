package pe.servosa.android.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import pe.servosa.android.GraficoPiramideActivity;

/**
 * Created by ucweb02 on 15/04/2016.
 */
public class ModalFiltroPiramide {

    private String[] filtroGerente = new String[]{"Piramide Nacional", "Piramide Regional", "Piramide por Operacion"};
    private String[] filtroSupervisor = new String[]{"Piramide Regional", "Piramide por Operacion"};
    private String[] filtroRegion = new String[]{"Sur", "Norte", "Centro"};

    private static ModalFiltroPiramide modalFiltroPiramide;

    private Activity activity;

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
        return modalFiltroPiramide;
    }

    public void show() {
        String[] filtroPrincipal = mostrarFiltroPorTipoUsuario();
        new AlertDialog.Builder(activity)
                .setTitle("Grafico Piramide")
                .setSingleChoiceItems(filtroPrincipal, 0, new DialogInterface.OnClickListener() {
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

            } else if (posicionFiltroPrincipal == 1) {
                new AlertDialog.Builder(activity)
                        .setTitle("Piramide Regional")
                        .setSingleChoiceItems(filtroRegion, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                activity.startActivity(new Intent(activity, GraficoPiramideActivity.class));
                            }
                        })
                        .create().show();
            } else if (posicionFiltroPrincipal == 2) {

            }
        } else if (MyPreferences.getInstance().getString("tipo_usuario", "").toLowerCase().equals("supervisor")) { // Por Supervisor
            if (posicionFiltroPrincipal == 0) {

            } else if (posicionFiltroPrincipal == 1) {

            }
            activity.startActivity(new Intent(activity, GraficoPiramideActivity.class));
        }
    }

}
