package com.example.gestiondeempleados;

import com.example.gestiondeempleados.App.Empresa;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Empresas extends AppCompatActivity {

    private List <Empresa> listEmpresa = new ArrayList<>();
    ArrayAdapter<Empresa> arrayAdapter;

    EditText nombreEmpresa;
    EditText nitEmpresa;
    EditText telefonoEmpresa;
    EditText direccionEmpresa;
    ListView listaEmpresa;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Empresa empresaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresas);

        nombreEmpresa = findViewById(R.id.edtNombreEmpresa);
        nitEmpresa = findViewById(R.id.edtNitEmpresa);
        telefonoEmpresa = findViewById(R.id.edtTelefonoEmpresa);
        direccionEmpresa = findViewById(R.id.edtDirecionEmpresa);

        listaEmpresa = findViewById(R.id.listaEmpresa);
        inicializarFireBase();
        listarDatos ();

        listaEmpresa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                empresaSelecionada = (Empresa) parent.getItemAtPosition(position);
                nombreEmpresa.setText(empresaSelecionada.getNombreEmpresa());
                nitEmpresa.setText(empresaSelecionada.getNitEmpresa());
                telefonoEmpresa.setText(empresaSelecionada.getTelefonoEmpresa());
                direccionEmpresa.setText(empresaSelecionada.getDireccionEmpresa());
            }
        });

        //REALIZAMOS LA FLECHA ATRAS
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //METODO PARA MOSTRAR DATOS EN EL LISTVIEW
    private void listarDatos() {

        databaseReference.child("Empresa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           listEmpresa.clear();

           for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
               Empresa e = objSnapshot.getValue(Empresa.class);
               listEmpresa.add(e);

               ArrayAdapter <Empresa> arrayAdapterEmpresa = new ArrayAdapter<>
                       (Empresas.this, android.R.layout.simple_list_item_1, listEmpresa);
                    listaEmpresa.setAdapter(arrayAdapterEmpresa);
           }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void inicializarFireBase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    //CON ESTE ONCREATE VISUALIZAMOS LOS ICONOS CREADOS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empresa,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //CREAMOS EL METODO PARA LA FLECHA ATRAS
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String nombre = nombreEmpresa.getText().toString();
        String nit = nitEmpresa.getText().toString();
        String telefono = telefonoEmpresa.getText().toString();
        String direccion = direccionEmpresa.getText().toString();

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return super.onOptionsItemSelected(item);
            //PARA EL ICONO AGREGAR
            case R.id.icon_agregar:{
                if (nombre.equals("")||nit.equals("")||telefono.equals("")||direccion.equals("")){
                    validacion();
                }
                else {
                    Empresa e = new Empresa();
                    e.setNombreEmpresa(nombre);
                    e.setNitEmpresa(nit);
                    e.setTelefonoEmpresa(telefono);
                    e.setDireccionEmpresa(direccion);
                    databaseReference.child("Empresa").child(e.getNitEmpresa()).setValue(e);

                    Toast.makeText(this, "Empresa Agregada", Toast.LENGTH_LONG).show();
                    limpiar();
                }
                break;
            }
            //PARA EL ICONO Actualizar
            case R.id.icon_guardar:{

                Empresa e = new Empresa();

                e.setNitEmpresa(empresaSelecionada.getNitEmpresa());

                e.setNombreEmpresa(nombreEmpresa.getText().toString().trim());
                e.setTelefonoEmpresa(telefonoEmpresa.getText().toString().trim());
                e.setDireccionEmpresa(direccionEmpresa.getText().toString().trim());
                databaseReference.child("Empresa").child(e.getNitEmpresa()).setValue(e);

                Toast.makeText(this, "Empresa Actualizada ", Toast.LENGTH_LONG).show();
                limpiar();
                break;
            }
            //PARA EL ICONO Eliminar
            case R.id.icon_eliminar:{

                Empresa e = new Empresa();
                e.setNitEmpresa(empresaSelecionada.getNitEmpresa());
                databaseReference.child("Empresa").child(e.getNitEmpresa()).removeValue();
                Toast.makeText(this, "Empresa Eliminada", Toast.LENGTH_LONG).show();
                limpiar();
                break;
            }
            default:break;
        }
        return true;
    }

    private void limpiar() {
        nombreEmpresa.setText("");
        nitEmpresa.setText("");
        telefonoEmpresa.setText("");
        direccionEmpresa.setText("");
    }

    //VALIDAMOS LOS DATOS DEL USUARIO
    private void validacion() {

        String nombre = nombreEmpresa.getText().toString();
        String nit = nitEmpresa.getText().toString();
        String telefono = telefonoEmpresa.getText().toString();
        String direccion = direccionEmpresa.getText().toString();

        if (nombre.equals("")){
            nombreEmpresa.setError("Requerido");
        }
        else if (nit.equals("")){
            nitEmpresa.setError("Requerido");
        }
        else if (telefono.equals("")){
            telefonoEmpresa.setError("Requerido");
        }
        else if (direccion.equals("")){
            direccionEmpresa.setError("Requerido");
        }
    }
}