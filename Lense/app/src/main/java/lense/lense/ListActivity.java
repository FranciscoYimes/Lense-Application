package lense.lense;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class ListActivity extends AppCompatActivity
{

    private Toolbar mToolbar;
    private LinearLayout abcContent;
    private LinearLayout listContent;
    private LinearLayout wordContent;
    private TextView abcTextViewExample;
    private TextView abcTextChar;
    private TextView wordExample;
    private ImageView wordImageView;
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
        wordContent = (LinearLayout) findViewById(R.id.word_content);
        wordImageView = (ImageView) findViewById(R.id.word_image_view);

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
            textView.setTextSize(30);
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

    public void AddWord(String word, int id)
    {
        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setLayoutParams(wordContent.getLayoutParams());
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setLayoutParams(wordImageView.getLayoutParams());
        final TextView textView = new TextView(getApplicationContext());
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
        Ion.with(imageView).load("http://www.emoji.co.uk/files/apple-emojis/animals-nature-ios/215-panda-face.png");
        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        listContent.addView(linearLayout);
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
                SoapObject infoPalabra;
                for(int i = 0; i< resultado.getPropertyCount();i++)
                {
                    infoPalabra = (SoapObject) resultado.getProperty(i);
                    AddWord(infoPalabra.getProperty(1).toString(),Integer.parseInt(infoPalabra.getProperty(0).toString()));
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
