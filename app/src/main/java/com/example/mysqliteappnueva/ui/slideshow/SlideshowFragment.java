package com.example.mysqliteappnueva.ui.slideshow;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import com.example.mysqliteappnueva.AdminSQLiteOpenHelper;
import com.example.mysqliteappnueva.R;

public class SlideshowFragment extends Fragment {

    EditText codigo,nombre,direccion,latitud,longitud;
    Button  boton_registrar,boton_buscar,boton_eliminar;



    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_registrar, container, false);



        boton_registrar = (Button) root.findViewById(R.id.btn_registrar);
        boton_buscar = (Button) root.findViewById(R.id.btn_buscar);
        boton_eliminar = (Button) root.findViewById(R.id.btn_eliminar);

        codigo=(EditText)root.findViewById(R.id.txt_codigo);
        nombre=(EditText ) root.findViewById(R.id.txt_name);
        direccion=(EditText) root.findViewById(R.id.txt_dir);
        latitud=(EditText) root.findViewById(R.id.txt_lat);
        longitud=(EditText) root.findViewById(R.id.txt_long);

        final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

                boton_registrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        registrar();

                    }
                });

                boton_buscar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mostrar();

                    }
                });

                boton_eliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        eliminarEstudiante();

                    }
                });


            }
        });
        return root;




    }

    private void registrar() {

        AdminSQLiteOpenHelper  admin =new AdminSQLiteOpenHelper(getActivity(),"administracion", null, 1);
        SQLiteDatabase base = admin.getWritableDatabase();


        String code =codigo.getText().toString();
        String name=nombre.getText().toString();
        String dir =direccion.getText().toString();
        String lat=latitud.getText().toString();
        String longt =longitud.getText().toString();

        if(!code.isEmpty()&& !name.isEmpty() && !dir.isEmpty() && !lat.isEmpty()&& !longt.isEmpty()){

            ContentValues registro=new ContentValues();

            registro.put("codigo",code);
            registro.put("nombre",name);
            registro.put("direccion",dir);
            registro.put("latitud",lat);
            registro.put("longitud",longt);

            base.insert("estudiantes", null,registro);
            base.close();

            codigo.setText("");
            nombre.setText("");
            direccion.setText("");
            latitud.setText("");
            longitud.setText("");

            Toast.makeText(getActivity(), "DATOS GUARDADOS", Toast.LENGTH_SHORT).show();


        }else{

            Toast.makeText(getActivity(), "DEBES LLENAR TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();
        }
    }

    public void mostrar(){

        AdminSQLiteOpenHelper  admin =new AdminSQLiteOpenHelper(getActivity(),"administracion", null, 1);
        SQLiteDatabase datab = admin.getWritableDatabase();


        String code =codigo.getText().toString();

        if (!code.isEmpty()){

            Cursor fila=datab.rawQuery
                    ("SELECT nombre, direccion,latitud,longitud FROM estudiantes WHERE codigo="+code+"",null);

            if (fila.moveToFirst()){
                nombre.setText(fila.getString(0));
                direccion.setText(fila.getString(1));
                latitud.setText(fila.getString(2));
                longitud.setText(fila.getString(3));
                datab.close();
            }else{

                Toast.makeText(getActivity(), "NO EXISTE EL ESTUDIANTE", Toast.LENGTH_SHORT).show();
                datab.close();
            }

        }else{
            Toast.makeText(getActivity(), "DEBES PONER EL CODIGO A BUSCAR ", Toast.LENGTH_SHORT).show();
        }

    }


    public void eliminarEstudiante(){

        AdminSQLiteOpenHelper  admin =new AdminSQLiteOpenHelper(getActivity(),"administracion", null, 1);
        SQLiteDatabase datab = admin.getWritableDatabase();

        String code =codigo.getText().toString();

        if (!code.isEmpty()){

        int count =datab.delete("estudiantes","codigo="+code,null);
        datab.close();

            codigo.setText("");
            nombre.setText("");
            direccion.setText("");
            latitud.setText("");
            longitud.setText("");

            if(count==1){
                Toast.makeText(getActivity(), "Estudiante eliminado correctamente", Toast.LENGTH_SHORT).show();
            }else{

                Toast.makeText(getActivity(), "El estudiante NO EXISTE", Toast.LENGTH_SHORT).show();
            }



        }else{
            Toast.makeText(getActivity(), "Debes poner el codigo del estudainte a eliminar", Toast.LENGTH_SHORT).show();

        }

    }

}