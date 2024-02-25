
ALTER SESSION SET "_ORACLE_SCRIPT"=true;

CREATE TABLE TBL_PROVEEDOR(
    PROVEEDOR_ID NUMERIC(7) NOT NULL PRIMARY KEY,
    RUC VARCHAR2 (20) NOT NULL UNIQUE, 
	RAZON_SOCIAL VARCHAR2(150) NOT NULL UNIQUE,
    MOVIL VARCHAR2(15) NOT NULL, 
	EMAIL VARCHAR2(100) NOT NULL, 
	REPRESENTANTE_LEGAL VARCHAR2(150) NOT NULL,
    ESTADO CHAR(1) DEFAULT '1' NOT NULL
);

CREATE TABLE TBL_DESTINO(
    DESTINO_ID NUMERIC(1) NOT NULL PRIMARY KEY,
    NOMBRE VARCHAR2(240) NOT NULL UNIQUE,
    ESTADO CHAR(1) DEFAULT '1' NOT NULL
);

CREATE TABLE TBL_PRODUCTO(
    PRODUCTO_ID NUMERIC(6) NOT NULL PRIMARY KEY,
    NOMBRE VARCHAR2(240) NOT NULL UNIQUE,
    PRECIO NUMERIC(6,2) NOT NULL,
    STOCK NUMERIC(6,2) NOT NULL,
    COD_EAN VARCHAR2(13),
    TIENDA NUMERIC(1) NOT NULL,
    PROVEEDOR_ID NUMERIC(7) REFERENCES TBL_PROVEEDOR(PROVEEDOR_ID),
    DESTINO_ID NUMERIC(1) REFERENCES TBL_DESTINO(DESTINO_ID),
    ESTADO CHAR(1) DEFAULT '1' NOT NULL
);

CREATE TABLE TBL_USUARIO 
   (USUARIO_ID NUMBER(4,0) NOT NULL PRIMARY KEY, 
	USUARIO VARCHAR2(20 BYTE) NOT NULL UNIQUE, 
	CLAVE VARCHAR2(80 BYTE) NOT NULL , 
	NOMBRE VARCHAR2(120 BYTE)NOT NULL,
	ESTADO CHAR(1 BYTE) DEFAULT 1 NOT NULL
    );

CREATE TABLE TBL_AUTHORITY 
   (AUTHORITY_ID NUMBER(4,0) NOT NULL PRIMARY KEY,
	NOMBRE VARCHAR2(120 BYTE)NOT NULL,
	ESTADO CHAR(1 BYTE) DEFAULT 1 NOT NULL
    );
CREATE TABLE TBL_USUARIO_AUTHORITY 
   (USUARIO_AUTHORITY_ID NUMBER(4,0) NOT NULL PRIMARY KEY,
	USUARIO_ID NUMBER(4,0) NOT NULL REFERENCES TBL_USUARIO(USUARIO_ID),
    AUTHORITY_ID NUMBER(4,0) NOT NULL REFERENCES TBL_AUTHORITY(AUTHORITY_ID),
	ESTADO CHAR(1 BYTE) DEFAULT 1 NOT NULL
    );