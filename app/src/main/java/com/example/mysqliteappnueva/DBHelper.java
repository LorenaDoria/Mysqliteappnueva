package com.example.mysqliteappnueva;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/** * Created by RAUL on 26/05/2016. */
public class DBHelper  extends SQLiteOpenHelper{
    public DBHelper(Context context, String name,
                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuarios(usuario text primary key,contrasena text, rol text)");
        db.execSQL("insert into usuarios values('admin','admin')");
    }

    @Override    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("create table usuarios(codigo integer primary key autoincrement,usuario text,contrasena text)");
        db.execSQL("insert into usuarios values('admin','admin')");
    }

}