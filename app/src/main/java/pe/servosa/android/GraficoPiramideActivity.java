package pe.servosa.android;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import pe.servosa.android.util.MyPreferences;
import pe.servosa.android.util.MyToolbar;
import pe.servosa.android.util.chart.ChartData;
import pe.servosa.android.util.chart.PyramidChart;
import pe.servosa.android.util.internet.CustomJsonObjectRequest;
import pe.servosa.android.util.internet.MyVolley;

public class GraficoPiramideActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.imgHeaderServosa) ImageView imgHeaderServosa;
    @Bind(R.id.tituloHeader) TextView tituloHeader;
    @Bind(R.id.pyramidChart) PyramidChart pyramidChart;

    private ProgressDialog progressDialog;

    private List<ChartData> valuesPiramide = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico_piramide);
        ButterKnife.bind(this);
        new MyToolbar(this, toolbar);
        loadImages();

        if (getIntent().hasExtra("filtros")) {
            cargarTitulo();
            MyPreferences.getInstance().init(GraficoPiramideActivity.this, "UserProfile");
            showLoading();
            cargarDatosPiramide();
        } else {
            tituloHeader.setText("PIRAMIDE");
            new AlertDialog.Builder(GraficoPiramideActivity.this)
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
            cargarPiramideError();
        }

    }

    private void loadImages(){
        Glide.with(this)
                .load(R.drawable.logo_servosa_header)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(imgHeaderServosa);
    }

    private void cargarTitulo() {
        String filtro = getIntent().getExtras().getBundle("filtros").getString("filtro");
        if (filtro.equals("nacional")) {
            tituloHeader.setText("PIRAMIDE NACIONAL");
        } else if (filtro.equals("region")) {
            tituloHeader.setText("PIRAMIDE REGIONAL");
        } else if (filtro.equals("operacion")) {
            tituloHeader.setText("PIRAMIDE OPERACION");
        }
    }

    private void cargarDatosPiramide() {
        Map<String, String> params = new HashMap<String, String>();

        params.put("id_usuario", MyPreferences.getInstance().getString("id", ""));
        params.put("tipo_usuario", MyPreferences.getInstance().getString("tipo_usuario", ""));
        params.put("filtro", getIntent().getExtras().getBundle("filtros").getString("filtro"));

        if (getIntent().getExtras().getBundle("filtros").getString("id_region") != null) {
            params.put("id_region", getIntent().getExtras().getBundle("filtros").getString("id_region"));
        } else {
            params.put("id_region", "");
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
                                        mostrarPiramide(response.getJSONObject("data"));
                                    } else {
                                        Toast.makeText(GraficoPiramideActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException ex) {
                                    Toast.makeText(GraficoPiramideActivity.this, getString(R.string.json_object_exception), Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        cargarPiramideError();
                        new AlertDialog.Builder(GraficoPiramideActivity.this)
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
                        Log.d("VOLLEY ERROR", error.getMessage());
                    }
                });

        MyVolley.getInstance(GraficoPiramideActivity.this).addToRequestQueue(request);
    }

    private void mostrarPiramide(JSONObject data) {
        JSONObject jsonObject = data;
        try {
            valuesPiramide.add(new ChartData(jsonObject.getString("piramide_nivel_1"), 500));
            valuesPiramide.add(new ChartData(jsonObject.getString("piramide_nivel_2"), 500));
            valuesPiramide.add(new ChartData(jsonObject.getString("piramide_nivel_3"), 500));
            valuesPiramide.add(new ChartData(jsonObject.getString("piramide_nivel_4"), 500));
            valuesPiramide.add(new ChartData(jsonObject.getString("piramide_nivel_5"), 500));
            pyramidChart.setData(valuesPiramide);
        } catch (JSONException ex) {
            cargarPiramideError();
            Toast.makeText(GraficoPiramideActivity.this, "Lo sentimos ocurrio un error al procesar los datos de la piramide, inténtalo de nuevo.", Toast.LENGTH_LONG).show();
        }
    }

    private void cargarPiramideError() {
        valuesPiramide.add(new ChartData("0", 500));
        valuesPiramide.add(new ChartData("0", 500));
        valuesPiramide.add(new ChartData("0", 500));
        valuesPiramide.add(new ChartData("0", 500));
        valuesPiramide.add(new ChartData("0", 500));
        pyramidChart.setData(valuesPiramide);
    }

    private void showLoading() {
        progressDialog = new ProgressDialog(GraficoPiramideActivity.this);
        progressDialog.setTitle("Cargando Datos");
        progressDialog.setMessage("Espere un momento");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

}