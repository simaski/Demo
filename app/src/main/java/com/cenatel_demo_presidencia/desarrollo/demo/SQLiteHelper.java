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
    public static final String nombreParticipante = "nombreParticipante";
    public static final String FechaCaptura = "fecha_captura";
    public static final String Ubicacion = "ubicacion";
    public static final String FuncionarioNombre = "funcionario_nombre";
    public static final String Observacion = "observacion";



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
    private final String sql = "CREATE TABLE " + N_TABLA +
            "(" +
            ID_FILA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            nombreParticipante + " TEXT NOT NULL, " +
            FechaCaptura + " TEXT NOT NULL, " +
            Ubicacion + " TEXT NOT NULL, " +
            FuncionarioNombre + " TEXT NOT NULL, " +
            Observacion + " TEXT NOT NULL" + " )";

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