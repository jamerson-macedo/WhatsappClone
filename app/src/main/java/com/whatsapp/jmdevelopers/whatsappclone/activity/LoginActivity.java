package com.whatsapp.jmdevelopers.whatsappclone.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.whatsapp.jmdevelopers.whatsappclone.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void abrirtelacadastro(View v){
        Intent i = new Intent(LoginActivity.this,CadastroActivity.class);
        startActivity(i);


    }
}
