package com.ni.avalon.model;

public class NavCategoryModel {
    String nombre;
    String descripcion;
    String descuento;
    String img_url;

    public NavCategoryModel() {
    }

    public NavCategoryModel(String nombre, String descripcion, String descuento, String img_url) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.descuento = descuento;
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

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }
}
