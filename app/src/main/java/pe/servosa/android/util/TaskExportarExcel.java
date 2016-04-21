package pe.servosa.android.util;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

import pe.servosa.android.model.PiramideAccidentabilidadEntity;

/**
 * Created by ucweb02 on 20/04/2016.
 */
public class TaskExportarExcel extends AsyncTask<Void, Integer, Boolean> {

    @Override
    protected Boolean doInBackground(Void... params) {
        Log.d("EXPORTAR EXCEL", "INICIA");
        ArrayList<PiramideAccidentabilidadEntity> registros = new ArrayList<>();
        PiramideAccidentabilidadEntity registro = new PiramideAccidentabilidadEntity(
                "CENTRO",
                "TGP",
                "TRUJILLO",
                "CORCONA - LA OROYA",
                "B7D-875",
                "LUIS ANGEL JURADO CISNEROS",
                "NIVEL 2",
                "07/12/2015",
                "05:15:00 a.m.",
                "Comportamiento de riesgo",
                "ADELANTAMIENTO",
                "ADELANTAR EN CURVA",
                "Incidente D5B-871 km 45 Ancon - Conductor: William Congora"
        );
        registros.add(registro);

        registro = new PiramideAccidentabilidadEntity(
                "CENTRO",
                "INDUSTRIAS",
                "CHUNGAR",
                "CORCONA - LA OROYA",
                "B7X-456",
                "OMAR SANTILLAN CHUQUIMBALQUI",
                "NIVEL 2",
                "07/12/2015",
                "06:00:00 a.m.",
                "Casi Accidentes",
                "DESENGANCHE DE TRACTO QUEDANDO EN TORNA MESA",
                "-",
                "Al momento de enganchar su cisterna el Conductor: Filomeno Chocanga no reviso que se el gavilan haya cerrado de forma correcta en el cual al momento de avanzar se desengancho la cisterna quedando recostada sobre el chasis del tracto."
        );
        registros.add(registro);

        registro = new PiramideAccidentabilidadEntity(
                "CENTRO",
                "INDUSTRIAS",
                "CHUNGAR",
                "CORCONA - LA OROYA",
                "B7X-456",
                "OMAR SANTILLAN CHUQUIMBALQUI",
                "NIVEL 2",
                "07/12/2015",
                "06:00:00 a.m.",
                "Casi Accidentes",
                "DESENGANCHE DE TRACTO QUEDANDO EN TORNA MESA",
                "-",
                "Al momento de enganchar su cisterna el Conductor: Filomeno Chocanga no reviso que se el gavilan haya cerrado de forma correcta en el cual al momento de avanzar se desengancho la cisterna quedando recostada sobre el chasis del tracto."
        );
        registros.add(registro);

        registro = new PiramideAccidentabilidadEntity(
                "CENTRO",
                "INDUSTRIAS",
                "CHUNGAR",
                "CORCONA - LA OROYA",
                "B7X-456",
                "OMAR SANTILLAN CHUQUIMBALQUI",
                "NIVEL 2",
                "07/12/2015",
                "06:00:00 a.m.",
                "Casi Accidentes",
                "DESENGANCHE DE TRACTO QUEDANDO EN TORNA MESA",
                "-",
                "Al momento de enganchar su cisterna el Conductor: Filomeno Chocanga no reviso que se el gavilan haya cerrado de forma correcta en el cual al momento de avanzar se desengancho la cisterna quedando recostada sobre el chasis del tracto."
        );
        registros.add(registro);

        registro = new PiramideAccidentabilidadEntity(
                "CENTRO",
                "INDUSTRIAS",
                "CHUNGAR",
                "CORCONA - LA OROYA",
                "B7X-456",
                "OMAR SANTILLAN CHUQUIMBALQUI",
                "NIVEL 2",
                "07/12/2015",
                "06:00:00 a.m.",
                "Casi Accidentes",
                "DESENGANCHE DE TRACTO QUEDANDO EN TORNA MESA",
                "-",
                "Al momento de enganchar su cisterna el Conductor: Filomeno Chocanga no reviso que se el gavilan haya cerrado de forma correcta en el cual al momento de avanzar se desengancho la cisterna quedando recostada sobre el chasis del tracto."
        );
        registros.add(registro);

        registro = new PiramideAccidentabilidadEntity(
                "CENTRO",
                "INDUSTRIAS",
                "CHUNGAR",
                "CORCONA - LA OROYA",
                "B7X-456",
                "OMAR SANTILLAN CHUQUIMBALQUI",
                "NIVEL 2",
                "07/12/2015",
                "06:00:00 a.m.",
                "Casi Accidentes",
                "DESENGANCHE DE TRACTO QUEDANDO EN TORNA MESA",
                "-",
                "Al momento de enganchar su cisterna el Conductor: Filomeno Chocanga no reviso que se el gavilan haya cerrado de forma correcta en el cual al momento de avanzar se desengancho la cisterna quedando recostada sobre el chasis del tracto."
        );
        registros.add(registro);

        registro = new PiramideAccidentabilidadEntity(
                "CENTRO",
                "INDUSTRIAS",
                "CHUNGAR",
                "CORCONA - LA OROYA",
                "B7X-456",
                "OMAR SANTILLAN CHUQUIMBALQUI",
                "NIVEL 2",
                "07/12/2015",
                "06:00:00 a.m.",
                "Casi Accidentes",
                "DESENGANCHE DE TRACTO QUEDANDO EN TORNA MESA",
                "-",
                "Al momento de enganchar su cisterna el Conductor: Filomeno Chocanga no reviso que se el gavilan haya cerrado de forma correcta en el cual al momento de avanzar se desengancho la cisterna quedando recostada sobre el chasis del tracto."
        );
        registros.add(registro);

        ReporteExcel reporteExcel = new ReporteExcel(registros);
        reporteExcel.exportar();
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    @Override
    protected void onPreExecute() {


    }

    @Override
    protected void onPostExecute(Boolean result) {
        Log.d("EXPORTAR EXCEL", "FINALIZO");
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
