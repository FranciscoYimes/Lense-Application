package lense.lense;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.MalformedURLException;

import lense.lense.Adapters.SimpleProgressDialog;

public class SenaticaOnClickActivity extends AppCompatActivity {

    private final static String DEFAULT_URL = "https://raw.githubusercontent.com/FranciscoYimes/Lense-Application/master/as.gif";
    private String letter;
    private LinearLayout contentSenatico;
    private LinearLayout contentSignExample;
    private LinearLayout lineLayoutExample;
    private ImageView signImageExample;
    private TextView wordExample;
    private TextView categoryExample;
    private SimpleProgressDialog dialog;
    private int idRegion;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senatica_on_click);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dialog = SimpleProgressDialog.build(this, "Cargando...");

        letter = getIntent().getStringExtra("letter");
        idRegion = getIntent().getIntExtra("idRegion",0);

        contentSenatico = (LinearLayout) findViewById(R.id.content_senatico);
        contentSignExample = (LinearLayout) findViewById(R.id.content_image_senatico);
        lineLayoutExample = (LinearLayout) findViewById(R.id.line_example);
        signImageExample = (ImageView) findViewById(R.id.word_image_senatico);
        wordExample = (TextView) findViewById(R.id.word_text_senatico);
        categoryExample = (TextView) findViewById(R.id.category_text_senatico);

        new GetListWords().execute();

    }

    public void AddSign(String word,String cat,String url)
    {
        LinearLayout contentSign = new LinearLayout(getApplicationContext());
        LinearLayout lineLayout = new LinearLayout(getApplicationContext());
        TextView wordText = new TextView(getApplicationContext());
        TextView categoryText = new TextView(getApplicationContext());
        ImageView imageView = new ImageView(getApplicationContext());

        contentSign.setLayoutParams(contentSignExample.getLayoutParams());
        lineLayout.setLayoutParams(lineLayoutExample.getLayoutParams());
        wordText.setLayoutParams(wordExample.getLayoutParams());
        categoryText.setLayoutParams(categoryExample.getLayoutParams());
        imageView.setLayoutParams(signImageExample.getLayoutParams());

        contentSign.setOrientation(LinearLayout.VERTICAL);
        contentSign.setBackground(getResources().getDrawable(R.drawable.accent_button));

        if(url ==null) Ion.with(imageView).load(DEFAULT_URL);
        else Ion.with(imageView).load(url);

        contentSign.addView(imageView);

        wordText.setTextSize(20);
        wordText.setTextColor(wordExample.getTextColors());

        if(word==null) wordText.setText("Error");
        else wordText.setText(word);

        categoryText.setTextSize(20);
        categoryText.setTextColor(categoryExample.getTextColors());

        if(cat==null) categoryText.setText("Categoría: No Encontrado");
        else categoryText.setText("Categoría: "+cat);

        lineLayout.setOrientation(LinearLayout.VERTICAL);
        lineLayout.setBackgroundColor(getResources().getColor(R.color.line_color));

        contentSenatico.addView(contentSign);
        contentSenatico.addView(wordText);
        contentSenatico.addView(categoryText);
        contentSenatico.addView(lineLayout);
    }

    private class GetListWords extends AsyncTask<Void,Void,Void>
    {
        SoapObject resultado;
        SoapObject palabraRes;
        @Override
        protected Void doInBackground(Void... params) {
            final String NAMESPACE = "http://tempuri.org/";
            final String URL = "http://www.lensechile.cl/lenseservice/Service1.svc";
            final String METHOD_NAME = "senatica";
            final String SOAP_ACTION = "http://tempuri.org/IService1/senatica";
            String Error;
            dialog.show();
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("letra", letter); // Paso parametros al WS
                request.addProperty("idRegion", idRegion); // Paso parametros al WS

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
                for(int i = 0; i< resultado.getPropertyCount();i++)
                {
                    palabraRes = (SoapObject) resultado.getProperty(i);
                    if(palabraRes.getProperty("Nombre")!=null && palabraRes.getProperty("Categoria")!=null && palabraRes.getProperty("Url")!=null)
                        AddSign(palabraRes.getProperty("Nombre").toString(),palabraRes.getProperty("Categoria").toString(),palabraRes.getProperty("Url").toString());

                }
            }
            else
            {

            }
            dialog.dismiss();
            super.onPostExecute(result);
        }
    }
}
