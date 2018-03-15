package com.example.skywalker.proveedorcontenidoplaystation2.pojos;

import android.graphics.Bitmap;

import com.example.skywalker.proveedorcontenidoplaystation2.constantes.G;

/**
 * Created by Skywalker on 04/03/2018.
 */

public class PS2 {
    private int ID;
    private String nombre;
    private String abreviatura;
    private Bitmap imagen;

    public PS2(){
        this.ID = G.SIN_VALOR_INT;
        this.nombre = G.SIN_VALOR_STRING;
        this.abreviatura = G.SIN_VALOR_STRING;
        //this.setImagen(null);
    }

    public PS2(int ID, String nombre, String abreviatura) {
        this.ID = ID;
        this.nombre = nombre;
        this.abreviatura = abreviatura;
      //this.imagen = imagen;
    }

    public PS2(int ps2Id, String nombre, String abreviatura, Bitmap imagen) {
        this.ID = ps2Id;
        this.nombre = nombre;
        this.abreviatura = abreviatura;
        this.imagen = imagen;
    }

    public Bitmap getImagen() {return imagen;}

    public void setImagen(Bitmap imagen) {this.imagen = imagen;}

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }
}
