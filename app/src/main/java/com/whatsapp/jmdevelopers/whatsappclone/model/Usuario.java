package com.whatsapp.jmdevelopers.whatsappclone.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.whatsapp.jmdevelopers.whatsappclone.config.ConfiguracaoFirebase;
import com.whatsapp.jmdevelopers.whatsappclone.helper.UsuarioFirebase;

import java.util.HashMap;
import java.util.Map;

public class Usuario {
    private String uid;
    String nome;
    String email;
    String senha;
    String fotousuario;

    public String getFotousuario() {
        return fotousuario;
    }

    public void setFotousuario(String fotousuario) {
        this.fotousuario = fotousuario;
    }

    public Usuario() {

    }

    public void salvar() {
        // pegando a referencia
        DatabaseReference databaseref = ConfiguracaoFirebase.getDatabaseReference();
        // get id pega o id e cria um novo a cada rodada
        DatabaseReference usuarioref = databaseref.child("usuarios").child(getUid());
        usuarioref.setValue(this);
        // salva o objetoo inteiro


    }

    public void atualizar() {
        // pega usuario
        String identificadorusuario = UsuarioFirebase.getidentificador();
        // pega a referencia do banco
        DatabaseReference database = ConfiguracaoFirebase.getDatabaseReference();
        // usuarios
        DatabaseReference usuariosref = database.child("usuarios").child(identificadorusuario);
        Map<String, Object> valoresusuario = converterparamap();
        // atualizando os dados
        usuariosref.updateChildren(valoresusuario);

    }

    @Exclude
    public Map<String, Object> converterparamap() {
        HashMap<String, Object> usuariomap = new HashMap<>();

        // configurando o map
        usuariomap.put("email", getEmail());
        usuariomap.put("nome", getNome());
        usuariomap.put("foto", getFotousuario());
        return usuariomap;

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
