package com.ni.avalon.model;

public class NavCategoryDetailedModel {
    String nombre;
    String tipo;
    String precio;
    String img_url;

    public NavCategoryDetailedModel() {
    }

    public NavCategoryDetailedModel(String nombre, String tipo, String precio, String img_url) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.img_url = img_url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
