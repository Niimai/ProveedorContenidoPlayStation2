package com.example.skywalker.proveedorcontenidoplaystation2.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.example.skywalker.proveedorcontenidoplaystation2.constantes.G;
import com.example.skywalker.proveedorcontenidoplaystation2.constantes.Utilidades;
import com.example.skywalker.proveedorcontenidoplaystation2.pojos.Bitacora;
import com.example.skywalker.proveedorcontenidoplaystation2.pojos.PS2;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Skywalker on 04/03/2018.
 */

public class PS2Proveedor {
    static public Uri insertRecord(ContentResolver resolvedor, PS2 juego){
        Uri uri = Contrato.PS2.CONTENT_URI;

        ContentValues values = new ContentValues();
        //values.put(Contrato.PS2.NOMBRE, juego.getNombre());
        //values.put(Contrato.PS2.ABREVIATURA, juego.getAbreviatura());

        values.put(Contrato.PS2.NOMBRE, juego.getNombre());
        values.put(Contrato.PS2.ABREVIATURA, juego.getAbreviatura());

        Uri uriResultado = resolvedor.insert(uri, values);

        return uriResultado;

        //return resolvedor.insert(uri, values);

        //String juegoId = uriResultado.getLastPathSegment();

        /*if(juego.getImagen()!=null){
            try {
                Utilidades.storeImage(juego.getImagen(), contexto, "img_" + juegoId + ".jpg");
            } catch (IOException e) {
                Toast.makeText(contexto,"No se pudo guardar la imagen", Toast.LENGTH_LONG).show();
            }
        }*/
    }

    static public void insertConBitacora (ContentResolver resolvedor, PS2 juego) {
        Uri uri = insertRecord(resolvedor, juego);
        juego.setID(Integer.parseInt(uri.getLastPathSegment()));

        Bitacora bitacora = new Bitacora();
        bitacora.setID_juego(juego.getID());
        bitacora.setOperacion(G.OPERACION_INSERTAR);

        BitacoraProveedor.insert(resolvedor, bitacora);
    }

    static public void deleteRecord(ContentResolver resolver, int juegoId){
        Uri uri = Uri.parse(Contrato.PS2.CONTENT_URI + "/" + juegoId);
        resolver.delete(uri, null, null);
    }

    static public void deleteConBitacora (ContentResolver resolvedor, int juegoId) {
        deleteRecord(resolvedor, juegoId);

        Bitacora bitacora = new Bitacora();
        bitacora.setID_juego(juegoId);
        bitacora.setOperacion(G.OPERACION_BORRAR);

        BitacoraProveedor.insert(resolvedor, bitacora);
    }

    static public void updateRecord(ContentResolver resolver, PS2 juego){
        Uri uri = Uri.parse(Contrato.PS2.CONTENT_URI + "/" + juego.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.PS2.NOMBRE, juego.getNombre());
        values.put(Contrato.PS2.ABREVIATURA, juego.getAbreviatura());

        resolver.update(uri, values, null, null);

    /*    if(juego.getImagen()!=null){
            try {
                Utilidades.storeImage(juego.getImagen(), contexto, "img_" + juego.getID() + ".jpg");
            } catch (IOException e) {
                Toast.makeText(contexto,"No se pudo guardar la imagen", Toast.LENGTH_LONG).show();
            }
        } */
    }

    static public void updateConBitacora (ContentResolver resolvedor, PS2 juego) {
        updateRecord(resolvedor, juego);


        Bitacora bitacora = new Bitacora();
        bitacora.setID_juego(juego.getID());
        bitacora.setOperacion(G.OPERACION_MODIFICAR);

        BitacoraProveedor.insert(resolvedor, bitacora);
    }

    static public PS2 read(ContentResolver resolver, int juegoId) {
        Uri uri = Uri.parse(Contrato.PS2.CONTENT_URI + "/" + juegoId);

        String[] projection = {Contrato.PS2._ID,
                Contrato.PS2.NOMBRE,
                Contrato.PS2.ABREVIATURA};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()){
            PS2 juego = new PS2();
            juego.setID(cursor.getInt(cursor.getColumnIndex(Contrato.PS2._ID)));
            juego.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.PS2.NOMBRE)));
            juego.setAbreviatura(cursor.getString(cursor.getColumnIndex(Contrato.PS2.ABREVIATURA)));
            return juego;
        }

        return null;

    }

    static public ArrayList<PS2> readAll(ContentResolver resolver) {
        Uri uri = Contrato.PS2.CONTENT_URI;

        String[] projection = {
                Contrato.PS2._ID,
                Contrato.PS2.NOMBRE,
                Contrato.PS2.ABREVIATURA};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        ArrayList<PS2> juegos = new ArrayList<>();
        PS2 juego;

        while (cursor.moveToNext()){
            juego = new PS2();

            juego.setID(cursor.getInt(cursor.getColumnIndex(Contrato.PS2._ID)));
            juego.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.PS2.NOMBRE)));
            juego.setAbreviatura(cursor.getString(cursor.getColumnIndex(Contrato.PS2.ABREVIATURA)));

            juegos.add(juego);
        }

        return juegos;

    }
}
