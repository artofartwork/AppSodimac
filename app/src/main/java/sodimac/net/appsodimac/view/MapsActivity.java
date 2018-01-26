package sodimac.net.appsodimac.view;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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
import java.util.Scanner;
import java.util.logging.LogRecord;

import sodimac.net.appsodimac.R;
import sodimac.net.appsodimac.model.Afiliado;

public class MapsActivity extends ActionBarActivity implements OnMapReadyCallback,
         GoogleApiClient.ConnectionCallbacks,
         GoogleApiClient.OnConnectionFailedListener,
         LocationListener{
     private ActionBarDrawerToggle mDrawerToggle;
     private DrawerLayout mDrawerLayout;
     private String mActivityTitle;
     TextView textoResultado;
    ProgressDialog progressDialog;
     private ListView mDrawerList;
     private ArrayAdapter<String> mAdapter;
     private GoogleMap mMap;
     GoogleApiClient mGoogleApiClient;
     Location mLastLocation;
     Marker mCurrLocationMarker;
     Button botonDialogAfiliado;
     LocationRequest mLocationRequest;
     String ocupacion;
     String user;
     int response;
     int respuntuar;
     int idSolicitud;
     ImageView aButton;
     double latitudecliente;
     double longitudecliente;
     Button botonAf;

     public  String rutaServicio ;
    @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_maps);
         Intent intent = getIntent();

         user = intent.getExtras().getString("user");
        rutaServicio = getResources().getString(R.string.ruta_servicio);
         if(intent.getExtras().getString("ocupacion") !=null) {

             ocupacion = intent.getExtras().getString("ocupacion");
         }
         mDrawerList = (ListView)findViewById(R.id.navList);
        clickActualizar();
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         getSupportActionBar().setHomeButtonEnabled(true);
         mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
         mActivityTitle = getTitle().toString();
         addDrawerItems();
         botonAf = (Button) findViewById(R.id.btnBotonAfiliado);
         setupDrawer();

         mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 if(position == 0){
                     Intent menuIntent = new Intent(getApplicationContext(), DatosClienteActivity.class);
                     menuIntent.putExtra("user",user);
                     menuIntent.putExtra("rutaServicio",rutaServicio);
                     startActivity(menuIntent);

                 }
                 if(position == 1){
                     //TODO SERVICIO DE CIERRE DE SESIÓN

                     HttpURLConnection connection = null;

                     try {
                         String rpuntuacion= "http://"+rutaServicio+":8080/SpringRest/cerrarSesionCliente?user=" + user;
                         URL url = new URL(rpuntuacion);
                         connection = (HttpURLConnection) url.openConnection();
                         connection.setDoInput(true);
                         connection.setRequestMethod("GET");
                         StringBuilder result = new StringBuilder();
                         InputStream input = new BufferedInputStream(connection.getInputStream());
                         BufferedReader read = new BufferedReader(new InputStreamReader(input));


                         Toast.makeText(MapsActivity.this,"Vuelva pronto" ,
                                 Toast.LENGTH_LONG).show();
                         Intent menuIntent = new Intent(getApplicationContext(), LoginActivity.class);

                         startActivity(menuIntent);


                     }catch (Exception e){
                         e.printStackTrace();
                     }finally {

                         connection.disconnect();
                     }


                 }


             }
         });

         if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             checkLocationPermission();
         }
         // Obtain the SupportMapFragment and get notified when the map is ready to be used.
         SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                 .findFragmentById(R.id.map);
         mapFragment.getMapAsync(this);
     }

    private void clickActualizar() {
        Button btn = (Button) findViewById(R.id.btnActualizarAfiliados);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent menuIntent = new Intent(getApplicationContext(), MapsActivity.class);
                    menuIntent.putExtra("user", user.toString());
                    menuIntent.putExtra("ocupacion",ocupacion.toString());
                    startActivity(menuIntent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setupDrawer() {

         mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                 R.string.drawer_open, R.string.drawer_close) {

             /** Called when a drawer has settled in a completely open state. */
             public void onDrawerOpened(View drawerView) {
                 mDrawerList.bringToFront();
                 mDrawerLayout.requestLayout();
                 super.onDrawerOpened(drawerView);

                 invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
             }

             /** Called when a drawer has settled in a completely closed state. */
             public void onDrawerClosed(View view) {
                 super.onDrawerClosed(view);
                 invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
             }
         };

         mDrawerToggle.setDrawerIndicatorEnabled(true);
         mDrawerLayout.setDrawerListener(mDrawerToggle);

     }
     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         // Handle action bar item clicks here. The action bar will
         // automatically handle clicks on the Home/Up button, so long
         // as you specify a parent activity in AndroidManifest.xml.
         int id = item.getItemId();

         //noinspection SimplifiableIfStatement

         // Activate the navigation drawer toggle
         if (mDrawerToggle.onOptionsItemSelected(item)) {
             return true;
         }

         return super.onOptionsItemSelected(item);
     }


     @Override
     public void onMapReady(GoogleMap googleMap) {
         mMap = googleMap;
         mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

         //Initialize Google Play Services
         if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             if (ContextCompat.checkSelfPermission(this,
                     Manifest.permission.ACCESS_FINE_LOCATION)
                     == PackageManager.PERMISSION_GRANTED) {
                 buildGoogleApiClient();
                 mMap.setMyLocationEnabled(true);
             }
         }
         else {
             buildGoogleApiClient();
             mMap.setMyLocationEnabled(true);
         }
     }

     protected synchronized void buildGoogleApiClient() {
         mGoogleApiClient = new GoogleApiClient.Builder(this)
                 .addConnectionCallbacks(this)
                 .addOnConnectionFailedListener(this)
                 .addApi(LocationServices.API)
                 .build();
         mGoogleApiClient.connect();
     }

     @Override
     public void onConnected(Bundle bundle) {

         mLocationRequest = new LocationRequest();
         mLocationRequest.setInterval(1000);
         mLocationRequest.setFastestInterval(1000);
         mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
         if (ContextCompat.checkSelfPermission(this,
                 Manifest.permission.ACCESS_FINE_LOCATION)
                 == PackageManager.PERMISSION_GRANTED) {
             LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
         }

     }

     @Override
     public void onConnectionSuspended(int i) {

     }

     @Override
     public void onLocationChanged(Location location) {

         mLastLocation = location;
         if (mCurrLocationMarker != null) {
             mCurrLocationMarker.remove();
         }

         //Place current location marker
         LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
         MarkerOptions markerOptions = new MarkerOptions();
         markerOptions.position(latLng);

          latitudecliente = location.getLatitude();
          longitudecliente = location.getLongitude();

         try{

             llamarUbicacionAfiliado(user,latitudecliente,longitudecliente,ocupacion);
         }catch(Exception e){
             e.printStackTrace();

         }


               //Marker del cliente
         markerOptions.title("Posición Actual");
         markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
         mCurrLocationMarker = mMap.addMarker(markerOptions);

         //move map camera
         mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
         CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
         mMap.animateCamera(cameraUpdate);

         //stop location updates
         if (mGoogleApiClient != null) {
             LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
         }

     }

     private void llamarUbicacionAfiliado(final String user, double latitude, double longitude, final String ocupacion) {
         Log.e("PROGRESO","ACTUALIZANDO UBICACION DE CLIENTE");
         Log.e("LATITUD",latitude+"");
         Log.e("LONGITUD",longitude+"");
         insertarUbicacionCliente(user,latitude,longitude);



         HttpURLConnection connection = null;
         textoResultado = (TextView) findViewById(R.id.txttextoResultado);
         try {
             Log.e("PROGRESO","CARGANDO MAPA");
             String rAfiliado= "http://"+rutaServicio+":8080/SpringRest/obtenerAfiliado?oficio=" + ocupacion;
             URL url = new URL(rAfiliado);
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
             JSONArray jsonAfiliado = obj.getJSONArray("response");
             ArrayList<LatLng> latLngs = new ArrayList<LatLng>();

             textoResultado.setText("Cargando..");
             final ArrayList<Afiliado> listaAfiliado = new ArrayList<Afiliado>();

             if (jsonAfiliado != null) {
                 if (jsonAfiliado.length() == 0) {

                     textoResultado.setText("Actualmente no se encuentran expertos en tu área");
                     botonAf.setText("Buscar de Nuevo");
                     botonAf.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             Intent intent = getIntent();
                             intent.putExtra("user", user.toString());
                             startActivity(intent);
                         }
                     });

                 } else {
                     botonAf.setText("Elija el experto a llamar");
                     textoResultado.setText("Aqui se muestran los expertos disponibles");
                 Log.e("PROGRESO", "TAMAÑO DE JSON : " + jsonAfiliado.length());
                 for (int i = 0; i < jsonAfiliado.length(); i++) {
                     Afiliado afiliado = new Afiliado();
                     Log.e("PROGRESO", "CARGANDO AFILIADOS AL MAPA");
                     JSONObject id = jsonAfiliado.getJSONObject(i);
                     afiliado.setIdafiliado(Integer.parseInt(id.getString("idafiliado")));
                     afiliado.setNumdoc(id.getString("numdoc"));
                     afiliado.setNombres(id.getString("nombres"));
                     afiliado.setAmat(id.getString("amat"));
                     afiliado.setApat(id.getString("apat"));
                     afiliado.setFoto(id.getString("foto"));
                     afiliado.setLatitudafiliado(Double.parseDouble(id.getString("latitudAfiliado")));
                     afiliado.setLongitudafiliado(Double.parseDouble(id.getString("longitudAfiliado")));
                     LatLng latLng = new LatLng(afiliado.getLatitudafiliado(), afiliado.getLongitudafiliado());

                     listaAfiliado.add(afiliado);
                     latLngs.add(latLng);
                 }


                 for (int i = 0; i < latLngs.size(); i++) {
                     MarkerOptions markerOptions = new MarkerOptions();
                     Log.e("PROGRESO", "AÑADIENDO MARKERS");
                     markerOptions.position(latLngs.get(i));
                     markerOptions.title(listaAfiliado.get(i).getNombres() + " " + listaAfiliado.get(i).getApat() + " " + listaAfiliado.get(i).getAmat());
                     markerOptions.snippet(listaAfiliado.get(i).getIdafiliado() + "");
                     Log.e("PROGRESO", markerOptions.getTitle());
                     markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                     final byte[] fotoAfiliado = Base64.decode(listaAfiliado.get(i).getFoto(), Base64.DEFAULT);
                     mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                                                       @Override
                                                       public boolean onMarkerClick(Marker marker) {

                                                           Log.e("PROGRESO", "CLICK AL MARKER");
                                                           aButton = (ImageView) findViewById(R.id.btnAfiliadoImagen);

                                                           Bitmap bmp = BitmapFactory.decodeByteArray(fotoAfiliado, 0, fotoAfiliado.length);
                                                           aButton.setImageBitmap(Bitmap.createScaledBitmap(bmp, bmp.getWidth(),
                                                                   bmp.getHeight(), false));

                                                           botonAf.setText(marker.getTitle());
                                                           botonDialogAfiliado = botonAf;

                                                               clickBotonDialog(bmp, marker.getTitle(), user, Integer.parseInt(marker.getSnippet()), Integer.parseInt(ocupacion));


                                                           return true;
                                                       }


                                                   }
                     );
                     mMap.addMarker(markerOptions);


                 }

             }
             }else{
                 textoResultado.setText("Actualmente no se encuentran expertos en tu área");
                 botonAf.setText("Buscar de Nuevo");

                     }
         }catch (Exception ex){
             textoResultado.setText("Actualmente no se encuentran expertos en tu área");
             botonAf.setText("Buscar de Nuevo");
             ex.printStackTrace();
         }

     }

    private void clickBotonDialog(final Bitmap bmp, final String titulo,final String cliente,final int idafiliado,final int idoficio) {


        botonDialogAfiliado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                    builder.setCancelable(true);
                    Drawable d = new BitmapDrawable(getResources(), bmp);

                    d.setBounds(0,0,100,100);
                   builder.setIcon(d);
                   builder.setTitle(titulo);
                    builder.setPositiveButton("Solicitar Experto", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Toast.makeText(MapsActivity.this,"Espere respuesta del experto por favor" ,
                                    Toast.LENGTH_LONG).show();
                            final int res = insertarSolicitudInicial();
                            response = 0;
                            if (res == 1) {

                                do {
                                    Log.e("PROGRESO", "ENTRANDO A HANDLER");
                                    response = consultarEstadoAceptado(cliente, idafiliado, idoficio);
                                    Log.e("PROGRESO", response + "");


                                } while (response != 1);


                            }
                            respuntuar = 0;
                            if(response==1){

                                do{

                                    Log.e("PROGRESO", "ENTRANDO A HANDLER");
                                    respuntuar = consultarEstadoPuntuar(cliente, idafiliado, idoficio);
                                    Log.e("PROGRESO", respuntuar + "");
                                }while(respuntuar!=1);

                            }

                        }
                        private int consultarEstadoPuntuar(String cliente, int idafiliado, int idoficio) {
                            HttpURLConnection connection = null;
                         //   Log.e("CATALOGO","BUSCANDO ESTADO PUNTUAR");

                            int res  =0;
                            try {
                                Log.e("PROGRESO",idSolicitud+
                                "");
                                String request = "http://"+rutaServicio+":8080/SpringRest/consultaEstadoPuntuar?" +
                                        "idsolicitud=" + idSolicitud;

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
                                String[] split = result.toString().split(":");

                                if (split[1].contains("1")) {
                                    Toast.makeText(MapsActivity.this,"FAVOR PUNTUAR EL TRBAJO DEL EXPERTO" ,
                                            Toast.LENGTH_LONG).show();
                                    Intent menuIntent = new Intent(getApplicationContext(), PuntuacionActivity.class);
                                    menuIntent.putExtra("idsolicitud",idSolicitud);
                                    menuIntent.putExtra("user",cliente);
                                    menuIntent.putExtra("rutaServicio",rutaServicio);
                                    startActivity(menuIntent);
                                    res = 1;
                                }

                            }catch (Exception e){


                                e.printStackTrace();

                            }finally {
                                connection.disconnect();
                            }

                            return res;




                        }

                        private int consultarEstadoAceptado(String cliente, int idafiliado, int idoficio) {
                            HttpURLConnection connection = null;
                            Log.e("CATALOGO","BUSCANDO ESTADO ACEPTADO");

                            int res  =0;
                            try {
                                String request = "http://"+rutaServicio+":8080/SpringRest/consultaEstadoAceptado?" +
                                        "cliente=" + cliente +
                                        "&idafiliado=" + idafiliado +
                                        "&idoficio="+ idoficio;

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
                                String jsonSolicitud = obj.getString("response");
                                idSolicitud = Integer.parseInt(jsonSolicitud.toString());
                                if (idSolicitud != 0) {
                                    Toast.makeText(MapsActivity.this,"SU SOLICITUD HA SIDO ACEPTADA, ESPERE LA LLEGADA DEL EXPERTO" ,
                                            Toast.LENGTH_LONG).show();

                                    Log.e("ID SOLICITUD",idSolicitud+"");
                                    res = 1;
                                }

                            }catch (Exception e){


                                e.printStackTrace();

                            }finally {
                                connection.disconnect();
                            }

                            return res;


                        }

                        private int insertarSolicitudInicial() {
                            HttpURLConnection connection = null;
                        int res  =0;
                            try {
                            String request ="http://"+rutaServicio+":8080/SpringRest/registroSolicitudInicial?" +
                                    "cliente=" + cliente +
                                    "&idafiliado=" + idafiliado +
                                    "&idoficio="+ idoficio;

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
                            String[] split = result.toString().split(":");

                            if (split[1].contains("INSERTADO")) {
                                Toast.makeText(MapsActivity.this,"Solicitud realizada, esperando confirmación del Experto" ,
                                        Toast.LENGTH_LONG).show();
                                res = 1;
                            } else {
                                Log.e("ERROR",split[1]);
                                Toast.makeText(MapsActivity.this,"ERROR",
                                        Toast.LENGTH_LONG).show();
                            }

                        }catch (Exception e){


                            e.printStackTrace();

                        }finally {
                            connection.disconnect();
                        }

                            return res;

                    }

                    });


                    builder.setNegativeButton("Rechazar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //TODO SERVICIO DE RECHAZO DE SOLICITUD
                            dialogInterface.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void insertarUbicacionCliente(String user,double latitude, double longitude) {

         HttpURLConnection connection = null;
         try{
             String request = "http://"+rutaServicio+":8080/SpringRest/actualizarUbicacion?" +
                     "latitud=" + latitude +
                     "&longitud=" + longitude+
                     "&user=" + user;
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

             if (split[1].contains("ACTUALIZADO")) {
                 Toast.makeText(MapsActivity.this, "ACTUALIZADO",
                         Toast.LENGTH_LONG).show();
             } else {

                 Toast.makeText(MapsActivity.this, "ERROR",
                         Toast.LENGTH_LONG).show();

             }
         }catch(Exception e){
             Log.e("PROGRESO","ERROR ");

         }finally{

             findViewById(R.id.loadingPanel).setVisibility(View.GONE);
         }




     }


     private void addDrawerItems() {
         String[] osArray = { "Ver Datos", "Cerrar Sesión" };
         mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
         mDrawerList.setAdapter(mAdapter);
     }

     @Override
     public void onConnectionFailed(ConnectionResult connectionResult) {

     }

     public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
     public boolean checkLocationPermission(){
         if (ContextCompat.checkSelfPermission(this,
                 Manifest.permission.ACCESS_FINE_LOCATION)
                 != PackageManager.PERMISSION_GRANTED) {

             // Asking user if explanation is needed
             if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                     Manifest.permission.ACCESS_FINE_LOCATION)) {

                 // Show an explanation to the user *asynchronously* -- don't block
                 // this thread waiting for the user's response! After the user
                 // sees the explanation, try again to request the permission.

                 //Prompt the user once explanation has been shown
                 ActivityCompat.requestPermissions(this,
                         new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                         MY_PERMISSIONS_REQUEST_LOCATION);


             } else {
                 // No explanation needed, we can request the permission.
                 ActivityCompat.requestPermissions(this,
                         new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                         MY_PERMISSIONS_REQUEST_LOCATION);
             }
             return false;
         } else {
             return true;
         }
     }

     @Override
     public void onBackPressed() {


         Intent i = new Intent(Intent.ACTION_MAIN);
         i.addCategory(Intent.CATEGORY_HOME);
         startActivity(i);

     }

     @Override
     public void onRequestPermissionsResult(int requestCode,
                                            String permissions[], int[] grantResults) {
         switch (requestCode) {
             case MY_PERMISSIONS_REQUEST_LOCATION: {
                 // If request is cancelled, the result arrays are empty.
                 if (grantResults.length > 0
                         && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                     // permission was granted. Do the
                     // contacts-related task you need to do.
                     if (ContextCompat.checkSelfPermission(this,
                             Manifest.permission.ACCESS_FINE_LOCATION)
                             == PackageManager.PERMISSION_GRANTED) {

                         if (mGoogleApiClient == null) {
                             buildGoogleApiClient();
                         }
                         mMap.setMyLocationEnabled(true);
                     }

                 } else {

                     // Permission denied, Disable the functionality that depends on this permission.
                     Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                 }
                 return;
             }

             // other 'case' lines to check for other permissions this app might request.
             // You can add here other case statements according to your requirement.
         }
     }





 }

