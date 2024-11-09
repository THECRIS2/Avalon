package com.ni.avalon.model;

public class PopularModel {
    // tienen que tener los mismos nombres que los campos de la bd de firebase
    String nombre;
    String descripcion;
    String raiting;
    String descuento;
    String tipo;
    String img_url;

    // generamos un constructor para nuestra clase
    public PopularModel() {
    }

    // generamos un constructor para nuestros datos

    public PopularModel(String nombre, String descripcion, String raiting, String descuento, String tipo, String img_url) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.raiting = raiting;
        this.descuento = descuento;
        this.tipo = tipo;
        this.img_url = img_url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRaiting() {
        return raiting;
    }

    public void setRaiting(String raiting) {
        this.raiting = raiting;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
