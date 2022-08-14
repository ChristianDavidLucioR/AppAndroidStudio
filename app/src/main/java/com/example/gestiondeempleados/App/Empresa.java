package com.example.gestiondeempleados.App;

public class Empresa {

    private String nombreEmpresa;
    private String nitEmpresa;
    private String telefonoEmpresa;
    private String direccionEmpresa;

    public Empresa() {
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNitEmpresa() {
        return nitEmpresa;
    }

    public void setNitEmpresa(String nitEmpresa) {
        this.nitEmpresa = nitEmpresa;
    }

    public String getTelefonoEmpresa() {
        return telefonoEmpresa;
    }

    public void setTelefonoEmpresa(String telefonoEmpresa) {
        this.telefonoEmpresa = telefonoEmpresa;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    @Override
    public String toString() {
        return "Nombre: "+nombreEmpresa + " Nit: " + nitEmpresa +
                "\nTelefono: "+telefonoEmpresa + " Dirección: "+direccionEmpresa;
    }
}
