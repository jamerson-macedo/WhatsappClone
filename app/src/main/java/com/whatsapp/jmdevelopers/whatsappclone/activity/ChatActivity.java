package com.whatsapp.jmdevelopers.whatsappclone.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.whatsapp.jmdevelopers.whatsappclone.R;
import com.whatsapp.jmdevelopers.whatsappclone.adapter.MensagensAdapter;
import com.whatsapp.jmdevelopers.whatsappclone.config.ConfiguracaoFirebase;
import com.whatsapp.jmdevelopers.whatsappclone.helper.Base64Custom;
import com.whatsapp.jmdevelopers.whatsappclone.helper.UsuarioFirebase;
import com.whatsapp.jmdevelopers.whatsappclone.model.Mensagem;
import com.whatsapp.jmdevelopers.whatsappclone.model.Usuario;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    private TextView nomeusuario;
    private static final int SELECAO_CAMERA = 100;
    private CircleImageView fotoperfiltoolbar;
    private Usuario destinatario;
    private EditText mensagem;
    private ImageView botaofotos;
    // identificador dos usuarios, remetente e destinatario
    private String idremetente;

    private String iddestinatario;
    private RecyclerView recyclermensagens;
    private MensagensAdapter adapter;
    private DatabaseReference databaseReference;
    private DatabaseReference mensagensref;
    private StorageReference storageReference;

    private ChildEventListener childEventListenermensagens;
    private List<Mensagem> listademensagem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        nomeusuario = findViewById(R.id.nometoolbar);
        fotoperfiltoolbar = findViewById(R.id.imagemdotoolbar);
        mensagem = findViewById(R.id.textomensagem);
        botaofotos = findViewById(R.id.botaofotos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclermensagens = findViewById(R.id.recyclermensagens);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        // recuperar remetente

        idremetente = UsuarioFirebase.getidentificador();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            destinatario = (Usuario) bundle.getSerializable("chatcontato");
            nomeusuario.setText(destinatario.getNome());
            String foto = destinatario.getFotousuario();
            if (foto != null) {

                Uri uri = Uri.parse(destinatario.getFotousuario());
                Glide.with(ChatActivity.this).load(uri).into(fotoperfiltoolbar);
            } else {
                fotoperfiltoolbar.setImageResource(R.drawable.foto);

            }
            iddestinatario = Base64Custom.codificarbase64(destinatario.getEmail());
        }

        // configurar adapter
        adapter = new MensagensAdapter(listademensagem, getApplicationContext());

        // configurar recycler
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclermensagens.setLayoutManager(layoutManager);
        recyclermensagens.setHasFixedSize(true);
        recyclermensagens.setAdapter(adapter);
        storageReference=ConfiguracaoFirebase.getStorageReference();

        //recuperar mensagens
        databaseReference = ConfiguracaoFirebase.getDatabaseReference();
        mensagensref = databaseReference.child("mensagens").child(iddestinatario).child(idremetente);
        // enviar foto
        botaofotos.setOnClickListener(new View.OnClickListener() {
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
    }

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
                }

                if (imagem != null) {

                    // recuérando do firebase
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] dadosimagem = baos.toByteArray();
                    // criar nome da imagem
                    String nomedafoto=UUID.randomUUID().toString();

                    // salvar no firebase
                    // criando caminho
                    final StorageReference imagemref = storageReference
                            .child("imagens").child("fotos").child(idremetente).child(nomedafoto);
                    // salvando
                    UploadTask uploadTask = imagemref.putBytes(dadosimagem);
                    // Verificando se deu certo

                    uploadTask.continueWithTask(
                            new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            Toast.makeText(getApplicationContext(), "Foto salva", Toast.LENGTH_LONG).show();
                            // Continue with the task to get the download URL
                            return imagemref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                // pega o resultado e njoga no url
                                String url = task.getResult().toString();
                                Mensagem mensagem=new Mensagem();
                                mensagem.setIdusuario(idremetente);
                                mensagem.setMensagem("imagem.jpg");
                                mensagem.setImagem(url);
                                salvarmenssagem(idremetente,iddestinatario,mensagem);
                                salvarmenssagem(iddestinatario,idremetente,mensagem);
                                Toast.makeText(ChatActivity.this, "Sucesso ao fazer upload da imagem",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle failures
                                Toast.makeText(ChatActivity.this, "Erro ao fazer upload da imagem",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void enviarmensagem(View view) {
        String menssagemenviada = mensagem.getText().toString();
        if (!menssagemenviada.isEmpty()) {
            // salvar menssagem
            Mensagem msgm = new Mensagem();
            msgm.setIdusuario(idremetente);
            msgm.setMensagem(menssagemenviada);
            // salva mensagem para o remetente
            salvarmenssagem(idremetente, iddestinatario, msgm);
            // salvar para o destinatario
            salvarmenssagem(iddestinatario, idremetente, msgm);


        } else {
            Toast.makeText(ChatActivity.this, "Digite uma mensagem para enviar", Toast.LENGTH_LONG).show();

        }


    }

    private void salvarmenssagem(String idremetente, String iddestinatario, Mensagem msg) {
        // referencia ao banco
        DatabaseReference databaseReference = ConfiguracaoFirebase.getDatabaseReference();
        // configurando o no mensagens
        DatabaseReference mensagemref = databaseReference.child("mensagens");
        // no remetente que ira conter o destinatario e a mensagem com o ppsh cria varias mensagens
        mensagemref.child(idremetente).child(iddestinatario).push().setValue(msg);
        // limpar o texto quando enviar
        mensagem.setText("");


    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarmensagem();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mensagensref.removeEventListener(childEventListenermensagens);
    }

    private void recuperarmensagem() {
        // se ele atualizar ou fizer algo esse metodo é melhor
        childEventListenermensagens = mensagensref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // recuperar
                Mensagem mensagem = dataSnapshot.getValue(Mensagem.class);
                listademensagem.add(mensagem);
                // arualizar os metodos
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}