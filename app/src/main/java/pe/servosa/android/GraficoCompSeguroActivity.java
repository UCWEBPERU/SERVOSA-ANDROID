package pe.servosa.android;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.achartengine.model.SeriesSelection;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import pe.servosa.android.util.MyPreferences;
import pe.servosa.android.util.MyToolbar;
import pe.servosa.android.util.internet.CustomJsonObjectRequest;
import pe.servosa.android.util.internet.MyVolley;

public class GraficoCompSeguroActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.imgHeaderServosa) ImageView imgHeaderServosa;
    @Bind(R.id.tituloHeader) TextView tituloHeader;

    private ProgressDialog progressDialog;
    private static int datosGraficoJSON[] = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico_comp_seguro);
        ButterKnife.bind(this);
        new MyToolbar(this, toolbar);
        loadImages();

        if (getIntent().hasExtra("filtros")) {
            MyPreferences.getInstance().init(GraficoCompSeguroActivity.this, "UserProfile");
            cargarTitulo();
            showLoading();
            cargarDatosGrafico();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.pieChart, new PlaceholderFragment(new float[]{0,0})).commit();
            new AlertDialog.Builder(GraficoCompSeguroActivity.this)
                    .setTitle(getString(R.string.act_graf_comportamiento_seguros_error_configuracion_title))
                    .setCancelable(false)
                    .setMessage(getString(R.string.act_graf_comportamiento_seguros_error_configuracion_message))
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).create().show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
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
            tituloHeader.setText("GRAFICO NACIONAL");
        } else if (filtro.equals("region")) {
            tituloHeader.setText("GRAFICO REGIONAL");
        } else if (filtro.equals("operacion")) {
            tituloHeader.setText("GRAFICO OPERACION");
        }
    }

    /**
     * A fragment containing a pie chart.
     */
    public static class PlaceholderFragment extends Fragment {

        private PieChartView chart;
        private PieChartData data;

        private boolean hasLabels = true;
        private boolean hasLabelsOutside = false;
        private boolean hasCenterCircle = false;
        private boolean hasLabelForSelected = false;

        private float datosGrafico[];

        public PlaceholderFragment(float datosGrafico[]) {
            this.datosGrafico = datosGrafico;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            View rootView = inflater.inflate(R.layout.fragment_pie_chart, container, false);

            chart = (PieChartView) rootView.findViewById(R.id.chart);
            chart.setChartRotationEnabled(false);
            chart.setOnValueTouchListener(new ValueTouchListener());
            chart.setZoomEnabled(false);

            loadData();

            return rootView;
        }

        public void loadData() {
            int numValues = 2;
            int colors[] = new int[]{ContextCompat.getColor(getContext(), R.color.azul_b), ContextCompat.getColor(getContext(), R.color.rojo)};
            List<SliceValue> values = new ArrayList<SliceValue>();
            for (int i = 0; i < numValues; ++i) {
//                SliceValue sliceValue = new SliceValue((float) Math.random() * 30 + 15, colors[i]);
                SliceValue sliceValue = new SliceValue(datosGrafico[i], colors[i]);
                sliceValue.setLabel((int)datosGrafico[i] + "%");
                values.add(sliceValue);
            }

            data = new PieChartData(values);
            data.setHasLabels(hasLabels);
            data.setHasLabelsOnlyForSelected(hasLabelForSelected);
            data.setHasLabelsOutside(hasLabelsOutside);
            data.setHasCenterCircle(hasCenterCircle);

            chart.setPieChartData(data);
        }

        private class ValueTouchListener implements PieChartOnValueSelectListener {

            @Override
            public void onValueSelected(int arcIndex, SliceValue value) {
//                Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
                if (arcIndex == 0) {
                    Toast.makeText(getActivity(), "Comportamiento Riesgo: " + datosGraficoJSON[arcIndex] + " eventos.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Comportamiento Seguro: " + datosGraficoJSON[arcIndex] + " eventos.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onValueDeselected() {
                // TODO Auto-generated method stub

            }

        }

    }

    private void cargarDatosGrafico() {
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

        CustomJsonObjectRequest request = new CustomJsonObjectRequest
                (Request.Method.POST, MyVolley.URL_API_REST + "evento/getEventosComportamientoSeguro", params,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                progressDialog.dismiss();
                                Log.d("JSONObject RESPONSE", response + "");
                                try {
                                    if (response.getBoolean("status")) {
                                        mostrarDatosGrafico(response.getJSONObject("data"));
                                    } else {
                                        Toast.makeText(GraficoCompSeguroActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException ex) {
                                    Toast.makeText(GraficoCompSeguroActivity.this, getString(R.string.json_object_exception), Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        new AlertDialog.Builder(GraficoCompSeguroActivity.this)
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

        MyVolley.getInstance(GraficoCompSeguroActivity.this).addToRequestQueue(request);
    }

    private void mostrarDatosGrafico(JSONObject data) {
        float datosGrafico[] = calcularDatosGrafico(data);
        if (datosGrafico != null) {
            getSupportFragmentManager().beginTransaction().add(R.id.pieChart, new PlaceholderFragment(datosGrafico)).commit();
        }
    }

    private float[] calcularDatosGrafico(JSONObject data) {
        JSONObject jsonObject = data;
        float datosGrafico[] = null;

        try {
            int comportamientoRiesgo = jsonObject.getInt("comportamiento_riesgo");
            int comportamientoSeguro = jsonObject.getInt("comportamiento_seguro");
            int total = comportamientoRiesgo + comportamientoSeguro;

            datosGraficoJSON = new int[]{comportamientoRiesgo, comportamientoSeguro};

            if (comportamientoRiesgo != 0) {
                comportamientoRiesgo = (comportamientoRiesgo * 100) / total;
            }
            if (comportamientoSeguro != 0) {
                comportamientoSeguro = (comportamientoSeguro * 100) / total;
            }
            datosGrafico = new float[]{comportamientoRiesgo, comportamientoSeguro};

            if (comportamientoRiesgo == 0 && comportamientoSeguro == 0) {
                datosGrafico = new float[]{100, 0};
                Toast.makeText(GraficoCompSeguroActivity.this, getString(R.string.act_graf_comportamiento_seguros_no_hay_registros), Toast.LENGTH_LONG).show();
            }
        } catch (JSONException ex) {
            Toast.makeText(GraficoCompSeguroActivity.this, getString(R.string.json_object_exception), Toast.LENGTH_LONG).show();
        }

        return datosGrafico;
    }

    private void showLoading() {
        progressDialog = new ProgressDialog(GraficoCompSeguroActivity.this);
        progressDialog.setTitle("Cargando Datos");
        progressDialog.setMessage("Espere un momento...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

}
