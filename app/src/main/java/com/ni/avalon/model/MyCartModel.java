package com.ni.avalon.model;

import java.io.Serializable;

public class MyCartModel implements Serializable {
    String productNombre;
    String productPrecio;
    String currentDate;
    String currentTime;
    String precioTotal;
    String cantTotal;
    String documentid;

    public MyCartModel() {
    }

    public MyCartModel(String productNombre, String productPrecio, String currentDate, String currentTime, String precioTotal, String cantTotal) {
        this.productNombre = productNombre;
        this.productPrecio = productPrecio;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.precioTotal = precioTotal;
        this.cantTotal = cantTotal;
    }

    public String getDocumentid() {
        return documentid;
    }

    public void setDocumentid(String documentid) {
        this.documentid = documentid;
    }

    public String getProductNombre() {
        return productNombre;
    }

    public void setProductNombre(String productNombre) {
        this.productNombre = productNombre;
    }

    public String getProductPrecio() {
        return productPrecio;
    }

    public void setProductPrecio(String productPrecio) {
        this.productPrecio = productPrecio;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(String precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getCantTotal() {
        return cantTotal;
    }

    public void setCantTotal(String cantTotal) {
        this.cantTotal = cantTotal;
    }
}
