package pe.servosa.android.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import pe.servosa.android.GraficoCompSeguroActivity;
import pe.servosa.android.GraficoPiramideBridActivity;
import pe.servosa.android.ReporteExcelActivity;

/**
 * Created by ucweb02 on 25/04/2016.
 */
public class ModalGraficoEstadistico extends ModalFiltro {

    private static ModalGraficoEstadistico modalGraficoEstadistico;

    private ModalGraficoEstadistico(Activity activity) {
        super(activity);
        setSeleccionePresentacionDatos(new String[]{"Grafico Piramide Brid", "Grafico Comportamiento Seguros"});
        setFiltroGerente(new String[]{"Grafico Nacional", "Grafico Regional", "Grafico por Operación"});
        setFiltroSupervisor(new String[]{"Grafico Regional", "Grafico Operación"});
    }

    public static ModalGraficoEstadistico getInstance(Activity activity) {
        if (modalGraficoEstadistico == null) {
            modalGraficoEstadistico = new ModalGraficoEstadistico(activity);
            return modalGraficoEstadistico;
        }
        return modalGraficoEstadistico;
    }

    @Override
    public void showModal() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Graficos Estadisticos")
                .setSingleChoiceItems(getSeleccionePresentacionDatos(), -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (which == 0) {
                            setIntent(new Intent(getActivity(), GraficoPiramideBridActivity.class));
                        } else {
                            setIntent(new Intent(getActivity(), GraficoCompSeguroActivity.class));
                        }
                        showModalOpciones(which);
                    }
                })
                .create().show();
    }

    @Override
    public void destroy() {
        modalGraficoEstadistico = null;
    }

}
