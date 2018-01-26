package sodimac.net.appsodimac.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
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
import android.util.JsonWriter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import org.json.JSONStringer;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import sodimac.net.appsodimac.R;
import sodimac.net.appsodimac.model.Afiliado;

public class MapsAFActivity  extends ActionBarActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{
    double latitudCliente;
    double longitudCliente;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    Button botonTrabajo;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    String user;
    double latitudeafiliado;
    private String mActivityTitle;
    ArrayList<Afiliado> datosAfiliado;
    List<String> dCliente;
    double longitudeafiliado;
    int response;
    int idSolicitud;
    TextView textoResultado;
    int idocupacion;
    int resaceptar;
    int resprogreso;
    String correoCliente;
    private ListView mDrawerList;
    ProgressDialog progressDialog;
    private DrawerLayout mDrawerLayout;
      String rutaServicio = "192.168.43.40" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeAF);
        setContentView(R.layout.activity_maps_af);
        Intent intent = getIntent();
        dCliente = new ArrayList<>();
        botonTrabajo = (Button)findViewById(R.id.btnBotonBuscarTrabajo);
        user = intent.getExtras().getString("user");
       this.setFinishOnTouchOutside(false);
        datosAfiliado = obtenerInformacionAfiliado(user);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }


        mDrawerList = (ListView)findViewById(R.id.navListAF);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        addDrawerItems();
        setupDrawer();
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    Intent menuIntent = new Intent(getApplicationContext(), DatosAfiliadoActivity.class);
                    menuIntent.putExtra("user",user);
                    startActivity(menuIntent);

                }
                if(i == 1){
                    //SERVICIO DE CIERRE DE SESIÓN

                    HttpURLConnection connection = null;

                    try {
                        String rpuntuacion= "http://"+rutaServicio+":8080/SpringRest/cerrarSesionAfiliado?user=" +user;
                        URL url = new URL(rpuntuacion);
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.setRequestMethod("GET");
                        connection.setReadTimeout(10000);
                        connection.setConnectTimeout(15000);
                        StringBuilder result = new StringBuilder();
                        InputStream input = new BufferedInputStream(connection.getInputStream());
                        BufferedReader read = new BufferedReader(new InputStreamReader(input));


                        Toast.makeText(MapsAFActivity.this,"Vuelva pronto" ,
                                Toast.LENGTH_LONG).show();
                        Intent menuIntent = new Intent(getApplicationContext(), AFLoginActivity.class);
                        startActivity(menuIntent);


                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {

                        connection.disconnect();
                    }


                }
            }
        });


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void addDrawerItems() {
        String[] osArray = { "Ver Datos", "Cerrar Sesión" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
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


    private ArrayList<Afiliado> obtenerInformacionAfiliado(String user) {
            Log.e("PROGRESO","OBTENIENDO INFORMACION DEL AFILIADO");
            HttpURLConnection connection = null;
        ArrayList<Afiliado> listaAfiliado = new ArrayList<Afiliado>();
            try {
                Log.e("PROGRESO","CARGANDO INFORMACION DEL AFILIADO");
                String rAfiliado= "http://"+rutaServicio+":8080/SpringRest/obtenerAfiliadoCorreo?correo=" + user;
                URL url = new URL(rAfiliado);
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

                JSONObject obj = new JSONObject(result.toString());
                JSONArray jsonAfiliado = obj.getJSONArray("response");


                for (int i = 0; i < jsonAfiliado.length(); i++) {
                    Afiliado afiliado = new Afiliado();
                    Log.e("PROGRESO", "CARGANDO INFORMACION DEL AFILIADO");
                    JSONObject id = jsonAfiliado.getJSONObject(i);
                    afiliado.setIdafiliado(Integer.parseInt(id.getString("idafiliado")));
                    afiliado.setNumdoc(id.getString("numdoc"));
                    afiliado.setNombres(id.getString("nombres"));
                    afiliado.setAmat(id.getString("amat"));
                    afiliado.setApat(id.getString("apat"));
                    afiliado.setFoto(id.getString("foto"));
                    afiliado.setLatitudafiliado(Double.parseDouble(id.getString("latitudAfiliado")));
                    afiliado.setLongitudafiliado(Double.parseDouble(id.getString("longitudAfiliado")));
                    listaAfiliado.add(afiliado);
                }


        }catch (Exception ex){
            ex.printStackTrace();
        }
        return listaAfiliado;
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
        mLocationRequest.setInterval(20);
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
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        latitudeafiliado = location.getLatitude();
        longitudeafiliado = location.getLongitude();

        if(!datosAfiliado.isEmpty()){

            String nombreAfiliado = datosAfiliado.get(0).getNombres();
            textoResultado = (TextView) findViewById(R.id.txttextoResultado);
            textoResultado.setText("Bienvenido, " + nombreAfiliado);
        }


        botonTrabajo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 int res = insertarUbicacionAfiliado(user,latitudeafiliado,longitudeafiliado);
                response = 0;



                if(res==1){

                    do{

                        response=  consultarEstadoInicial();
                    }while(response!=1);

                }


                if(response == 1){

                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsAFActivity.this);
                    builder.setCancelable(true);
                    Log.e("PROGRESO",dCliente.get(4)+"");
                    builder.setTitle(dCliente.get(4) + " " + dCliente.get(3) + " " + dCliente.get(2));
                    builder.setMessage("Con teléfono : " + dCliente.get(5) +" Solicita sus servicios ");
                    builder.setPositiveButton("Aceptar Solicitud", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialogInterface, int i) {
                            int res = aceptarSolicitud(dCliente.get(0),datosAfiliado.get(0).getIdafiliado());
                            resprogreso = 0;
                            if(res==1){


                                final MarkerOptions markerOptions = new MarkerOptions();
                                Log.e("PROGRESO", "AÑADIENDO MARKERS");
                                LatLng latLng1 = new LatLng(latitudCliente,longitudCliente);
                                markerOptions.position(latLng1);
                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                final Marker markerCliente =  mMap.addMarker(markerOptions);

                                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(Marker marker) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MapsAFActivity.this);
                                        builder.setCancelable(true);
                                        Log.e("PROGRESO","Trabajo en Progreso");
                                        builder.setTitle("Terminar trabajo - ");
                                        builder.setPositiveButton("Terminar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                pasarAPuntuar(dCliente.get(0),datosAfiliado.get(0).getIdafiliado());
                                                markerCliente.remove();
                                            }

                                            private void pasarAPuntuar(String cliente , int afiliado) {

                                                HttpURLConnection connection = null;

                                                try{

                                                    String request ="http://"+rutaServicio+":8080/SpringRest/pasarAPuntuar?" +
                                                            "cliente=" + cliente +
                                                            "&idafiliado=" + afiliado;
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

                                                    if (split[1].contains("ACTUALIZADO")) {
                                                        Toast.makeText(MapsAFActivity.this,"Trabajo terminado. Presione buscar si quiere buscar otro trabajo" ,
                                                                Toast.LENGTH_LONG).show();
                                                        dCliente = new ArrayList<>();
                                                        dialogInterface.dismiss();


                                                    } else {
                                                        Log.e("ERROR",split[1]);
                                                        Toast.makeText(MapsAFActivity.this,"ERROR",
                                                                Toast.LENGTH_LONG).show();
                                                    }

                                                }catch (Exception e){

                                                    e.printStackTrace();

                                                }


                                            }
                                        }).create();
                                        builder.show();
                                        return true;
                                    }
                                });




                            }


                        }

                        private int aceptarSolicitud(String s, int idafiliado) {
                            HttpURLConnection connection = null;
                            int res  =0;
                            try{

                                String request ="http://"+rutaServicio+":8080/SpringRest/aceptarSolicitud?" +
                                        "cliente=" + s +
                                        "&idafiliado=" + idafiliado;
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

                                if (split[1].contains("ACTUALIZADO")) {
                                    Toast.makeText(MapsAFActivity.this,"Solicitud aceptada, proceder a realizar el trabajo y posterior evaluación" ,
                                            Toast.LENGTH_LONG).show();
                                    res = 1;
                                } else {
                                    Log.e("ERROR",split[1]);
                                    Toast.makeText(MapsAFActivity.this,"ERROR",
                                            Toast.LENGTH_LONG).show();
                                }

                            }catch (Exception e){

                                e.printStackTrace();

                            }

                            return res;
                        }
                    });
                    builder.setNegativeButton("Rechazar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //TODO SERVICIO DE RECHAZO DE CLIENTE
                            int res = rechazarSolicitud(idSolicitud);
                            dialogInterface.dismiss();

                        }

                        private int rechazarSolicitud(int idsol) {
                            HttpURLConnection connection = null;
                            int res  =0;
                            try{

                                String request ="http://"+rutaServicio+":8080/SpringRest/rechazarSolicitud?" +
                                        "idsolicitud=" + idsol;

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

                                if (split[1].contains("ACTUALIZADO")) {
                                    Toast.makeText(MapsAFActivity.this,"Ha rechazado la solicitud." ,
                                            Toast.LENGTH_LONG).show();
                                    res = 1;
                                } else {
                                    Log.e("ERROR",split[1]);
                                    Toast.makeText(MapsAFActivity.this,"ERROR",
                                            Toast.LENGTH_LONG).show();
                                }

                            }catch (Exception e){

                                e.printStackTrace();

                            }

                            return res;
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                }


            }
        });
        markerOptions.title("Posicion actual");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        mMap.animateCamera(cameraUpdate);


            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            botonTrabajo.setText("Aparecer como Disponible");

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }


    }

    private int consultarEstadoInicial() {
        HttpURLConnection connection = null;
        int res = 0;
        Log.e("PROGRESO","CONSULTANDO ESTADO INICIAL");
        try{
            String request = "http://"+rutaServicio+":8080/SpringRest/consultaEstadoInicial?" +
                    "&correo=" + user;
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

            JSONObject obj = new JSONObject(result.toString());
            JSONArray jsonCliente = obj.getJSONArray("response");


            List<String> datosCliente = new ArrayList<String>();
            if (datosCliente != null) {


                for (int i=0;i<jsonCliente.length();i++) {
                    JSONObject id = jsonCliente.getJSONObject(i);


                    String idcliente = id.getString("idcliente");

                    String numdoc = id.getString("numdoc");
                    String apaterno = id.getString("apaterno");
                    String amaterno = id.getString("amaterno");
                    String nombres = id.getString("nombres");
                    String tlf = id.getString("tlf");

                    String direccion = id.getString("direccion");
                    String email = id.getString("email");

                    String fechaCreacion = id.getString("fechaCreacion");


                    String datosdocumento = id.getString("idtipodoc");
                    String[] partesdocumento = datosdocumento.split(",");
                    String tipodoc = partesdocumento[1];
                    String tipodocumento = tipodoc.substring(tipodoc.indexOf(":")+ 2, tipodoc.indexOf("}"));
                    tipodocumento =  tipodocumento.replace("\"", "");
                     latitudCliente = Double.parseDouble(id.getString("latitudCliente"));
                     longitudCliente = Double.parseDouble(id.getString("longitudCliente"));
                    String datosubigeo = id.getString("idubigeo");
                    String[] partesubigeo = datosubigeo.split(",");
                    String dist = partesubigeo[1];
                    String distrito = dist.substring(dist.indexOf(":"));
                    distrito = distrito.replace("\"", "");
                    String depart = partesubigeo[2];
                    String departamento = depart.substring(depart.indexOf(":") + 2);
                    departamento = departamento.replace("\"", "");
                    departamento = departamento.replace("}", "");
                    String prov = partesubigeo[3];
                    String provincia = prov.substring(prov.indexOf(":")+2);
                    provincia=  provincia.replace("\"", "");
                    provincia=  provincia.replace(":", "");

                    String datosestadoregistro = id.getString("idestadoreg");
                    String[] partesestreg = datosestadoregistro.split(",");
                    String estr = partesestreg[1];
                    String estadoregistro = estr.substring(estr.indexOf(":")+2);
                    estadoregistro=estadoregistro.replace("\"", "");
                    estadoregistro=estadoregistro.replace("}", "");



                    datosCliente.add(idcliente);
                    datosCliente.add(numdoc);
                    datosCliente.add(apaterno);
                    datosCliente.add(amaterno);
                    datosCliente.add(nombres);
                    datosCliente.add(tlf);
                    datosCliente.add(departamento);
                    datosCliente.add(provincia);
                    datosCliente.add(distrito);
                    datosCliente.add(direccion);
                    datosCliente.add(email);
                    datosCliente.add(estadoregistro);
                    datosCliente.add(fechaCreacion);
                    idSolicitud = obtenerIdSolicitud(idcliente,user);

res = 1;

                  dCliente =  datosCliente;
                }

            }
        }catch (Exception e){

            e.printStackTrace();
        }


        return res;
    }

    private int obtenerIdSolicitud(String idcliente,String user) {
        int res = 0;
        HttpURLConnection connection = null;
        try{
            String request = "http://"+rutaServicio+":8080/SpringRest/obtenerIdSolicitud?" +
                    "idcliente=" + idcliente +
                    "&correo=" + user;
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
            while ((line = read.readLine()) != null) {

                result.append(line);
            }
            String[] split = result.toString().split(":");

            JSONObject obj = new JSONObject(result.toString());
            String jsonSolicitud = obj.getString("response");

            res = Integer.parseInt(jsonSolicitud);
        Log.e("IDSOLICITUD",res+"");
        }catch(Exception xe){

            Log.e("ERROR" , xe+"");
        }

        return res;
    }

    private int insertarUbicacionAfiliado(String user, double latitudeafiliado, double longitudeafiliado) {
        Log.e("PROGRESO","ACTUALIZANDO UBICACION DEL AFILIADO");
        Log.e("LATITUD",latitudeafiliado+"");
        Log.e("LONGITUD",longitudeafiliado+"");
int res = 0;
        HttpURLConnection connection = null;
        try{
            String request = "http://"+rutaServicio+":8080/SpringRest/actualizarUbicacionAfiliado?" +
                    "latitud=" + latitudeafiliado +
                    "&longitud=" + longitudeafiliado+
                    "&user=" + user;
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
            while ((line = read.readLine()) != null) {

                result.append(line);
            }
            String[] split = result.toString().split(":");

            if (split[1].contains("ACTUALIZADO")) {
                Toast.makeText(MapsAFActivity.this, "ACTUALIZADO",
                        Toast.LENGTH_LONG).show();
                res = 1;
                Log.e("PROGRESO","AFILIADO ACTUALIZADO");
            } else {
res = 0;
                Toast.makeText(MapsAFActivity.this, "ERROR",
                        Toast.LENGTH_LONG).show();
                Log.e("PROGRESO","ERROR");

            }
        }catch(Exception xe){

Log.e("ERROR" , xe+"");
        }



        return res;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e("CONN/ERROR", String.valueOf(connectionResult.getErrorCode()));
    ;
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
