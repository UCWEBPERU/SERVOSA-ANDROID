package pe.servosa.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pe.servosa.android.util.MyToolbar;
import pe.servosa.android.util.chart.ChartData;
import pe.servosa.android.util.chart.PyramidChart;

public class GraficoPiramideActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.pyramidChart) PyramidChart pyramidChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico_piramide);
        ButterKnife.bind(this);
        new MyToolbar(this, toolbar);

        List<ChartData> values = new ArrayList<>();

        values.add(new ChartData("1", 500));
        values.add(new ChartData("10", 500));
        values.add(new ChartData("30", 500));
        values.add(new ChartData("600", 500));
        values.add(new ChartData("3000", 500));

        pyramidChart.setData(values);
    }

    public class ActivityManager {
        public void a(){

        }
    }

}