package com.whatsapp.jmdevelopers.whatsappclone.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.whatsapp.jmdevelopers.whatsappclone.config.ConfiguracaoFirebase;

public class Usuario {
    private String uid;
    String nome;
    String email;
    String senha;

    public Usuario() {

    }
    public void salvar(){
    // pegando a referencia
        DatabaseReference databaseref=ConfiguracaoFirebase.getDatabaseReference();
        // get id pega o id e cria um novo a cada rodada
        DatabaseReference usuarioref=databaseref.child("usuarios").child(getUid());
        usuarioref.setValue(this);
        // salva o objetoo inteiro


    }

    public String getNome() {
        return nome;
    }
    // NAO SALVA O ID
    @Exclude
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
