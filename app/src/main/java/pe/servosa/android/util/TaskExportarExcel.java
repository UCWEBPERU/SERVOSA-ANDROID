package pe.servosa.android.util;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import pe.servosa.android.interfaces.TaskExportarExcelDelegate;
import pe.servosa.android.model.PiramideAccidentabilidadEntity;

/**
 * Created by ucweb02 on 20/04/2016.
 */
public class TaskExportarExcel extends AsyncTask<Object, Integer, File> {

    private TaskExportarExcelDelegate delegate;

    public TaskExportarExcel(TaskExportarExcelDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    protected File doInBackground(Object... params) {
        Log.d("EXPORTAR EXCEL", "INICIA");
        ReporteExcel reporteExcel = new ReporteExcel((ArrayList<PiramideAccidentabilidadEntity>) params[0]);
        reporteExcel.exportar();
        return reporteExcel.getFileExcel();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    @Override
    protected void onPreExecute() {
        delegate.taskOnInit();
    }

    @Override
    protected void onPostExecute(File fileExcel) {
        Log.d("EXPORTAR EXCEL", "FINALIZO");
        delegate.taskOnCompletion(fileExcel);
//        if (result) {
//            progressDialog.dismiss();
//            if (posicionesauxiliar.size() > 1) {
//                Toast.makeText(ExportarActivity.this, "Las inspecciones fueron exportadas correctamente a archivos excel.", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(ExportarActivity.this, "La inspección fue exportada correctamente a un archivo excel.", Toast.LENGTH_SHORT).show();
//            }
//
//            Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
//            emailIntent.setType("message/rfc822");
//            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{
//                    "carlos.cordova@megarep.com.pe",
//                    "yuler.huaraz@megarep.com.pe",
//                    "daniel.velarde@megarep.com.pe",
//                    "ernesto.avalos@megarep.com.pe",
//                    "cersar.cruz@megarep.com.pe",
//                    "jaime.chavez@megarep.com.pe",
//                    "cesar.cisneros@megarep.com.pe",
//                    "miguel.ruiz@megarep.com.pe",
//                    "rommel.gastelo@megarep.com.pe",
//                    "felipe.galvez@megarep.com.pe",
//                    "eduardo.lozada@megarep.com.pe",
//                    "mario.palomino@megarep.com.pe",
//                    "martin.atoche@megarep.com.pe",
//                    "neyer.guevara@megarep.com.pe",
//                    "dometila.huamali@megarep.com.pe"});
//            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inspeccion");
//            emailIntent.putExtra(Intent.EXTRA_TEXT, "mensaje email");
////                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(exportarExcel.getExcelsExportados().get(0)));
//
////            ArrayList<Uri> uris = new ArrayList<Uri>();
////            String[] filePaths = new String[] {"sdcard/sample.png", "sdcard/sample.png"};
////            for (String file : filePaths) {
////                File fileIn = new File(file);
////                Uri u = Uri.fromFile(fileIn);
////                uris.add(u);
////            }
//
//            ArrayList<Uri> uris = new ArrayList<Uri>();
//            for (File file : exportarExcel.getExcelsExportados()) {
//                Uri u = Uri.fromFile(file);
//                uris.add(u);
//            }
//
//            emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
//
//            try {
//                startActivity(Intent.createChooser(emailIntent, "Enviar Inspecciones..."));
//            } catch (android.content.ActivityNotFoundException ex) {
//                Toast.makeText(ExportarActivity.this, "No hay aplicaciones de email instalado.", Toast.LENGTH_SHORT).show();
//            }
//        }
//        exportarExcel = null;
    }

}
