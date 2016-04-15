package pe.servosa.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import pe.servosa.android.adapter.ListadoEventoAdapter;
import pe.servosa.android.model.DataEventos;
import pe.servosa.android.model.EventoRiesgoEntity;

public class ExportarExcelActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.lvListadoEventos)
    ListView lvListadoEventos;
    ArrayList<EventoRiesgoEntity> listadoEventoEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exportar_excel);
        ButterKnife.bind(this);
        setToolbar();

        cargarListadoEventos();
        lvListadoEventos.setAdapter(new ListadoEventoAdapter(this, listadoEventoEntities, R.layout.row_exportar_excel));
        lvListadoEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mostrarDetalleEvento(position);
            }
        });

    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }

    private void cargarListadoEventos() {
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
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Operación: " + listadoEventoEntities.get(position).getOperacion() + "\n\n");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Operación: " + listadoEventoEntities.get(position).getOperacion() + "\n\n" +
                "Ruta: " + listadoEventoEntities.get(position).getRuta() + "\n\n" +
                "Tramo: " + listadoEventoEntities.get(position).getTramo() + "\n\n" +
                "Evento: " + listadoEventoEntities.get(position).getEvento() + "\n\n" +
                "Categoria: " + listadoEventoEntities.get(position).getCategoria() + "\n\n" +
                "Tipo: " + listadoEventoEntities.get(position).getTipo() + "\n\n" +
                "Numero de Placa: " + listadoEventoEntities.get(position).getNumPlaca() + "\n\n" +
                "Descripción: " + listadoEventoEntities.get(position).getDescripcion() + "\n\n" +
                "Operación: " + listadoEventoEntities.get(position).getOperacion() + "\n\n" +
                listadoEventoEntities.get(position).getFechaHora());

        try {
            startActivity(Intent.createChooser(emailIntent, "Exportar Excels"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "No hay aplicaciones de email instalado.", Toast.LENGTH_SHORT).show();
        }

    }
}
