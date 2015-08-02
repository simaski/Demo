package com.cenatel_demo_presidencia.desarrollo.demo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

//

@SuppressLint("NewApi")
public class SQLite {

    private SQLiteHelper sqliteHelper;
    private SQLiteDatabase db;

    /** Constructor de clase */
    public SQLite(Context context)
    {
        sqliteHelper = new SQLiteHelper( context );
    }

    /** Abre conexion a base de datos */
    public void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos " + sqliteHelper.getDatabaseName());
        db = sqliteHelper.getWritableDatabase();
    }

    /** Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos " + sqliteHelper.getDatabaseName());
        sqliteHelper.close();
    }

    /**
     * Metodo para agregar un nuevo registro
     * @param String nombre Nombre completo
     * @param String fecha fecha de nacimiento de la forma 12/05/1900
     * @param String pais
     * @param String sexo
     * @param String ingles si habla ingles
     * @return BOOLEAN TRUE si tuvo exito FALSE caso contrario
     * */
    public boolean addRegistro( String funcionario_nombre,String fecha_captura,String ubicacion_oficina,String nombreFinca,String TipoCultivo,String fecha_siembra,String variedad,String etapa_fenologica,String condicion,String numero_lote,
                                String latitudpan,String longitudpan, String latituddet,String longituddet, String observacion,String punto1la,String punto1lo,String punto2la,String punto2lo,String punto3la,String punto3lo,
                                String punto4la,String punto4lo,String punto5la,String punto5lo,String punto6la,String punto6lo,String punto7la,String punto7lo,String punto8la,String punto8lo,String punto9la,String punto9lo,
                                String punto10la,String punto10lo)
    {
        if( funcionario_nombre.length()> 0 )
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(sqliteHelper.FuncionarioNombre,funcionario_nombre);
            contentValues.put(sqliteHelper.FechaCaptura,fecha_captura);
            contentValues.put(sqliteHelper.UbicacionOficina,ubicacion_oficina);
            contentValues.put(sqliteHelper.nombreFinca,nombreFinca);
            contentValues.put(sqliteHelper.TipoCultivo,TipoCultivo);
            contentValues.put(sqliteHelper.FechaSiembra,fecha_siembra);
            contentValues.put(sqliteHelper.Variedad,variedad);
            contentValues.put(sqliteHelper.EtapaFenologica,etapa_fenologica);
            contentValues.put(sqliteHelper.Condicion,condicion);
            contentValues.put(sqliteHelper.NumeroLote,numero_lote);
            contentValues.put(sqliteHelper.Latitudpan,latitudpan);
            contentValues.put(sqliteHelper.Longitudpan,longitudpan);
            contentValues.put(sqliteHelper.Latituddet,latituddet);
            contentValues.put(sqliteHelper.Longituddet,longituddet);
            contentValues.put(sqliteHelper.Observacion,observacion);
            contentValues.put(sqliteHelper.punto1la,punto1la);
            contentValues.put(sqliteHelper.punto1lo,punto1lo);
            contentValues.put(sqliteHelper.punto2la,punto2la);
            contentValues.put(sqliteHelper.punto2lo,punto2lo);
            contentValues.put(sqliteHelper.punto3la,punto3la);
            contentValues.put(sqliteHelper.punto3lo,punto3lo);
            contentValues.put(sqliteHelper.punto4la,punto4la);
            contentValues.put(sqliteHelper.punto4lo,punto4lo);
            contentValues.put(sqliteHelper.punto5la,punto5la);
            contentValues.put(sqliteHelper.punto5lo,punto5lo);
            contentValues.put(sqliteHelper.punto6la,punto6la);
            contentValues.put(sqliteHelper.punto6lo,punto6lo);
            contentValues.put(sqliteHelper.punto7la,punto7la);
            contentValues.put(sqliteHelper.punto7lo,punto7lo);
            contentValues.put(sqliteHelper.punto8la,punto8la);
            contentValues.put(sqliteHelper.punto8lo,punto8lo);
            contentValues.put(sqliteHelper.punto9la,punto9la);
            contentValues.put(sqliteHelper.punto9lo,punto9lo);
            contentValues.put(sqliteHelper.punto10la,punto10la);
            contentValues.put(sqliteHelper.punto10lo,punto10lo);
            Log.i("SQLite", "Nuevo registro ");
            return ( db.insert( sqliteHelper.N_TABLA , null, contentValues ) != -1 )?true:false;
        }
        else
            return false;
    }


    /**
     * Metodo que retorna el ID del ultimo universitario registrado
     * @return integer ID -1 si no existen registros
     * */
    public int getUltimoID()
    {
        int id = -1;
        //query(String table,
        //String[] columns,
        //String selection, String[] selectionArgs, String groupBy, String having,
        //String orderBy, String limit)
        Cursor cursor = db.query( sqliteHelper.N_TABLA ,
                new String[]{ sqliteHelper.ID_FILA },
                null, null, null,null,
                sqliteHelper.ID_FILA + " DESC ", "1");
        if( cursor.moveToFirst() )
        {
            do
            {
                id = cursor.getInt(0);
            } while ( cursor.moveToNext() );
        }
        return id;
    }

    /**
     * @param INT ID del registro a eliminar
     * @return BOOLEAN
     * */
	/*public boolean borrar_registro( int id )
	{
		//table , whereClause, whereArgs
		return  (db.delete( sqliteHelper.N_TABLA , sqliteHelper.ID_FILA + " = " + id ,  null) > 0) ? true:false;

	}
	/**
	 * Obtiene todos los registros de la unica tabla de la base de datos
	 * @return Cursor
	 * */
    public Cursor getRegistros()
    {
        return db.query( sqliteHelper.N_TABLA ,
                new String[]{
                        sqliteHelper.ID_FILA ,
                        sqliteHelper.FuncionarioNombre,
                        sqliteHelper.FechaCaptura,
                        sqliteHelper.UbicacionOficina,
                        sqliteHelper.nombreFinca,
                        sqliteHelper.TipoCultivo,
                        sqliteHelper.FechaSiembra,
                        sqliteHelper.Variedad,
                        sqliteHelper.EtapaFenologica,
                        sqliteHelper.Condicion,
                        sqliteHelper.NumeroLote,
                        sqliteHelper.Latitudpan,
                        sqliteHelper.Longitudpan,
                        sqliteHelper.Latituddet,
                        sqliteHelper.Longituddet,
                        sqliteHelper.Observacion,
                        sqliteHelper.punto1la,
                        sqliteHelper.punto1lo,
                        sqliteHelper.punto2la,
                        sqliteHelper.punto2lo,
                        sqliteHelper.punto3la,
                        sqliteHelper.punto3lo,
                        sqliteHelper.punto4la,
                        sqliteHelper.punto4lo,
                        sqliteHelper.punto5la,
                        sqliteHelper.punto5lo,
                        sqliteHelper.punto6la,
                        sqliteHelper.punto6lo,
                        sqliteHelper.punto7la,
                        sqliteHelper.punto7lo,
                        sqliteHelper.punto8la,
                        sqliteHelper.punto8lo,
                        sqliteHelper.punto9la,
                        sqliteHelper.punto9lo,
                        sqliteHelper.punto10la,
                        sqliteHelper.punto10lo
                },
                null, null, null, null, null);
    }


    /**
     * Obtiene un registro
     * */
    public Cursor getRegistro( int id )
    {
        return db.query( sqliteHelper.N_TABLA ,
                new String[]{
                        sqliteHelper.ID_FILA ,
                        sqliteHelper.FuncionarioNombre,
                        sqliteHelper.FechaCaptura,
                        sqliteHelper.UbicacionOficina,
                        sqliteHelper.nombreFinca,
                        sqliteHelper.TipoCultivo,
                        sqliteHelper.FechaSiembra,
                        sqliteHelper.Variedad,
                        sqliteHelper.EtapaFenologica,
                        sqliteHelper.Condicion,
                        sqliteHelper.NumeroLote,
                        sqliteHelper.Latitudpan,
                        sqliteHelper.Longitudpan,
                        sqliteHelper.Latituddet,
                        sqliteHelper.Longituddet,
                        sqliteHelper.Observacion,
                        sqliteHelper.punto1la,
                        sqliteHelper.punto1lo,
                        sqliteHelper.punto2la,
                        sqliteHelper.punto2lo,
                        sqliteHelper.punto3la,
                        sqliteHelper.punto3lo,
                        sqliteHelper.punto4la,
                        sqliteHelper.punto4lo,
                        sqliteHelper.punto5la,
                        sqliteHelper.punto5lo,
                        sqliteHelper.punto6la,
                        sqliteHelper.punto6lo,
                        sqliteHelper.punto7la,
                        sqliteHelper.punto7lo,
                        sqliteHelper.punto8la,
                        sqliteHelper.punto8lo,
                        sqliteHelper.punto9la,
                        sqliteHelper.punto9lo,
                        sqliteHelper.punto10la,
                        sqliteHelper.punto10lo
                },
                sqliteHelper.ID_FILA + " = " + id ,
                null, null, null, null);
    }

    /**
     * Dado un Cursor con los registros de la base de datos, da formato y retorna resultado
     * @return ArrayList<String>
     * */
    public ArrayList<String> getFormatListUniv( Cursor cursor )
    {
        ArrayList<String> listData = new ArrayList<String>();
        String item = "";
        if( cursor.moveToFirst() )
        {
            do
            {
                item += "ID: [" + cursor.getInt(0) + "]\r\n";
                item += "Nombre Funcionario: " + cursor.getString(1) + "\r\n";
                item += "Fecha de Captura: " + cursor.getString(2) + "\r\n";
                item += "Ubicacion Oficina: " + cursor.getString(3) + "\r\n";
                item += "Nombre de la Finca: " + cursor.getString(4) + "\r\n";
                item += "Tipo de Cultivo: " + cursor.getString(5) + "\r\n";
                item += "Fecha de Siembra: " + cursor.getString(6) + "\r\n";
                item += "Variedad: " + cursor.getString(7) + "\r\n";
                item += "Etapa Fenologica: " + cursor.getString(8) + "\r\n";
                item += "Condicion: " + cursor.getString(9) + "\r\n";
                item += "Numero de Lote: " + cursor.getString(10) + "\r\n";
                item += "Latitud Foto Panoramica: " + cursor.getString(11) + "\r\n";
                item += "Longitud Foto Panoramica: " + cursor.getString(12) + "\r\n";
                item += "Latitud Foto Detalle: " + cursor.getString(13) + "\r\n";
                item += "Longitud Foto Detalle: " + cursor.getString(14) + "\r\n";
                item += "Observacion: " + cursor.getString(15) + "\r\n";
                item += "Latitud punto 1: " + cursor.getString(16) + "\r\n";
                item += "Longitud punto 1: " + cursor.getString(17) + "\r\n";
                item += "Latitud punto 2: " + cursor.getString(18) + "\r\n";
                item += "Longitud punto 2: " + cursor.getString(19) + "\r\n";
                item += "Latitud punto 3: " + cursor.getString(20) + "\r\n";
                item += "Longitud punto 3: " + cursor.getString(21) + "\r\n";
                item += "Latitud punto 4: " + cursor.getString(22) + "\r\n";
                item += "Longitud punto 4: " + cursor.getString(23) + "\r\n";
                item += "Latitud punto 5: " + cursor.getString(24) + "\r\n";
                item += "Longitud punto 5: " + cursor.getString(25) + "\r\n";
                item += "Latitud punto 6: " + cursor.getString(26) + "\r\n";
                item += "Longitud punto 6: " + cursor.getString(27) + "\r\n";
                item += "Latitud punto 7: " + cursor.getString(28) + "\r\n";
                item += "Longitud punto 7: " + cursor.getString(29) + "\r\n";
                item += "Latitud punto 8: " + cursor.getString(30) + "\r\n";
                item += "Longitud punto 8: " + cursor.getString(31) + "\r\n";
                item += "Latitud punto 9: " + cursor.getString(32) + "\r\n";
                item += "Longitud punto 9: " + cursor.getString(33) + "\r\n";
                item += "Longitud punto 10: " + cursor.getString(34) + "";
                listData.add( item );
                item="";

            } while ( cursor.moveToNext() );
        }
        return listData;
    }

    public ArrayList<String> getFormatListaPrimera( Cursor cursor )
    {
        ArrayList<String> listData2 = new ArrayList<String>();
        String item = "";
        if( cursor.moveToFirst() )
        {
            do
            {
                item += "ID: " + cursor.getInt(0) + "\r\n";
                item += "Nombre Funcionario: " + cursor.getString(1) + "\r\n";
                item += "Fecha de Captura: " + cursor.getString(2) + "\r\n";
                listData2.add( item );
                item="";

            } while ( cursor.moveToNext() );
        }
        return listData2;
    }

}