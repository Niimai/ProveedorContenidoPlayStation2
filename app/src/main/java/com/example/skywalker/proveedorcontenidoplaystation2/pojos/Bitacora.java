package com.example.skywalker.proveedorcontenidoplaystation2.pojos;

import com.example.skywalker.proveedorcontenidoplaystation2.constantes.G;

/**
 * Created by Skywalker on 11/03/2018.
 */

public class Bitacora {
    int ID;
    int ID_juego;
    int operacion;

    public Bitacora() {
        this.ID = G.SIN_VALOR_INT;
        this.ID_juego = G.SIN_VALOR_INT;
        this.operacion = G.SIN_VALOR_INT;
    }

    public Bitacora(int ID, int ID_juego, int operacion) {
        this.ID = ID;
        this.ID_juego = ID_juego;
        this.operacion = operacion;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_juego() {
        return ID_juego;
    }

    public void setID_juego(int ID_juego) {
        this.ID_juego = ID_juego;
    }

    public int getOperacion() {
        return operacion;
    }

    public void setOperacion(int operacion) {
        this.operacion = operacion;
    }
}
