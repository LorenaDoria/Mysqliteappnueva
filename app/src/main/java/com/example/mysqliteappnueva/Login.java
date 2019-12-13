package com.example.mysqliteappnueva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mysqliteappnueva.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private Button boton;
    private EditText user, et1, et2;
    private EditText passw;
    private Cursor fila, fila2;

    ArrayList<Usuario> usuarios;
    Usuario us;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        boton = findViewById(R.id.ingresar);
        user = findViewById(R.id.user);
        passw = findViewById(R.id.passw);
        cargarJson();
        registrar();


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               login();
            }
        });


    }


    public String leerJson() {
        String sContent = "";
        try {
            InputStream stream = getResources().openRawResource(R.raw.json2);
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            sContent = new String(buffer);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Error de lectura", Toast.LENGTH_LONG).show();
        }
        return sContent;
    }

    public void cargarJson() {
        usuarios = new ArrayList<>();
        String json = leerJson();

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray results = obj.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                us = new Usuario();

                JSONObject nombres = results.getJSONObject(i);
                String usu = nombres.getString("usuario");
                us.setUser(usu);
                String pass = nombres.getString("password");
                us.setPassw(pass);
                String rol = nombres.getString("rol");
                us.setRol(rol);
                usuarios.add(us);
                registrar();
            }
        } catch (JSONException ex) {
            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
        }
    }

    private void registrar() {

        UsuarioSQLiteOpenHelper admin = new UsuarioSQLiteOpenHelper(getApplicationContext(), "bdU", null, 1);
        SQLiteDatabase base = admin.getWritableDatabase();


        for (int i = 0; i < usuarios.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("usuario", usuarios.get(i).getUser());
            registro.put("password", usuarios.get(i).getPassw());
            registro.put("rol", usuarios.get(i).getRol());
            base.insert("datos", null, registro);
        }

        base.close();
        Toast.makeText(getApplicationContext(), "DATOS GUARDADOS", Toast.LENGTH_LONG).show();
    }


    public void login() {

        UsuarioSQLiteOpenHelper admin = new UsuarioSQLiteOpenHelper(getApplicationContext(), "bdU", null, 1);
        SQLiteDatabase base = admin.getWritableDatabase();
        String usuario = user.getText().toString();
        String password = passw.getText().toString();

        fila = base.rawQuery("select usuario, password from datos where usuario='" + usuario + "' and password='" + password+"'" , null);
        if (fila.moveToFirst()) {
            String user = fila.getString(0);
            String pass = fila.getString(1);

            if (usuario.equals(user) && password.equals(pass)) {
                Intent respuesta = new Intent(Login.this, MainActivity.class);
                startActivity(respuesta);
            } else {
                Toast.makeText(getApplicationContext(), "Login Fallido", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Login Fallido", Toast.LENGTH_LONG).show();
        }


    }

    public void prueba(){
        String cadena ="";
        UsuarioSQLiteOpenHelper admin = new UsuarioSQLiteOpenHelper(getApplicationContext(), "bdU", null, 1);
        SQLiteDatabase base = admin.getWritableDatabase();

        Cursor f = base.rawQuery("select usuario from datos",null);

        if (f != null){
            f.moveToFirst();
            do {
                cadena += "User:"+ f.getString(0);
            }while(f.moveToNext());
            Toast.makeText(getApplicationContext(), ""+cadena, Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "la base de datos está vacía", Toast.LENGTH_LONG).show();
        }
    }

    public void login2() {
        String usuario = user.getText().toString();
        String password = passw.getText().toString();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuario.equals(usuarios.get(i).getUser()) && password.equals(usuarios.get(i).getPassw())) {
                Intent respuesta = new Intent(Login.this, MainActivity.class);
                startActivity(respuesta);
                break;
            } else {
                Toast.makeText(getApplicationContext(), "NO FUNCIONO", Toast.LENGTH_LONG).show();
            }
        }
    }


}












