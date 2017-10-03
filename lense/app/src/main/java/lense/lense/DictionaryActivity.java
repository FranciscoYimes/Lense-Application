package lense.lense;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import lense.lense.Adapters.Region;
import lense.lense.Adapters.SimpleProgressDialog;
import lense.lense.server_conection.Utils;

public class DictionaryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final static String DEFAULT_URL = "Default.gif";
    private int idPalabra = 0;
    private int idRegion;
    private int sessionId;

    private String ruta;
    private String resolutionDir;
    private Region[] listaRegiones;
    private Utils utils;
    private String macAdress;
    private String palabra;
    private SimpleProgressDialog dialog;
    private Button goToABC;
    private Button backToSearch;
    private LinearLayout abcLayout;
    private LinearLayout searchLayout;
    private WebView webView;
    private EditText translateText;
    private TextView mailText;
    private TextView nameText;
    private TextView regionText;
    private TextView categoryText;
    private TextView subCategoryText;
    private TextView resolution;
    private Button letter_a;
    private Button letter_b;
    private Button letter_c;
    private Button letter_d;
    private Button letter_e;
    private Button letter_f;
    private Button letter_g;
    private Button letter_h;
    private Button letter_i;
    private Button letter_j;
    private Button letter_k;
    private Button letter_l;
    private Button letter_m;
    private Button letter_n;
    private Button letter_nn;
    private Button letter_o;
    private Button letter_p;
    private Button letter_q;
    private Button letter_r;
    private Button letter_s;
    private Button letter_t;
    private Button letter_u;
    private Button letter_v;
    private Button letter_w;
    private Button letter_x;
    private Button letter_y;
    private Button letter_z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Typeface walkwayBold = Typeface.createFromAsset(getAssets(), "WalkwayBold.ttf");
        translateText = (EditText) findViewById(R.id.translate_text);

        categoryText = (TextView) findViewById(R.id.category_text);
        subCategoryText = (TextView) findViewById(R.id.sub_category_text);
        abcLayout = (LinearLayout) findViewById(R.id.abc_layout);
        searchLayout = (LinearLayout) findViewById(R.id.search_layout);
        backToSearch = (Button) findViewById(R.id.back_to_search);
        goToABC = (Button) findViewById(R.id.go_to_abc);
        categoryText.setTypeface(walkwayBold);
        subCategoryText.setTypeface(walkwayBold);
        webView = (WebView) findViewById(R.id.web);
        resolution = (TextView) findViewById(R.id.resolution);

        letter_a = (Button) findViewById(R.id.a);
        letter_b = (Button) findViewById(R.id.b);
        letter_c = (Button) findViewById(R.id.c);
        letter_d = (Button) findViewById(R.id.d);
        letter_e = (Button) findViewById(R.id.e);
        letter_f = (Button) findViewById(R.id.f);
        letter_g = (Button) findViewById(R.id.g);
        letter_h = (Button) findViewById(R.id.h);
        letter_i = (Button) findViewById(R.id.i);
        letter_j = (Button) findViewById(R.id.j);
        letter_k = (Button) findViewById(R.id.k);
        letter_l = (Button) findViewById(R.id.l);
        letter_m = (Button) findViewById(R.id.m);
        letter_n = (Button) findViewById(R.id.n);
        letter_nn = (Button) findViewById(R.id.nn);
        letter_o = (Button) findViewById(R.id.o);
        letter_p = (Button) findViewById(R.id.p);
        letter_q = (Button) findViewById(R.id.q);
        letter_r = (Button) findViewById(R.id.r);
        letter_s = (Button) findViewById(R.id.s);
        letter_t = (Button) findViewById(R.id.t);
        letter_u = (Button) findViewById(R.id.u);
        letter_v = (Button) findViewById(R.id.v);
        letter_w = (Button) findViewById(R.id.w);
        letter_x = (Button) findViewById(R.id.x);
        letter_y = (Button) findViewById(R.id.y);
        letter_z = (Button) findViewById(R.id.z);

        letter_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("A");
            }
        });
        letter_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("B");
            }
        });
        letter_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("C");
            }
        });
        letter_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("D");
            }
        });
        letter_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("E");
            }
        });
        letter_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("F");
            }
        });
        letter_g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("G");
            }
        });
        letter_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("H");
            }
        });
        letter_i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("I");
            }
        });
        letter_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("J");
            }
        });
        letter_k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("K");
            }
        });
        letter_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("L");
            }
        });
        letter_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("M");
            }
        });
        letter_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("N");
            }
        });
        letter_nn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("Ñ");
            }
        });
        letter_o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("O");
            }
        });
        letter_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("P");
            }
        });
        letter_q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("Q");
            }
        });
        letter_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("R");
            }
        });
        letter_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("S");
            }
        });
        letter_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("T");
            }
        });
        letter_u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("U");
            }
        });
        letter_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("V");
            }
        });
        letter_w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("W");
            }
        });
        letter_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("X");
            }
        });
        letter_y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("Y");
            }
        });
        letter_z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Translate("Z");
            }
        });

        translateText.setTypeface(walkwayBold);

        dialog = SimpleProgressDialog.build(this, "Cargando...");

        ruta = " ";

        sessionId = getIntent().getIntExtra("sessionId",0);
        utils = new Utils();
        macAdress = utils.getMACAddress("wlan0");

        GetResDir();

        new GetRegiones().execute();

        goToABC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchLayout.setVisibility(View.GONE);
                abcLayout.setVisibility(View.VISIBLE);
            }
        });

        backToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abcLayout.setVisibility(View.GONE);
                searchLayout.setVisibility(View.VISIBLE);
            }
        });

        translateText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (i == EditorInfo.IME_ACTION_SEARCH) {

                    if(!translateText.getText().toString().equals(""))
                    {
                        palabra = translateText.getText().toString();
                        new PalabrasWS().execute();
                    }
                    return false;
                }
                return false;
            }
        });


        new InfoUsuario().execute();

        Button translateButton = (Button) findViewById(R.id.translateButton);
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!translateText.getText().toString().equals(""))
                {
                    palabra = translateText.getText().toString();
                    new PalabrasWS().execute();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //************************************************************************
        //******* MENU ***********************************************************

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View hView = navigationView.getHeaderView(0);

        nameText = (TextView) hView.findViewById(R.id.username);
        mailText = (TextView) hView.findViewById(R.id.mail);
        regionText = (TextView) hView.findViewById(R.id.region);

        navigationView.setNavigationItemSelectedListener(this);
        hideKeyboardFrom();

        //*************************************************************************
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
            Intent i = new Intent(DictionaryActivity.this,SenaticaActivity.class);
            i.putExtra("idRegion",idRegion);
            i.putExtra("idUsuario",sessionId);
            startActivityForResult(i,0);
        } else if (id == R.id.category) {

            Intent i = new Intent(DictionaryActivity.this,CategoryActivity.class);
            i.putExtra("idRegion",idRegion);
            i.putExtra("idUsuario",sessionId);
            startActivityForResult(i,0);

        } else if (id == R.id.logout_option) {
            new Logout().execute();
        }
        else if (id == R.id.change_region) {
            showRegionsDialog();
        }
        else if (id == R.id.change_pass) {
            Intent i = new Intent(DictionaryActivity.this,ChangePassActivity.class);
            i.putExtra("sessionId",sessionId);
            startActivityForResult(i,0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setImageGif(String text)
    {

        webView.loadUrl(ruta+resolutionDir+text);
        webView.setVisibility(View.VISIBLE);

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
                request.addProperty("idRegion", idRegion); // Paso parametros al WS
                request.addProperty("idUsuario", sessionId); // Paso parametros al WS


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
                if(resultado.getProperty(0)!=null)
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
                    Toast toast = Toast.makeText(DictionaryActivity.this, "Error al buscar.", Toast.LENGTH_SHORT);
                    toast.show();
                    categoryText.setText("-");
                    subCategoryText.setText("-");
                    setImageGif(DEFAULT_URL);
                }
            }
            else
            {
                Toast toast = Toast.makeText(DictionaryActivity.this, "Lo sentimos, palabra no encontrada.", Toast.LENGTH_SHORT);
                toast.show();
                categoryText.setText("-");
                subCategoryText.setText("-");
                setImageGif(DEFAULT_URL);
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
                if(resultado.getProperty("Nombre")!=null) translateText.setText(resultado.getProperty("Nombre").toString());
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

    private class GetRegiones extends AsyncTask<Void,Void,Void>
    {
        SoapObject resultado;
        @Override
        protected Void doInBackground(Void... params) {
            final String NAMESPACE = "http://tempuri.org/";
            final String URL = "http://www.lensechile.cl/lenseservice/Service1.svc";
            final String METHOD_NAME = "regiones";
            final String SOAP_ACTION = "http://tempuri.org/IService1/regiones";
            String Error;
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
                AddRegiones(resultado);
            }
            else
            {

            }
            super.onPostExecute(result);
        }
    }

    public void AddRegiones(SoapObject soapObject)
    {
        listaRegiones = new Region[soapObject.getPropertyCount()];

        SoapObject region;
        for(int i=0;i<soapObject.getPropertyCount();i++)
        {
            region = (SoapObject) soapObject.getProperty(i);
            listaRegiones[i] = new Region(region.getProperty(1).toString(),Integer.parseInt(region.getProperty(0).toString()));
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
                    if(idRegion<=listaRegiones.length) regionText.setText(getRegionName(idRegion));
                    else regionText.setText("Error");
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

    public String getRegionName(int id)
    {
        for(int i=0;i<listaRegiones.length;i++)
        {
            if(listaRegiones[i].idRegion==id) return listaRegiones[i].nombreRegion;
        }
        return "Region no encontrada";
    }

    public void showRegionsDialog()
    {
        final CharSequence[] items = new CharSequence[listaRegiones.length];
        final int[] ids = new int[listaRegiones.length];
        for(int i=0;i<listaRegiones.length;i++)
        {
            items[i] = listaRegiones[i].nombreRegion;
            ids[i] = listaRegiones[i].idRegion;

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

    private class InfoUsuario extends AsyncTask<Void,Void,Void>
    {
        SoapObject resultado;
        SoapObject respuesta;
        @Override
        protected Void doInBackground(Void... params) {
            final String NAMESPACE = "http://tempuri.org/";
            final String URL = "http://www.lensechile.cl/lenseservice/Service1.svc";
            final String METHOD_NAME = "infoUsuario";
            final String SOAP_ACTION = "http://tempuri.org/IService1/infoUsuario";
            String Error;
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("idUsuario", sessionId); // Paso parametros al WS

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
                idRegion = Integer.parseInt(resultado.getProperty("Region").toString());
                nameText.setText(resultado.getProperty("Nombre").toString()+" "+resultado.getProperty("Apellido").toString());
                mailText.setText(resultado.getProperty("Mail").toString());
                int reg = Integer.parseInt(resultado.getProperty("Region").toString());

                if(resultado.getProperty("Ruta") !=null) ruta = resultado.getProperty("Ruta").toString();

                if(idRegion<=listaRegiones.length) regionText.setText(getRegionName(idRegion));
                else regionText.setText("Error");

                setImageGif(DEFAULT_URL);
            }
            else
            {
                idRegion = 1;
            }
            super.onPostExecute(result);
        }
    }

    public void GetResDir()
    {
        if(resolution.getText().toString().equals("448"))
        {
            resolutionDir = "448x336/";
        }
        else
        {
            if(resolution.getText().toString().equals("320"))
            {
                resolutionDir = "320x240/";
            }
            else
            {
                resolutionDir = "256x192/";
            }
        }
    }

    public void Translate(String letter)
    {
        if(!letter.equals(""))
        {
            palabra = letter;
            new PalabrasWS().execute();
        }
    }
    public void hideKeyboardFrom() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
