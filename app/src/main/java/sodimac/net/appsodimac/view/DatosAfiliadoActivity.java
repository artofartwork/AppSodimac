package sodimac.net.appsodimac.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import sodimac.net.appsodimac.R;

public class DatosAfiliadoActivity extends AppCompatActivity {
    String user;
    private ListView listaAfiliado;
    public  String rutaServicio = "192.168.43.40";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_afiliado);
        setTheme(R.style.AppThemeAF);
        listaAfiliado = (ListView) findViewById(R.id.lstdatosafiliado);
        Intent intent = getIntent();
        user = intent.getExtras().getString("user");

        llamarServicio();
    }

    private void llamarServicio() {

        HttpURLConnection connection = null;

        try {
            String request = "http://"+rutaServicio+":8080/SpringRest/obtenerAfiliadoCorreo?" +"correo=" + user;
            URL url = new URL(request);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            StringBuilder result = new StringBuilder();
            InputStream input = new BufferedInputStream(connection.getInputStream());
            BufferedReader read = new BufferedReader(new InputStreamReader(input));
            String line;
            while((line = read.readLine()) !=null){

                result.append(line);
            }

            JSONObject obj = new JSONObject(result.toString());
            JSONArray jsonafiliado = obj.getJSONArray("response");


            List<String> datosafiliado = new ArrayList<>();
            if (datosafiliado != null) {
                for (int i=0;i<jsonafiliado.length();i++) {
                    JSONObject id = jsonafiliado.getJSONObject(i);


                    String idafiliado = id.getString("idafiliado");

                    String numdoc = id.getString("numdoc");
                    String apaterno = id.getString("apat");
                    String amaterno = id.getString("amat");
                    String nombres = id.getString("nombres");
                    String tlf = id.getString("tlf");

                    String direccion = id.getString("direccion");
                    String email = id.getString("correo");
                    String datosdocumento = id.getString("tipoDocumento");
                    String[] partesdocumento = datosdocumento.split(",");
                    String tipodoc = partesdocumento[1];
                    String tipodocumento = tipodoc.substring(tipodoc.indexOf(":")+ 2, tipodoc.indexOf("}"));
                    tipodocumento =  tipodocumento.replace("\"", "");

                    String datosubigeo = id.getString("ubigeo");
                    String[] partesubigeo = datosubigeo.split(",");
                    String dist = partesubigeo[1];
                    String distrito = dist.substring(dist.indexOf(":"));
                    distrito = distrito.replace("\"", "");
                    distrito=  distrito.replace(":", "");
                    String depart = partesubigeo[2];
                    String departamento = depart.substring(depart.indexOf(":") + 2);
                    departamento = departamento.replace("\"", "");
                    departamento = departamento.replace("}", "");
                    String prov = partesubigeo[3];
                    String provincia = prov.substring(prov.indexOf(":")+2);
                    provincia=  provincia.replace("\"", "");
                    provincia=  provincia.replace(":", "");
                    provincia = provincia.replace("}", "");


                    datosafiliado.add("Nombre de afiliado : " +nombres + " " + apaterno + " " + amaterno);
                    datosafiliado.add("Número de documento de Afiliado : "  +numdoc);
                    datosafiliado.add("Teléfono : " + tlf);
                    datosafiliado.add("Dirección : " + direccion);
                    datosafiliado.add("Correo electrónico : " + email);







                }

                ArrayAdapter adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1,
                        datosafiliado);
                listaAfiliado.setAdapter(adapter);
            }



        }catch (Exception e){

            e.printStackTrace();
        }



    }
}
