package com.example.mysqliteappnueva.ui.gallery;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mysqliteappnueva.AdminSQLiteOpenHelper;
import com.example.mysqliteappnueva.Estudiante;
import com.example.mysqliteappnueva.R;
import com.example.mysqliteappnueva.Usuario;
import com.example.mysqliteappnueva.ui.Adapter.MyAdapter;
import com.example.mysqliteappnueva.ui.Adapter.ResistroLoginAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GalleryFragment extends Fragment {
    private ListView listView;
    private Button btnLeerJson, btnBorrarBD, btnUsuarios;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("registro-login");



    Estudiante es;
    ArrayList<Estudiante> estudiantes;
    ArrayList<Estudiante> listaEstudiantes;
    List<Usuario> listaUsuarios;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cargar, container, false);
        listView = root.findViewById(R.id.listView);
        btnLeerJson = root.findViewById(R.id.btnLeerJson);
        btnBorrarBD = root.findViewById(R.id.btnBorrarDataBase);
        btnUsuarios = root.findViewById(R.id.btn_user);
        renedrListView();

        btnLeerJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarJson();
                renedrListView();
            }
        });

       btnBorrarBD.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               borrarBaseDeDatos();
               renedrListView();
           }
       });

       btnUsuarios.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Boolean admin = getActivity().getIntent().getBooleanExtra("admin", false);
               if (admin) fetchData();
               else Toast.makeText(getContext(), "Acceso Denegado", Toast.LENGTH_LONG).show();
           }
       });
        return root;
    }
    private void renedrListView() {
        consultarListaEstudiantes();
        MyAdapter myAdapter = new MyAdapter(getContext(), R.layout.item, listaEstudiantes);
        listView.setAdapter(myAdapter);
    }


    private void consultarListaEstudiantes() {
        AdminSQLiteOpenHelper  admin =new AdminSQLiteOpenHelper(getActivity(),"administracion", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();

        Estudiante estudiante;
        listaEstudiantes = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + "estudiantes", null);

        while (cursor.moveToNext()) {
            estudiante = new Estudiante();
            estudiante.setCodigo(cursor.getInt(0));
            estudiante.setNombre(cursor.getString(1));
            estudiante.setDireccion(cursor.getString(2));
            estudiante.setLatitud(cursor.getDouble(3));
            estudiante.setLongitud(cursor.getDouble(4));

            listaEstudiantes.add(estudiante);
        }
        db.close();
    }



    private void borrarBaseDeDatos(){
        AdminSQLiteOpenHelper  admin =new AdminSQLiteOpenHelper(getActivity(),"administracion", null, 1);

        SQLiteDatabase db = admin.getReadableDatabase();
        db.execSQL("delete from "+ "estudiantes");
        db.close();
    }
    /*-------------------------------Leer Firebase BD----------------------------------*/
    private void fetchData() {
        listaUsuarios = new ArrayList<>();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaUsuarios.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Usuario user = userSnapshot.getValue(Usuario.class);
                    listaUsuarios.add(user);
                    database.removeEventListener(this);
                }
                    Collections.reverse(listaUsuarios);
                    ResistroLoginAdapter adapter = new ResistroLoginAdapter(getActivity(), listaUsuarios);
                    listView.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    /*-------------------------------Lectura y registro en BD--------------------------*/

    public String leerJson() {
        String sContent = "";
        try {
            InputStream stream = getResources().openRawResource(R.raw.json);
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            sContent = new String(buffer);
        } catch (IOException e) {
            Toast.makeText(getContext(), "Error de lectura", Toast.LENGTH_SHORT).show();
        }
        return sContent;
    }

    public void cargarJson() {
        estudiantes = new ArrayList<>();
        String json = leerJson();

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray results = obj.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                es = new Estudiante();
                int code = (int) (Math.random() * 10000) + 1000;
                es.setCodigo(code);
                JSONObject nombres = results.getJSONObject(i);
                String nom = nombres.getString("name");
                es.setNombre(nom);
                String dir = nombres.getString("formatted_address");
                es.setDireccion(dir);
                JSONObject geometry = nombres.getJSONObject("geometry");
                JSONObject location = geometry.getJSONObject("location");
                double lat = location.getDouble("lat");
                es.setLatitud(lat);
                double lon = location.getDouble("lng");
                es.setLongitud(lon);
                estudiantes.add(es);
                registrar();
            }
        } catch (JSONException ex) {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void registrar() {

        AdminSQLiteOpenHelper admin =new AdminSQLiteOpenHelper(getActivity(),"administracion", null, 1);
        SQLiteDatabase base = admin.getWritableDatabase();


        for (int i = 0; i < estudiantes.size(); i++) {
            ContentValues registro=new ContentValues();
            registro.put("codigo",estudiantes.get(i).getCodigo());
            registro.put("nombre",estudiantes.get(i).getNombre());
            registro.put("direccion",estudiantes.get(i).getDireccion());
            registro.put("latitud",estudiantes.get(i).getLatitud());
            registro.put("longitud",estudiantes.get(i).getLongitud());

            base.insert("estudiantes", null,registro);
        }

            base.close();
            Toast.makeText(getActivity(), "DATOS GUARDADOS", Toast.LENGTH_SHORT).show();
    }


}