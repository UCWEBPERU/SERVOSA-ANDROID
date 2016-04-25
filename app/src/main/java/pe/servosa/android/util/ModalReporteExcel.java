package pe.servosa.android.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import pe.servosa.android.ReporteExcelActivity;

/**
 * Created by ucweb02 on 25/04/2016.
 */
public class ModalReporteExcel extends ModalFiltro {

    private static ModalReporteExcel modalReporteExcel;

    private ModalReporteExcel(Activity activity) {
        super(activity);
        setSeleccionePresentacionDatos(new String[]{"Reporte Piramide Brid", "Reporte Comportamiento Seguros"});
        setFiltroGerente(new String[]{"Reporte Nacional", "Reporte Regional", "Reporte por Operacion"});
        setFiltroSupervisor(new String[]{"Reporte Regional", "Reporte Operacion"});
    }

    public static ModalReporteExcel getInstance(Activity activity) {
        if (modalReporteExcel == null) {
            modalReporteExcel = new ModalReporteExcel(activity);
            return modalReporteExcel;
        }
        return modalReporteExcel;
    }

    @Override
    public void showModal() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Reportes Excel")
                .setSingleChoiceItems(getSeleccionePresentacionDatos(), -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        setIntent(new Intent(getActivity(), ReporteExcelActivity.class));
                        showModalOpciones(which);
                    }
                })
                .create().show();
    }

    @Override
    public void destroy() {
        modalReporteExcel = null;
    }
}
