package sodimac.net.appsodimac.view;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import sodimac.net.appsodimac.R;

public class PuntuacionActivity extends AppCompatActivity {
   Spinner idPuntualidad;
    Spinner idDesempeño;
    Spinner idConocimiento;
    String comboPuntualidad,comboDesempeño,comboConocimiento,idSolicitud,cliente;
    EditText txtObservaciones;
    String rutaServicio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuacion);
propiedades();

        Intent intent = getIntent();
        idSolicitud = intent.getExtras().getInt("idsolicitud")+"";
        cliente = intent.getExtras().getString("user");
        rutaServicio = intent.getExtras().getString("rutaServicio");
        asignarReferencias();
      click();

    }

    private void click() {
        Button btn = (Button) findViewById(R.id.btnEnviarPuntuacion);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    llamarServicio();
                    Log.e("PROGRESO","CLICK AL BOTON");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private void llamarServicio() {
                HttpURLConnection connection = null;
                try {

                    String request = "http://"+"192.168.43.40"+":8080/SpringRest/insertarPuntuaciones?" +"puntualidad=" +
                    comboPuntualidad +
                    "&conocimiento=" + comboConocimiento +
                            "&desempenio=" + comboDesempeño+
                            "&observaciones=" + txtObservaciones.getText().toString() +
                            "&idsolicitud=" + idSolicitud ;
                    URL url = new URL(request);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.setRequestMethod("GET");
                    StringBuilder result = new StringBuilder();
                    InputStream input = new BufferedInputStream(connection.getInputStream());
                    BufferedReader read = new BufferedReader(new InputStreamReader(input));
                    String line;

                    while ((line = read.readLine()) != null) {

                        result.append(line);
                    }
                    String[] split = result.toString().split(":");
                    Log.e("PROGRESO",split[1]);
                    if (split[1].contains("ERROR")) {
                        Toast.makeText(PuntuacionActivity.this, split[1].toString(),
                                Toast.LENGTH_LONG).show();
                    } else {

                        Toast.makeText(PuntuacionActivity.this, "GRACIAS POR PUNTUAR",
                                Toast.LENGTH_LONG).show();
                        Intent menuIntent = new Intent(getApplicationContext(), EleccionExperto.class);
                        menuIntent.putExtra("user",cliente);
                        startActivity(menuIntent);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    private void asignarReferencias() {
        idPuntualidad = (Spinner) findViewById(R.id.spPuntualidad);
        idConocimiento = (Spinner) findViewById(R.id.spConocimiento);
        idDesempeño = (Spinner) findViewById(R.id.spDesempeño);
        cargaPuntualidad();
        cargaDesempenio();
        cargaConocimiento();
         txtObservaciones = (EditText) findViewById(R.id.txtObservaciones);
    }

    private void cargaPuntualidad() {

        HttpURLConnection connection = null;

        try {
            String rpuntuacion= "http://"+"192.168.43.40"+":8080/SpringRest/comboPuntuacion";
            URL url = new URL(rpuntuacion);
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
            JSONArray jsonpuntuacion = obj.getJSONArray("response");

            List<String> combopuntuacion = new ArrayList<>();
            final List<String> comboIdpuntuacion = new ArrayList<String>();
            if (jsonpuntuacion != null) {
                for (int i=0;i<jsonpuntuacion.length();i++){
                    JSONObject id = jsonpuntuacion.getJSONObject(i);
                    String idstring = id.getString("idpuntuacion");
                    String puntuacionStrng = id.getString("puntuacion");

                    combopuntuacion.add(puntuacionStrng);
                    comboIdpuntuacion.add(idstring);


                }
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, combopuntuacion);
            spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            idPuntualidad.setAdapter(spinnerArrayAdapter);

            idPuntualidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                           @Override
                                                           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                               comboPuntualidad = comboIdpuntuacion.get(i);

                                                           }

                                                           @Override
                                                           public void onNothingSelected(AdapterView<?> adapterView) {


                                                           }
                                                       }


            );


        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void cargaDesempenio() {

        HttpURLConnection connection = null;

        try {
            String rpuntuacion= "http://"+"192.168.43.40"+":8080/SpringRest/comboPuntuacion";
            URL url = new URL(rpuntuacion);
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
            JSONArray jsonpuntuacion = obj.getJSONArray("response");

            List<String> combopuntuacion = new ArrayList<>();
            final List<String> comboIdpuntuacion = new ArrayList<String>();
            if (jsonpuntuacion != null) {
                for (int i=0;i<jsonpuntuacion.length();i++){
                    JSONObject id = jsonpuntuacion.getJSONObject(i);
                    String idstring = id.getString("idpuntuacion");
                    String puntuacionStrng = id.getString("puntuacion");

                    combopuntuacion.add(puntuacionStrng);
                    comboIdpuntuacion.add(idstring);


                }
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, combopuntuacion);
            spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            idDesempeño.setAdapter(spinnerArrayAdapter);

            idDesempeño.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                        @Override
                                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                            comboDesempeño = comboIdpuntuacion.get(i);

                                                        }

                                                        @Override
                                                        public void onNothingSelected(AdapterView<?> adapterView) {


                                                        }
                                                    }


            );


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void cargaConocimiento() {
        HttpURLConnection connection = null;

        try {
            String rpuntuacion= "http://"+"192.168.43.40"+":8080/SpringRest/comboPuntuacion";
            URL url = new URL(rpuntuacion);
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
            JSONArray jsonpuntuacion = obj.getJSONArray("response");

            List<String> combopuntuacion = new ArrayList<>();
            final List<String> comboIdpuntuacion = new ArrayList<String>();
            if (jsonpuntuacion != null) {
                for (int i=0;i<jsonpuntuacion.length();i++){
                    JSONObject id = jsonpuntuacion.getJSONObject(i);
                    String idstring = id.getString("idpuntuacion");
                    String puntuacionStrng = id.getString("puntuacion");

                    combopuntuacion.add(puntuacionStrng);
                    comboIdpuntuacion.add(idstring);


                }
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, combopuntuacion);
            spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            idConocimiento.setAdapter(spinnerArrayAdapter);

            idConocimiento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                      @Override
                                                      public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                          comboConocimiento = comboIdpuntuacion.get(i);

                                                      }

                                                      @Override
                                                      public void onNothingSelected(AdapterView<?> adapterView) {


                                                      }
                                                  }


            );


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void propiedades() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
