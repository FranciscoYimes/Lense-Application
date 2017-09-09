package lense.lense;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.MalformedURLException;

import lense.lense.Adapters.SimpleProgressDialog;

public class WordsActivity extends AppCompatActivity {

    private TextView wordExample;
    private LinearLayout listContent;
    private LinearLayout lineLayout;
    private SimpleProgressDialog dialog;
    private String subCategoryName;
    private String categoryName;
    private int idPalabra;
    private int idRegion;
    private int idUsuario;
    private Toolbar mToolbar;
    private Typeface walkwayBold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idRegion = getIntent().getIntExtra("idRegion",0);
        idUsuario = getIntent().getIntExtra("idUsuario",0);

        walkwayBold = Typeface.createFromAsset(getAssets(), "WalkwayBold.ttf");

        listContent = (LinearLayout) findViewById(R.id.list_content);
        lineLayout = (LinearLayout) findViewById(R.id.line_layout);
        wordExample = (TextView) findViewById(R.id.word_example);
        subCategoryName = getIntent().getStringExtra("subCategoryName");
        categoryName = getIntent().getStringExtra("categoryName");
        dialog = SimpleProgressDialog.build(this, "Cargando...");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new AddWordsList().execute();
    }

    public void AddWord(String word, int id)
    {
        final TextView textView = new TextView(getApplicationContext());
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setLayoutParams(lineLayout.getLayoutParams());
        linearLayout.setBackgroundColor(getResources().getColor(R.color.special_edit_text__text_color));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        textView.setLayoutParams(wordExample.getLayoutParams());
        textView.setText(word);
        textView.setTextColor(wordExample.getTextColors());
        textView.setTextSize(20);
        textView.setId(id);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.setData(Uri.parse(String.valueOf(textView.getId())));
                setResult(RESULT_OK, data);
                finish();
            }
        });
        listContent.addView(textView);
        listContent.addView(linearLayout);
    }

    private class AddWordsList extends AsyncTask<Void,Void,Void>
    {
        SoapObject resultado;
        @Override
        protected Void doInBackground(Void... params) {
            final String NAMESPACE = "http://tempuri.org/";
            final String URL = "http://www.lensechile.cl/lenseservice/Service1.svc";
            final String METHOD_NAME = "listaPalabrasCategoriaSubCategoria";
            final String SOAP_ACTION = "http://tempuri.org/IService1/listaPalabrasCategoriaSubCategoria";
            String Error;
            dialog.show();
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("categoria", categoryName); // Paso parametros al WS
                request.addProperty("subCategoria", subCategoryName); // Paso parametros al WS
                request.addProperty("idRegion", idRegion); // Paso parametros al WS
                request.addProperty("idUsuario", idUsuario); // Paso parametros al WS

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
                SoapObject wordObject;
                for(int i = 0; i< resultado.getPropertyCount();i++)
                {
                    wordObject = (SoapObject) resultado.getProperty(i);
                    AddWord(wordObject.getProperty(1).toString(),Integer.parseInt(wordObject.getProperty(0).toString()));
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
