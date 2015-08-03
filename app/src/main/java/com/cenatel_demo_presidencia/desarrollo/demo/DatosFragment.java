package com.cenatel_demo_presidencia.desarrollo.demo;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

                    new MyAsyncTask(getActivity()).execute();


                    sqlite = new SQLite(getActivity());
                    sqlite.abrir();
                    sqlite.addRegistro(et_nombreParticpanteR, et_fechaCapturaR, et_ubicacionR, et_funcionarioR, et_observacionR);
                    sqlite.cerrar();

                    et_funcionario.setText("");
                    et_nombreParticipante.setText("");
                    et_observacion.setText("");

                }
            }
        });

        return v;
    }

    public void CamposVacios(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        // Setting Dialog Title
        alertDialog.setTitle("Alerta!!!");
        // Setting Dialog Message
        alertDialog.setMessage("Uno o varios campos obligatorios no han sido llenados.");
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

    //@SuppressLint("NewApi")
    public class MyAsyncTask extends AsyncTask<String, Void, String> {

        private static final int REGISTRATION_TIMEOUT = 3 * 1000;
        private static final int WAIT_TIMEOUT = 30 * 1000;
        private final HttpClient httpclient = new DefaultHttpClient();

        final HttpParams params = httpclient.getParams();
        HttpResponse response;
        private String content =  null;
        private boolean error = false;

        private Context mContext;
        private int NOTIFICATION_ID = 1;
        private Notification mNotification;
        private NotificationManager mNotificationManager;


        public MyAsyncTask(Context context){

            this.mContext = context;

            //Get the notification manager
            mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        }


        protected void onPreExecute() {
            //createNotification("Data download is in progress","");
            //pb.setVisibility(View.GONE);
            //progressBar.show();
            Toast.makeText(mContext, "Envio de datos en progreso", Toast.LENGTH_LONG).show();

        }

        protected String doInBackground(String... urls) {

            String URL = null;
			/*String param1 = etNombre.getText().toString();
			String param2 = etClave.getText().toString();*/

            /*for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(50);
                    progressBar.setProgress(i + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/

            ArrayList<NameValuePair> postValores = new ArrayList<NameValuePair>();
            postValores.add(new BasicNameValuePair("nombreParticipante", et_nombreParticpanteR));
            postValores.add(new BasicNameValuePair("fechaCaptura", et_fechaCapturaR));
            postValores.add(new BasicNameValuePair("ubicacion", et_ubicacionR));
            postValores.add(new BasicNameValuePair("nombreFuncionario", et_funcionarioR));

            postValores.add(new BasicNameValuePair("observacion", et_observacionR));


            String respuesta = null;
            try {
                respuesta=CustomHttpClient.executeHttpPost("http://10.0.2.2:8000/demo/", postValores);
                //respuesta = CustomHttpClient.ejecutaHttpPost("http://cenatelgeo.fii.gob.ve/insertar.php", postValores);
                //res = respuesta.toString();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                Log.w("HTTP2:", e);
                content = e.getMessage();
                error = true;
                cancel(true);
            } catch (IOException e) {
                Log.w("HTTP3:",e );
                content = e.getMessage();
                error = true;
                cancel(true);
            }catch (Exception e) {
                Log.w("HTTP4:",e );
                content = e.getMessage();
                error = true;
                cancel(true);
            }


            return content;
        }

        protected void onProgressUpdate(Integer... progress){
            //pb.setProgress(progress[0]);
        }

        protected void onCancelled() {
            //createNotification("Error occured during data download",content);
            //pb.setVisibility(View.GONE);
            //progressBar.dismiss();
            Toast.makeText(mContext, "Error ocurrido durante la transmision de los datos. Por favor revisa tu conexion a Internet..!", Toast.LENGTH_LONG).show();
        }

        protected void onPostExecute(String content) {
            if (error) {
                //createNotification("Data download ended abnormally!",content);
                //pb.setVisibility(View.GONE);
                //progressBar.dismiss();
                Toast.makeText(mContext, "Envio de datos Anormales", Toast.LENGTH_LONG).show();
            } else {
                //createNotification("Data download is complete!","");
                //pb.setVisibility(View.GONE);
                //progressBar.dismiss();
                Toast.makeText(mContext, "Envio de datos Completo!!!!", Toast.LENGTH_LONG).show();
                //tvResultado.setText("Bienvenidos a JavaAndroid");
            }
        }

    }

}
