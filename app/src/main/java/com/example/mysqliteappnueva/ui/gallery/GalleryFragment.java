package com.example.mysqliteappnueva.ui.gallery;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.mysqliteappnueva.Estudiante;
import com.example.mysqliteappnueva.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GalleryFragment extends Fragment {
    Button btn_cargar;
    TextView text;

    Estudiante es;
    ArrayList<Estudiante> estudiantes;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cargar, container, false);
        btn_cargar = (Button) root.findViewById(R.id.button);
        text = (TextView) root.findViewById(R.id.textojson);
        text.setMovementMethod(new ScrollingMovementMethod());

        btn_cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarJson();
                mostrarJSON();


            }
        });

        return root;

    }

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
        Estudiante e;
        int code = 0;
        String json = leerJson();

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray results = obj.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                e = new Estudiante();
                code = (int) (Math.random() * 10000) + 1000;
                e.setCodigo(code);
                JSONObject nombres = results.getJSONObject(i);
                String nom = nombres.getString("name");
                e.setNombre(nom);
                String dir = nombres.getString("formatted_address");
                e.setDireccion(dir);
                JSONObject geometry = nombres.getJSONObject("geometry");
                JSONObject location = geometry.getJSONObject("location");
                double lat = location.getDouble("lat");
                e.setLatitud(lat);
                double lon = location.getDouble("lng");
                e.setLongitud(lon);
                estudiantes.add(e);
            }
        } catch (JSONException ex) {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void mostrarJSON() {
        String cadena = "";
        for (int i = 0; i < estudiantes.size(); i++) {
            cadena += "Nombre :" + estudiantes.get(i).getNombre() + "\nCódigo :" + estudiantes.get(i).getCodigo()
                    + "\nDirección: " + estudiantes.get(i).getDireccion() + "\nLatitud: "+estudiantes.get(i).getLatitud()
                    +"\nLongitud: "+estudiantes.get(i).getLongitud()+"\n\n";
        }
        text.setText(""+cadena);
    }


}