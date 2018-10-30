package com.whatsapp.jmdevelopers.whatsappclone.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.whatsapp.jmdevelopers.whatsappclone.R;
import com.whatsapp.jmdevelopers.whatsappclone.config.ConfiguracaoFirebase;
import com.whatsapp.jmdevelopers.whatsappclone.helper.Base64Custom;
import com.whatsapp.jmdevelopers.whatsappclone.helper.UsuarioFirebase;
import com.whatsapp.jmdevelopers.whatsappclone.model.Mensagem;
import com.whatsapp.jmdevelopers.whatsappclone.model.Usuario;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    private TextView nomeusuario;
    private CircleImageView fotoperfiltoolbar;
    private Usuario destinatario;
    private EditText mensagem;
    // identificador dos usuarios, remetente e destinatario
    private String idremetente;
    private String iddestinatario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        nomeusuario = findViewById(R.id.nometoolbar);
        fotoperfiltoolbar = findViewById(R.id.imagemdotoolbar);
        mensagem = findViewById(R.id.textomensagem);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        // recuperar remetente

        idremetente=UsuarioFirebase.getidentificador();



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            destinatario = (Usuario) bundle.getSerializable("chatcontato");
            nomeusuario.setText(destinatario.getNome());
            String foto = destinatario.getFotousuario();
            if (foto != null) {
                String uriString;
                Uri uri = Uri.parse(destinatario.getFotousuario());
                Glide.with(ChatActivity.this).load(uri).into(fotoperfiltoolbar);
            } else {
                fotoperfiltoolbar.setImageResource(R.drawable.foto);

            }
        }
        iddestinatario=Base64Custom.codificarbase64(destinatario.getEmail());

    }

    public void enviarmensagem(View view) {
        String menssagemenviada = mensagem.getText().toString();
        if (!menssagemenviada.isEmpty()) {
            // salvar menssagem
            Mensagem msgm = new Mensagem();
            msgm.setIdusuario(idremetente);
            msgm.setMensagem(menssagemenviada);
            salvarmenssagem(iddestinatario,idremetente,msgm);


        } else {
            Toast.makeText(ChatActivity.this, "Digite uma mensagem para enviar", Toast.LENGTH_LONG).show();

        }


    }

    private void salvarmenssagem(String idremetente,String iddestinatario,Mensagem msg){
        // referencia ao banco
        DatabaseReference databaseReference= ConfiguracaoFirebase.getDatabaseReference();
        // configurando o no mensagens
        DatabaseReference mensagemref=databaseReference.child("mensagens");
        // no remetente que ira conter o destinatario e a mensagem com o ppsh cria varias mensagens
        mensagemref.child(idremetente).child(iddestinatario).push().setValue(msg);
        // limpar o texto quando enviar
        mensagem.setText("");


    }

}
