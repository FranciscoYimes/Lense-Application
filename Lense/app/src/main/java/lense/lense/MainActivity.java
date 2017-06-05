package lense.lense;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.MalformedURLException;

public class MainActivity extends AppCompatActivity {

    private boolean active = true;
    private int splashTime = 2000;
    private ImageView splashImage;
    private AlphaAnimation appearAnimation;
    private int status = 0;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        splashImage = (ImageView) findViewById(R.id.splashImage);
        appearAnimation = new AlphaAnimation(0.0f, 1.0f);
        appearAnimation.setDuration(2000);
        appearAnimation.setFillAfter(true);


        Thread splashThread = new Thread()
        {
            @Override
            public void run()
            {
                splashImage.startAnimation(appearAnimation);
                try
                {
                    int waited = 0;
                    while (active && (waited < splashTime))
                    {
                        sleep(100);
                        if (active)
                        {
                            waited += 100;
                        }

                    }
                }
                catch (InterruptedException e)
                {

                }
                finally
                {

                    do
                    {
                        if(status==2)
                        {
                            Intent intent = new Intent(MainActivity.this, TranslateActivity.class);
                            startActivity(intent);
                        }
                        if(status==1)
                        {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }while (status==0);

                    finish();
                }

            }
        };
        splashThread.start();
        new GetSessionStatus().execute();

    }

    private class GetSessionStatus extends AsyncTask<Void,Void,Void>
    {
        SoapPrimitive resultado;
        @Override
        protected Void doInBackground(Void... params) {
            final String NAMESPACE = "http://tempuri.org/";
            final String URL = "http://www.lensechile.cl/lenseservice/Service1.svc";
            final String METHOD_NAME = "getStatusSession";
            final String SOAP_ACTION = "http://tempuri.org/IService1/getStatusSession";
            String Error;
            try {

                // Modelo el request
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


                // Modelo el Sobre
                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.dotNet = true;
                sobre.setOutputSoapObject(request);

                // Modelo el transporte
                HttpTransportSE transporte = new HttpTransportSE(URL);
                // Llamada
                transporte.call(SOAP_ACTION, sobre);

                // Resultado

                resultado = (SoapPrimitive) sobre.getResponse();

                Log.i("Resultado", resultado.toString());


            } catch (MalformedURLException e) {
                e.printStackTrace();
                Error = e.toString();
            } catch (SoapFault soapFault) {
                soapFault.printStackTrace();
                Error = soapFault.toString();

            } catch (XmlPullParserException e) {
                e.printStackTrace();
                Error = e.toString();

            } catch (IOException e) {
                e.printStackTrace();
                Error = e.toString();

            }

            return null;
        }
        protected void onPostExecute(Void result)
        {
           // String response = resultado.toString();
            //boolean sessionStatus = Boolean.getBoolean(response);
            boolean sessionStatus = false;

            if(sessionStatus)
            {
                status = 2;
            }
            else
            {
                status = 1;
            }

            super.onPostExecute(result);
        }
    }


}
