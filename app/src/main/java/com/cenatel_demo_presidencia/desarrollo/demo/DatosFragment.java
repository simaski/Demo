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
public class DatosFragment extends Fragment implements LocationListener {

    public EditText et_funcionario;
    public EditText et_fechaCaptura;
    public EditText et_nombreFinca;
    public EditText et_tipoCultivo;
    public EditText et_fechaSiembra;
    public EditText et_variedad;
    public EditText et_numeroLote;
    public EditText et_observacion;

    public Spinner spi_oficinaEstados;
    public Spinner spi_etapaFenologica;
    public Spinner spi_condicion;

    public String spi_oficinaEstadosR;
    public String spi_etapaFenologicaR;
    public String spi_condicionR;
    public String et_funcionarioR;
    public String et_fechaCapturaR;
    public String et_ubicacionOficinaR;
    public String et_nombreFincaR;
    public String et_tipoCultivoR;
    public String et_fechaSiembraR;
    public String et_variedadR;
    public String et_etapaFenologicaR;
    public String et_condicionR;
    public String et_numeroLoteR;
    public String et_observacionR;

    public Button btPanoramica;
    public Button btDetalle;
    public Button btEnviar;
    public Button btFechaSiembra;
    public Button btCapturar;

    int dw; // ancho pantalla
    int dh; // alto pantalla
    int acum;

    private ImageView img_panoramica;
    private ImageView img_detalle;

    private Calendar c;

    public static int CAMERA_RESULT = 1;
    public static int TAKE_PICTURE = 2;
    private static final String JPEG_FILE_PREFIX = "IMG_"; // prefijo imagenes
    private static final String JPEG_FILE_SUFFIX = ".jpg"; // sufijo para jpeg


    private String mCurrentPhotoPath; // String para guardar el camino hacia la foto
    private String mCurrentPhotoPath1; // String para guardar el camino hacia la foto

    private Uri imageFileUri; // Ver proveedores de contenidos.
    private Uri imageFileUri1; // Ver proveedores de contenidos.
    private String timeStamp;
    private String imageFileName;
    private String imageFileName1;
    private String imageFileName2;
    private String imageFileName3;
    //private ImageView iv2;
    //private ImageView iv1;
    public String longitudpan;
    public String latitudpan;
    public String longituddet;
    public String latituddet;
    public String latglobal;
    public String longlobal;
    //--------------------------------------------------------------------------

    //--------------GPS----------------------------------------
    final android.os.Handler hand = new android.os.Handler();
    private LocationManager locationManager;
    public boolean backButton = false;
    //---------------------------------------------------------

    //--------------FORMULARIO---------------------------------
    public TextView lblLatitud;
    public TextView lblLongitud;
    public TextView lblPrecision;
    public TextView latp1;
    public TextView latp2;
    public TextView latp3;
    public TextView latp4;
    public TextView latp5;
    public TextView latp6;
    public TextView latp7;
    public TextView latp8;
    public TextView latp9;
    public TextView latp10;
    public TextView lonp1;
    public TextView lonp2;
    public TextView lonp3;
    public TextView lonp4;
    public TextView lonp5;
    public TextView lonp6;
    public TextView lonp7;
    public TextView lonp8;
    public TextView lonp9;
    public TextView lonp10;
    public TextView punto2;
    public TextView punto3;
    public TextView punto4;
    public TextView punto5;
    public TextView punto6;
    public TextView punto7;
    public TextView punto8;
    public TextView punto9;
    public TextView punto10;

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
        acum = 1;

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        et_funcionario = (EditText) v.findViewById(R.id.et_funcionario);
        et_fechaCaptura = (EditText) v.findViewById(R.id.et_fechaCaptura);
        et_fechaCaptura.setText(sdf.format(c.getTime()));
        et_nombreFinca = (EditText) v.findViewById(R.id.et_nombreFinca);
        et_tipoCultivo = (EditText) v.findViewById(R.id.et_tipoCultivo);
        et_fechaSiembra = (EditText) v.findViewById(R.id.et_fechaSiembra);
        et_variedad = (EditText) v.findViewById(R.id.et_variedad);
        et_numeroLote = (EditText) v.findViewById(R.id.et_numeroLote);
        et_observacion = (EditText) v.findViewById(R.id.et_observacion);

        latp1 = (TextView) v.findViewById(R.id.lat_p1);
        latp2 = (TextView) v.findViewById(R.id.lat_p2);
        latp3 = (TextView) v.findViewById(R.id.lat_p3);
        latp4 = (TextView) v.findViewById(R.id.lat_p4);
        latp5 = (TextView) v.findViewById(R.id.lat_p5);
        latp6 = (TextView) v.findViewById(R.id.lat_p6);
        latp7 = (TextView) v.findViewById(R.id.lat_p7);
        latp8 = (TextView) v.findViewById(R.id.lat_p8);
        latp9 = (TextView) v.findViewById(R.id.lat_p9);
        latp10 = (TextView) v.findViewById(R.id.lat_p10);
        lonp1 = (TextView) v.findViewById(R.id.lon_p1);
        lonp2 = (TextView) v.findViewById(R.id.lon_p2);
        lonp3 = (TextView) v.findViewById(R.id.lon_p3);
        lonp4 = (TextView) v.findViewById(R.id.lon_p4);
        lonp5 = (TextView) v.findViewById(R.id.lon_p5);
        lonp6 = (TextView) v.findViewById(R.id.lon_p6);
        lonp7 = (TextView) v.findViewById(R.id.lon_p7);
        lonp8 = (TextView) v.findViewById(R.id.lon_p8);
        lonp9 = (TextView) v.findViewById(R.id.lon_p9);
        lonp10 = (TextView) v.findViewById(R.id.lon_p10);
        punto2 = (TextView) v.findViewById(R.id.punto2);
        punto3 = (TextView) v.findViewById(R.id.punto3);
        punto4 = (TextView) v.findViewById(R.id.punto4);
        punto5 = (TextView) v.findViewById(R.id.punto5);
        punto6 = (TextView) v.findViewById(R.id.punto6);
        punto7 = (TextView) v.findViewById(R.id.punto7);
        punto8 = (TextView) v.findViewById(R.id.punto8);
        punto9 = (TextView) v.findViewById(R.id.punto9);
        punto10 = (TextView) v.findViewById(R.id.punto10);

        spi_oficinaEstados = (Spinner) v.findViewById(R.id.spi_oficinaEstado);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.arr_estados, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spi_oficinaEstados.setAdapter(adapter1);
        spi_oficinaEstados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spi_oficinaEstadosR = spi_oficinaEstados.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spi_etapaFenologica = (Spinner) v.findViewById(R.id.spi_etapaFenologica);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.array_etapasArroz, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spi_etapaFenologica.setAdapter(adapter2);
        spi_etapaFenologica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spi_etapaFenologicaR = spi_etapaFenologica.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spi_condicion = (Spinner) v.findViewById(R.id.spi_condicion);
        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getActivity(), R.array.array_condicion, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spi_condicion.setAdapter(adapter3);
        spi_condicion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spi_condicionR = spi_condicion.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        lblLatitud = (TextView) v.findViewById(R.id.latitudres);
        lblLongitud = (TextView) v.findViewById(R.id.longitudres);
        lblPrecision = (TextView) v.findViewById(R.id.precisonres);

        btPanoramica = (Button) v.findViewById(R.id.bt_panoramica);
        btDetalle = (Button) v.findViewById(R.id.bt_detalle);


        btCapturar = (Button) v.findViewById(R.id.bt_capturar);
        btCapturar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lblLatitud.getText().toString().equals("No disponible")) {
                    Toast.makeText(getActivity(), "No ha posicionado aun. Por favor espere!", Toast.LENGTH_SHORT).show();
                } else if(acum == 1){
                    latglobal = lblLatitud.getText().toString();
                    longlobal = lblLongitud.getText().toString();
                    latp1.setText(latglobal);
                    lonp1.setText(longlobal);
                    DialogoPersonalizado();
                }else if (acum == 2){
                    latglobal = lblLatitud.getText().toString();
                    longlobal = lblLongitud.getText().toString();
                    latp2.setVisibility(View.VISIBLE);
                    lonp2.setVisibility(View.VISIBLE);
                    punto2.setVisibility(View.VISIBLE);
                    latp2.setText(String.valueOf(latglobal));
                    lonp2.setText(String.valueOf(longlobal));
                    DialogoPersonalizado();
                }else if (acum == 3){
                    latglobal = lblLatitud.getText().toString();
                    longlobal = lblLongitud.getText().toString();
                    latp3.setVisibility(View.VISIBLE);
                    lonp3.setVisibility(View.VISIBLE);
                    punto3.setVisibility(View.VISIBLE);
                    latp3.setText(String.valueOf(latglobal));
                    lonp3.setText(String.valueOf(longlobal));
                    DialogoPersonalizado();
                }else if (acum == 4){
                    latglobal = lblLatitud.getText().toString();
                    longlobal = lblLongitud.getText().toString();
                    latp4.setVisibility(View.VISIBLE);
                    lonp4.setVisibility(View.VISIBLE);
                    punto4.setVisibility(View.VISIBLE);
                    latp4.setText(String.valueOf(latglobal));
                    lonp4.setText(String.valueOf(longlobal));
                    DialogoPersonalizado();
                }else if (acum == 5){
                    latglobal = lblLatitud.getText().toString();
                    longlobal = lblLongitud.getText().toString();
                    latp5.setVisibility(View.VISIBLE);
                    lonp5.setVisibility(View.VISIBLE);
                    punto5.setVisibility(View.VISIBLE);
                    latp5.setText(String.valueOf(latglobal));
                    lonp5.setText(String.valueOf(longlobal));
                    DialogoPersonalizado();
                }else if (acum == 6){
                    latglobal = lblLatitud.getText().toString();
                    longlobal = lblLongitud.getText().toString();
                    latp6.setVisibility(View.VISIBLE);
                    lonp6.setVisibility(View.VISIBLE);
                    punto6.setVisibility(View.VISIBLE);
                    latp6.setText(String.valueOf(latglobal));
                    lonp6.setText(String.valueOf(longlobal));
                    DialogoPersonalizado();
                }else if (acum == 7){
                    latglobal = lblLatitud.getText().toString();
                    longlobal = lblLongitud.getText().toString();
                    latp7.setVisibility(View.VISIBLE);
                    lonp7.setVisibility(View.VISIBLE);
                    punto7.setVisibility(View.VISIBLE);
                    latp7.setText(String.valueOf(latglobal));
                    lonp7.setText(String.valueOf(longlobal));
                    DialogoPersonalizado();
                }else if (acum == 8){
                    latglobal = lblLatitud.getText().toString();
                    longlobal = lblLongitud.getText().toString();
                    latp8.setVisibility(View.VISIBLE);
                    lonp8.setVisibility(View.VISIBLE);
                    punto8.setVisibility(View.VISIBLE);
                    latp8.setText(String.valueOf(latglobal));
                    lonp8.setText(String.valueOf(longlobal));
                    DialogoPersonalizado();
                }else if (acum == 9){
                    latglobal = lblLatitud.getText().toString();
                    longlobal = lblLongitud.getText().toString();
                    latp9.setVisibility(View.VISIBLE);
                    lonp9.setVisibility(View.VISIBLE);
                    punto9.setVisibility(View.VISIBLE);
                    latp9.setText(String.valueOf(latglobal));
                    lonp9.setText(String.valueOf(longlobal));
                    DialogoPersonalizado();
                }else if (acum == 10){
                    latglobal = lblLatitud.getText().toString();
                    longlobal = lblLongitud.getText().toString();
                    latp10.setVisibility(View.VISIBLE);
                    lonp10.setVisibility(View.VISIBLE);
                    punto10.setVisibility(View.VISIBLE);
                    latp10.setText(String.valueOf(latglobal));
                    lonp10.setText(String.valueOf(longlobal));
                    btCapturar.setVisibility(View.GONE);
                }
            }
        });


        btFechaSiembra = (Button) v.findViewById(R.id.bt_fechaSiembra);
        btFechaSiembra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch Date Picker Dialog
                DatePickerDialog dpd = new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                et_fechaSiembra.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth-1, mDay);
                dpd.show();
            }
        });

        img_panoramica = (ImageView) v.findViewById(R.id.img_panoramica);
        img_detalle = (ImageView) v.findViewById(R.id.img_detalle);

        //-----------------------GPS---------------------------------------------------------------------
        //hand.removeCallbacks(actualizar);
        //hand.postDelayed(actualizar,100);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 400, 1, this);


        if (location != null) {
            onLocationChanged(location);
        } else {
            lblLatitud.setText("No disponible");
            lblLongitud.setText("No disponible");
            lblPrecision.setText("No disponible");
        }
        //-------------------------------------------------------------------------------------------------

        //--------------------CARPETA IMAGENES--------------------------------------------------------------
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/CenatelSSCP/");
        if (!f.exists()) {
            f.mkdir();
        }
        //--------------------------------------------------------------------------------------------------

        //------------------------------ESCRITURA DE IMAGENES-------------------------------------------------
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        dw = 200;
        dh = 200;
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = JPEG_FILE_PREFIX + timeStamp;
        mCurrentPhotoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CenatelSSCP/" + imageFileName;
        imageFileUri = Uri.fromFile(new File(mCurrentPhotoPath));


//        DisplayMetrics metrics1 = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics1);
        dw = 200;
        dh = 200;
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName1 = JPEG_FILE_PREFIX + timeStamp + 1;
        mCurrentPhotoPath1 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CenatelSSCP/" + imageFileName1;
        imageFileUri1 = Uri.fromFile(new File(mCurrentPhotoPath1));


        btPanoramica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lblLatitud.getText().toString().equals("No disponible")) {
                    Toast.makeText(getActivity(), "No ha posicionado aun. Por favor espere!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent ivd = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    ivd.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri1);
                    startActivityForResult(ivd, TAKE_PICTURE);
                }

            }
        });


        btDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
	    		if (lblLatitud.getText().toString().equals("No disponible")) {
                    Toast.makeText(getActivity(), "No ha posicionado aun. Por favor espere!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent iv = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    iv.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);
                    startActivityForResult(iv, CAMERA_RESULT);
                }
            }
        });
        //--------------------------ESCRITURA DE IMAGENES----------------------------------------------------------

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
                }else if(et_nombreFinca.getText().toString().equals("")){
                    CamposVacios();
                }else if(et_tipoCultivo.getText().toString().equals("")){
                    CamposVacios();
                }else if(et_variedad.getText().toString().equals("")){
                    CamposVacios();
                }else if(et_numeroLote.getText().toString().equals("")){
                    CamposVacios();
                }else if(img_panoramica.getDrawable() == null){
                    CamposVacios();
                }else if(img_panoramica.getDrawable() == null){
                    CamposVacios();
                }else{
                    //progressBar.show();
                    et_funcionarioR = et_funcionario.getText().toString();
                    et_fechaCapturaR = et_fechaCaptura.getText().toString();
                    et_ubicacionOficinaR = spi_oficinaEstadosR;
                    et_nombreFincaR = et_nombreFinca.getText().toString();
                    et_tipoCultivoR = et_tipoCultivo.getText().toString();
                    et_fechaSiembraR = et_fechaSiembra.getText().toString();
                    et_variedadR = et_variedad.getText().toString();
                    et_etapaFenologicaR = spi_etapaFenologicaR;
                    et_condicionR = spi_condicionR;
                    et_numeroLoteR = et_numeroLote.getText().toString();
                    et_observacionR = et_observacion.getText().toString();

                    String punto1la = latp1.getText().toString();
                    String punto1lo = lonp1.getText().toString();
                    String punto2la = latp2.getText().toString();
                    String punto2lo = lonp2.getText().toString();
                    String punto3la = latp3.getText().toString();
                    String punto3lo = lonp3.getText().toString();
                    String punto4la = latp4.getText().toString();
                    String punto4lo = lonp4.getText().toString();
                    String punto5la = latp5.getText().toString();
                    String punto5lo = lonp5.getText().toString();
                    String punto6la = latp6.getText().toString();
                    String punto6lo = lonp6.getText().toString();
                    String punto7la = latp7.getText().toString();
                    String punto7lo = lonp7.getText().toString();
                    String punto8la = latp8.getText().toString();
                    String punto8lo = lonp8.getText().toString();
                    String punto9la = latp9.getText().toString();
                    String punto9lo = lonp9.getText().toString();
                    String punto10la = latp10.getText().toString();
                    String punto10lo = lonp10.getText().toString();



                    sqlite = new SQLite(getActivity());
                    sqlite.abrir();
                    sqlite.addRegistro(et_funcionarioR,et_fechaCapturaR,et_ubicacionOficinaR,et_nombreFincaR,et_tipoCultivoR,et_fechaSiembraR,
                            et_variedadR,et_etapaFenologicaR,et_condicionR,et_numeroLoteR,latitudpan,longitudpan,latituddet,longituddet,et_observacionR,
                            punto1la,punto1lo,punto2la,punto2lo,punto3la,punto3lo,punto4la,punto4lo,punto5la,punto5lo,punto6la,punto6lo,punto7la,punto7lo,
                            punto8la,punto8lo,punto9la,punto9lo,punto10la,punto10lo);
                    sqlite.cerrar();

                    et_funcionario.setText("");
                    et_fechaCaptura.setText("");
                    et_nombreFinca.setText("");
                    et_tipoCultivo.setText("");
                    et_fechaSiembra.setText("");
                    et_variedad.setText("");
                    et_numeroLote.setText("");
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

    //------------INICIO Runnable----------------------------------------------------------------------
//    private Runnable actualizar = new Runnable() {
//        @Override
//        public void run() {
//            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                showSettingsAlert();
//            }
//            hand.postDelayed(this,7000);
//
//        }
//    };
    //------------FIN Runnable----------------------------------------------------------------------

//    public void showSettingsAlert() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
//        // Setting Dialog Title
//        alertDialog.setTitle("Configuracion GPS");
//        // Setting Dialog Message
//        alertDialog.setMessage("GPS no esta activado. Quieres ir al menu de configuracion");
//        // Setting Icon to Dialog
//        // alertDialog.setIcon(R.drawable.delete);
//        // On pressing Settings button
//        alertDialog.setPositiveButton("Configuracin",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivity(intent);
//                        //dialog.cancel();
//                    }
//                });
//        // on pressing cancel button
//        alertDialog.setNegativeButton("Cancelar",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        hand.removeCallbacks(actualizar);
////                        MainActivity.this.finish();
//                    }
//                });
//        // Showing Alert Message
//        alertDialog.show();
//    }//------------------------FIN Dialogo GPS-------------------------------------------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
       /* Se revisa si la imagen viene de la camara (TAKE_PICTURE) o de la galeria (SELECT_PICTURE)
                */
        try {
            if (requestCode == TAKE_PICTURE) {

                BitmapFactory.Options bmOptions1 = new BitmapFactory.Options();
                bmOptions1.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(mCurrentPhotoPath1, bmOptions1);
                int photoW = bmOptions1.outWidth;
                int photoH = bmOptions1.outHeight;
                // Determinar cuanto escalamos la imagen
                int scaleFactor1 = Math.min(photoW / dw, photoH / dh);
                // Decodificar la imagen en un Bitmap escalado a View
                bmOptions1.inJustDecodeBounds = false;
                bmOptions1.inSampleSize = scaleFactor1;
                bmOptions1.inPurgeable = true;
                Bitmap bitmap1 = BitmapFactory.decodeFile(mCurrentPhotoPath1, bmOptions1);
                img_panoramica.setImageBitmap(bitmap1);
                imageFileName2 = "cenatel";
                File file = new File(mCurrentPhotoPath1 + imageFileName2 + ".jpg");
                try {
                    file.createNewFile();
                    FileOutputStream out = new FileOutputStream(file);
                    // bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, out);//Convertimos la imagen a JPEG
                    bitmap1.compress(Bitmap.CompressFormat.PNG, 100, out);//Convertimos la imagen a JPEG
                    out.flush();
                    out.close();
                    Toast.makeText(getActivity(), "Hecho 1!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                latitudpan = lblLatitud.getText().toString();
                longitudpan = lblLongitud.getText().toString();

            }
            if (requestCode == CAMERA_RESULT) {
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                bmOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
                int photoW1 = bmOptions.outWidth;
                int photoH1 = bmOptions.outHeight;
                // Determinar cuanto escalamos la imagen
                int scaleFactor = Math.min(photoW1 / dw, photoH1 / dh);
                // Decodificar la imagen en un Bitmap escalado a View
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;
                bmOptions.inPurgeable = true;
                Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
                img_detalle.setImageBitmap(bitmap);
                imageFileName3 = "cenatel";
                File file1 = new File(mCurrentPhotoPath + imageFileName3 + ".jpg");
                try {
                    file1.createNewFile();
                    FileOutputStream out1 = new FileOutputStream(file1);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out1);//Convertimos la imagen a JPEG
                    out1.flush();
                    out1.close();
                    Toast.makeText(getActivity(), "Hecho 2!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                latituddet = lblLatitud.getText().toString();
                longituddet = lblLongitud.getText().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//---------------------------FIN Funciones IMAGEN-------------------------------------------------------------------

    @Override
    public void onLocationChanged(Location location) {
        lblLatitud.setText(String.valueOf(location.getLatitude()));
        lblLongitud.setText(String.valueOf(location.getLongitude()));
        lblPrecision.setText(String.valueOf(location.getAccuracy()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getActivity(), "Enabled new provider " + provider, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getActivity(), "Disabled provider " + provider, Toast.LENGTH_SHORT).show();
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

    //**************************************DIALOGO PERSONALIZADO***************************************
    public void DialogoPersonalizado(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        // Setting Dialog Title
        alertDialog.setTitle("Ya ha tomado uno o varios puntos");
        // Setting Dialog Message
        alertDialog.setMessage("Desea tomar otro?");
        alertDialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                acum++;
                dialog.cancel();
            }
        });
        // on pressing cancel button
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //hand.removeCallbacks(actualizar);
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }
    //************************************FIN DIALOGO PERSONALIZADO****************************************


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
