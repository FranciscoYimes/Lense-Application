package lense.lense;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.koushikdutta.ion.Ion;

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

public class DictionaryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView imageView;
    private EditText translateText;
    private TextView categoryText;
    private TextView subCategoryText;
    private int sessionId;
    private Utils utils;
    private String macAdress;
    private String palabra;
    private int idPalabra = 0;
    private int idRegion;
    private SimpleProgressDialog dialog;
    private final static String DEFAULT_URL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Typeface walkwayBold = Typeface.createFromAsset(getAssets(), "WalkwayBold.ttf");
        translateText = (EditText) findViewById(R.id.translate_text);
        translateText.setTypeface(walkwayBold);

        categoryText = (TextView) findViewById(R.id.category_text);
        subCategoryText = (TextView) findViewById(R.id.sub_category_text);
        categoryText.setTypeface(walkwayBold);
        subCategoryText.setTypeface(walkwayBold);

        dialog = SimpleProgressDialog.build(this, "Cargando...");

        sessionId = getIntent().getIntExtra("sessionId",0);
        utils = new Utils();
        macAdress = utils.getMACAddress("wlan0");


        Button translateButton = (Button) findViewById(R.id.translateButton);
        translateButton.setTypeface(walkwayBold);
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setImageGif(translateText.getText().toString());
                palabra = translateText.getText().toString();
                new PalabrasWS().execute();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dictionary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.list) {
            Intent i = new Intent(DictionaryActivity.this,ListActivity.class);
            startActivityForResult(i,0);
        } else if (id == R.id.category) {

            Intent i = new Intent(DictionaryActivity.this,CategoryActivity.class);
            startActivityForResult(i,0);

        } else if (id == R.id.logout_option) {
            new Logout().execute();
        }
        else if (id == R.id.change_region) {
            new Regiones().execute();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setImageGif(String text)
    {
        imageView = (ImageView) findViewById(R.id.translateImageView);

        Ion.with(imageView).load(text);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (data != null) {
            idPalabra = Integer.parseInt(data.getData().toString());
            new InfoPalabra().execute();
        }
    }


    private class Logout extends AsyncTask<Void,Void,Void>
    {
        SoapPrimitive resultado;
        @Override
        protected Void doInBackground(Void... params) {
            final String NAMESPACE = "http://tempuri.org/";
            final String URL = "http://www.lensechile.cl/lenseservice/Service1.svc";
            final String METHOD_NAME = "logout";
            final String SOAP_ACTION = "http://tempuri.org/IService1/logout";
            String Error;
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
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

            if(resultado != null)
            {
                String response = resultado.toString();
                boolean resp = Boolean.parseBoolean(response);

                if(resp)
                {
                    Intent i = new Intent(DictionaryActivity.this,LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {

                }
                super.onPostExecute(result);
            }
        }
    }
    private class PalabrasWS extends AsyncTask<Void,Void,Void>
    {
        SoapObject resultado;
        SoapObject respuesta;
        @Override
        protected Void doInBackground(Void... params) {
            final String NAMESPACE = "http://tempuri.org/";
            final String URL = "http://www.lensechile.cl/lenseservice/Service1.svc";
            final String METHOD_NAME = "Palabra";
            final String SOAP_ACTION = "http://tempuri.org/IService1/Palabra";
            String Error;
            dialog.show();
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("palabra", palabra); // Paso parametros al WS

                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.dotNet = true;
                sobre.setOutputSoapObject(request);

                HttpTransportSE transporte = new HttpTransportSE(URL);

                transporte.call(SOAP_ACTION, sobre);

                resultado = (SoapObject) sobre.getResponse();

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
                respuesta = (SoapObject) resultado.getProperty(0);

                int res = Integer.parseInt(respuesta.getProperty("SiNo").toString());
                if(res==0)
                {
                    if(respuesta.getProperty("Url")!=null)
                        setImageGif(respuesta.getProperty("Url").toString());
                    else
                    {
                        categoryText.setText("-");
                        subCategoryText.setText("-");
                        setImageGif(DEFAULT_URL);
                    }

                    if(respuesta.getProperty("Categoria")!=null)
                        categoryText.setText("Categoría: "+respuesta.getProperty("Categoria").toString());
                    else categoryText.setText("Categoría: Sin Información");

                    if(respuesta.getProperty("SubCategoria")!=null)
                        subCategoryText.setText("Sub Categoría: "+respuesta.getProperty("SubCategoria").toString());
                    else subCategoryText.setText("Sub Categoría: Sin Información");

                }
                else    // mas de un caso
                {
                    showDialog(resultado);
                }
            }
            else
            {
                Toast toast = Toast.makeText(DictionaryActivity.this, "Lo sentimos, palabra no encontrada.", Toast.LENGTH_SHORT);
                toast.show();
                categoryText.setText("-");
                subCategoryText.setText("-");
            }
            dialog.dismiss();
            super.onPostExecute(result);
        }
    }

    public void showDialog(SoapObject soapObject)
    {
        final CharSequence[] items = new CharSequence[soapObject.getPropertyCount()];
        final CharSequence[] urls = new CharSequence[soapObject.getPropertyCount()];
        final CharSequence[] categoria = new CharSequence[soapObject.getPropertyCount()];
        final CharSequence[] subCategoria = new CharSequence[soapObject.getPropertyCount()];
        SoapObject infoPalabra;
        for(int i=0;i<soapObject.getPropertyCount();i++)
        {
            infoPalabra = (SoapObject) soapObject.getProperty(i);
            items[i] = (infoPalabra.getProperty("Categoria").toString())+", "+infoPalabra.getProperty("SubCategoria").toString();
            if (infoPalabra.getProperty("Url")!=null) urls[i] = infoPalabra.getProperty("Url").toString();
            else urls[i] = "";

            if (infoPalabra.getProperty("Categoria")!=null) categoria[i] = infoPalabra.getProperty("Categoria").toString();
            else urls[i] = "";

            if (infoPalabra.getProperty("SubCategoria")!=null) subCategoria[i] = infoPalabra.getProperty("SubCategoria").toString();
            else urls[i] = "";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(DictionaryActivity.this);
        builder.setTitle("Selecciona una categoría:");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                setImageGif(urls[item].toString());
                categoryText.setText(categoria[item]);
                subCategoryText.setText(subCategoria[item]);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private class InfoPalabra extends AsyncTask<Void,Void,Void>
    {
        SoapObject resultado;
        @Override
        protected Void doInBackground(Void... params) {
            final String NAMESPACE = "http://tempuri.org/";
            final String URL = "http://www.lensechile.cl/lenseservice/Service1.svc";
            final String METHOD_NAME = "informacionPalabra";
            final String SOAP_ACTION = "http://tempuri.org/IService1/informacionPalabra";
            String Error;
            dialog.show();
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("idPalabra", idPalabra); // Paso parametros al WS

                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.dotNet = true;
                sobre.setOutputSoapObject(request);

                HttpTransportSE transporte = new HttpTransportSE(URL);

                transporte.call(SOAP_ACTION, sobre);

                resultado = (SoapObject) sobre.getResponse();

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
                resultado = (SoapObject) resultado.getProperty(0);

                if(resultado.getProperty("Url")!=null)
                    setImageGif(resultado.getProperty("Url").toString());
                else
                {
                    categoryText.setText("-");
                    subCategoryText.setText("-");
                    setImageGif(DEFAULT_URL);
                }
                if(resultado.getProperty("Categoria")!=null)
                    categoryText.setText("Categoría: "+resultado.getProperty("Categoria").toString());
                else categoryText.setText("Categoría: Sin Información");
                if(resultado.getProperty("SubCategoria")!=null)
                    subCategoryText.setText("Sub Categoría: "+resultado.getProperty("SubCategoria").toString());
                else subCategoryText.setText("Sub Categoría: Sin Información");
            }
            else
            {
                Toast toast = Toast.makeText(DictionaryActivity.this, "Lo sentimos, palabra no encontrada.", Toast.LENGTH_SHORT);
                toast.show();
                categoryText.setText("-");
                subCategoryText.setText("-");
            }
            dialog.dismiss();
            super.onPostExecute(result);
        }
    }

    private class Regiones extends AsyncTask<Void,Void,Void>
    {
        SoapObject resultado;
        @Override
        protected Void doInBackground(Void... params) {
            final String NAMESPACE = "http://tempuri.org/";
            final String URL = "http://www.lensechile.cl/lenseservice/Service1.svc";
            final String METHOD_NAME = "regiones";
            final String SOAP_ACTION = "http://tempuri.org/IService1/regiones";
            String Error;
            dialog.show();
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.dotNet = true;
                sobre.setOutputSoapObject(request);

                HttpTransportSE transporte = new HttpTransportSE(URL);

                transporte.call(SOAP_ACTION, sobre);

                resultado = (SoapObject) sobre.getResponse();

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
                showRegionsDialog(resultado);
            }
            else
            {

            }
            dialog.dismiss();
            super.onPostExecute(result);
        }
    }

    private class ModificarRegion extends AsyncTask<Void,Void,Void>
    {
        SoapPrimitive resultado;
        @Override
        protected Void doInBackground(Void... params) {
            final String NAMESPACE = "http://tempuri.org/";
            final String URL = "http://www.lensechile.cl/lenseservice/Service1.svc";
            final String METHOD_NAME = "modificarRegion";
            final String SOAP_ACTION = "http://tempuri.org/IService1/modificarRegion";
            String Error;
            dialog.show();
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                request.addProperty("idUsuario", sessionId); // Paso parametros al WS
                request.addProperty("idNuevaRegion", idRegion); // Paso parametros al WS

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
                    Toast toast = Toast.makeText(DictionaryActivity.this, "La región ha sido cambiada.", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    Toast toast = Toast.makeText(DictionaryActivity.this, "Error al cambiar región.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            else
            {
                Toast toast = Toast.makeText(DictionaryActivity.this, "Error al cambiar región.", Toast.LENGTH_SHORT);
                toast.show();
            }
            dialog.dismiss();
            super.onPostExecute(result);
        }
    }

    public void showRegionsDialog(SoapObject soapObject)
    {
        final CharSequence[] items = new CharSequence[soapObject.getPropertyCount()];
        final int[] ids = new int[soapObject.getPropertyCount()];
        SoapObject region;
        for(int i=0;i<soapObject.getPropertyCount();i++)
        {
            region = (SoapObject) soapObject.getProperty(i);
            items[i] = region.getProperty(1).toString();
            ids[i] = Integer.parseInt(region.getProperty(0).toString());

        }

        AlertDialog.Builder builder = new AlertDialog.Builder(DictionaryActivity.this);
        builder.setTitle("Selecciona tu Región:");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                idRegion = ids[item];
                new ModificarRegion().execute();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
