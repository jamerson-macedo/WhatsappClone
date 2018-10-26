package com.whatsapp.jmdevelopers.whatsappclone.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.whatsapp.jmdevelopers.whatsappclone.R;
import com.whatsapp.jmdevelopers.whatsappclone.model.Usuario;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    private TextView nomeusuario;
    private CircleImageView fotoperfiltoolbar;
    private Usuario destinatario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        nomeusuario = findViewById(R.id.nometoolbar);
        fotoperfiltoolbar = findViewById(R.id.imagemdotoolbar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            destinatario = (Usuario) bundle.getSerializable("chatcontato");
            nomeusuario.setText(destinatario.getNome());
            String foto = destinatario.getFotousuario();
            if (foto != null) {
                String uriString;
                Uri uri=Uri.parse(destinatario.getFotousuario());
                Glide.with(ChatActivity.this).load(uri).into(fotoperfiltoolbar);



            } else {
            fotoperfiltoolbar.setImageResource(R.drawable.foto);

            }
        }

    }

}
