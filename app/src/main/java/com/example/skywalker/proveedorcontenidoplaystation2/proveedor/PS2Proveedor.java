package com.example.skywalker.proveedorcontenidoplaystation2.proveedor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.example.skywalker.proveedorcontenidoplaystation2.constantes.Utilidades;
import com.example.skywalker.proveedorcontenidoplaystation2.pojos.PS2;

import java.io.IOException;

/**
 * Created by Skywalker on 04/03/2018.
 */

public class PS2Proveedor {
    static public void insert(ContentResolver resolvedor, PS2 ciclo, Context contexto){
        Uri uri = Contrato.PS2.CONTENT_URI;

        ContentValues values = new ContentValues();
        values.put(Contrato.PS2.NOMBRE, ciclo.getNombre());
        values.put(Contrato.PS2.ABREVIATURA, ciclo.getAbreviatura());

        Uri uriResultado = resolvedor.insert(uri, values);

        String cicloId = uriResultado.getLastPathSegment();

        if(ciclo.getImagen()!=null){
            try {
                Utilidades.storeImage(ciclo.getImagen(), contexto, "img_" + cicloId + ".jpg");
            } catch (IOException e) {
                Toast.makeText(contexto,"No se pudo guardar la imagen", Toast.LENGTH_LONG).show();
            }
        }
    }

    static public void delete(ContentResolver resolver, int cicloId){
        Uri uri = Uri.parse(Contrato.PS2.CONTENT_URI + "/" + cicloId);
        resolver.delete(uri, null, null);
    }

    static public void update(ContentResolver resolver, PS2 ciclo, Context contexto){
        Uri uri = Uri.parse(Contrato.PS2.CONTENT_URI + "/" + ciclo.getID());

        ContentValues values = new ContentValues();
        values.put(Contrato.PS2.NOMBRE, ciclo.getNombre());
        values.put(Contrato.PS2.ABREVIATURA, ciclo.getAbreviatura());

        resolver.update(uri, values, null, null);

        if(ciclo.getImagen()!=null){
            try {
                Utilidades.storeImage(ciclo.getImagen(), contexto, "img_" + ciclo.getID() + ".jpg");
            } catch (IOException e) {
                Toast.makeText(contexto,"No se pudo guardar la imagen", Toast.LENGTH_LONG).show();
            }
        }
    }

    static public PS2 read(ContentResolver resolver, int cicloId) {
        Uri uri = Uri.parse(Contrato.PS2.CONTENT_URI + "/" + cicloId);

        String[] projection = {Contrato.PS2._ID,
                Contrato.PS2.NOMBRE,
                Contrato.PS2.ABREVIATURA};

        Cursor cursor = resolver.query(uri, projection, null, null, null);

        if (cursor.moveToFirst()){
            PS2 ciclo = new PS2();
            ciclo.setID(cursor.getInt(cursor.getColumnIndex(Contrato.PS2._ID)));
            ciclo.setNombre(cursor.getString(cursor.getColumnIndex(Contrato.PS2.NOMBRE)));
            ciclo.setAbreviatura(cursor.getString(cursor.getColumnIndex(Contrato.PS2.ABREVIATURA)));
            return ciclo;
        }

        return null;

    }
}
