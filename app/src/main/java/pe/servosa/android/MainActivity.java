package pe.servosa.android;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
import pe.servosa.android.util.Banner;
import pe.servosa.android.util.ModalFiltroPiramide;
import pe.servosa.android.util.MyPreferences;
import pe.servosa.android.util.MyToolbar;
import pe.servosa.android.util.internet.Connection;
import pe.servosa.android.util.internet.CustomJsonObjectRequest;
import pe.servosa.android.util.internet.MyVolley;
import pe.servosa.android.util.navigation.FragmentDrawer;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.imgHeaderServosa) ImageView imgHeaderServosa;
    @Bind(R.id.btnNuevoEvento) FrameLayout btnNuevoEvento;
    @Bind(R.id.btnListadoEventos) FrameLayout btnListadoEventos;
    @Bind(R.id.btnGraficoPiramide) FrameLayout btnGraficoPiramide;
    @Bind(R.id.btnExportarExcel) FrameLayout btnExportarExcel;
    @Bind(R.id.btnConfigurar) FrameLayout btnConfigurar;
    @Bind(R.id.btnActualizar) FrameLayout btnActualizar;
    @Bind(R.id.btnCerrar) FrameLayout btnCerrar;
    @Bind(R.id.viewPagerBanner) ViewPager viewPagerBanner;
    private FragmentDrawer drawerFragment;
    private ProgressDialog progressDialog;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        new MyToolbar(this, toolbar);
        setFragmentDrawer();
        MyPreferences.getInstance().init(this, "UserProfile");
        new Banner(this, viewPagerBanner).autoScroll();

        loadImages();

        btnNuevoEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NuevoEventoActivity.class));
            }
        });

        btnListadoEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListadoEventosActivity.class));
            }
        });

        btnGraficoPiramide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModalFiltroPiramide.getInstance().init(MainActivity.this).show();
            }
        });

        btnExportarExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ExportarExcelActivity.class));
            }
        });

        btnConfigurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ConfigurarActivity.class));
            }
        });
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Connection.hasNetworkConnectivity(getApplicationContext())) {
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setTitle("Cargando Datos");
                    progressDialog.setMessage("Espere un momento");
                    progressDialog.setCancelable(false);
                    progressDialog.setIndeterminate(true);
                    progressDialog.show();
                    getAllRegistros();
                }else{
                    Connection.showMessageNotConnectedToNetwork(MainActivity.this);
                }

            }
        });

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });

    }

    private void setFragmentDrawer() {
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        switch (position) {
            case 0:
                startActivity(new Intent(getApplicationContext(), NuevoEventoActivity.class));
                break;
            case 1:
                startActivity(new Intent(getApplicationContext(), ListadoEventosActivity.class));
                break;
            case 2:
                ModalFiltroPiramide.getInstance().init(MainActivity.this).show();
                break;
            case 3:
                startActivity(new Intent(getApplicationContext(), ExportarExcelActivity.class));
                break;
            case 4:
                break;
            case 5:
                startActivity(new Intent(getApplicationContext(), ConfigurarActivity.class));
                break;
            case 6:
                MyPreferences.getInstance().edit()
                        .remove("id")
                        .remove("email")
                        .remove("tipo_usuario")
                        .remove("id_tipo_usuario").commit();
                finish();
                break;
        }
    }

    private void loadImages(){
        Glide.with(this)
                .load(R.drawable.logo_servosa_header)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(imgHeaderServosa);
    }


    private void close(){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Salir")
                .setMessage("¿Está seguro que desea salir?")
                .setNegativeButton(android.R.string.cancel, null)//sin listener
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which){

                        MyPreferences.getInstance().edit()
                                .remove("id")
                                .remove("email")
                                .remove("tipo_usuario")
                                .remove("id_tipo_usuario").commit();
                        //Salir
                        MainActivity.this.finish();
                    }
                })
                .show();
    }

    public void getAllRegistros() {
        MyPreferences.getInstance().init(MainActivity.this, "UserProfile");

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
                                        Toast.makeText(MainActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
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

        MyVolley.getInstance(MainActivity.this).addToRequestQueue(request);
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
                        Toast.makeText(MainActivity.this, getString(R.string.act_main_informacion_actualizada), Toast.LENGTH_LONG).show();
                    }
                });
                progressDialog.dismiss();
            } else {
                Toast.makeText(MainActivity.this, getString(R.string.act_nuevo_evento_error_guardar_registros), Toast.LENGTH_LONG).show();
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
