package lense.lense;

import android.content.Intent;
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

public class SubCategoryActivity extends AppCompatActivity {

    private LinearLayout subCategoryLayoutExample;
    private LinearLayout contentSubCategory;
    private LinearLayout lineLayoutExample;
    private TextView subCategoryTextExample;
    private String categoryName;
    private int idPalabra;
    private int idRegion;
    private SimpleProgressDialog dialog;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        idRegion = getIntent().getIntExtra("idRegion",0);

        contentSubCategory = (LinearLayout) findViewById(R.id.content_sub_category);
        subCategoryLayoutExample = (LinearLayout) findViewById(R.id.sub_category_layout_example);
        subCategoryTextExample = (TextView) findViewById(R.id.sub_category_text_example);
        lineLayoutExample = (LinearLayout) findViewById(R.id.line_layout_example);

        categoryName = getIntent().getStringExtra("categoryName");
        dialog = SimpleProgressDialog.build(this, "Cargando...");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new AddListSubCategory().execute();

    }

    public void AddSubCategory(String name)
    {
        final TextView textView = new TextView(getApplicationContext());
        final LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        LinearLayout lineLayout = new LinearLayout(getApplicationContext());
        textView.setLayoutParams(subCategoryTextExample.getLayoutParams());
        linearLayout.setLayoutParams(subCategoryLayoutExample.getLayoutParams());
        lineLayout.setLayoutParams(lineLayoutExample.getLayoutParams());
        lineLayout.setBackgroundColor(getResources().getColor(R.color.special_edit_text__text_color));
        textView.setTextSize(20);
        textView.setText(name);
        textView.setTextColor(subCategoryTextExample.getTextColors());
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SubCategoryActivity.this,WordsActivity.class);
                i.putExtra("subCategoryName",textView.getText().toString());
                i.putExtra("categoryName",categoryName);
                i.putExtra("idRegion",idRegion);
                startActivityForResult(i,0);
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SubCategoryActivity.this,WordsActivity.class);
                i.putExtra("subCategoryName",textView.getText().toString());
                i.putExtra("categoryName",categoryName);
                i.putExtra("idRegion",idRegion);
                startActivityForResult(i,0);
            }
        });

        linearLayout.addView(textView);
        contentSubCategory.addView(linearLayout);
        contentSubCategory.addView(lineLayout);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (data != null) {
            idPalabra = Integer.parseInt(data.getData().toString());
            Intent data1 = new Intent();
            data1.setData(Uri.parse(String.valueOf(idPalabra)));
            setResult(RESULT_OK, data1);
            finish();
        }
    }

    private class AddListSubCategory extends AsyncTask<Void,Void,Void>
    {
        SoapObject resultado;
        @Override
        protected Void doInBackground(Void... params) {
            final String NAMESPACE = "http://tempuri.org/";
            final String URL = "http://www.lensechile.cl/lenseservice/Service1.svc";
            final String METHOD_NAME = "subCategorias";
            final String SOAP_ACTION = "http://tempuri.org/IService1/subCategorias";
            String Error;
            dialog.show();
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("Categoria", categoryName); // Paso parametros al WS
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
                    AddSubCategory(resultado.getProperty(i).toString());
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
