package sodimac.net.appsodimac.view;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import sodimac.net.appsodimac.R;

public class AFLoginActivity extends AppCompatActivity implements Validator.ValidationListener {
    protected Validator validator;
    protected boolean validated;
    public  String rutaServicio;
    @Email
    EditText user;
    @Password
    EditText clave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeAF);
        setContentView(R.layout.activity_aflogin);
        propiedades();
        asignarReferencias();
        click();
        rutaServicio = getResources().getString(R.string.ruta_servicio);

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    private void click() {
        Button btn = (Button) findViewById(R.id.btnIniciarSesion);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    llamarServicio();
                    validator.validate();
                } catch (IOException e) {
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
        if(!user.getText().toString().isEmpty() && !clave.getText().toString().isEmpty()) {
            HttpURLConnection connection = null;
            try {
                String request = "http://" + rutaServicio + ":8080/SpringRest/iniciosesionaf?" +
                        "usuario=" + user.getText().toString() +
                        "&clave=" + clave.getText().toString();

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

                if (split[1].contains("1")) {
                    Toast.makeText(AFLoginActivity.this, "Bienvenido",
                            Toast.LENGTH_LONG).show();
                    Intent menuIntent = new Intent(getApplicationContext(), MapsAFActivity.class);
                    menuIntent.putExtra("user", user.getText().toString());
                    startActivity(menuIntent);
                }
                if (split[1].contains("0")) {

                    Toast.makeText(AFLoginActivity.this, "Usuario o Contraseña Incorrecta",
                            Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {


                e.printStackTrace();
                throw e;
            } finally {
                connection.disconnect();
            }

        }
    }

    @Override
    public void onValidationSucceeded() {
        validated = true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),OpcionesActivity.class);;
        startActivity(i);
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
