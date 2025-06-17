package com.sise.botonpanico.repositories;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sise.botonpanico.entities.Incidencia;
import com.sise.botonpanico.entities.TipoIncidencia;
import com.sise.botonpanico.shared.BaseResponse;
import com.sise.botonpanico.shared.Callback;
import com.sise.botonpanico.shared.Constants;
import com.sise.botonpanico.shared.HttpUtil;

import java.util.List;

public class TipoIncidenciaRepository {
    public void listarTipoIncidencias(Callback<List<TipoIncidencia>> callback){
        try {
            String response = HttpUtil.GET(Constants.BASE_URL_API, "/tipo-incidencias");
            if (response == null) {
                callback.onFailure();
                return;
            }

            BaseResponse<List<TipoIncidencia>> baseResponse = new Gson().fromJson(
                    response,
                    TypeToken.getParameterized(BaseResponse.class, List.class, TipoIncidencia.class).getType()
            );
            if(baseResponse == null){
                callback.onFailure();
                return;
            }

            if(!baseResponse.isSuccess()){
                callback.onFailure();
                return;
            }
            callback.onSuccess(baseResponse.getData());
        } catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
            callback.onFailure();
        }
    }
}
