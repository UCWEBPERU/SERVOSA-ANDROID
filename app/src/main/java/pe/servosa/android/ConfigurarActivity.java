package pe.servosa.android;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import pe.servosa.android.sqlite.model.SqlCategoriaEntity;
import pe.servosa.android.sqlite.model.SqlEventoEntity;
import pe.servosa.android.sqlite.model.SqlOperacionEntity;
import pe.servosa.android.sqlite.model.SqlPlacaEntity;
import pe.servosa.android.sqlite.model.SqlRutaEntity;
import pe.servosa.android.sqlite.model.SqlTipoEntity;
import pe.servosa.android.sqlite.model.SqlTramoEntity;
import pe.servosa.android.util.MyPreferences;
import pe.servosa.android.util.MyToolbar;
import pe.servosa.android.util.internet.Connection;
import pe.servosa.android.util.internet.CustomJsonObjectRequest;
import pe.servosa.android.util.internet.MyVolley;

public class ConfigurarActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;

    private List<SqlOperacionEntity> sqlOperacionEntities;
    private List<SqlRutaEntity> sqlRutaEntities;
    private List<SqlTramoEntity> sqlTramoEntities;
    private List<SqlEventoEntity> sqlEventoEntities;
    private List<SqlCategoriaEntity> sqlCategoriaEntities;
    private List<SqlTipoEntity> sqlTipoEntities;
    private List<SqlPlacaEntity> sqlPlacaEntities;

    private SqlOperacionEntity sqlOperacionEntity;
    private SqlRutaEntity sqlRutaEntity;
    private SqlTramoEntity sqlTramoEntity;
    private SqlEventoEntity sqlEventoEntity;
    private SqlCategoriaEntity sqlCategoriaEntity;
    private SqlTipoEntity sqlTipoEntity;
    private SqlPlacaEntity sqlPlacaEntity;
    private Map<String, String> params;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar);
        ButterKnife.bind(this);
        new MyToolbar(this, toolbar);

        Switch toggle_gps = (Switch) findViewById(R.id.s_gps);
        Switch toggle_wifi = (Switch) findViewById(R.id.s_wifi);
        Switch toggle_email = (Switch) findViewById(R.id.s_email);
        Switch toggle_actualizar = (Switch) findViewById(R.id.s_actualizar);

        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            toggle_gps.setChecked(true);
        }
        if (Connection.hasNetworkConnectivity(getApplicationContext())) {
            toggle_wifi.setChecked(true);
        }

        toggle_gps.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                } else {
                }
            }
        });
        toggle_wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!Connection.hasNetworkConnectivity(getApplicationContext())) {
                        startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                    }
                } else {

                }
            }
        });
        toggle_email.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String[] to = { "ucweb03@hotmail.com" };
                    String[] cc = { "" };
                    enviar(to, cc, "NOTIFICACION DE ERROR EN APP",
                            "Usuario:"+ MyPreferences.getInstance().getString("usuario", "")+
                            "\nCorreo Electronico:"+ MyPreferences.getInstance().getString("email", "")+
                                    "\nNotifico error en aplicacion movil");
                } else {

                }
            }
        });
        toggle_actualizar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    progressDialog = new ProgressDialog(ConfigurarActivity.this);
                    progressDialog.setTitle("Cargando Datos");
                    progressDialog.setMessage("Espere un momento");
                    progressDialog.setCancelable(false);
                    progressDialog.setIndeterminate(true);
                    progressDialog.show();
                    getAllRegistros();
                } else {
                    Connection.showMessageNotConnectedToNetwork(ConfigurarActivity.this);
                }
            }
        });
    }

    private void enviar(String[] to, String[] cc,
                        String asunto, String mensaje) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));

        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
        emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Email "));
    }

    public void getAllRegistros() {
        MyPreferences.getInstance().init(ConfigurarActivity.this, "UserProfile");

        params = new HashMap<String, String>();
        params.put("id_usuario", MyPreferences.getInstance().getString("id", ""));

        CustomJsonObjectRequest request = new CustomJsonObjectRequest
                (Request.Method.POST, MyVolley.URL_API_REST + "evento/getAllData", params,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("JSONObject RESPONSE", response + "");
                                try {
                                    if (response.getBoolean("status")) {
                                        new TaskProcesarRegistros().execute(response.getJSONObject("data"));
                                    } else {
                                        Toast.makeText(ConfigurarActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException ex) {
                                    Toast.makeText(getApplicationContext(), getString(R.string.json_object_exception), Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        cargarDatosSQLite();

                        progressDialog.dismiss();
                    }
                });

        MyVolley.getInstance(ConfigurarActivity.this).addToRequestQueue(request);
    }
    public class TaskProcesarRegistros extends AsyncTask<JSONObject, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(JSONObject... params) {

            try {
                JSONArray jsonArray = params[0].getJSONArray("dataOperaciones");
                JSONObject jsonObject;

                SqlOperacionEntity.deleteAll(SqlOperacionEntity.class);
                for (int c = 0; c < jsonArray.length(); c++) {
                    jsonObject = jsonArray.getJSONObject(c);
                    sqlOperacionEntity = new SqlOperacionEntity(
                            jsonObject.getInt("id_operacion"),
                            jsonObject.getInt("id_region"),
                            jsonObject.getString("nombre_operacion"));
                    sqlOperacionEntity.save();
                }

                jsonArray = params[0].getJSONArray("dataRutas");
                SqlRutaEntity.deleteAll(SqlRutaEntity.class);
                for (int c = 0; c < jsonArray.length(); c++) {
                    jsonObject = jsonArray.getJSONObject(c);
                    sqlRutaEntity = new SqlRutaEntity(
                            jsonObject.getInt("id_ruta"),
                            jsonObject.getInt("id_operacion"),
                            jsonObject.getString("nombre_ruta"));
                    sqlRutaEntity.save();
                }

                jsonArray = params[0].getJSONArray("dataTramos");
                SqlTramoEntity.deleteAll(SqlTramoEntity.class);
                for (int c = 0; c < jsonArray.length(); c++) {
                    jsonObject = jsonArray.getJSONObject(c);
                    sqlTramoEntity = new SqlTramoEntity(
                            jsonObject.getInt("id_tramo"),
                            jsonObject.getInt("id_ruta"),
                            jsonObject.getString("nombre_tramo"),
                            jsonObject.getString("velocidad"));
                    sqlTramoEntity.save();
                }

                jsonArray = params[0].getJSONArray("dataEventos");
                SqlEventoEntity.deleteAll(SqlEventoEntity.class);
                for (int c = 0; c < jsonArray.length(); c++) {
                    jsonObject = jsonArray.getJSONObject(c);
                    sqlEventoEntity = new SqlEventoEntity(
                            jsonObject.getInt("id_evento"),
                            jsonObject.getString("codigo_evento"),
                            jsonObject.getString("nombre_evento"));
                    sqlEventoEntity.save();
                }

                jsonArray = params[0].getJSONArray("dataCategorias");
                SqlCategoriaEntity.deleteAll(SqlCategoriaEntity.class);
                for (int c = 0; c < jsonArray.length(); c++) {
                    jsonObject = jsonArray.getJSONObject(c);
                    sqlCategoriaEntity = new SqlCategoriaEntity(
                            jsonObject.getInt("id_categoria"),
                            jsonObject.getInt("id_evento"),
                            jsonObject.getString("codigo_categoria"),
                            jsonObject.getString("nombre_categoria"));
                    sqlCategoriaEntity.save();
                }

                jsonArray = params[0].getJSONArray("dataTipos");
                SqlTipoEntity.deleteAll(SqlTipoEntity.class);
                for (int c = 0; c < jsonArray.length(); c++) {
                    jsonObject = jsonArray.getJSONObject(c);
                    sqlTipoEntity = new SqlTipoEntity(
                            jsonObject.getInt("id_tipo"),
                            jsonObject.getInt("id_categoria"),
                            jsonObject.getString("codigo_tipo"),
                            jsonObject.getString("nombre_tipo"));
                    sqlTipoEntity.save();
                }

                jsonArray = params[0].getJSONArray("dataPlacas");
                SqlPlacaEntity.deleteAll(SqlPlacaEntity.class);
                for (int c = 0; c < jsonArray.length(); c++) {
                    jsonObject = jsonArray.getJSONObject(c);
                    sqlPlacaEntity = new SqlPlacaEntity(
                            jsonObject.getInt("id_placa"),
                            jsonObject.getInt("id_operacion"),
                            jsonObject.getString("placa"));
                    sqlPlacaEntity.save();
                }
                cargarDatosSQLite();
                return true;
            } catch (JSONException ex) {
                Log.e("JSONException", "ERROR GUARDANDO REGISTROS");
                Log.e("JSONException", ex.getMessage());
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ConfigurarActivity.this, getString(R.string.act_main_informacion_actualizada), Toast.LENGTH_LONG).show();
                    }
                });
                progressDialog.dismiss();
            } else {
                Toast.makeText(ConfigurarActivity.this, getString(R.string.act_nuevo_evento_error_guardar_registros), Toast.LENGTH_LONG).show();
            }
        }
    }
    private void cargarDatosSQLite() {
        sqlOperacionEntities = SqlOperacionEntity.listAll(SqlOperacionEntity.class);
        sqlRutaEntities = SqlRutaEntity.listAll(SqlRutaEntity.class);
        sqlTramoEntities = SqlTramoEntity.listAll(SqlTramoEntity.class);
        sqlEventoEntities = SqlEventoEntity.listAll(SqlEventoEntity.class);
        sqlCategoriaEntities = SqlCategoriaEntity.listAll(SqlCategoriaEntity.class);
        sqlTipoEntities = SqlTipoEntity.listAll(SqlTipoEntity.class);
        sqlPlacaEntities = SqlPlacaEntity.listAll(SqlPlacaEntity.class);
    }

}
