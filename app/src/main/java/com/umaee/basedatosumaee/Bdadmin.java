package com.umaee.basedatosumaee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Bdadmin {
    //Tabla de alumnos
    public static final String TABLA_ALUMNOS = "alumnos_umaee";
    //Columnas de la tabla alumnos
    public static final String MATRICULA = "_id";
    public static final String NOMBRE_ALUMNOS = "nombre_alumno";
    public static final String MATERIA = "materia";

    //Tabla de materias
    public static final String TABLA_MATERIAS = "materias_umaee";
    //Columnas de la tabla materias
    public static final String ID_MATERIAS = "_id";
    public static final String NOMBRE_MATERIAS = "nombre_materia";

    //sentencia SQL para crear la tabla alumnos
    public static final String CREATE_ALUMNOS = "CREATE TABLE " + TABLA_ALUMNOS + " (" +
            MATRICULA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOMBRE_ALUMNOS + " TEXT NOT NULL)," +
            MATERIA + " TEXT NOT NULL" +
            "FOREIGN KEY (" + MATERIA + ") REFERENCES " + TABLA_MATERIAS + "(" + ID_MATERIAS + ") ON DELETE CASCADE ON UPDATE CASCADE);";

    //sentencia SQL para crear la tabla materias
    public static final String CREATE_MATERIAS = "CREATE TABLE " + TABLA_MATERIAS + " (" +
            ID_MATERIAS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NOMBRE_MATERIAS + " TEXT NOT NULL);";

    //crearemos objetos para la base de datos
    private Bdconexion _conexion;
    private SQLiteDatabase _basededatos;

    //Constructor de la instancia que nos ayudará en los mantenimientos
    public Bdadmin(Context context) {
        _conexion = new Bdconexion(context);
        _basededatos = _conexion.getWritableDatabase();
    }

    //Función que devuelve el contenedor de valores
    public ContentValues getContentValues(String nombre_alumno, String materia) {
        ContentValues valores = new ContentValues();
        valores.put(NOMBRE_ALUMNOS, nombre_alumno);
        valores.put(MATERIA, materia);
        return valores;
    }

    //insertar datos a alumnos
    public void insertarAlumno(String nombre_alumno, String materia) {
        _basededatos.insert(TABLA_ALUMNOS, null, getContentValues(nombre_alumno, materia));
    }

    //eliminar datos del alumno por matricula
    public void eliminarAlumno(int matricula) {
        _basededatos.delete(TABLA_ALUMNOS, MATRICULA + "=" + matricula, null);
    }

    //eliminar datos del alumno por nombre
    public void eliminarAlumno(String nombre_alumno) {
        _basededatos.delete(TABLA_ALUMNOS, NOMBRE_ALUMNOS + "=" + nombre_alumno, null);
    }

    //modificar datos del alumno por matricula
    public void modificarAlumno(int matricula, String nombre_alumno, String materia) {
        _basededatos.update(TABLA_ALUMNOS, getContentValues(nombre_alumno, materia), MATRICULA + "=" + matricula, null);
    }

    //obtener datos de alumnos
    public Cursor obtenerAlumnos() {
        String[] columnas = {MATRICULA, NOMBRE_ALUMNOS, MATERIA};
        return _basededatos.query(TABLA_ALUMNOS, columnas, null, null, null, null, null);
    }

    //Buscar alumnos por nombre
    public Cursor buscarAlumno(String nombre_alumno) {
        String[] columnas = {MATRICULA, NOMBRE_ALUMNOS, MATERIA};
        return _basededatos.query(TABLA_ALUMNOS, columnas, NOMBRE_ALUMNOS + " LIKE ?", new String[]{"%" + nombre_alumno + "%"}, null, null, null);
    }

    //Buscar alumno por matricula
    public Cursor buscarMatricula(int matricula){
        String[] columnas =  new String[]{MATRICULA, NOMBRE_ALUMNOS, MATERIA};
        return _basededatos.query(TABLA_ALUMNOS, columnas, MATRICULA + " LIKE ?", new String[]{"%" + matricula + "%"}, null, null, null);
    }

    //cargar materias
    public Cursor cargarMaterias() {
        String[] columnas = new String[]{ID_MATERIAS, NOMBRE_MATERIAS};
        return _basededatos.query(TABLA_MATERIAS, columnas, null, null, null, null, null);
    }

    //insertar datos a materias
    public void insertarMateria(String nombre_materia) {
        ContentValues valores = new ContentValues();
        valores.put(NOMBRE_MATERIAS, nombre_materia);
        _basededatos.insert(TABLA_MATERIAS, null, valores);
    }
}
