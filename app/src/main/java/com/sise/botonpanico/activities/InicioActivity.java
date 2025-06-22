package com.sise.botonpanico.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sise.botonpanico.R;
import com.sise.botonpanico.entities.EstadoIncidencia;
import com.sise.botonpanico.entities.Incidencia;
import com.sise.botonpanico.entities.TipoIncidencia;
import com.sise.botonpanico.viewmodel.IncidenciaViewModel;

public class InicioActivity extends AppCompatActivity {

    private IncidenciaViewModel incidenciaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio);

        incidenciaViewModel = new ViewModelProvider(this).get(IncidenciaViewModel.class);

        observeIncidenciaViewModel();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void observeIncidenciaViewModel(){
        incidenciaViewModel.getInsertarIncidenciaStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                String text = aBoolean.booleanValue() ? "¡Se ha enviado correctamente!" : "¡Ocurrió un error al enviar!";
                Toast.makeText(getApplicationContext(),text, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onClickSOSPrincipal(View view){
        Incidencia incidencia = new Incidencia();
        incidencia.setDescripcion("SOS Principal");

        EstadoIncidencia estadoIncidencia = new EstadoIncidencia();
        estadoIncidencia.setIdEstadoIncidencia(1);
        incidencia.setEstadoIncidencia(estadoIncidencia);

        TipoIncidencia tipoIncidencia = new TipoIncidencia();
        tipoIncidencia.setIdTipoIncidencia(1);
        incidencia.setTipoIncidencia(tipoIncidencia);
        incidenciaViewModel.insertarIncidencia(incidencia);
    }

    public void onClickOtraIncidencia(View view){
        Intent intent = new Intent(this, OtroIncidenteActivity.class);
        startActivity(intent);
    }
}