package lense.lense;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
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

import lense.lense.server_conection.Utils;

public class MainActivity extends AppCompatActivity {

    private boolean active = true;
    private int splashTime = 2000;
    private ImageView splashImage;
    private AlphaAnimation appearAnimation;
    private int status = 0;
    private Utils utils;
    private int id;

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

        utils = new Utils();

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
                    new GetSessionStatus().execute();
                }

            }
        };
        splashThread.start();
    }

    private class GetSessionStatus extends AsyncTask<Void,Void,Void>
    {
        SoapPrimitive resultado;
        @Override
        protected Void doInBackground(Void... params) {
            final String NAMESPACE = "http://tempuri.org/";
            final String URL = "http://www.lensechile.cl/lenseservice/Service1.svc";
            final String METHOD_NAME = "getSessionSatus";
            final String SOAP_ACTION = "http://tempuri.org/IService1/getSessionSatus";
            String Error;
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("macAddress", utils.getMACAddress("wlan0")); // Paso parametros al WS

                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.dotNet = true;
                sobre.setOutputSoapObject(request);

                HttpTransportSE transporte = new HttpTransportSE(URL);

                transporte.call(SOAP_ACTION, sobre);

                resultado = (SoapPrimitive) sobre.getResponse();


            } catch (MalformedURLException e) {
                e.printStackTrace();
                status = 1;
                Error = e.toString();
            } catch (SoapFault soapFault) {
                soapFault.printStackTrace();
                status = 1;
                Error = soapFault.toString();

            } catch (XmlPullParserException e) {
                e.printStackTrace();
                status = 1;
                Error = e.toString();

            } catch (IOException e) {
                e.printStackTrace();
                status = 1;
                Error = e.toString();
            }

            return null;
        }
        protected void onPostExecute(Void result)
        {

            if(resultado != null)
            {
                String response = resultado.toString();
                id = Integer.parseInt(response);


                if(id==0)
                {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this, DictionaryActivity.class);
                    intent.putExtra("sessionId",id);
                    startActivity(intent);
                    finish();
                }
            }
            else
            {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            super.onPostExecute(result);
        }
    }


}
