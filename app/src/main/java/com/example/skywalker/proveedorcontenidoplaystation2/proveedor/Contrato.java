package com.example.skywalker.proveedorcontenidoplaystation2.proveedor;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Skywalker on 04/03/2018.
 */

public class Contrato {
    public static final String AUTHORITY = "com.example.skywalker.proveedorcontenidoplaystation2.proveedor.ProveedorDeContenido";

    public static final class PS2 implements BaseColumns {

        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Playstation2");

        // Table column
        public static final String NOMBRE = "Nombre";
        public static final String ABREVIATURA = "Abreviatura";
    }

    public static final class Bitacora implements BaseColumns {

        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Bitacora");

        // Table column
        public static final String ID_JUEGO = "ID_Juego";
        public static final String OPERACION = "Operacion";
    }
}
