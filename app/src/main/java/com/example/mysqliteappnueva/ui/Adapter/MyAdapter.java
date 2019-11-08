package com.example.mysqliteappnueva.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mysqliteappnueva.Estudiante;
import com.example.mysqliteappnueva.R;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private int Layout;
    private ArrayList<Estudiante> estudiantes;

    public MyAdapter(Context context, int layout, ArrayList<Estudiante> estudiantes) {
        this.context = context;
        Layout = layout;
        this.estudiantes = estudiantes;
    }

    @Override
    public int getCount() {
        return estudiantes.size();
    }

    @Override
    public Object getItem(int position) {
        return this.estudiantes.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int i, View converView, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        converView = layoutInflater.inflate(Layout,null);

        Estudiante currentName = estudiantes.get(i);

        TextView textViewID = converView.findViewById(R.id.textViewID);
        TextView textViewNombre = converView.findViewById(R.id.textViewNombre);
        TextView textViewDireccion = converView.findViewById(R.id.textViewDireccion);
        TextView textViewLatitud = converView.findViewById(R.id.textViewLatitud);
        TextView textViewLogitud = converView.findViewById(R.id.textViewLongitud);

        textViewID.setText("Codigo: " + currentName.getCodigo());
        textViewNombre.setText("Nombre: " + currentName.getNombre());
        textViewDireccion.setText("Direccion: " + currentName.getDireccion());
        textViewLatitud.setText("Latitud: " + currentName.getLatitud());
        textViewLogitud.setText("Longitud: " + currentName.getLongitud());

        return converView;
    }
}
