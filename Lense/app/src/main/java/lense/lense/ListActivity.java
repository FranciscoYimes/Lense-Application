package lense.lense;

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

public class ListActivity extends AppCompatActivity
{

    private Toolbar mToolbar;
    private LinearLayout abcContent;
    private LinearLayout listContent;
    private TextView abcTextViewExample;
    private TextView abcTextChar;
    private TextView wordExample;
    private String abc;
    private SimpleProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        abc = "A";

        dialog = SimpleProgressDialog.build(this, "Cargando...");

        abcContent = (LinearLayout) findViewById(R.id.ABC_content);
        abcTextViewExample = (TextView) findViewById(R.id.abc_text_example);
        abcTextChar = (TextView) findViewById(R.id.ABC_Char);
        wordExample = (TextView) findViewById(R.id.word_example);
        listContent = (LinearLayout) findViewById(R.id.list_content);

        SetListABC();

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new AddListWords().execute();
    }

    public void SetListABC()
    {
        final String ABC[] = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","Ñ","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        int i;

        for (i = 0; i<ABC.length;i++)
        {
            final TextView textView = new TextView(getApplicationContext());
            textView.setText(ABC[i]);
            textView.setTextSize(20);
            textView.setTextColor(abcTextViewExample.getTextColors());
            textView.setLayoutParams(abcTextViewExample.getLayoutParams());
            textView.setId(i);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = textView.getId();
                    abcTextChar.setText(ABC[id]);
                    abc = ABC[id];
                    new AddListWords().execute();
                }
            });
            abcContent.addView(textView);
        }
    }

    public void AddWord(String word)
    {
        TextView textView = new TextView(getApplicationContext());
        textView.setLayoutParams(wordExample.getLayoutParams());
        textView.setText("- "+word);
        textView.setTextColor(wordExample.getTextColors());
        textView.setTextSize(20);
        listContent.addView(textView);
    }

    private class AddListWords extends AsyncTask<Void,Void,Void>
    {
        SoapObject resultado;
        @Override
        protected Void doInBackground(Void... params) {
            final String NAMESPACE = "http://tempuri.org/";
            final String URL = "http://www.lensechile.cl/lenseservice/Service1.svc";
            final String METHOD_NAME = "Palabras";
            final String SOAP_ACTION = "http://tempuri.org/IService1/Palabras";
            String Error;
            dialog.show();
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("letraPalabra", abc); // Paso parametros al WS

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
            listContent.removeAllViews();
            if(resultado!=null)
            {

                for(int i = 0; i< resultado.getPropertyCount();i++)
                {
                    AddWord(resultado.getProperty(i).toString());
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
