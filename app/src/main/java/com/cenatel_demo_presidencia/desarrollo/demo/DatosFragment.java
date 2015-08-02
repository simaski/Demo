package com.cenatel_demo_presidencia.desarrollo.demo;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Admin on 04-06-2015.
 */
public class DatosFragment extends Fragment {

    public EditText et_funcionario;
    public EditText et_fechaCaptura;
    public EditText et_nombreParticipante;
    public EditText et_observacion;

    public Spinner spi_ubicacion;

    public String spi_ubicacionR;
    public String et_ubicacionR;
    public String et_funcionarioR;
    public String et_fechaCapturaR;
    public String et_nombreParticpanteR;
    public String et_observacionR;



    public Button btEnviar;


    private Calendar c;






    public boolean backButton = false;




    private String dia;
    private String mes;
    private String anio;



    private int mYear;
    private int mMonth;
    private int mDay;
    //----------------------------------------------------------

    private Button btnVerDatos;
    //----------------------------------------------------------

    ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();
    public Location locationp;

    private static final String TAG = "MainActivity";

    private SQLite sqlite;
//    private GenerarPDFActivity gPdf;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_datos, container, false);

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        et_funcionario = (EditText) v.findViewById(R.id.et_funcionario);
        et_fechaCaptura = (EditText) v.findViewById(R.id.et_fechaCaptura);
        et_fechaCaptura.setText(sdf.format(c.getTime()));
        et_nombreParticipante = (EditText) v.findViewById(R.id.et_nombreParticipante);
        et_observacion = (EditText) v.findViewById(R.id.et_observacion);



        spi_ubicacion = (Spinner) v.findViewById(R.id.spi_ubicacion);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.arr_estados, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spi_ubicacion.setAdapter(adapter1);
        spi_ubicacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spi_ubicacionR = spi_ubicacion.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });





        //-------------------------------------------------------------------------------------------------

        //--------------------CARPETA IMAGENES--------------------------------------------------------------
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CenatelSSCP/");
        if (!f.exists()) {
            f.mkdir();
        }
        //--------------------------------------------------------------------------------------------------

        //------------------------------ESCRITURA DE IMAGENES-------------------------------------------------

        btEnviar = (Button) v.findViewById(R.id.bt_enviar);
        btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(true);
                progressBar.setMessage("Subiendo Archivos ...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBarStatus = 0;*/
//                Toast.makeText(getActivity(), "hola", Toast.LENGTH_SHORT).show();
                if(et_funcionario.getText().toString().equals("")){
                    CamposVacios();
                }else if(et_nombreParticipante.getText().toString().equals("")){
                    CamposVacios();
                }else{
                    //progressBar.show();
                    et_funcionarioR = et_funcionario.getText().toString();
                    et_fechaCapturaR = et_fechaCaptura.getText().toString();
                    et_ubicacionR = spi_ubicacionR;
                    et_nombreParticpanteR = et_nombreParticipante.getText().toString();
                    et_observacionR = et_observacion.getText().toString();





                    sqlite = new SQLite(getActivity());
                    sqlite.abrir();
                    sqlite.addRegistro(et_nombreParticpanteR, et_fechaCapturaR, et_ubicacionR, et_funcionarioR, et_observacionR);
                    sqlite.cerrar();

                    et_funcionario.setText("");
                    et_fechaCaptura.setText("");
                    et_nombreParticipante.setText("");
                    et_observacion.setText("");




                }
            }
        });

        btnVerDatos = (Button) v.findViewById(R.id.bt_ver);
        btnVerDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(getActivity(), "No!"+prueba, Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    public void CamposVacios(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        // Setting Dialog Title
        alertDialog.setTitle("Alerta!!!");
        // Setting Dialog Message
        alertDialog.setMessage("Uno o varios campos obligatorios no han sido llenados. O no ha capturado las Fotografias");
        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);
        // On pressing Settings button
        alertDialog.setPositiveButton("OK!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }




    /*public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            backButton = true;
            //Toast tosat2 = Toast.makeText(getApplicationContext(),"Funciona!!!!", Toast.LENGTH_SHORT ); tosat2.show();
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            // Setting Dialog Title
            alertDialog.setTitle("Salir de la Aplicación");
            // Setting Dialog Message
            alertDialog.setMessage("¿Quieres salir de la aplicación?");
            // Setting Icon to Dialog
            // alertDialog.setIcon(R.drawable.delete);
            // On pressing Settings button
            alertDialog.setPositiveButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            // on pressing cancel button
            alertDialog.setNegativeButton("Si",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //moveTaskToBack(true);
                            //locationManager.removeUpdates(locListener);
                            //hand.removeCallbacks(actualizar);
                            getActivity().finish();
                            //onStop();
                        }
                    });
            // Showing Alert Message
            alertDialog.show();
        }
        return true;
    }*/


}
