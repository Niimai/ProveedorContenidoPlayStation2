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
import com.example.skywalker.proveedorcontenidoplaystation2.pojos.Bitacora;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Skywalker on 04/03/2018.
 */

public class BitacoraProveedor {
    static public void insert(ContentResolver resolvedor, Bitacora bitacora){
        Uri uri = Contrato.Bitacora.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contrato.Bitacora.ID_JUEGO, bitacora.getID_juego());
        values.put(Contrato.Bitacora.OPERACION, bitacora.getOperacion());

        Uri uriResultado = resolvedor.insert(uri, values);

        //String bitacoraId = uriResultado.getLastPathSegment();

        /*if(Bitacora.getImagen()!=null){
            try {
                Utilidades.storeImage(Bitacora.getImagen(), contexto, "img_" + bitacoraId + ".jpg");
            } catch (IOException e) {
                Toast.makeText(contexto,"No se pudo guardar la imagen", Toast.LENGTH_LONG).show();
            }
        }*/
    }
    

    static public void delete(ContentResolver resolver, int bitacoraId){
        Uri uri = Uri.parse(Contrato.Bitacora.CONTENT_URI + "/" + bitacoraId);
        resolver.delete(uri, null, null);
    }

    static public void update(ContentResolver resolver, Bitacora bitacora, Context contexto){
        Uri uri = Uri.parse(Contrato.Bitacora.CONTENT_URI + "/" + bitacora.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.Bitacora.ID_JUEGO, bitacora.getID_juego());
        values.put(Contrato.Bitacora.OPERACION, bitacora.getOperacion());

        resolver.update(uri, values, null, null);

        /*if(Bitacora.getImagen()!=null){
            try {
                Utilidades.storeImage(Bitacora.getImagen(), contexto, "img_" + Bitacora.getID() + ".jpg");
            } catch (IOException e) {
                Toast.makeText(contexto,"No se pudo guardar la imagen", Toast.LENGTH_LONG).show();
            }
        }*/
    }

    static public Bitacora read(ContentResolver resolver, int bitacoraId) {
        Uri uri = Uri.parse(Contrato.Bitacora.CONTENT_URI + "/" + bitacoraId);

        String[] projection = {Contrato.Bitacora._ID,
                Contrato.Bitacora.ID_JUEGO,
                Contrato.Bitacora.OPERACION};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()){
            Bitacora Bitacora = new Bitacora();

            Bitacora.setID(cursor.getInt(cursor.getColumnIndex(Contrato.Bitacora._ID)));
            Bitacora.setID_juego(cursor.getInt(cursor.getColumnIndex(Contrato.Bitacora.ID_JUEGO)));
            Bitacora.setOperacion(cursor.getInt(cursor.getColumnIndex(Contrato.Bitacora.OPERACION)));

            return Bitacora;
        }

        return null;

    }

    static public ArrayList<Bitacora> readAll(ContentResolver resolver) {
        Uri uri = Contrato.Bitacora.CONTENT_URI;

        String[] projection = {Contrato.Bitacora._ID,
                Contrato.Bitacora.ID_JUEGO,
                Contrato.Bitacora.OPERACION};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        ArrayList<Bitacora> bitacoras = new ArrayList<>();
        Bitacora bitacora;

        while (cursor.moveToNext()){
            bitacora = new Bitacora();

            bitacora.setID(cursor.getInt(cursor.getColumnIndex(Contrato.Bitacora._ID)));
            bitacora.setID_juego(cursor.getInt(cursor.getColumnIndex(Contrato.Bitacora.ID_JUEGO)));
            bitacora.setOperacion(cursor.getInt(cursor.getColumnIndex(Contrato.Bitacora.OPERACION)));

            bitacoras.add(bitacora);
        }
        cursor.close();
        return bitacoras;

    }
}
