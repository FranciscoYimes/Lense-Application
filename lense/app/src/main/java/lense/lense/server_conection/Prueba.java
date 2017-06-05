package lense.lense.server_conection;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by franciscoyimesinostroza on 13-05-17.
 */

public class Prueba {

    public void main() {

        final String NAMESPACE = "http://tempuri.org/";
        final String URL = "http://www.lensechile.cl/lenseservice/Service1.svc";
        final String METHOD_NAME = "pruebapancho";
        final String SOAP_ACTION = "http://tempuri.org/IService1/pruebapancho";

        //SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        //request.addProperty("nro1", nro1.getText().toString());
        //request.addProperty("nro2", nro2.getText().toString());

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.setOutputSoapObject(request);
        String resultado = "";

        HttpTransportSE ht = new HttpTransportSE(URL);

        try {
            ht.call(SOAP_ACTION, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            resultado = response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            resultado = "no funciona";
        }

    }
}
