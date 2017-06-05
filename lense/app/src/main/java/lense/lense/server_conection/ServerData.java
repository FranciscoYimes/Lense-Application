package lense.lense.server_conection;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.MalformedURLException;

import lense.lense.R;

/**
 * Created by Paulo on 06/05/2016.
 */

public class ServerData{

    String result="";
    String result2="";
    EditText translateText;

    public String getMessage()
    {

        new MyTask2().execute();

        return result;
    }

    private class MyTask2 extends AsyncTask<Void,Void,Void>
    {


        SoapPrimitive resultado;
        @Override
        protected Void doInBackground(Void... params) {
            final String NAMESPACE = "http://tempuri.org/";
            final String URL = "http://www.lensechile.cl/lenseservice/Service1.svc";
            final String METHOD_NAME = "pruebapancho";
            final String SOAP_ACTION = "http://tempuri.org/IService1/pruebapancho";
            String Error;
            try {

                // Modelo el request
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("Param", "valor"); // Paso parametros al WS

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

                result = resultado.toString();

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
            result2 = resultado.toString();
            super.onPostExecute(result);
        }
    }



  public void intento(Activity activity)
  {
      translateText = (EditText)activity.findViewById(R.id.translate_text);

      new MyTask().execute();

  }
    private class MyTask extends AsyncTask<Void,Void,Void>
    {


        SoapPrimitive resultado;
        @Override
        protected Void doInBackground(Void... params) {
            final String NAMESPACE = "http://tempuri.org/";
            final String URL = "http://www.lensechile.cl/lenseservice/Service1.svc";
            final String METHOD_NAME = "pruebapancho";
            final String SOAP_ACTION = "http://tempuri.org/IService1/pruebapancho";
            String Error;
            try {

                // Modelo el request
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("Param", "valor"); // Paso parametros al WS

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

                result = resultado.toString();

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
            translateText.setText(resultado.toString());
            super.onPostExecute(result);
        }
    }

}
