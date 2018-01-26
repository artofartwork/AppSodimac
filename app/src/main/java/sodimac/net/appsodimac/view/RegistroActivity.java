package sodimac.net.appsodimac.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sodimac.net.appsodimac.R;
import sodimac.net.appsodimac.model.Documento;
import sodimac.net.appsodimac.model.Ubigeo;

public class RegistroActivity extends AppCompatActivity implements Validator.ValidationListener{
    ProgressDialog dialog;
    public  String rutaServicio;

    @NotEmpty
    EditText tlf;
    protected Validator validator;
    protected boolean validated;

    @Password(min = 8, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS)
    EditText  clave;
    @NotEmpty
    EditText direccion;

    @NotEmpty
    EditText nrodocumento;

    @NotEmpty
    EditText nombres;

    @NotEmpty
    EditText amaterno;

    @Email
    EditText email;

    @NotEmpty
    EditText apaterno;

    String ubigeoCombo,documentoCombo;
    Spinner idubigeo,idtipodocumento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_registro);
        propiedades();
        rutaServicio = getResources().getString(R.string.ruta_servicio);
        asignarReferencias();
        validator = new Validator(this);
        validator.setValidationListener(this);
        click();
    }

    private void cargaDocumento() {
        HttpURLConnection connection = null;

        try {
            String rDocumento= "http://"+rutaServicio+":8080/SpringRest/comboDocumento";
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
            JSONArray jsonDocumento = obj.getJSONArray("response");

            List<String> combodocumento = new ArrayList<String>();
            final List<String> comboIdDocumento = new ArrayList<String>();
            if (jsonDocumento != null) {
                for (int i=0;i<jsonDocumento.length();i++){
                    JSONObject id = jsonDocumento.getJSONObject(i);
                    String idstring = id.getString("idtipodocumento");
                    String documentoStrng = id.getString("tipodocumento");

                    combodocumento.add(documentoStrng);
                    comboIdDocumento.add(idstring);


                }
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, combodocumento);
            spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            idtipodocumento.setAdapter(spinnerArrayAdapter);

            idtipodocumento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                      @Override
                                                      public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                          documentoCombo = comboIdDocumento.get(i);

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

    private void cargaUbigeo() {
        HttpURLConnection connection = null;
        Ubigeo ubigeo = new Ubigeo();
        try {
            String rUbigeo= "http://"+rutaServicio+":8080/SpringRest/comboUbigeo";
            URL url = new URL(rUbigeo);
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
            JSONArray jsonUbigeo = obj.getJSONArray("response");

            List<String> comboDistrito = new ArrayList<String>();
            final List<String> comboIdUbigeo = new ArrayList<String>();
            if (jsonUbigeo != null) {
                for (int i=0;i<jsonUbigeo.length();i++){
                    JSONObject id = jsonUbigeo.getJSONObject(i);
                    String idstring = id.getString("idubigeo");
                    String distritoStrng = id.getString("distrito");

                    comboDistrito.add(distritoStrng);
                    comboIdUbigeo.add(idstring);


                }
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, comboDistrito);
            spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
            idubigeo.setAdapter(spinnerArrayAdapter);
       idubigeo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                   ubigeoCombo = comboIdUbigeo.get(i);

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

    private void click() {
        Button btn = (Button) findViewById(R.id.btnRegistro);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    llamarServicio();
                    Log.e("PROGRESO","CLICK AL BOTON");
                    validator.validate();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void asignarReferencias() {



        nrodocumento = (EditText) findViewById((R.id.txtnumdoc));
        apaterno = (EditText) findViewById((R.id.txtapaterno));
        amaterno = (EditText) findViewById((R.id.txtmaterno));
        nombres = (EditText) findViewById((R.id.txtnombres));
        tlf = (EditText) findViewById((R.id.txttlf));
        idubigeo = (Spinner) findViewById((R.id.txtidubigeo));
        direccion = (EditText) findViewById((R.id.txtdireccion));
        email = (EditText) findViewById((R.id.txtemail));
        clave =(EditText) findViewById((R.id.txtClave));
        idtipodocumento = (Spinner) findViewById((R.id.txtidtipodocumento));

        cargaUbigeo();
        cargaDocumento();

    }

    private  void llamarServicio() throws IOException {
        HttpURLConnection connection = null;
try {

    Date today = new Date();
    today.setHours(0);
    SimpleDateFormat sdf = new SimpleDateFormat();
    sdf.applyPattern("dd/MM/yyyy");
    Log.e("PROGRESO","ENTRANDO A LLAMARSERVICIO");

    String eApaterno = apaterno.getText().toString().replace(" ","%20") ;
    String eAmaterno = amaterno.getText().toString().replace(" ","%20") ;
    String eNombres =  nombres.getText().toString().replace(" ","%20") ;
    String eDireccion = direccion.getText().toString().replace(" ","%20") ;




if(eAmaterno.length() > 0 & eApaterno.length() > 0 & nombres.getText().toString().length() > 0 & email.getText().toString().length() > 0 & direccion.getText().toString().length() > 0 & clave.getText().toString().length() > 7 & email.getText().toString().contains("@")   ) {
    String str = sdf.format(today.getTime());


    String request = "http://"+rutaServicio+":8080/SpringRest/registrar?" +
            "idtipodoc=" + documentoCombo +
            "&numdoc=" + nrodocumento.getText().toString() +
            "&apaterno=" + eApaterno +
            "&amaterno=" + eAmaterno +
            "&nombres=" + eNombres +
            "&tlf=" + tlf.getText().toString() +
            "&idubigeo=" + ubigeoCombo +
            "&direccion=" + eDireccion +
            "&email=" + email.getText().toString() +
            "&fechaCreacion=" + str +
            "&Clave=" + clave.getText().toString();

    Log.e("PROGRESO",request+"");
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
        Toast.makeText(RegistroActivity.this, split[1].toString(),
                Toast.LENGTH_LONG).show();
    } else {

        Toast.makeText(RegistroActivity.this, "USUARIO REGISTRADO, YA PUEDE INICIAR SESIÓN",
                Toast.LENGTH_LONG).show();
        Intent menuIntent = new Intent(getApplicationContext(), OpcionesActivity.class);
        startActivity(menuIntent);
    }


    Log.e("ERROR", connection.getResponseMessage());
    Log.e("ERROR", connection.getRequestMethod());
    Log.e("ERROR", String.valueOf(connection.getResponseCode()));
}

}catch (Exception e){

    e.printStackTrace();
    throw e;
}finally {

    if(connection != null){

        connection.disconnect();
    }

}


    }

    protected boolean validate() {
        if (validator != null)
            validator.validate();
        return validated;           // would be set in one of the callbacks below
    }


    @Override
    public void onValidationSucceeded() {
        validated = true;

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        validated = false;

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            String mensaje = "";
            if(message.equalsIgnoreCase("This field is required")){
                mensaje = "Campo requerido";

            }

            if(message.equalsIgnoreCase("Invalid password")){
                mensaje = "La contraseña debe tener 8 caracteres mínimo entre números y letras";

            }  if(message.equalsIgnoreCase("Invalid email")){
                mensaje = "El correo ingresado no es válido";

            }



            // Display error messages
            if (view instanceof Spinner) {
                Spinner sp = (Spinner) view;
                view = ((LinearLayout) sp.getSelectedView()).getChildAt(0);        // we are actually interested in the text view spinner has
            }

            if (view instanceof TextView) {
                TextView et = (TextView) view;
                et.setError(mensaje);
            }
        }

    }



}
