package com.umaee.basedatosumaee;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class AgregarActivity extends AppCompatActivity implements View.OnClickListener {
    Bdadmin bdadmin;
    EditText txtnombre;
    EditText txtmateria;
    Button btnGuardar;
    Spinner cmbcategorias;
    SimpleCursorAdapter adapter;

    Cursor cursor;
    Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_main);
        bdadmin = new Bdadmin(this);

        txtnombre = (EditText) findViewById(R.id.txtNAlumno);
        txtmateria = (EditText) findViewById(R.id.editTextText2);
        btnGuardar = (Button) findViewById(R.id.btnactualizar);
        cmbcategorias = (Spinner) findViewById(R.id.cmbcategorias);
        btnGuardar.setOnClickListener(this);

        final String[] from = new String[]{Bdadmin.NOMBRE_MATERIAS};
        int[] to = new int[]{android.R.id.text1};
        cursor = bdadmin.cargarMaterias();
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor, from, to, 0);
        cmbcategorias.setAdapter(adapter);

        ImageView imagen = (ImageView) findViewById(R.id.imageView);
        imagen.setImageResource(R.drawable.alumno);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnactualizar) {
            position = cmbcategorias.getSelectedItemPosition();
            cursor.moveToPosition(position);
            bdadmin.insertarAlumno(txtnombre.getText().toString(), cursor.getString(0));
            txtnombre.setText("");
            txtmateria.setText("");
            finish();
        }
    }
}
