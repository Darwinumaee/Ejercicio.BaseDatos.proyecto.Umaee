package com.umaee.basedatosumaee;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends Activity implements View.OnClickListener {
    Bdadmin manager;
    SimpleCursorAdapter adapter;
    Cursor cursor;

    SharedPreferences registro;
    SharedPreferences.Editor admin;

    ListView lista;
    TextView tv;
    Button btn;
    Button btnbuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = (ListView) findViewById(R.id.ListView);
        tv = (TextView) findViewById(R.id.textView);

        //creando tablas
        manager = new Bdadmin(this);
        cursor = manager.obtenerAlumnos();

        //eventos de botones
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);

        btnbuscar = (Button) findViewById(R.id.btnagregar);
        btnbuscar.setOnClickListener(this);

        //crear columnas para cursores
        final String[] from  = new String[]{Bdadmin.NOMBRE_ALUMNOS, Bdadmin.MATERIA};
        int[] to = new int[]{android.R.id.text1, android.R.id.text2};

        //crear adaptador
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, null, 0);
        lista.setAdapter(adapter);

        //Obtener ID de alumnos para mostrar información
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                Intent editar = new Intent(MainActivity.this, EditarActivity.class);
                editar.putExtra("matricula", cursor.getInt(0));
                startActivity(editar);
                finish();
            }
        });

        //realizar inserción cuando se abra por primera vez la app
        registro = getSharedPreferences("registro", MODE_PRIVATE);
        admin = registro.edit();
        //verificiar si es la primera vez que se abre la app
        if (registro.getBoolean("primeravez", true)) {
            manager.insertarMateria("Matemáticas");
            manager.insertarMateria("Español");
            manager.insertarMateria("Inglés");
            manager.insertarMateria("Física");
            manager.insertarMateria("Química");
            manager.insertarMateria("Biología");

            admin.putBoolean("primeravez", false).commit();
        }
    }

    @Override
    //evento click de botones
    public void onClick(View v) {
        if(v.getId() == R.id.button) {
            //hacer una busqueda de alumnos
            final Cursor cursor = manager.buscarAlumno(tv.getText().toString());
            adapter.changeCursor(cursor);
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    cursor.moveToPosition(position);
                    Intent editar = new Intent(MainActivity.this, EditarActivity.class);
                    editar.putExtra("matricula", cursor.getInt(0));
                    startActivity(editar);
                    finish();
                }
            });
        }

        if(v.getId() == R.id.btnagregar){
            Intent agregar = new Intent(MainActivity.this, AgregarActivity.class);
            startActivity(agregar);
            finish();
        }
    }


}

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }*/