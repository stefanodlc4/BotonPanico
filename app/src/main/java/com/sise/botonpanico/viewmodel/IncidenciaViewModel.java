package com.sise.botonpanico.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sise.botonpanico.entities.Incidencia;
import com.sise.botonpanico.repositories.IncidenciaRepository;
import com.sise.botonpanico.shared.Callback;

public class IncidenciaViewModel extends ViewModel {

    private MutableLiveData<Boolean> insertarIncidenciaStatus;
    private IncidenciaRepository incidenciaRepository;

    public IncidenciaViewModel(){
        insertarIncidenciaStatus = new MutableLiveData<>();
        incidenciaRepository = new IncidenciaRepository();
    }

    public LiveData<Boolean> getInsertarIncidenciaStatus(){
        return insertarIncidenciaStatus;
    }

    public void insertarIncidencia(Incidencia incidencia){
        incidenciaRepository.insertarIncidencia(incidencia, new Callback<Incidencia>() {
            @Override
            public void onSuccess(Incidencia result) {
                insertarIncidenciaStatus.setValue(true);
            }

            @Override
            public void onFailure() {
                insertarIncidenciaStatus.setValue(false);
            }
        });
    }

}
