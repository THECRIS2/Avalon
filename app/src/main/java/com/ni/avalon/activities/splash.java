package com.ni.avalon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.ni.avalon.R;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        // esta condicional sirve para que nuestro splash se ejecute por el tiempo indicado y pasado ese tiempo vaya al MainActivity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(splash.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 3000); // aqui establecemos el tiempo en milisegundos para hacerlo hay que tener en cuenta que cada segundo equivale a 1000 mls
    }
}