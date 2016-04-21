package pe.servosa.android;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import pe.servosa.android.adapter.ListadoEventoAdapter;
import pe.servosa.android.adapter.PiramideAccidentabilidadAdapter;
import pe.servosa.android.model.DataEventos;
import pe.servosa.android.model.EventoRiesgoEntity;
import pe.servosa.android.model.PiramideAccidentabilidadEntity;
import pe.servosa.android.util.MyPreferences;
import pe.servosa.android.util.MyToolbar;
import pe.servosa.android.util.TaskExportarExcel;
import pe.servosa.android.util.internet.CustomJsonObjectRequest;
import pe.servosa.android.util.internet.MyVolley;

public class ExportarExcelActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.lvListadoEventos) ListView lvListadoEventos;
    @Bind(R.id.imgHeaderServosa) ImageView imgHeaderServosa;
    ArrayList<PiramideAccidentabilidadEntity> registros;
    ArrayList<PiramideAccidentabilidadEntity> registrosAdapter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exportar_excel);
        ButterKnife.bind(this);
        new MyToolbar(this, toolbar);
        loadImages();

        if (getIntent().hasExtra("filtros")) {
            MyPreferences.getInstance().init(ExportarExcelActivity.this, "UserProfile");
            showLoading();
            cargarListadoEventos();
        } else {
            new AlertDialog.Builder(ExportarExcelActivity.this)
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

        lvListadoEventos.setAdapter(new PiramideAccidentabilidadAdapter(this, registrosAdapter, R.layout.row_exportar_excel));
        lvListadoEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mostrarDetalleEvento(position);
                new TaskExportarExcel().execute();
            }
        });

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

        CustomJsonObjectRequest request = new CustomJsonObjectRequest
                (Request.Method.POST, MyVolley.URL_API_REST + "evento/getEventosPiramide", params,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                progressDialog.dismiss();
                                Log.d("JSONObject RESPONSE", response + "");
                                try {
                                    if (response.getBoolean("status")) {
                                        procesarRegistros(response.getJSONObject("data"));
                                    } else {
                                        Toast.makeText(ExportarExcelActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException ex) {
                                    Toast.makeText(ExportarExcelActivity.this, getString(R.string.json_object_exception), Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        new AlertDialog.Builder(ExportarExcelActivity.this)
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

        MyVolley.getInstance(ExportarExcelActivity.this).addToRequestQueue(request);
    }

    private void procesarRegistros(JSONObject data) {





        listadoEventoEntities = new ArrayList<>();
        listadoEventoEntities.add(DataEventos.REGISTRO_1);
//        listadoEventoEntities.add(DataEventos.REGISTRO_1);
//        listadoEventoEntities.add(DataEventos.REGISTRO_1);
    }

    private void mostrarDetalleEvento(int position) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{
                "contacto@servosa.pe"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Operación: " + registros.get(position).getOperacion() + "\n\n");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Operación: " + registros.get(position).getOperacion() + "\n\n" +
                "Ruta: " + registros.get(position).getRuta() + "\n\n" +
                "Tramo: " + registros.get(position).getTramo() + "\n\n" +
                "Evento: " + registros.get(position).getEvento() + "\n\n" +
                "Categoria: " + registros.get(position).getCategoria() + "\n\n" +
                "Tipo: " + registros.get(position).getTipo() + "\n\n" +
                "Numero de Placa: " + registros.get(position).getPlaca() + "\n\n" +
                "Descripción: " + registros.get(position).getDescripcion() + "\n\n" +
                "Operación: " + registros.get(position).getOperacion() + "\n\n" +
                registros.get(position).getFecha() + " / " + registros.get(position).getHora());

        try {
            startActivity(Intent.createChooser(emailIntent, "Exportar Excels"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "No hay aplicaciones de email instalado.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showLoading() {
        progressDialog = new ProgressDialog(ExportarExcelActivity.this);
        progressDialog.setTitle("Cargando Datos");
        progressDialog.setMessage("Espere un momento");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }
}
