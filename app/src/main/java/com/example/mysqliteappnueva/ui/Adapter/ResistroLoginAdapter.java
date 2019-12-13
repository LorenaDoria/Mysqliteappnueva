package com.example.mysqliteappnueva.ui.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mysqliteappnueva.R;
import com.example.mysqliteappnueva.Usuario;

import java.util.List;

public class ResistroLoginAdapter extends ArrayAdapter<Usuario> {
    private Activity ctx;
    private List<Usuario> userList;

    public ResistroLoginAdapter(Activity ctx, List<Usuario> userList) {
        super(ctx, R.layout.item_registro_login, userList);
        this.ctx = ctx;
        this.userList = userList;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = ctx.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.item_registro_login, null, true);

        TextView textViewName = listViewItem.findViewById(R.id.user_Logged);
        TextView textViewAdmin = listViewItem.findViewById(R.id.rol_logged);
        TextView textViewDate = listViewItem.findViewById(R.id.date_logged);

        Usuario usuario = userList.get(position);

        textViewName.setText("UserName: " + usuario.getUser());
        textViewAdmin.setText("Rol: " + usuario.getRol());
        textViewDate.setText("Dia: " + usuario.getDate());

        return listViewItem;
    }
}
