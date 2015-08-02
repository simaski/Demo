package com.cenatel_demo_presidencia.desarrollo.demo;

/**
 * Created by San Casimiro on 08/07/2015.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class SplashScreen extends Activity {
    private final int SPLASH_TIME = 3000;
    int densidad;
    int ancho;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.splashscreen);
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        ancho = display.getWidth();
        densidad = metrics.densityDpi;

		/*
		 * Handler que quitará el splash screen después del SPLASH_TIME y creará
		 * un intent de la actividad principal.
		 */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
				/*
				 * Creamos un Intent que lanzará nuestra Actividad principal (en
				 * nuestro caso Main.java)
				 */
				/*if ((ancho >= 700 && ancho <= 900) && (densidad == 120 || densidad == 160)) {
						Intent miIntent = new Intent(SplashScreenActivity.this,
								BienvenidaActivity2.class);
						SplashScreenActivity.this.startActivity(miIntent);
						SplashScreenActivity.this.finish();
					} else {*/
                Intent miIntent = new Intent(SplashScreen.this,MainActivity.class);
                SplashScreen.this.startActivity(miIntent);
                SplashScreen.this.finish();
						/*
						 * Toast tosat = Toast.makeText(getApplicationContext(),
						 * "Ancho"+ancho, Toast.LENGTH_SHORT ); tosat.show();
						 */
                //System.out.println("Anchotytyt " + ancho);
                //	}
            }
        }, SPLASH_TIME);
    }

    @Override
    public void onPause() {
        super.onPause();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onResume() {
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

}