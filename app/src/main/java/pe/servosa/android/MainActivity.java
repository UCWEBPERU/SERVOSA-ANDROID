package pe.servosa.android;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.Bind;
import butterknife.ButterKnife;
import pe.servosa.android.util.Banner;
import pe.servosa.android.util.ModalFiltroPiramide;
import pe.servosa.android.util.MyPreferences;
import pe.servosa.android.util.MyToolbar;
import pe.servosa.android.util.navigation.FragmentDrawer;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.imgHeaderServosa) ImageView imgHeaderServosa;
    @Bind(R.id.btnNuevoEvento) FrameLayout btnNuevoEvento;
    @Bind(R.id.btnListadoEventos) FrameLayout btnListadoEventos;
    @Bind(R.id.btnGraficoPiramide) FrameLayout btnGraficoPiramide;
    @Bind(R.id.btnExportarExcel) FrameLayout btnExportarExcel;
    @Bind(R.id.btnConfigurar) FrameLayout btnConfigurar;
    @Bind(R.id.btnCerrar) FrameLayout btnCerrar;
    @Bind(R.id.viewPagerBanner) ViewPager viewPagerBanner;
    private FragmentDrawer drawerFragment;



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

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPreferences.getInstance().edit()
                        .remove("id")
                        .remove("email")
                        .remove("tipo_usuario")
                        .remove("id_tipo_usuario").commit();
                finish();
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

}
