package lense.lense;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;
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

public class LoginActivity extends AppCompatActivity {

    private EditText password;
    private EditText mail;
    private TextView forgotPass;
    private Animation shake;
    private Button loginButton;
    private ImageButton facebookLogin;
    private String mailText;
    private String passText;
    private ImageButton signUpButton;
    private String macAdress;
    private Utils utils;
    private SimpleProgressDialog dialog;
    private LoginButton facebookButton;
    private CallbackManager callbackManager;
    private String email;
    private String name;
    private String lastName;
    private String birthDay;
    private String gender;
    private int facebookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.login__button);
        facebookButton = (LoginButton) findViewById(R.id.facebook_button);
        mail = (EditText) findViewById(R.id.login__mail);
        password = (EditText) findViewById(R.id.login__password);
        signUpButton = (ImageButton) findViewById(R.id.login__sign_up);
        forgotPass = (TextView) findViewById(R.id.LoginForgotPassword);
        facebookLogin = (ImageButton) findViewById(R.id.facebook_login);
        Typeface walkwayBold = Typeface.createFromAsset(getAssets(), "WalkwayBold.ttf");
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logOut();

        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facebookButton.performClick();
            }
        });

        facebookButton.setReadPermissions("email");
        facebookButton.setReadPermissions("user_birthday");
        facebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    email = object.getString("email");
                                    gender = object.getString("gender");
                                    birthDay = object.getString("birthday");
                                    name = object.getString("first_name");
                                    facebookId = object.getInt("id");
                                    lastName = object.getString("last_name");

                                    if(!email.equals("") && email!=null)
                                    {
                                        new sendEmailInfo().execute();
                                    }
                                    else
                                    {
                                        Toast toast = Toast.makeText(LoginActivity.this, "Estamos presentando problemas, por favor intentalo más tarde.", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,first_name,last_name");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast toast = Toast.makeText(LoginActivity.this, "Cancelado.", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast toast = Toast.makeText(LoginActivity.this, "Error.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        utils = new Utils();

        dialog = SimpleProgressDialog.build(this, "Cargando...");

        macAdress = utils.getMACAddress("wlan0");

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });

        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

        mail.setTypeface(walkwayBold);
        password.setTypeface(walkwayBold);
        loginButton.setTypeface(walkwayBold);
        forgotPass.setTypeface(walkwayBold);

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,PassRecoveryActivity.class);
                startActivity(i);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mailText = mail.getText().toString();
                passText = password.getText().toString();

                new sendLogginInfo().execute();
            }
        });
    }

    private class sendLogginInfo extends AsyncTask<Void,Void,Void>
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
                request.addProperty("mail", mailText); // Paso parametros al WS
                request.addProperty("password", passText); // Paso parametros al WS
                request.addProperty("macAddress", macAdress); // Paso parametros al WS

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
            dialog.dismiss();
            if(resultado != null)
            {
                String response = resultado.toString();
                int resp = Integer.parseInt(response);

                if(resp!=0)
                {
                    Intent i = new Intent(LoginActivity.this,DictionaryActivity.class);
                    i.putExtra("sessionId",resp);
                    startActivity(i);
                    finish();
                }
                else
                {
                    mail.startAnimation(shake);
                    password.startAnimation(shake);
                    loginButton.startAnimation(shake);
                }
                super.onPostExecute(result);
            }
            else
            {
                Toast toast = Toast.makeText(LoginActivity.this, "Estamos presentando problemas, por favor intentalo más tarde.", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private class sendEmailInfo extends AsyncTask<Void,Void,Void>
    {
        SoapPrimitive resultado;
        @Override
        protected Void doInBackground(Void... params) {
            final String NAMESPACE = "http://tempuri.org/";
            final String URL = "http://www.lensechile.cl/lenseservice/Service1.svc";
            final String METHOD_NAME = "comprobarMail";
            final String SOAP_ACTION = "http://tempuri.org/IService1/comprobarMail";
            String Error;
            dialog.show();
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("mail", email); // Paso parametros al WS
                request.addProperty("macAddress", macAdress); // Paso parametros al WS

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
            dialog.dismiss();
            if(resultado != null)
            {

                if(Integer.parseInt(resultado.toString())>0)
                {

                    Intent i = new Intent(LoginActivity.this,DictionaryActivity.class);
                    i.putExtra("sessionId",Integer.parseInt(resultado.toString()));
                    startActivity(i);
                    finish();
                }
                else
                {
                    if(!email.equals("") && !name.equals("") && !lastName.equals(""))
                    {
                        Intent i = new Intent(LoginActivity.this,FacebookRegisterActivity.class);
                        i.putExtra("email",email);
                        i.putExtra("name",name);
                        i.putExtra("lastName",lastName);
                        i.putExtra("birthDay",birthDay);
                        i.putExtra("gender",gender);
                        i.putExtra("facebookId",facebookId);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Toast toast = Toast.makeText(LoginActivity.this, "Estamos presentando problemas, por favor intentalo más tarde.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

                super.onPostExecute(result);
            }
            else
            {
                Toast toast = Toast.makeText(LoginActivity.this, "Estamos presentando problemas, por favor intentalo más tarde.", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
