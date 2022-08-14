package com.example.gestiondeempleados.App;

public class Empleado {

    private String Nombre;
    private String Celular;
    private String EmpresaEmpleado;
    private String idEmpleado;

    public Empleado() {

    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }

    public String getEmpresaEmpleado() {
        return EmpresaEmpleado;
    }

    public void setEmpresaEmpleado(String empresaEmpleado) {
        EmpresaEmpleado = empresaEmpleado;
    }

    @Override
    public String toString() {
        return "Nombre: "+Nombre+ " Celular: "+Celular+
                "\nEmpresa: " +EmpresaEmpleado+ " ID: "+idEmpleado;
    }
}
