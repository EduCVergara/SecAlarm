package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.util.TypedValue;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.SharedPreferences;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class activityAccesibilidad extends BaseActivity {

    private Button btnNoche, btnDia, btnAumentar, btnReducir;
    private Spinner spinnerSensitivity;
    private SharedPreferences sharedPreferences;
    private TextView textView; // Asegúrate de inicializar textView en el layout
    private float currentTextSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accesibilidad);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences("AccesibilityPrefs", MODE_PRIVATE);
        btnNoche = findViewById(R.id.btnNoche);
        btnDia = findViewById(R.id.btnDia);
        btnAumentar = findViewById(R.id.btnAumentar);
        btnReducir = findViewById(R.id.btnReducir);
        spinnerSensitivity = findViewById(R.id.spinner);
        textView = findViewById(R.id.textView15);

        // Obtener el tamaño de texto almacenado y aplicarlo
        float savedTextSize = sharedPreferences.getFloat("textSize", 1.0f);
        getResources().getConfiguration().fontScale = savedTextSize;
        getResources().updateConfiguration(getResources().getConfiguration(), getResources().getDisplayMetrics());
        updateTextSizes();

        btnNoche.setOnClickListener(view -> setNightMode());
        btnDia.setOnClickListener(view -> setDayMode());

        btnAumentar.setOnClickListener(view -> changeTextSize(true));
        btnReducir.setOnClickListener(view -> changeTextSize(false));

        spinnerSensitivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sensitivityLevel = (String) parent.getItemAtPosition(position);
                sharedPreferences.edit().putString("sensitivity", sensitivityLevel).apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setNightMode(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.black, getTheme()));
        } else {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.black));
        }
    }

    private void setDayMode(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.white, getTheme()));
        } else {
            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    public void changeTextSize(boolean increase) {
        float currentSize = getResources().getConfiguration().fontScale;
        float newSize = increase ? currentSize + 0.1f : currentSize - 0.1f;

        if (newSize >= 1.0f && newSize <= 1.5f) {
            // Guarda el nuevo tamaño en SharedPreferences
            sharedPreferences.edit().putFloat("textSize", newSize).apply();

            // Actualiza la configuración global
            getResources().getConfiguration().fontScale = newSize;
            getResources().updateConfiguration(getResources().getConfiguration(), getResources().getDisplayMetrics());

            // Llama a un método para aplicar el nuevo tamaño de texto a todas las vistas
            updateTextSizes();
        }
    }

    // Método para actualizar el tamaño del texto de todas las vistas
    private void updateTextSizes() {
        float textSize = getResources().getConfiguration().fontScale * getResources().getDisplayMetrics().scaledDensity;

        // Actualiza todos los TextView en esta actividad (puedes hacer lo mismo para otros tipos de vistas)
        //textView.setTextSize(textSize);

        // Si tienes otros TextViews, agrégales aquí
        // Ejemplo: textView2.setTextSize(textSize);
        recreate();
    }

    public void paginaMenuPrincipal(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
