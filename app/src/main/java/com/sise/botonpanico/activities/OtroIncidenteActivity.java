package com.sise.botonpanico.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sise.botonpanico.R;
import com.sise.botonpanico.adapters.TipoIncidenciaSpinnerAdapter;
import com.sise.botonpanico.entities.EstadoIncidencia;
import com.sise.botonpanico.entities.Incidencia;
import com.sise.botonpanico.entities.TipoIncidencia;
import com.sise.botonpanico.viewmodel.IncidenciaViewModel;
import com.sise.botonpanico.viewmodel.TipoIncidenciaViewModel;

public class OtroIncidenteActivity extends AppCompatActivity {

    private final String TAG = OtroIncidenteActivity.class.getSimpleName();
    private TipoIncidenciaViewModel tipoIncidenciaViewModel;
    private IncidenciaViewModel incidenciaViewModel;
    private Spinner tipoIncidenciaSpinner;
    private EditText etDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otro_incidente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tipoIncidenciaSpinner = findViewById(R.id.activityotroincidente_sp_tipoincidente);
        etDescripcion = findViewById(R.id.activityotroincidente_et_descripcion);

        incidenciaViewModel = new ViewModelProvider(this).get(IncidenciaViewModel.class);
        tipoIncidenciaViewModel = new ViewModelProvider(this).get(TipoIncidenciaViewModel.class);

        observeTipoIncidenciaViewModel();
        observeIncidenciaViewModel();
    }

    @Override
    protected void onStart() {
        super.onStart();
        tipoIncidenciaViewModel.listarTipoIncidencias();
    }

    private void observeTipoIncidenciaViewModel(){
        tipoIncidenciaViewModel.getListarTipoIncidenciasLiveData().observe(this,tipoIncidencias -> {
            TipoIncidenciaSpinnerAdapter adapter = new TipoIncidenciaSpinnerAdapter(this,tipoIncidencias);
            tipoIncidenciaSpinner.setAdapter(adapter);
        });
    }

    private void observeIncidenciaViewModel(){
        incidenciaViewModel.getInsertarIncidenciaStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                String text = aBoolean.booleanValue() ? "¡Se ha enviado correctamente!" : "¡Ocurrió un error al enviar!";
                Toast.makeText(getApplicationContext(),text, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(OtroIncidenteActivity.this, InicioActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onClickEnviar(View view) {

        String descripcion = etDescripcion.getText().toString().trim();
        if(descripcion.isEmpty()){
            etDescripcion.setError("Debe ingresar una descripción");
            return;
        }

        Incidencia incidencia = new Incidencia();
        incidencia.setDescripcion(etDescripcion.getText().toString());

        EstadoIncidencia estadoIncidencia = new EstadoIncidencia();
        estadoIncidencia.setIdEstadoIncidencia(1);
        incidencia.setEstadoIncidencia(estadoIncidencia);

        TipoIncidencia tipoIncidencia = (TipoIncidencia)tipoIncidenciaSpinner.getSelectedItem();
        incidencia.setTipoIncidencia(tipoIncidencia);
        incidenciaViewModel.insertarIncidencia(incidencia);
    }



}