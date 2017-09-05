package lense.lense;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import lense.lense.server_conection.Utils;

public class FacebookRegisterActivity extends AppCompatActivity {

    private String name;
    private String lastName;
    private String email;
    private String birthDay;
    private int facebookId;
    private String gender;
    private String password;
    private TextView passwordText;
    private TextView passwordText2;
    private Button createButton;
    private SimpleProgressDialog dialog;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_register);

        passwordText = (TextView) findViewById(R.id.change_password);
        passwordText2 = (TextView) findViewById(R.id.change_password2);
        createButton = (Button) findViewById(R.id.create_account_password_button);

        name = getIntent().getStringExtra("name");
        lastName = getIntent().getStringExtra("lastName");
        email = getIntent().getStringExtra("email");
        birthDay = getIntent().getStringExtra("birthDay");
        gender = getIntent().getStringExtra("gender");
        facebookId = getIntent().getIntExtra("facebookId",0);

        utils = new Utils();

        dialog = SimpleProgressDialog.build(this, "Cargando...");

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreatePass();
            }
        });
    }

    public void CreatePass()
    {
        if(passwordText.getText().toString().equals("") || passwordText2.getText().toString().equals(""))
        {
            Toast toast = Toast.makeText(FacebookRegisterActivity.this, "Debes ingresar una nueva contraseña.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            if(passwordText.getText().toString().length()<7)
            {
                Toast toast = Toast.makeText(FacebookRegisterActivity.this, "Contraseña demasiado corta.", Toast.LENGTH_SHORT);
                toast.show();
            }
            else
            {
                if(passwordText.getText().toString().equals(passwordText2.getText().toString()))
                {
                    password = passwordText.getText().toString();
                    new registerWithFacebook().execute();
                }
                else
                {
                    Toast toast = Toast.makeText(FacebookRegisterActivity.this, "Contraseñas no coinciden.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
    }

    private class registerWithFacebook extends AsyncTask<Void,Void,Void>
    {
        SoapPrimitive resultado;
        @Override
        protected Void doInBackground(Void... params) {
            final String NAMESPACE = "http://tempuri.org/";
            final String URL = "http://www.lensechile.cl/lenseservice/Service1.svc";
            final String METHOD_NAME = "Login";
            final String SOAP_ACTION = "http://tempuri.org/IService1/Login";
            String Error;
            dialog.show();
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("email", email); // Paso parametros al WS
                request.addProperty("name", name); // Paso parametros al WS
                request.addProperty("lastName", lastName); // Paso parametros al WS
                request.addProperty("macAddress", utils.getMACAddress("wlan0")); // Paso parametros al WS
                request.addProperty("gender", gender); // Paso parametros al WS
                request.addProperty("birthDay", birthDay); // Paso parametros al WS
                request.addProperty("facebookId", facebookId); // Paso parametros al WS


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

                if(Integer.parseInt(resultado.toString())>0)
                {

                    Intent i = new Intent(FacebookRegisterActivity.this,DictionaryActivity.class);
                    i.putExtra("sessionId",Integer.parseInt(resultado.toString()));
                    startActivity(i);
                    finish();
                }
                else
                {

                }

                super.onPostExecute(result);
            }
            else
            {
                Toast toast = Toast.makeText(FacebookRegisterActivity.this, "Estamos presentando problemas, por favor intentalo más tarde.", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
