package com.example.mysqliteappnueva.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mysqliteappnueva.Estudiante;
import com.example.mysqliteappnueva.R;
import com.example.mysqliteappnueva.Usuario;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {

    private Context context;
    private int Layout;
    private ArrayList<Usuario> usuarios;

    public Adaptador(Context context, int layout, ArrayList<Usuario> usuarios) {
        this.context = context;
        Layout = layout;
        this.usuarios = usuarios;
    }

    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return this.usuarios.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int i, View converView, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        converView = layoutInflater.inflate(Layout,null);

        Usuario currentName = usuarios.get(i);

        TextView textViewID = converView.findViewById(R.id.textViewID);
        TextView textViewNombre = converView.findViewById(R.id.textViewNombre);
        TextView textViewDireccion = converView.findViewById(R.id.textViewDireccion);


        textViewID.setText("Usuario: " + currentName.getUser());
        textViewNombre.setText("Nombre: " + currentName.getPassw());
        textViewDireccion.setText("Direccion: " + currentName.getRol());



        return converView;
    }
}
