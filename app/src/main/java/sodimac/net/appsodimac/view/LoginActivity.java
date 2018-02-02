package sodimac.net.appsodimac.view;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.mobsandgeeks.saripaar.annotation.Password;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import Request.ApiConexion;
import sodimac.net.appsodimac.LogicMethods.LogicMaestro;
import sodimac.net.appsodimac.R;

public class LoginActivity extends AppCompatActivity implements Validator.ValidationListener{
    protected Validator validator;
    protected boolean validated;
    public  String rutaServicio;
    public LogicMaestro logicMaestro;

    @Email
    EditText user;

    @Password
    EditText clave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        propiedades();
        asignarReferencias();
        click();
        rutaServicio = getResources().getString(R.string.ruta_servicio);
        logicMaestro = new LogicMaestro(this,null);
        validator = new Validator(this);
        validator.setValidationListener(this);

    }

    private void click() {
        Button btn = (Button) findViewById(R.id.btnIniciarSesion);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    logicMaestro.doLogin(user.getText().toString(),clave.getText().toString());
                    //llamarServicio();
                   // validator.validate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void propiedades() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void asignarReferencias() {

        user =(EditText) findViewById((R.id.txtUsuario));
        clave = (EditText) findViewById((R.id.txtClave));
    }

    private void llamarServicio() throws  IOException{
        HttpURLConnection connection = null;
if(!user.getText().toString().isEmpty() && !clave.getText().toString().isEmpty()){

    try {
        Log.e("PROGRESO","INICIANDO SESION");
        String request = "http://"+rutaServicio+"iniciosesion/" +
                 user.getText().toString() +
                "/" + clave.getText().toString();

        URL url = new URL(request);
        connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setRequestMethod("GET");
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        StringBuilder result = new StringBuilder();
        InputStream input = new BufferedInputStream(connection.getInputStream());
        BufferedReader read = new BufferedReader(new InputStreamReader(input));

        String line;
        while((line = read.readLine()) !=null){

            result.append(line);
        }
        String[] split = result.toString().split(":");

        if (split[1].contains("1")) {
            Toast.makeText(LoginActivity.this,"Bienvenido" ,
                    Toast.LENGTH_LONG).show();
            Intent menuIntent = new Intent(getApplicationContext(), EleccionExperto.class);
            menuIntent.putExtra("user", user.getText().toString());
            menuIntent.putExtra("rutaServicio",rutaServicio.toString());
            startActivity(menuIntent);
        } if(split[1].contains("0")) {

            Toast.makeText(LoginActivity.this,"Usuario o Contraseña Incorrecta",
                    Toast.LENGTH_LONG).show();
        }

    }catch (Exception e){


        e.printStackTrace();
        throw e;
    }finally {
        connection.disconnect();
    }
}



    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),OpcionesActivity.class);;
        startActivity(i);
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
