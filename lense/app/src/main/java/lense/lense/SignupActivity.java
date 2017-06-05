package lense.lense;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class SignupActivity extends AppCompatActivity {

    private EditText mail;
    private EditText name;
    private EditText lastName;
    private EditText password;
    private EditText password2;
    private Button signUpButton;
    private UserData userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mail = (EditText) findViewById(R.id.signup__mail);
        name = (EditText) findViewById(R.id.signup__name);
        lastName = (EditText) findViewById(R.id.signup_last_name);
        password = (EditText) findViewById(R.id.signup__password);
        password2 = (EditText) findViewById(R.id.signup__confirm_password);
        signUpButton = (Button) findViewById(R.id.signup__submit);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EditTextIsEmpty())
                {
                    if(password.getText().toString().equals(password2.getText().toString()))
                    {
                        userData = new UserData(name.getText().toString(),lastName.getText().toString(),mail.getText().toString(),password.getText().toString());
                        new sendLogginInfo().execute();
                    }
                    else
                    {
                        //mensaje "no coinciden las contraseñas"
                    }
                }
            }
        });
    }



    public boolean EditTextIsEmpty()
    {
        if(mail.getText().toString().equals("") || password.getText().toString().equals("") || password2.getText().toString().equals("") || name.getText().toString().equals("") || lastName.getText().toString().equals(""))
            return true;
        else
            return false;
    }

    private class sendLogginInfo extends AsyncTask<Void,Void,Void>
    {
        SoapPrimitive resultado;
        @Override
        protected Void doInBackground(Void... params) {
            final String NAMESPACE = "http://tempuri.org/";
            final String URL = "http://www.lensechile.cl/lenseservice/Service1.svc";
            final String METHOD_NAME = "AddUsuario";
            final String SOAP_ACTION = "http://tempuri.org/IService1/AddUsuario";
            String Error;
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("newUsuario", userData); // Paso parametros al WS


                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.dotNet = true;
                sobre.setOutputSoapObject(request);

                HttpTransportSE transporte = new HttpTransportSE(URL);

                transporte.call(SOAP_ACTION, sobre);

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
            String response = resultado.toString();
            Boolean resp = Boolean.parseBoolean(response);

            if(resp)
            {
                finish();
            }
            else
            {

            }
            super.onPostExecute(result);
        }
    }
}