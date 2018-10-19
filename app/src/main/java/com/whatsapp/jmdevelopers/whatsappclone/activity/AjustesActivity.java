package com.whatsapp.jmdevelopers.whatsappclone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.whatsapp.jmdevelopers.whatsappclone.R;

public class AjustesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        Toolbar toolbar = findViewById(R.id.toolbarprincipal);
        toolbar.setTitle("Configurações");
        // configurando para rodar em todas versões
        setSupportActionBar(toolbar);
        // configgurando botao voltar simples :) tem que configurar o mainfedt colocar ela como filha do main
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
