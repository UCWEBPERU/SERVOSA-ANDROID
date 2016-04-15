package pe.servosa.android;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pe.servosa.android.adapter.ListadoEventoAdapter;
import pe.servosa.android.model.DataEventos;
import pe.servosa.android.model.EventoRiesgoEntity;
import pe.servosa.android.sqlite.model.SqlEventoRiesgoEntity;
import pe.servosa.android.util.MyToolbar;

public class ListadoEventosActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.lvListadoEventos) ListView lvListadoEventos;
    @Bind(R.id.imgHeaderServosa)
    ImageView imgHeaderServosa;
    ArrayList<EventoRiesgoEntity> listadoEventoEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_eventos);

        ButterKnife.bind(this);

        new MyToolbar(this, toolbar);

        loadImages();

        cargarListadoEventos();
        lvListadoEventos.setAdapter(new ListadoEventoAdapter(this, listadoEventoEntities, R.layout.row_listado_evento));
        lvListadoEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mostrarDetalleEvento(position);
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
        listadoEventoEntities = new ArrayList<>();
        List<SqlEventoRiesgoEntity> sqlEventoRiesgoEntities = SqlEventoRiesgoEntity.listAll(SqlEventoRiesgoEntity.class);
        if (sqlEventoRiesgoEntities.size() > 0) {
            EventoRiesgoEntity eventoRiesgoEntity;
            for (int c = 0; c < sqlEventoRiesgoEntities.size(); c++) {
                eventoRiesgoEntity = new EventoRiesgoEntity(
                        sqlEventoRiesgoEntities.get(c).getOperacion(),
                        sqlEventoRiesgoEntities.get(c).getRuta(),
                        sqlEventoRiesgoEntities.get(c).getTramo(),
                        sqlEventoRiesgoEntities.get(c).getEvento(),
                        sqlEventoRiesgoEntities.get(c).getCategoria(),
                        sqlEventoRiesgoEntities.get(c).getTipo(),
                        sqlEventoRiesgoEntities.get(c).getNumero_placa(),
                        sqlEventoRiesgoEntities.get(c).getDescripcion(),
                        sqlEventoRiesgoEntities.get(c).getFecha_registro());
                listadoEventoEntities.add(eventoRiesgoEntity);
            }
        } else {
            Toast.makeText(ListadoEventosActivity.this, getString(R.string.act_listado_evento_no_hay_eventos_registrados), Toast.LENGTH_LONG).show();
        }
    }

    private void mostrarDetalleEvento(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Detalle Evento")
                .setMessage("Operación: " + listadoEventoEntities.get(position).getOperacion() + "\n\n" +
                        "Ruta: " + listadoEventoEntities.get(position).getRuta() + "\n\n" +
                        "Tramo: " + listadoEventoEntities.get(position).getTramo() + "\n\n" +
                        "Evento: " + listadoEventoEntities.get(position).getEvento() + "\n\n" +
                        "Categoria: " + listadoEventoEntities.get(position).getCategoria() + "\n\n" +
                        "Tipo: " + listadoEventoEntities.get(position).getTipo() + "\n\n" +
                        "Numero de Placa: " + listadoEventoEntities.get(position).getNumPlaca() + "\n\n" +
                        "Descripción: " + listadoEventoEntities.get(position).getDescripcion() + "\n\n" +
                        listadoEventoEntities.get(position).getFechaHora())
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create().show();
    }

}
