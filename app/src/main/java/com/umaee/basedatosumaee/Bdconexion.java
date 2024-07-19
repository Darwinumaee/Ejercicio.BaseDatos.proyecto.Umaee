package com.umaee.basedatosumaee;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Bdconexion extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bd_umaee.sqlite";
    private static final int DATABASE_VERSION = 1;

    public Bdconexion(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Bdadministrador.CREATE_ALUMNOS);
        db.execSQL(Bdadministrador.CREATE_MATERIAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
