package com.example.gestiondeempleados;
/*
    JUAN DIEGO ZAPATEIRO CARABALI
    CHRISTIAN DAVID LUCIO REYES
 */
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void empresas (View view){
        Intent empresa = new Intent(this, Empresas.class);
        startActivity(empresa);
    }
    public void empleados (View view){
        Intent empleados = new Intent(this, Empleados.class);
        startActivity(empleados);
    }
}