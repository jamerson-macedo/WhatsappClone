package com.whatsapp.jmdevelopers.whatsappclone.activity;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.whatsapp.jmdevelopers.whatsappclone.R;
import com.whatsapp.jmdevelopers.whatsappclone.helper.Permissao;

import java.util.ArrayList;

public class AjustesActivity extends AppCompatActivity {
   private String[] permissoes=new String []{
           // para ler arquivos
           Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA
            // acessos a camera


   };

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
        // SOLIICITAR PERMISSOES
        Permissao.validarpermissoes(permissoes,this,1);



    }
}
