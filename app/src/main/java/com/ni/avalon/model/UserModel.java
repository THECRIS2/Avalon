package com.ni.avalon.model;

public class UserModel {
    protected String nombre;
    protected String apellido;
    protected String fnacimiento;
    protected String correo;
    protected String contraseña;

    public UserModel() {
    }

    public UserModel(String nombre, String apellido, String fnacimiento, String correo, String contraseña) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fnacimiento = fnacimiento;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFnacimiento() {
        return fnacimiento;
    }

    public void setFnacimiento(String fnacimiento) {
        this.fnacimiento = fnacimiento;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
