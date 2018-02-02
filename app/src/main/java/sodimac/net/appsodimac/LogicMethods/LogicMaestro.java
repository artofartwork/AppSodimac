package sodimac.net.appsodimac.LogicMethods;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.util.List;

import Request.ApiConexion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sodimac.net.appsodimac.model.Usuario;

/**
 * Created by cvillanueva on 02/02/2018.
 */

public class LogicMaestro {

    private Context ctx;
    View progressDialog = null;


    public LogicMaestro(Context ctx, View progressDialog)
    {
        this.ctx = ctx;
        this.progressDialog = progressDialog;


    }


    public int doLogin(String usuario, String pass)  // 1 es exito, 0 es fallado
    {


        try {
            Call<List<Integer>> call= ApiConexion.getMyApiClient().inicioSesion(usuario,pass);
            call.enqueue(new Callback<List<Integer>>() {
                @Override
                public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {

                    if(response!=null && response.isSuccessful()){
                          Log.e("RESULTADO DE CONSULTA",1+"");

                        List<Integer> listaResponse = response.body();
                        //limpio todo
                        for (Integer obj : listaResponse)
                        {

                         //   Log.e("RESULTADO DE CONSULTA",obj+"");
                        }

                    }
                }

                @Override
                public void onFailure(Call<List<Integer>> call, Throwable t) {
                //    progressDialog.setVisibility(View.GONE);
                    Log.e("RESULTADO DE CONSULTA",t+"");
                    Log.e("RESULTADO DE CONSULTA",0+"");
                 //   Log.e("RESULTADO DE CONSULTA",t+"");

                }
            });


            return 1;

        }catch (Exception e)
        {
            throw e;

        }



    }






}
