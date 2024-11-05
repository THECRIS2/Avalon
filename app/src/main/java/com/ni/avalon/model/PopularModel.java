package com.ni.avalon.model;

public class PopularModel {
    // tienen que tener los mismos nombres que los campos de la bd de firebase
    String NombreP;
    String DescripcionP;
    String RaitingP;
    String DescuentoP;
    String TipoP;
    String Img_UrlP;

    // generamos un constructor para nuestra clase
    public PopularModel() {
    }

    // generamos un constructor para nuestros datos
    public PopularModel(String nombreP, String descripcionP, String raitingP, String descuentoP, String tipoP, String img_UrlP) {
        NombreP = nombreP;
        DescripcionP = descripcionP;
        RaitingP = raitingP;
        DescuentoP = descuentoP;
        TipoP = tipoP;
        Img_UrlP = img_UrlP;
    }

    public String getNombreP() {
        return NombreP;
    }

    public void setNombreP(String nombreP) {
        NombreP = nombreP;
    }

    public String getDescripcionP() {
        return DescripcionP;
    }

    public void setDescripcionP(String descripcionP) {
        DescripcionP = descripcionP;
    }

    public String getRaitingP() {
        return RaitingP;
    }

    public void setRaitingP(String raitingP) {
        RaitingP = raitingP;
    }

    public String getDescuentoP() {
        return DescuentoP;
    }

    public void setDescuentoP(String descuentoP) {
        DescuentoP = descuentoP;
    }

    public String getTipoP() {
        return TipoP;
    }

    public void setTipoP(String tipoP) {
        TipoP = tipoP;
    }

    public String getImg_UrlP() {
        return Img_UrlP;
    }

    public void setImg_UrlP(String img_UrlP) {
        Img_UrlP = img_UrlP;
    }
}
