package com.whatsapp.jmdevelopers.whatsappclone.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.whatsapp.jmdevelopers.whatsappclone.R;
import com.whatsapp.jmdevelopers.whatsappclone.config.ConfiguracaoFirebase;
import com.whatsapp.jmdevelopers.whatsappclone.helper.Base64Custom;
import com.whatsapp.jmdevelopers.whatsappclone.helper.Permissao;
import com.whatsapp.jmdevelopers.whatsappclone.helper.UsuarioFirebase;
import com.whatsapp.jmdevelopers.whatsappclone.model.Usuario;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AjustesActivity extends AppCompatActivity {
    private static final int SELECAO_CAMERA = 100;
    private static final int SELECAO_GALERIA = 200;
    private StorageReference storageReference;
    private String identificacaoUsuario;
    private CircleImageView imagem_perfil;
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
        imagem_perfil = findViewById(R.id.profile_image);
        storageReference = ConfiguracaoFirebase.getStorageReference();
        identificacaoUsuario=UsuarioFirebase.getidentificador();



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
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, SELECAO_CAMERA);
                }
            }
        });

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PEGA DA GALERIA
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // STARTA ACTIVITY MAS PRECISA DO RETORNO
                // testAR SE AINTENTE CONSEGUIU ABRIR
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, SELECAO_GALERIA);
                }

            }
        });


    }

    // metodo para pegar a foto
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
// verifica se pegou
        if (resultCode == RESULT_OK) {
            Bitmap imagem = null;

            try {

                switch (requestCode) {
                    // caso for a camera
                    case SELECAO_CAMERA:
                        imagem = (Bitmap) data.getExtras().get("data");
                        break;
                    case SELECAO_GALERIA:
                        Uri localImagemSelecionada = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);
                        break;
                }

                if (imagem != null) {

                    // recuérando do firebase
                    ByteArrayOutputStream baos= new ByteArrayOutputStream();

                    imagem.compress(Bitmap.CompressFormat.JPEG,70,baos);
                    byte[] dadosimagem = baos.toByteArray();



                    imagem_perfil.setImageBitmap(imagem);
                    // salvar no firebase
                    StorageReference imagemref = storageReference
                            .child("imagens").child("perfil").child(identificacaoUsuario).child("perfil.jpg");
                    // salvando
                    UploadTask uploadTask=imagemref.putBytes(dadosimagem);
                    // Verificando se deu certo
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Erro de Upload", Toast.LENGTH_LONG).show();

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(), "Upload com sucesso", Toast.LENGTH_LONG).show();

                        }
                    });


                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    // metodos para permissoes
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
