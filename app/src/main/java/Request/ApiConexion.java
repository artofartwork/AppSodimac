package Request;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import sodimac.net.appsodimac.model.Usuario;

/**
 * Created by cvillanueva on 02/02/2018.
 */

public class ApiConexion {




    private static final String API_BASE_URL="http://192.168.19.2:8733/Design_Time_Addresses/ServicioWeb/";

    private static ServicesApiInterface servicesApiInterface;
    private static OkHttpClient.Builder httpClient;

    public static ServicesApiInterface getMyApiClient() {

        if (servicesApiInterface == null) {

            Retrofit.Builder builder =new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
            httpClient =new OkHttpClient.Builder();
            httpClient.addInterceptor(interceptor());

            Retrofit retrofit = builder.client(httpClient.build()).build();
            servicesApiInterface = retrofit.create(ServicesApiInterface.class);
        }
        return servicesApiInterface;
    }

    public interface ServicesApiInterface {


        @GET("/ClienteService/iniciosesion/{user}/{pass}")

        Call< List<Integer> > inicioSesion(@Path("user") String usuario,@Path("pass") String pass);

    }


    private  static HttpLoggingInterceptor interceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor= new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return httpLoggingInterceptor;
    }
}
