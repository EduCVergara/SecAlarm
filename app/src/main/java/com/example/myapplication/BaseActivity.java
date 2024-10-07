package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtener y aplicar el tamaño de texto desde SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("AccesibilityPrefs", MODE_PRIVATE);
        float textSize = sharedPreferences.getFloat("textSize", 1.0f); // Valor por defecto

        // Actualiza la configuración global
        getResources().getConfiguration().fontScale = textSize;
        getResources().updateConfiguration(getResources().getConfiguration(), getResources().getDisplayMetrics());
    }
}
