package com.example.skywalker.proveedorcontenidoplaystation2.sync;

import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;

import com.example.skywalker.proveedorcontenidoplaystation2.constantes.G;
import com.example.skywalker.proveedorcontenidoplaystation2.pojos.Bitacora;
import com.example.skywalker.proveedorcontenidoplaystation2.pojos.PS2;
import com.example.skywalker.proveedorcontenidoplaystation2.proveedor.BitacoraProveedor;
import com.example.skywalker.proveedorcontenidoplaystation2.proveedor.PS2Proveedor;
import com.example.skywalker.proveedorcontenidoplaystation2.volley.PS2Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Sincronizacion {
    private static final String LOGTAG = "PartesOnline - Sincronizacion";
    private static ContentResolver resolvedor;
    private static Context contexto;
    private static boolean esperandoRespuestaDeServidor = false;

    public Sincronizacion(Context contexto){
        this.resolvedor = contexto.getContentResolver();
        this.contexto = contexto;
        recibirActualizacionesDelServidor(); //La primera vez se cargan los datos siempre
    }

    public synchronized static boolean isEsperandoRespuestaDeServidor() {
        return esperandoRespuestaDeServidor;
    }

    public synchronized static void setEsperandoRespuestaDeServidor(boolean esperandoRespuestaDeServidor) {
        Sincronizacion.esperandoRespuestaDeServidor = esperandoRespuestaDeServidor;
    }

    public synchronized boolean sincronizar(){
        Log.i("sincronizacion","SINCRONIZAR");

        if(isEsperandoRespuestaDeServidor()){
            return true;
        }

        if(G.VERSION_ADMINISTRADOR){
            enviarActualizacionesAlServidor();
            recibirActualizacionesDelServidor();
        } else {
            recibirActualizacionesDelServidor();
        }

        return true;
    }



    private static void enviarActualizacionesAlServidor(){
        ArrayList<Bitacora> registrosBitacora = BitacoraProveedor.readAll(resolvedor);
        for(Bitacora bitacora : registrosBitacora){

            switch(bitacora.getOperacion()){
                case G.OPERACION_INSERTAR:
                    PS2 juego = null;
                    try {
                        juego = PS2Proveedor.read(resolvedor, bitacora.getID_juego());
                        ParteVolley.addJuego(juego, true, bitacora.getID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case G.OPERACION_MODIFICAR:
                    try {
                        juego = PS2Proveedor.read(resolvedor, bitacora.getID_juego());
                        ParteVolley.updateParte(juego, true, bitacora.getID());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case G.OPERACION_BORRAR:
                    ParteVolley.delParte(bitacora.getID_juego(), true, bitacora.getID());
                    break;
            }
            Log.i("sincronizacion", "acabo de enviar");
        }
    }

    private static void recibirActualizacionesDelServidor(){
        ParteVolley.getAllParte();
    }

    public static void realizarActualizacionesDelServidorUnaVezRecibidas(JSONArray jsonArray){
        Log.i("sincronizacion", "recibirActualizacionesDelServidor");

        try {
            ArrayList<Integer> identificadoresDeRegistrosActualizados = new ArrayList<Integer>();
            ArrayList<juego> registrosNuevos = new ArrayList<>();
            ArrayList<Parte> registrosViejos = PS2Proveedor.readAll(resolvedor);
            ArrayList<Integer> identificadoresDeRegistrosViejos = new ArrayList<Integer>();
            for(Parte i : registrosViejos) identificadoresDeRegistrosViejos.add(i.getID());

            JSONObject obj = null;
            for (int i = 0; i < jsonArray.length(); i++ ){
                obj = jsonArray.getJSONObject(i);
                registrosNuevos.add(new Parte(obj.getInt("PK_ID"), obj.getString("fecha"), obj.getString("cliente"), obj.getString("motivo"), obj.getString("resolucion")));
            }

            for(PS2 juego: registrosNuevos) {
                try {
                    if(identificadoresDeRegistrosViejos.contains(juego.getID())) {
                        PS2Proveedor.updateRecord(resolvedor, juego);
                    } else {
                        PS2Proveedor.insertRecord(resolvedor, juego);
                    }
                    identificadoresDeRegistrosActualizados.add(juego.getID());
                } catch (Exception e){
                    Log.i("sincronizacion",
                            "Probablemente el registro ya existía en la BD."+"" +
                                    " Esto se podría controlar mejor con una Bitácora.");
                }
            }

            for(PS2 juego: registrosViejos){
                if(!identificadoresDeRegistrosActualizados.contains(juego.getID())){
                    try {
                        PS2Proveedor.deleteRecord(resolvedor, juego.getID());
                    }catch(Exception e){
                        Log.i("sincronizacion", "Error al borrar el registro con id:" + juego.getID());
                    }
                }
            }

           // ParteVolley.getAllParte(); //Los baja y los guarda en SQLite
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
