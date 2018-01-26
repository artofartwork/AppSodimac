package sodimac.net.appsodimac.view;

import android.content.Intent;
import android.content.res.Resources;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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

public class EleccionExperto extends AppCompatActivity {
    String ocupacionCombo;
    Spinner idocupacion;
    String rutaServicio = "192.168.43.40";
     String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleccion_experto);
        propiedades();
        asignarReferencias();
        click();

        Intent intent = getIntent();
        user = intent.getExtras().getString("user");

    }

    private void click() {
      Button  botonE = (Button) findViewById(R.id.btnBuscarEx);
        botonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.e("PROGRESO","CARGANDO MAPA");
                    Intent menuIntent = new Intent(getApplicationContext(), MapsActivity.class);
                    menuIntent.putExtra("user", user.toString());
                    menuIntent.putExtra("ocupacion",ocupacionCombo);
                    startActivity(menuIntent);
                }catch (Exception e){


                    Log.e("ERROR",e+"");
                }


            }
        });



    }

    private void asignarReferencias() {
        idocupacion = (Spinner) findViewById((R.id.spOficio));

        cargaOcupacion();
    }

    private void cargaOcupacion() {
        HttpURLConnection connection = null;

        try {

            String rDocumento= "http://"+rutaServicio+":8080/SpringRest/comboOcupacion";
            URL url = new URL(rDocumento);
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
            JSONArray jsonOcupacion = obj.getJSONArray("response");

            List<String> comboocupacion = new ArrayList<String>();
            final List<String> comboIdOcupacion = new ArrayList<String>();
            if (jsonOcupacion != null) {
                for (int i=0;i<jsonOcupacion.length();i++){
                    JSONObject id = jsonOcupacion.getJSONObject(i);
                    String idstring = id.getString("idoficio");
                    String documentoStrng = id.getString("oficio");

                    comboocupacion.add(documentoStrng);
                    comboIdOcupacion.add(idstring);


                }
            }


            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.select_dialog_item, comboocupacion);
            spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            idocupacion.setAdapter(spinnerArrayAdapter);
            idocupacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                          @Override
                                                          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                              ocupacionCombo = comboIdOcupacion.get(i);

                                                          }

                                                          @Override
                                                          public void onNothingSelected(AdapterView<?> adapterView) {


                                                          }
                                                      }


            );
        }catch(Exception e){

            Log.e("ERROR",e+ "");
        }


    }

    private void propiedades() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
