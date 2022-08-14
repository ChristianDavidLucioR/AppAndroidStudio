package com.example.gestiondeempleados;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gestiondeempleados.App.Empleado;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Empleados extends AppCompatActivity {

    private List<Empleado> listEmpleado = new ArrayList<Empleado>();
    ArrayAdapter <Empleado> arrayAdapterEmpleado;


    EditText nomE;
    EditText celularE;
    EditText empresaEmpleado;
    EditText idEmpleado;

    ListView lv_listaEmpleado;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Empleado empleadoSeleccionado;

    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_empleados);


        nomE = findViewById(R.id.edtNombreEmpleado);
        celularE = findViewById(R.id.edtCelularEmpleado);
        empresaEmpleado = findViewById(R.id.edtEmpresaEmpleado);
        idEmpleado = findViewById(R.id.edtIdEmpleado);

        lv_listaEmpleado = findViewById(R.id.listaEmpleado);
        comenzarFireBase();
        mostraDatos();

        lv_listaEmpleado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                empleadoSeleccionado = (Empleado) parent.getItemAtPosition(position);

                nomE.setText(empleadoSeleccionado.getNombre());
                celularE.setText(empleadoSeleccionado.getCelular());
                empresaEmpleado.setText(empleadoSeleccionado.getEmpresaEmpleado());
                idEmpleado.setText(empleadoSeleccionado.getIdEmpleado());
            }
        });

        //REALIZAMOS LA FLECHA ATRAS
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void mostraDatos() {
        databaseReference.child("Empleado").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listEmpleado.clear();
                for (DataSnapshot objeSnapshot : dataSnapshot.getChildren()){
                    Empleado m = objeSnapshot.getValue(Empleado.class);
                    listEmpleado.add(m);

                    arrayAdapterEmpleado = new ArrayAdapter<Empleado>
                            (Empleados.this, android.R.layout.simple_list_item_1,listEmpleado);
                    lv_listaEmpleado.setAdapter(arrayAdapterEmpleado);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void comenzarFireBase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empleados, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String nombre = nomE.getText().toString();
        String idE = idEmpleado.getText().toString();
        String empresaE = empresaEmpleado.getText().toString();
        String celular = celularE.getText().toString();

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return super.onOptionsItemSelected(item);
            //Icono Agreagar Empleado
            case R.id.icon_agregarE: {

                if (nombre.equals("") || celular.equals("") || empresaE.equals("") || idE.equals("")){
                    validarDatos();
                }else {

                    Empleado m = new Empleado();

                    m.setNombre(nombre);
                    m.setCelular(celular);
                    m.setEmpresaEmpleado(empresaE);
                    m.setIdEmpleado(idE);

                    databaseReference.child("Empleado").child(m.getIdEmpleado()).setValue(m);
                    Toast.makeText(this, "Empleado Agregado", Toast.LENGTH_LONG).show();
                    borrarDatos();
                }
                break;
                }
            //Icono Actualizar
            case R.id.icon_guardarE: {

                Empleado m = new Empleado();
                m.setIdEmpleado(empleadoSeleccionado.getIdEmpleado());

                m.setNombre(nomE.getText().toString().trim());
                m.setCelular(celularE.getText().toString().trim());
                m.setEmpresaEmpleado(celularE.getText().toString().trim());
                m.setIdEmpleado(idEmpleado.getText().toString().trim());
                databaseReference.child("Empleado").child(m.getIdEmpleado()).setValue(m);

                Toast.makeText(this, "Empleado Actualizado", Toast.LENGTH_SHORT).show();
                borrarDatos();
                break;
            }
            //Icono Eliminar
            case R.id.icon_eliminarE: {

                Empleado m = new Empleado();
                m.setIdEmpleado(empleadoSeleccionado.getIdEmpleado());
                databaseReference.child("Empleado").child(m.getIdEmpleado()).removeValue();

                Toast.makeText(this, "Empleado Eliminado", Toast.LENGTH_LONG).show();
                borrarDatos();
                break;
            }
            default:break;
        }
        return true;
    }

    private void borrarDatos() {

        nomE.setText("");
        celularE.setText("");
        empresaEmpleado.setText("");
        idEmpleado.setText("");
    }

    private void validarDatos() {

        String nombre = nomE.getText().toString();
        String celular = celularE.getText().toString();
        String empresaE = empresaEmpleado.getText().toString();
        String idE = idEmpleado.getText().toString();

        if (nombre.equals("")) {
            nomE.setError("Requerido");

        }else if (celular.equals("")) {
            celularE.setError("Requerido");

        }else if (empresaE.equals("")){
            empresaEmpleado.setError("Requerido");

        }else if (idE.equals("")){
            idEmpleado.setError("Requerido");
        }
    }
}
