package com.sise.botonpanico.repositories;

import com.google.gson.Gson;
import com.sise.botonpanico.entities.Incidencia;
import com.sise.botonpanico.shared.Constants;
import com.sise.botonpanico.shared.ExceptionMessage;
import com.sise.botonpanico.shared.HttpUtil;

public class IncidenciaRepository {

    public void insertarIncidencia(Incidencia incidencia) throws Exception {
        String response = HttpUtil.POST(Constants.BASE_URL_API, "incidencias", new Gson().toJson(incidencia));
        if(response == null){
            throw new Exception(ExceptionMessage.RESPONSE_IS_NULL);
        }
        


    }

}
