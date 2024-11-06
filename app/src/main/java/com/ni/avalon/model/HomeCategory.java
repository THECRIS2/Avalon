package com.ni.avalon.model;

public class HomeCategory {
    String nombre;
    String tipo;
    String url_img;


    public HomeCategory() {
    }

    public HomeCategory(String nombre, String tipo, String url_img) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.url_img = url_img;
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

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }
}

