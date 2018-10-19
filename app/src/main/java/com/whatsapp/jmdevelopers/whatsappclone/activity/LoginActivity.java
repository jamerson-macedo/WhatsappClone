package com.whatsapp.jmdevelopers.whatsappclone.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.whatsapp.jmdevelopers.whatsappclone.R;
import com.whatsapp.jmdevelopers.whatsappclone.config.ConfiguracaoFirebase;
import com.whatsapp.jmdevelopers.whatsappclone.model.Usuario;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText email, senha;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        senha = findViewById(R.id.senha);
        auth = ConfiguracaoFirebase.getAuth();
    }

    public void abrirtelacadastro(View v) {
        Intent i = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(i);
    }

    public void logarusuario(Usuario usuario) {

        auth.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {


                    abrirtelaprincipal();
                } else {
                    String excecao = "";
                    // validacoes do firebase
                    try {
                        // recupera excecao
                        throw task.getException();

                    } catch (FirebaseAuthWeakPasswordException e) {
                        excecao = "Digite uma senha mais forte !";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Usuario não encontrado";
                    } catch (FirebaseAuthUserCollisionException e) {
                        excecao = "Esta conta ja foi cadastrada";
                    } catch (Exception e) {
                        excecao = "Erro ao cadastrar o usuario" + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), excecao, Toast.LENGTH_LONG).show();

                    }
            }
        });


    }

    public void validarusuariologin(View v) {
        // recuperar login
        String campoemail = email.getText().toString();
        String camposenha = senha.getText().toString();
        // validar
        if (!campoemail.isEmpty()) {
            if (!camposenha.isEmpty()) {
                Usuario usuario = new Usuario();
                usuario.setEmail(campoemail);
                usuario.setSenha(camposenha);
                logarusuario(usuario);


            } else {
                Toast.makeText(LoginActivity.this, "Insira sua senha ", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Insira seu E-mail ", Toast.LENGTH_LONG).show();

        }

    }

    public void abrirtelaprincipal() {


        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }


}
