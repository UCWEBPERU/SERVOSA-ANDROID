package pe.servosa.android;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import pe.servosa.android.model.CategoriaEntity;
import pe.servosa.android.model.EventoEntity;
import pe.servosa.android.model.OperacionEntity;
import pe.servosa.android.model.PlacaEntity;
import pe.servosa.android.model.RutaEntity;
import pe.servosa.android.model.TipoEntity;
import pe.servosa.android.model.TramoEntity;
import pe.servosa.android.sqlite.model.SqlCategoriaEntity;
import pe.servosa.android.sqlite.model.SqlEventoEntity;
import pe.servosa.android.sqlite.model.SqlEventoRiesgoEntity;
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
import pe.servosa.android.util.internet.TaskPing;

public class NuevoEventoActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.imgHeaderServosa) ImageView imgHeaderServosa;
    @Bind(R.id.btnEnviarEvento) ImageView btnEnviarEvento;
    @Bind(R.id.txtDescripcion) EditText txtDescripcion;
    @Bind(R.id.spnrSeleccioneOperacion) Spinner spnrSeleccioneOperacion;
    @Bind(R.id.spnrSeleccioneRuta) Spinner spnrSeleccioneRuta;
    @Bind(R.id.spnrSeleccioneTramo) Spinner spnrSeleccioneTramo;
    @Bind(R.id.spnrSeleccioneEvento) Spinner spnrSeleccioneEvento;
    @Bind(R.id.spnrSeleccioneCategoria) Spinner spnrSeleccioneCategoria;
    @Bind(R.id.spnrSeleccioneTipo) Spinner spnrSeleccioneTipo;
    @Bind(R.id.spnrSeleccioneNumPlaca) Spinner spnrSeleccioneNumPlaca;

    private ActivityManager activityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_evento);
        ButterKnife.bind(this);

        new MyToolbar(this, toolbar);
        loadImages();

        activityManager = new ActivityManager();

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.text_spinner, listSeleccioneOperacion);
//        spnrSeleccioneOperacion.setAdapter(adapter);
//        adapter = new ArrayAdapter<String>(this, R.layout.text_spinner, listSeleccioneRuta);
//        spnrSeleccioneRuta.setAdapter(adapter);
//        adapter = new ArrayAdapter<String>(this, R.layout.text_spinner, listSeleccioneTramo);
//        spnrSeleccioneTramo.setAdapter(adapter);
//        adapter = new ArrayAdapter<String>(this, R.layout.text_spinner, listSeleccioneEvento);
//        spnrSeleccioneEvento.setAdapter(adapter);
//        adapter = new ArrayAdapter<String>(this, R.layout.text_spinner, listSeleccioneCategoria);
//        spnrSeleccioneCategoria.setAdapter(adapter);
//        adapter = new ArrayAdapter<String>(this, R.layout.text_spinner, listSeleccioneTipo);
//        spnrSeleccioneTipo.setAdapter(adapter);
//        adapter = new ArrayAdapter<String>(this, R.layout.text_spinner, listSeleccioneNumPlaca);
//        spnrSeleccioneNumPlaca.setAdapter(adapter);

        new TaskPing().execute(this);
    }

    private void loadImages(){
        Glide.with(this)
                .load(R.drawable.logo_servosa_header)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(imgHeaderServosa);
    }

    private class ActivityManager {

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

        private ArrayList<SqlOperacionEntity> listSeleccioneOperacion;
        private ArrayList<SqlRutaEntity> listSeleccioneRuta;
        private ArrayList<SqlTramoEntity> listSeleccioneTramo;
        private ArrayList<SqlEventoEntity> listSeleccioneEvento;
        private ArrayList<SqlCategoriaEntity> listSeleccioneCategoria;
        private ArrayList<SqlTipoEntity> listSeleccioneTipo;
        private ArrayList<SqlPlacaEntity> listSeleccionePlaca;

        private ArrayList<String> spinnerItemsOperacion;
        private ArrayList<String> spinnerItemsRuta;
        private ArrayList<String> spinnerItemsTramo;
        private ArrayList<String> spinnerItemsEvento;
        private ArrayList<String> spinnerItemsCategoria;
        private ArrayList<String> spinnerItemsTipo;
        private ArrayList<String> spinnerItemsPlaca;

        private ProgressDialog progressDialog;

        private Map<String, String> params;

        private ArrayAdapter<String> adapter;

        public ActivityManager() {
            progressDialog = new ProgressDialog(NuevoEventoActivity.this);

            spinnerItemsOperacion = new ArrayList<>();
            spinnerItemsRuta = new ArrayList<>();
            spinnerItemsTramo = new ArrayList<>();
            spinnerItemsEvento = new ArrayList<>();
            spinnerItemsCategoria = new ArrayList<>();
            spinnerItemsTipo = new ArrayList<>();
            spinnerItemsPlaca = new ArrayList<>();

            listSeleccioneOperacion = new ArrayList<>();
            listSeleccioneRuta = new ArrayList<>();
            listSeleccioneTramo = new ArrayList<>();
            listSeleccioneEvento = new ArrayList<>();
            listSeleccioneCategoria = new ArrayList<>();
            listSeleccioneTipo = new ArrayList<>();
            listSeleccionePlaca = new ArrayList<>();

            spnrSeleccioneOperacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    cambiarColorTextoSpinner(parent, position);
                    cargarSpinnerRutas(position);
                    cargarSpinnerPlacas(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spnrSeleccioneRuta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    cambiarColorTextoSpinner(parent, position);
                    cargarSpinnerTramos(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spnrSeleccioneTramo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    cambiarColorTextoSpinner(parent, position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spnrSeleccioneEvento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    cambiarColorTextoSpinner(parent, position);
                    cargarSpinnerCategoria(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spnrSeleccioneCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    cambiarColorTextoSpinner(parent, position);
                    cargarSpinnerTipos(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spnrSeleccioneTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    cambiarColorTextoSpinner(parent, position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spnrSeleccioneNumPlaca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    cambiarColorTextoSpinner(parent, position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            btnEnviarEvento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enviarEvento();
                }
            });

            init();
        }

        private void init() {
            if (Connection.hasNetworkConnectivity(getApplicationContext())) {
                progressDialog.setTitle("Cargando Datos");
                progressDialog.setMessage("Espere un momento");
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(true);
                progressDialog.show();
                mostrarRegistros();
                getAllRegistros();
            } else {
                cargarDatosSQLite();
                mostrarRegistros();
            }
        }

        private void cambiarColorTextoSpinner(AdapterView<?> parent, int position) {
            if (position > 0) {
                ((TextView) parent.getChildAt(0)).setTextColor(ContextCompat.getColor(NuevoEventoActivity.this, R.color.colorPrimary));
            }
        }

        private void getAllRegistros() {
            MyPreferences.getInstance().init(NuevoEventoActivity.this, "UserProfile");

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
                            Toast.makeText(NuevoEventoActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException ex) {
                        Toast.makeText(getApplicationContext(), getString(R.string.json_object_exception), Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    cargarDatosSQLite();
                    mostrarRegistros();
                    progressDialog.dismiss();
                }
            });

            MyVolley.getInstance(NuevoEventoActivity.this).addToRequestQueue(request);
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

        private void mostrarRegistros() {
            cargarSpinnerOperaciones();
//            cargarSpinnerRutas(-1);
//            cargarSpinnerTramos(-1);
            cargarSpinnerEventos();
//            cargarSpinnerCategoria(-1);
//            cargarSpinnerTipos(-1);
//            cargarSpinnerPlacas(-1);
        }

        private void cargarSpinnerOperaciones() {
            spinnerItemsOperacion.clear();
            listSeleccioneOperacion.clear();
            spinnerItemsOperacion.add("Seleccione Operacion");
            if (sqlOperacionEntities != null) {
                for (int c = 0; c < sqlOperacionEntities.size(); c++) {
                    listSeleccioneOperacion.add(sqlOperacionEntities.get(c));
                    spinnerItemsOperacion.add(sqlOperacionEntities.get(c).getNombreOperacion());
                }
            }
            adapter = new ArrayAdapter<String>(NuevoEventoActivity.this, R.layout.text_spinner, spinnerItemsOperacion);
            spnrSeleccioneOperacion.setAdapter(adapter);
        }

        private void cargarSpinnerRutas(int positionOperacion) {
            spinnerItemsRuta.clear();
            listSeleccioneRuta.clear();
            spinnerItemsRuta.add("Seleccione Ruta");
            if (sqlRutaEntities != null) {
                for (int c = 0; c < sqlRutaEntities.size(); c++) {
                    if (positionOperacion > 0) {
                        if (sqlRutaEntities.get(c).getIDOperacion().equals(listSeleccioneOperacion.get(positionOperacion - 1).getIDOperacion())) {
                            listSeleccioneRuta.add(sqlRutaEntities.get(c));
                            spinnerItemsRuta.add(sqlRutaEntities.get(c).getNombreRuta());
                        }
                    }
                }
            }
            adapter = new ArrayAdapter<String>(NuevoEventoActivity.this, R.layout.text_spinner, spinnerItemsRuta);
            spnrSeleccioneRuta.setAdapter(adapter);
            cargarSpinnerTramos(-1);
        }

        private void cargarSpinnerTramos(int positionRuta) {
            spinnerItemsTramo.clear();
            listSeleccioneTramo.clear();
            spinnerItemsTramo.add("Seleccione Tramo");
            if (sqlTramoEntities != null) {
                for (int c = 0; c < sqlTramoEntities.size(); c++) {
                    if (positionRuta > 0) {
                        if (sqlTramoEntities.get(c).getIDRuta().equals(listSeleccioneRuta.get(positionRuta - 1).getIDRuta())) {
                            listSeleccioneTramo.add(sqlTramoEntities.get(c));
                            spinnerItemsTramo.add(sqlTramoEntities.get(c).getNombreTramo());
                        }
                    }
                }
            }
            adapter = new ArrayAdapter<String>(NuevoEventoActivity.this, R.layout.text_spinner, spinnerItemsTramo);
            spnrSeleccioneTramo.setAdapter(adapter);
        }

        private void cargarSpinnerPlacas(int positionOperacion) {
            spinnerItemsPlaca.clear();
            listSeleccionePlaca.clear();
            spinnerItemsPlaca.add("Seleccione Placa");
            if (sqlPlacaEntities != null) {
                for (int c = 0; c < sqlPlacaEntities.size(); c++) {
                    if (positionOperacion > 0) {
                        if (sqlPlacaEntities.get(c).getIDOperacion().equals(listSeleccioneOperacion.get(positionOperacion - 1).getIDOperacion())) {
                            listSeleccionePlaca.add(sqlPlacaEntities.get(c));
                            spinnerItemsPlaca.add(sqlPlacaEntities.get(c).getPlaca());
                        }
                    }
                }
            }
            adapter = new ArrayAdapter<String>(NuevoEventoActivity.this, R.layout.text_spinner, spinnerItemsPlaca);
            spnrSeleccioneNumPlaca.setAdapter(adapter);
        }

        private void cargarSpinnerEventos(){
            spinnerItemsEvento.clear();
            listSeleccioneEvento.clear();
            spinnerItemsEvento.add("Seleccione Evento");
            if (sqlEventoEntities != null) {
                for (int c = 0; c < sqlEventoEntities.size(); c++) {
                    listSeleccioneEvento.add(sqlEventoEntities.get(c));
                    spinnerItemsEvento.add(sqlEventoEntities.get(c).getNombreEvento());
                }
            }
            adapter = new ArrayAdapter<String>(NuevoEventoActivity.this, R.layout.text_spinner, spinnerItemsEvento);
            spnrSeleccioneEvento.setAdapter(adapter);
        }

        private void cargarSpinnerCategoria(int positionEvento){
            spinnerItemsCategoria.clear();
            listSeleccioneCategoria.clear();
            spinnerItemsCategoria.add("Seleccione Categoria");
            if (sqlCategoriaEntities != null) {
                for (int c = 0; c < sqlCategoriaEntities.size(); c++) {
                    if (positionEvento > 0) {
                        if (sqlCategoriaEntities.get(c).getIDEvento().equals(listSeleccioneEvento.get(positionEvento - 1).getIDEvento())) {
                            listSeleccioneCategoria.add(sqlCategoriaEntities.get(c));
                            spinnerItemsCategoria.add(sqlCategoriaEntities.get(c).getNombreCategoria());
                        }
                    }
                }
            }
            adapter = new ArrayAdapter<String>(NuevoEventoActivity.this, R.layout.text_spinner, spinnerItemsCategoria);
            spnrSeleccioneCategoria.setAdapter(adapter);
        }

        private void cargarSpinnerTipos(int positionCategoria){
            spinnerItemsTipo.clear();
            listSeleccioneTipo.clear();
            spinnerItemsTipo.add("Seleccione Tipo");
            if (sqlTipoEntities != null) {
                for (int c = 0; c < sqlTipoEntities.size(); c++) {
                    if (positionCategoria > 0) {
                        Log.d("TIPO ENTITY", sqlTipoEntities.get(c).getIDCategoria().toString());
                        Log.d("CATEGORIA", listSeleccioneCategoria.get(positionCategoria - 1).getIDCategoria().toString());
                        Log.d("TOTAL TIPOS", sqlTipoEntities.size() + "");
                        if (sqlTipoEntities.get(c).getIDCategoria().equals(listSeleccioneCategoria.get(positionCategoria - 1).getIDCategoria())) {
                            listSeleccioneTipo.add(sqlTipoEntities.get(c));
                            spinnerItemsTipo.add(sqlTipoEntities.get(c).getNombreTipo());
                        }
                    }
                }
            }
            adapter = new ArrayAdapter<String>(NuevoEventoActivity.this, R.layout.text_spinner, spinnerItemsTipo);
            spnrSeleccioneTipo.setAdapter(adapter);
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
                            mostrarRegistros();
                        }
                    });
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(NuevoEventoActivity.this, getString(R.string.act_nuevo_evento_error_guardar_registros), Toast.LENGTH_LONG).show();
                }
            }
        }

        private void enviarEvento() {
            if (Connection.hasNetworkConnectivity(NuevoEventoActivity.this)) {

                if (validarDatosIngresados()) {

                    progressDialog.setTitle("Registrando Nuevo Evento");
                    progressDialog.setMessage("Espere un momento");
                    progressDialog.setCancelable(false);
                    progressDialog.setIndeterminate(true);
                    progressDialog.show();
                    final Date objDate = new Date();
                    final String fechaRegistro = new Timestamp(objDate.getTime()).toString();

                    System.out.println("FECHA REGISTRO" + fechaRegistro);

                    params = new HashMap<String, String>();

                    if (listSeleccioneTipo.size() > 1) {
                        params.put("id_tipo", listSeleccioneTipo.get(spnrSeleccioneTipo.getSelectedItemPosition() - 1).getIDTipo().toString());
                    } else {
                        params.put("id_tipo", "");
                    }
                    params.put("id_usuario", MyPreferences.getInstance().getString("id", ""));
                    params.put("id_evento", listSeleccioneEvento.get(spnrSeleccioneEvento.getSelectedItemPosition() - 1).getIDEvento().toString());
                    params.put("id_categoria", listSeleccioneCategoria.get(spnrSeleccioneCategoria.getSelectedItemPosition() - 1).getIDCategoria().toString());
                    params.put("id_placa", listSeleccionePlaca.get(spnrSeleccioneNumPlaca.getSelectedItemPosition() - 1).getIDPlaca().toString());
                    params.put("id_tramo", listSeleccioneTramo.get(spnrSeleccioneTramo.getSelectedItemPosition() - 1).getIDTramo().toString());
                    params.put("descripcion", txtDescripcion.getText().toString().trim());
                    params.put("fecha_registro", fechaRegistro);

                    Log.d("PARAMS", params.toString());

                    CustomJsonObjectRequest request = new CustomJsonObjectRequest
                            (Request.Method.POST, MyVolley.URL_API_REST + "evento/registrarEvento", params,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            progressDialog.dismiss();
                                            Log.d("JSONObject RESPONSE", response + "");
                                            try {
                                                if (response.getBoolean("status")) {
                                                    guardarRegistroNuevoEvento(fechaRegistro);
                                                    new AlertDialog.Builder(NuevoEventoActivity.this)
                                                            .setTitle("Nuevo Evento")
                                                            .setMessage(response.getString("message"))
                                                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.dismiss();
                                                                    finish();
                                                                }
                                                            })
                                                            .create().show();
                                                } else {
                                                    Toast.makeText(NuevoEventoActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
                                                }
                                            } catch (JSONException ex) {
                                                Toast.makeText(getApplicationContext(), getString(R.string.json_object_exception), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.dismiss();
                                    new AlertDialog.Builder(NuevoEventoActivity.this)
                                            .setTitle(getString(R.string.volley_error_title))
                                            .setMessage(getString(R.string.volley_error_message))
                                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                            .create().show();
                                }
                            });

                    MyVolley.getInstance(NuevoEventoActivity.this).addToRequestQueue(request);

                } else {
                    Toast.makeText(NuevoEventoActivity.this, getString(R.string.act_nuevo_evento_error_datos_ingresados), Toast.LENGTH_LONG).show();
                }

            } else {
                Connection.showMessageNotConnectedToNetwork(NuevoEventoActivity.this);
            }
        }

        private boolean validarDatosIngresados() {
            Log.d("VALIDAR DATOS", listSeleccioneTipo.size() + "");
            Log.d("VALIDAR DATOS", spnrSeleccioneTipo.getSelectedItemPosition() + "");
            if (spnrSeleccioneOperacion.getSelectedItemPosition() > 0 &&
                    spnrSeleccioneRuta.getSelectedItemPosition() > 0 &&
                    spnrSeleccioneTramo.getSelectedItemPosition() > 0 &&
                    spnrSeleccioneEvento.getSelectedItemPosition() > 0 &&
                    spnrSeleccioneCategoria.getSelectedItemPosition() > 0 &&
                    spnrSeleccioneNumPlaca.getSelectedItemPosition() > 0 &&
                    txtDescripcion.getText().toString().trim().length() > 0 ) {
                    if (listSeleccioneTipo.size() > 1) {
                        if (spnrSeleccioneTipo.getSelectedItemPosition() > 0) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return true;
                    }
            }
            return false;
        }

        private void guardarRegistroNuevoEvento(String fechaRegistro) {
            SqlEventoRiesgoEntity sqlEventoRiesgoEntity = new SqlEventoRiesgoEntity(
                    listSeleccioneOperacion.get(spnrSeleccioneOperacion.getSelectedItemPosition() - 1).getNombreOperacion().toString(),
                    listSeleccioneRuta.get(spnrSeleccioneRuta.getSelectedItemPosition() - 1).getNombreRuta().toString(),
                    listSeleccioneTramo.get(spnrSeleccioneTramo.getSelectedItemPosition() - 1).getNombreTramo().toString(),
                    listSeleccioneEvento.get(spnrSeleccioneEvento.getSelectedItemPosition() - 1).getNombreEvento().toString(),
                    listSeleccioneCategoria.get(spnrSeleccioneCategoria.getSelectedItemPosition() - 1).getNombreCategoria().toString(),
                    (listSeleccioneTipo.size() > 1) ? listSeleccioneTipo.get(spnrSeleccioneTipo.getSelectedItemPosition() - 1).getNombreTipo().toString() : "",
                    listSeleccionePlaca.get(spnrSeleccioneNumPlaca.getSelectedItemPosition() - 1).getPlaca().toString(),
                    txtDescripcion.getText().toString().trim(),
                    fechaRegistro
            );
            sqlEventoRiesgoEntity.save();
        }

    }

}
