package com.ni.avalon.model;

public class OrderModel {
    String cantTotal;
    String currentDate;
    String currentTime;
    String precioTotal;
    String productNombre;
    String productPrecio;
    String documentid;

    public OrderModel() {
    }

    public OrderModel(String cantTotal, String currentDate, String currentTime, String precioTotal, String productNombre, String productPrecio, String documentid) {
        this.cantTotal = cantTotal;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.precioTotal = precioTotal;
        this.productNombre = productNombre;
        this.productPrecio = productPrecio;
        this.documentid = documentid;
    }

    public String getCantTotal() {
        return cantTotal;
    }

    public void setCantTotal(String cantTotal) {
        this.cantTotal = cantTotal;
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

    public String getProductNombre() {
        return productNombre;
    }

    public void setProductNombre(String productNombre) {
        this.productNombre = productNombre;
    }

    public String getDocumentid() {
        return documentid;
    }

    public void setDocumentid(String documentid) {
        this.documentid = documentid;
    }

    public String getProductPrecio() {
        return productPrecio;
    }

    public void setProductPrecio(String productPrecio) {
        this.productPrecio = productPrecio;
    }
}
