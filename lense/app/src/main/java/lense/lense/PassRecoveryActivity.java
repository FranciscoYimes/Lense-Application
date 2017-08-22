package lense.lense;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.MalformedURLException;

import lense.lense.Adapters.SimpleProgressDialog;

public class PassRecoveryActivity extends AppCompatActivity {

    private Button sendRecovery;
    private EditText mailRecovery;
    private String mail;
    private SimpleProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_recovery);

        mailRecovery = (EditText) findViewById(R.id.mail_recovery);
        sendRecovery = (Button) findViewById(R.id.send_button_recovery);

        dialog = SimpleProgressDialog.build(this, "Cargando...");

        sendRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mailRecovery.getText().toString().equals(""))
                {
                    Toast toast = Toast.makeText(PassRecoveryActivity.this, "Debe ingresar un correo.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    if (!isValidEmailAddress(mailRecovery.getText().toString()))
                    {
                        Toast toast = Toast.makeText(PassRecoveryActivity.this, "Debe ingresar un correo válido.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else
                    {
                        mail = mailRecovery.getText().toString();
                        new RecoveryPassword().execute();
                    }
                }
            }
        });
    }

    private class RecoveryPassword extends AsyncTask<Void,Void,Void>
    {
        SoapPrimitive resultado;
        @Override
        protected Void doInBackground(Void... params) {
            final String NAMESPACE = "http://tempuri.org/";
            final String URL = "http://www.lensechile.cl/lenseservice/Service1.svc";
            final String METHOD_NAME = "recoveryPassword";
            final String SOAP_ACTION = "http://tempuri.org/IService1/recoveryPassword";
            String Error;
            dialog.show();
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("mail", mail); // Paso parametros al WS

                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.dotNet = true;
                sobre.setOutputSoapObject(request);

                HttpTransportSE transporte = new HttpTransportSE(URL);

                transporte.call(SOAP_ACTION, sobre);

                resultado = (SoapPrimitive) sobre.getResponse();


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
            dialog.dismiss();
            if(resultado != null)
            {
                int res = Integer.parseInt(resultado.toString());
                if(res == 1)
                {
                    Toast toast = Toast.makeText(PassRecoveryActivity.this, "Te hemos enviado un correo electrónico.", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                }
                else
                {
                    if(res == 0)
                    {
                        Toast toast = Toast.makeText(PassRecoveryActivity.this, "Estamos presentando problemas, por favor intentalo más tarde.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    if(res == -1)
                    {
                        Toast toast = Toast.makeText(PassRecoveryActivity.this, "No hemos encontrado la dirección de correo electrónico.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
            else
            {
                Toast toast = Toast.makeText(PassRecoveryActivity.this, "Estamos presentando problemas, por favor intentalo más tarde.", Toast.LENGTH_SHORT);
                toast.show();
            }

            super.onPostExecute(result);
        }
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
