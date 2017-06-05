package lense.lense.server_conection;

/**
 * Created by franciscoyimesinostroza on 11-05-17.
 */

import android.os.StrictMode;
import android.util.Log;

import com.google.gson.JsonSerializer;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;


/**
 * Created by Paulo on 06/05/2016.
 */

public class HttpClientHelper {

    public static JSONArray GET() throws JSONException {

        String parametros = "?";
        JsonSerializer resulta = null;
        JSONStringer res;
        JSONObject resultado = null;
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout((HttpParams) basicHttpParams, (int) 10000);
        HttpConnectionParams.setSoTimeout((HttpParams) basicHttpParams, (int) 10000);
        HttpConnectionParams.setTcpNoDelay((HttpParams) basicHttpParams, (boolean) true);


        String prueba;


        DefaultHttpClient httpClient = new DefaultHttpClient((HttpParams) basicHttpParams);

        // Se concatenan los parámetros que irán por query string
        /*for(int i = 0; i < params.length; i++){
            Argumento item = params[i];
            parametros += item.getKey() + "=" + item.getValue();

            if(i < params.length - 1){
                parametros += "&";
            }
        }*/
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();

            StrictMode.setThreadPolicy(policy);
        }
        String result="";
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.lensechile.cl/lenseservice/Service1.svc?singleWsdl/pruebapancho"));
            HttpResponse response = client.execute(request);
            InputStream ips = response.getEntity().getContent();

            BufferedReader buf = new BufferedReader(new InputStreamReader(ips,
                    "UTF-8"));

            StringBuilder sb = new StringBuilder();
            String s;
            while (true) {
                s = buf.readLine();
                if (s == null || s.length() == 0)
                    break;
                sb.append(s);

            }
            buf.close();
            ips.close();
            result = sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }


        try{
            HttpGet request = new HttpGet(("http://www.lensechile.cl/lenseservice/Service1.svc?singleWsdl") + "pruebapancho" );
            BasicResponseHandler basicResponseHandler = new BasicResponseHandler();

            prueba = ((String)httpClient.execute((HttpUriRequest)request, (ResponseHandler)basicResponseHandler));

            resultado = new JSONObject((String)httpClient.execute((HttpUriRequest)request, (ResponseHandler)basicResponseHandler));
            //res =       new JSONStringer((String)httpClient.execute((HttpUriRequest)request, (ResponseHandler)basicResponseHandler));
        }
        catch(Exception ex){
            System.out.print(ex);
            Log.e("ConsultaMongoDB", "Error al acceder al servicio WCF de Capacitaciones. Error: " + ex.getMessage());
            throw ex;
        }
        finally {
            return resultado.getJSONArray(/*OperationName + */"Result");
       }

    }
}
