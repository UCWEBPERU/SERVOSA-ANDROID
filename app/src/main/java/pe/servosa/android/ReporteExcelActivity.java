package pe.servosa.android;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import pe.servosa.android.adapter.PiramideAccidentabilidadAdapter;
import pe.servosa.android.interfaces.TaskExportarExcelDelegate;
import pe.servosa.android.model.PiramideAccidentabilidadEntity;
import pe.servosa.android.util.MyPreferences;
import pe.servosa.android.util.MyToolbar;
import pe.servosa.android.util.TaskExportarExcel;
import pe.servosa.android.util.internet.CustomJsonObjectRequest;
import pe.servosa.android.util.internet.MyVolley;

public class ReporteExcelActivity extends AppCompatActivity implements TaskExportarExcelDelegate{
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.lvListadoEventos) ListView lvListadoEventos;
    @Bind(R.id.imgHeaderServosa) ImageView imgHeaderServosa;
    ArrayList<PiramideAccidentabilidadEntity> registros;
    ArrayList<PiramideAccidentabilidadEntity> registrosAdapter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_excel);
        ButterKnife.bind(this);
        new MyToolbar(this, toolbar);
        loadImages();

        if (getIntent().hasExtra("filtros")) {
            MyPreferences.getInstance().init(ReporteExcelActivity.this, "UserProfile");
            showLoading();
            cargarListadoEventos();
        } else {
            new AlertDialog.Builder(ReporteExcelActivity.this)
                    .setTitle("Error de Configuración")
                    .setCancelable(false)
                    .setMessage("Ocurrio un error al inicializar los datos de configuración de la piramide, inténtalo de nuevo.")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).create().show();
        }

        cargarListadoEventos();
    }

    private void loadImages(){
        Glide.with(this)
                .load(R.drawable.logo_servosa_header)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(imgHeaderServosa);
    }

    private void cargarListadoEventos() {
        Map<String, String> params = new HashMap<String, String>();

        params.put("id_usuario", MyPreferences.getInstance().getString("id", ""));
        params.put("tipo_usuario", MyPreferences.getInstance().getString("tipo_usuario", ""));
        params.put("id_tipo_usuario", MyPreferences.getInstance().getString("id_tipo_usuario", ""));
        params.put("filtro", getIntent().getExtras().getBundle("filtros").getString("filtro"));

        if (getIntent().getExtras().getBundle("filtros").getString("id_region") != null) {
            params.put("id_region", getIntent().getExtras().getBundle("filtros").getString("id_region"));
        } else {
            params.put("id_region", "");
        }
        if (getIntent().getExtras().getBundle("filtros").getString("id_operacion") != null) {
            params.put("id_operacion", getIntent().getExtras().getBundle("filtros").getString("id_operacion"));
        } else {
            params.put("id_operacion", "");
        }

        Log.d("PARAMS", params.toString());

        String url = "";

        if (getIntent().getExtras().getBundle("filtros").getBoolean("isPiramideBrid")) {
            url = "evento/getEventosPiramideExportarExcel";
        } else {
            url = "evento/getEventosComportamientoSeguroExportarExcel";
        }

        CustomJsonObjectRequest request = new CustomJsonObjectRequest
                (Request.Method.POST, MyVolley.URL_API_REST + url, params,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                progressDialog.dismiss();
                                Log.d("JSONObject RESPONSE", response + "");
                                try {
                                    if (response.getBoolean("status")) {
                                        procesarRegistros(response.getJSONArray("data"));
                                    } else {
                                        Toast.makeText(ReporteExcelActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException ex) {
                                    Toast.makeText(ReporteExcelActivity.this, getString(R.string.json_object_exception), Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        new AlertDialog.Builder(ReporteExcelActivity.this)
                                .setTitle(getString(R.string.volley_error_title))
                                .setMessage(getString(R.string.volley_error_message))
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create().show();
                        error.printStackTrace();
//                        Log.d("VOLLEY ERROR", error.getMessage());
                    }
                });

        MyVolley.getInstance(ReporteExcelActivity.this).addToRequestQueue(request);
    }

    private void procesarRegistros(JSONArray data) {
        try {
            if (data.length() > 0) {
                JSONObject jsonObject;
                registros = new ArrayList<>();
                PiramideAccidentabilidadEntity piramideEntity;
                String fechaRegistro[];
                for (int c = 0; c < data.length(); c++) {
                    jsonObject = data.getJSONObject(c);
                    fechaRegistro = formatFechaRegistro(jsonObject.getString("fecha_registro"));
                    piramideEntity = new PiramideAccidentabilidadEntity(
                            jsonObject.getString("nombre_region"),
                            jsonObject.getString("nombre_operacion"),
                            jsonObject.getString("nombre_ruta"),
                            jsonObject.getString("nombre_tramo"),
                            jsonObject.getString("placa"),
                            jsonObject.getString("nombre") + " " + jsonObject.getString("apellidos"),
                            "NIVEL " + jsonObject.getString("id_tipo_usuario"),
                            fechaRegistro[0],
                            fechaRegistro[1],
                            jsonObject.getString("nombre_evento"),
                            jsonObject.getString("nombre_categoria"),
                            (jsonObject.getString("nombre_tipo") == "null") ? "-" : jsonObject.getString("nombre_tipo"),
                            jsonObject.getString("descripcion")
                    );
                    registros.add(piramideEntity);
                    if (c == 0) {
                        registrosAdapter = new ArrayList<>();
                        registrosAdapter.add(piramideEntity);
                        lvListadoEventos.setAdapter(new PiramideAccidentabilidadAdapter(this, registrosAdapter, R.layout.row_exportar_excel));
                        lvListadoEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                new TaskExportarExcel(ReporteExcelActivity.this).execute(registros, progressDialog);
                            }
                        });
                    }
                }
            } else {
                Toast.makeText(ReporteExcelActivity.this, getString(R.string.act_exportar_no_hay_registros), Toast.LENGTH_LONG).show();
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    private String[] formatFechaRegistro(String fechaRegistro) {
        String formatFecha[];
        String fecha, hora;
        Timestamp timestamp;

        timestamp = Timestamp.valueOf(fechaRegistro);
        fecha = new SimpleDateFormat("dd/MM/yyyy").format(timestamp);
        hora = new SimpleDateFormat("hh:mm:ss a").format(timestamp);
        formatFecha = new String[]{fecha.toString(), hora.toString()};

        fecha = null; hora = null; timestamp = null;

        return formatFecha;
    }

    private void mostrarDetalleEvento(File fileExcel) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{
                "contacto@servosa.pe"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Datos de Accidentabilidad - Reporte en excel");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fileExcel));

        try {
            startActivity(Intent.createChooser(emailIntent, "Exportar Excels"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "No hay aplicaciones de email instalado.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showLoading() {
        progressDialog = new ProgressDialog(ReporteExcelActivity.this);
        progressDialog.setTitle("Cargando Datos");
        progressDialog.setMessage("Espere un momento");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    @Override
    public void taskOnInit() {
        progressDialog = new ProgressDialog(ReporteExcelActivity.this);
        progressDialog.setTitle("Exportando Excel");
        progressDialog.setMessage("Espere un momento");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    @Override
    public void taskOnCompletion(File fileExcel) {
        progressDialog.dismiss();
        Toast.makeText(ReporteExcelActivity.this, getString(R.string.act_exportar_success_exportar_excel), Toast.LENGTH_LONG).show();
        mostrarDetalleEvento(fileExcel);
    }
}
