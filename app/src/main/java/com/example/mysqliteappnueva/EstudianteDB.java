package com.example.mysqliteappnueva;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.math.BigDecimal;

public class EstudianteDB extends SQLiteOpenHelper {



    private static final String  NOMBRE_BD="POSITION";
    private static final int VERSION_BD =1;
    private static final String TABLA_ESTUDIANTES="CREATE TABLE ESTUDIANTES(CODIGO INTEGER PRIMARY KEY , NOMBRE TEXT,DIRECCION TEXT,LATITUD DOUBLE, LONGITUD DOUBLE)";


    public EstudianteDB(@Nullable Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLA_ESTUDIANTES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " +TABLA_ESTUDIANTES);
        db.execSQL(TABLA_ESTUDIANTES);

    }

    public void agregarEstudiantes( Integer codigo,String nombre, String direccion, Double latitud,Double longitud ){


        SQLiteDatabase bd=getWritableDatabase();
        if(bd!=null){
            bd.execSQL("INSERT INTO ESTUDIANTES VALUES('"+codigo+"''"+nombre+"''"+direccion+"''"+latitud+"''"+longitud+"')");
            bd.close();

        }
    }
}
