package com.petama.es.appMercadonaExamen.util;

import static com.petama.es.appMercadonaExamen.common.GlobalConstant.CODIGO_DESTINO_ALMACENES;
import static com.petama.es.appMercadonaExamen.common.GlobalConstant.CODIGO_DESTINO_COLMENAS;
import static com.petama.es.appMercadonaExamen.common.GlobalConstant.CODIGO_DESTINO_OFICINAS_MERCADONA;
import static com.petama.es.appMercadonaExamen.common.GlobalConstant.CODIGO_DESTINO_TIENDAS_ESPAÑA;
import static com.petama.es.appMercadonaExamen.common.GlobalConstant.CODIGO_DESTINO_TIENDAS_PORTUGAL;
import static java.util.Objects.isNull;

public class BDUtil {
    public static String getLike(String str) {
        if (isNull(str)) {
            str = "";
        }
        return "%" + str + "%";
    }
    public static String getFormat(String valor, int longitud) {
    	String formatNumero = "%0"+longitud+"d";
        if (!isNull(valor)) {
        	valor = String.format(formatNumero, Integer.parseInt(valor));
        }
        return valor;
    }
    public static long getDestinoId(int tienda) {
    	long destinoId=0;
    	switch(tienda) {
        case 0:
      	  destinoId = CODIGO_DESTINO_COLMENAS;break; 
        case 1:
        case 2:
        case 3:
        case 5:
      	  destinoId = CODIGO_DESTINO_TIENDAS_ESPAÑA;break;
        case 6:
      	  destinoId = CODIGO_DESTINO_TIENDAS_PORTUGAL;break;
        case 8:
      	  destinoId = CODIGO_DESTINO_OFICINAS_MERCADONA;break;
        case 9:
      	  destinoId = CODIGO_DESTINO_ALMACENES;break;      	  
    	}

      	return destinoId;
    }
}
