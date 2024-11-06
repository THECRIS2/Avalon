package com.ni.avalon.model;

public class RecommendedModel {
    String nombre;
    String precio;
    String rating;
    String descripcion;
    String img_url;

    public RecommendedModel() {
    }

    public RecommendedModel(String nombre, String precio, String rating, String descripcion, String img_url) {
        this.nombre = nombre;
        this.precio = precio;
        this.rating = rating;
        this.descripcion = descripcion;
        this.img_url = img_url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
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
}
