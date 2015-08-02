package com.cenatel_demo_presidencia.desarrollo.demo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    //nombre de la base de datos
    //private static final String __DATABASE = "dbUniversidad";
    public static final String N_BD = "CenatelSigsegIniaPortuguesa";
    //versi?n de la base de datos
    //private static final int __VERSION = 3;
    public static final int VERSION_BD = 1;
    //nombre tabla y campos de tabla
    //public final String __tabla__ = "Universitario";
    public static final String N_TABLA = "Tabla_CenatelSigseg";
    public static final String ID_FILA = "id";
    public static final String FuncionarioNombre = "funcionario_nombre";
    public static final String FechaCaptura = "fecha_captura";
    public static final String UbicacionOficina = "ubicacion_oficina";
    public static final String nombreFinca = "nombreFinca";
    public static final String TipoCultivo = "TipoCultivo";
    public static final String FechaSiembra = "fecha_siembra";
    public static final String Variedad = "variedad";
    public static final String EtapaFenologica = "etapa_fenologica";
    public static final String Condicion = "condicion";
    public static final String NumeroLote = "numero_lote";
    public static final String Latitudpan = "latitudpan";
    public static final String Longitudpan = "longitudpan";
    public static final String Latituddet = "latituddet";
    public static final String Longituddet = "longituddet";
    public static final String Observacion = "observacion";
    public static final String punto1la = "punto1la";
    public static final String punto1lo = "punto1lo";
    public static final String punto2la = "punto2la";
    public static final String punto2lo = "punto2lo";
    public static final String punto3la = "punto3la";
    public static final String punto3lo = "punto3lo";
    public static final String punto4la = "punto4la";
    public static final String punto4lo = "punto4lo";
    public static final String punto5la = "punto5la";
    public static final String punto5lo = "punto5lo";
    public static final String punto6la = "punto6la";
    public static final String punto6lo = "punto6lo";
    public static final String punto7la = "punto7la";
    public static final String punto7lo = "punto7lo";
    public static final String punto8la = "punto8la";
    public static final String punto8lo = "punto8lo";
    public static final String punto9la = "punto9la";
    public static final String punto9lo = "punto9lo";
    public static final String punto10la = "punto10la";
    public static final String punto10lo = "punto10lo";



    /*public final String __campo_id = "id";
    public final String __campo_nombre = "Nombre";
    public final String __campo_fechanac = "FechaNac";
    public final String __campo_pais = "Pais";
    public final String __campo_sexo = "Sexo";
    public final String __campo_ingles = "Ingles";*/
    //Instrucci?n SQL para crear las tablas
	/*
	 * CREATE TABLE "Universitario" (
	                "id" INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE ,
	                "Nombre" TEXT, "FechaNac" DATETIME, "Pais" TEXT, "Sexo" TEXT, "Ingles" TEXT )
	 * */
    private final String sql = "CREATE TABLE " + N_TABLA + /*" ( " +
	               __campo_id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
	               __campo_nombre + " TEXT NULL, " +
	               __campo_fechanac + " TEXT, " +
	               __campo_pais + " TEXT NULL, " +
	               __campo_sexo + " TEXT NULL, " +
	               __campo_ingles + " TEXT NULL " + " )";*/
            "(" +
            ID_FILA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FuncionarioNombre + " TEXT NOT NULL, " +
            FechaCaptura + " TEXT NOT NULL, " +
            UbicacionOficina + " TEXT NOT NULL, " +
            nombreFinca + " TEXT NOT NULL, " +
            TipoCultivo + " TEXT NOT NULL, " +
            FechaSiembra + " TEXT NOT NULL, " +
            Variedad + " TEXT NOT NULL, " +
            EtapaFenologica + " TEXT NOT NULL, " +
            Condicion + " TEXT NOT NULL, " +
            NumeroLote + " TEXT NOT NULL, " +
            Latitudpan + " TEXT NOT NULL, " +
            Longitudpan + " TEXT NOT NULL, " +
            Latituddet + " TEXT NOT NULL, " +
            Longituddet + " TEXT NOT NULL, " +
            Observacion + " TEXT NOT NULL, " +
            punto1la + " TEXT NOT NULL, " +
            punto1lo + " TEXT NOT NULL, " +
            punto2la + " TEXT NOT NULL, " +
            punto2lo + " TEXT NOT NULL, " +
            punto3la + " TEXT NOT NULL, " +
            punto3lo + " TEXT NOT NULL, " +
            punto4la + " TEXT NOT NULL, " +
            punto4lo + " TEXT NOT NULL, " +
            punto5la + " TEXT NOT NULL, " +
            punto5lo + " TEXT NOT NULL, " +
            punto6la + " TEXT NOT NULL, " +
            punto6lo + " TEXT NOT NULL, " +
            punto7la + " TEXT NOT NULL, " +
            punto7lo + " TEXT NOT NULL, " +
            punto8la + " TEXT NOT NULL, " +
            punto8lo + " TEXT NOT NULL, " +
            punto9la + " TEXT NOT NULL, " +
            punto9lo + " TEXT NOT NULL, " +
            punto10la + " TEXT NOT NULL, " +
            punto10lo + " TEXT NULL" + " )";

    /**
     * Constructor de clase
     * */
    public SQLiteHelper(Context context) {
        super( context, N_BD, null, VERSION_BD );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( sql );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db,  int oldVersion, int newVersion ) {
        if ( newVersion > oldVersion )
        {
            //elimina tabla
            db.execSQL( "DROP TABLE IF EXISTS " + N_TABLA );
            //y luego creamos la nueva tabla
            db.execSQL( sql );
        }
    }

}