package sodimac.net.appsodimac.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sodimac.net.appsodimac.R;

public class OpcionesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
        try {
               clickRegistro();
               clickInicioSesion();
            clickInicioSesionAF();
        }catch(Exception e){}


    }

    private void clickInicioSesionAF() {
        Button btn = (Button) findViewById(R.id.btnIrSesionAfiliado);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(getApplicationContext(), AFLoginActivity.class);
                startActivity(menuIntent);
            }
        });
    }

    private void clickRegistro() {
        Button btn = (Button) findViewById(R.id.btntIrRegistroUsuario);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(getApplicationContext(), RegistroActivity.class);
                startActivity(menuIntent);
            }
        });

    }

    private void clickInicioSesion(){
        Button btn = (Button) findViewById(R.id.btnIrInicioSesion);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(menuIntent);
            }
        });

    }
}
