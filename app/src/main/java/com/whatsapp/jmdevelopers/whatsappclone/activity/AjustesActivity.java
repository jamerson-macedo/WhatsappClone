package com.whatsapp.jmdevelopers.whatsappclone.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.whatsapp.jmdevelopers.whatsappclone.R;
import com.whatsapp.jmdevelopers.whatsappclone.helper.Permissao;

import java.util.ArrayList;

public class AjustesActivity extends AppCompatActivity {
    private static final int SELECAO_CAMERA = 100;
    private static final int SELECAO_GALERIA = 200;


    private ImageButton camera;
    private ImageButton galeria;
    private String[] permissoes = new String[]{
            // para ler arquivos
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA
            // acessos a camera
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        camera = findViewById(R.id.imagebuttoncamera);
        galeria = findViewById(R.id.imagebuttongaleria);


        Toolbar toolbar = findViewById(R.id.toolbarprincipal);
        toolbar.setTitle("Configurações");
        // configurando para rodar em todas versões
        setSupportActionBar(toolbar);
        // configgurando botao voltar simples :) tem que configurar o mainfedt colocar ela como filha do main
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // SOLIICITAR PERMISSOES
        Permissao.validarpermissoes(permissoes, this, 1);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // STARTA ACTIVITY MAS PRECISA DO RETORNO
                // testAR SE AINTENTE CONSEGUIU ABRIR
                if (intent.resolveActivity(getPackageManager())!=null) {
                    startActivityForResult(intent, SELECAO_CAMERA);
                }
            }
        });

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PEGA DA GALERIA
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // STARTA ACTIVITY MAS PRECISA DO RETORNO
                // testAR SE AINTENTE CONSEGUIU ABRIR
                if (intent.resolveActivity(getPackageManager())!=null) {
                    startActivityForResult(intent, SELECAO_GALERIA);
                }

            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int permissaoresultado : grantResults) {
            // verifica se foi negado
            if (permissaoresultado == PackageManager.PERMISSION_DENIED) {
                alertapermissao();
                /// avisa ao usuario

            }

        }
    }

    public void alertapermissao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para ultilizar o App, é nescessario aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }
}
