package com.sise.botonpanico.activities;

import android.os.Bundle;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.sise.botonpanico.R;
import com.sise.botonpanico.adapters.TipoIncidenciaSpinnerAdapter;
import com.sise.botonpanico.viewmodel.IncidenciaViewModel;
import com.sise.botonpanico.viewmodel.TipoIncidenciaViewModel;

public class OtroIncidenteActivity extends AppCompatActivity {

    private TipoIncidenciaViewModel tipoIncidenciaViewModel;
    private Spinner tipoIncidenciaSpinner;

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

        tipoIncidenciaViewModel = new ViewModelProvider(this).get(TipoIncidenciaViewModel.class);
        observeTipoIncidenciaViewModel();
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


}