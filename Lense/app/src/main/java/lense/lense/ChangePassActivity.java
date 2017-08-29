package lense.lense;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ChangePassActivity extends AppCompatActivity {

    private EditText changePass;
    private EditText changePass2;
    private EditText changePassOld;
    private Button changePassButton;
    private int idUser;
    private Toolbar mToolbar;
    private SimpleProgressDialog dialog;
    private String pass;
    private String oldPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        changePass = (EditText) findViewById(R.id.change_password);
        changePass2 = (EditText) findViewById(R.id.change_password2);
        changePassButton = (Button) findViewById(R.id.change_password_button);
        changePassOld = (EditText) findViewById(R.id.change_password_old);

        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idUser = getIntent().getIntExtra("sessionId",0);
        dialog = SimpleProgressDialog.build(this, "Cargando...");

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        changePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(changePassOld.getText().toString().equals(""))
                {
                    Toast toast = Toast.makeText(ChangePassActivity.this, "Debes ingresar contraseña actual.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    if(changePass.getText().toString().equals("") || changePass2.getText().toString().equals(""))
                    {
                        Toast toast = Toast.makeText(ChangePassActivity.this, "Debes ingresar una nueva contraseña.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else
                    {
                        if(changePass.getText().toString().length()<7)
                        {
                            Toast toast = Toast.makeText(ChangePassActivity.this, "Contraseña demasiado corta.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        else
                        {
                            if(changePass.getText().toString().equals(changePass2.getText().toString()))
                            {
                                pass = changePass.getText().toString();
                                oldPass = changePassOld.getText().toString();
                                new ChangePassword().execute();
                            }
                            else
                            {
                                Toast toast = Toast.makeText(ChangePassActivity.this, "Contraseñas no coinciden.", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    }
                }
            }
        });
    }

    private class ChangePassword extends AsyncTask<Void,Void,Void>
    {
        SoapPrimitive resultado;
        @Override
        protected Void doInBackground(Void... params) {
            final String NAMESPACE = "http://tempuri.org/";
            final String URL = "http://www.lensechile.cl/lenseservice/Service1.svc";
            final String METHOD_NAME = "changePassword";
            final String SOAP_ACTION = "http://tempuri.org/IService1/changePassword";
            String Error;
            dialog.show();
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("idUsuario", idUser); // Paso parametros al WS
                request.addProperty("password", pass); // Paso parametros al WS
                request.addProperty("passwordAntigua", oldPass); // Paso parametros al WS

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
            if(resultado!=null)
            {
                if(Boolean.parseBoolean(resultado.toString()))
                {
                    Toast toast = Toast.makeText(ChangePassActivity.this, "La contraseña ha sido cambiada.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    Toast toast = Toast.makeText(ChangePassActivity.this, "Error al cambiar contraseña.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            else
            {
                Toast toast = Toast.makeText(ChangePassActivity.this, "Error al cambiar contraseña.", Toast.LENGTH_SHORT);
                toast.show();
            }
            dialog.dismiss();
            super.onPostExecute(result);
        }
    }


}
