package pe.servosa.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pe.servosa.android.R;
import pe.servosa.android.model.EventoRiesgoEntity;
import pe.servosa.android.model.PiramideAccidentabilidadEntity;

/**
 * Created by ucweb02 on 20/04/2016.
 */
public class PiramideAccidentabilidadAdapter extends BaseAdapter {

    ArrayList<PiramideAccidentabilidadEntity> registros;
    LayoutInflater inflater;
    Context context;
    Integer resIDLayoutRow;

    public PiramideAccidentabilidadAdapter(Context context, ArrayList<PiramideAccidentabilidadEntity> data, Integer resID) {
        this.registros = data;
        this.context = context;
        this.resIDLayoutRow = resID;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return registros.size();
    }

    @Override
    public Object getItem(int position) {
        return registros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(resIDLayoutRow, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        PiramideAccidentabilidadEntity currentListData = (PiramideAccidentabilidadEntity) getItem(position);

        mViewHolder.txtOperacion.setText("Operacion:" + currentListData.getOperacion());
        mViewHolder.txtRuta.setText("Ruta:" + currentListData.getRuta());
        mViewHolder.txtTramo.setText("Tramo:" + currentListData.getTramo());
        mViewHolder.txtEvento.setText("Evento:" + currentListData.getEvento());
        mViewHolder.txtFechaHora.setText(currentListData.getFecha() + " / " + currentListData.getHora());

        return convertView;
    }

    private class MyViewHolder {
        TextView txtOperacion, txtRuta, txtTramo, txtEvento, txtFechaHora;

        public MyViewHolder(View item) {
            txtOperacion = (TextView) item.findViewById(R.id.txtOperacion);
            txtRuta = (TextView) item.findViewById(R.id.txtRuta);
            txtTramo = (TextView) item.findViewById(R.id.txtTramo);
            txtEvento = (TextView) item.findViewById(R.id.txtEvento);
            txtFechaHora = (TextView) item.findViewById(R.id.txtFechaHora);
        }
    }
}
