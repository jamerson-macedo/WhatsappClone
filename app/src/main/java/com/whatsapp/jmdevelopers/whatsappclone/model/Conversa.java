package com.whatsapp.jmdevelopers.whatsappclone.model;

import com.google.firebase.database.DatabaseReference;
import com.whatsapp.jmdevelopers.whatsappclone.config.ConfiguracaoFirebase;

import java.io.Serializable;

public class Conversa  implements Serializable {
    private String idremetente;
    private String iddestinatario;
    private String ulimamensagem;
    private Usuario usuarioexibicao;

    public Conversa() {
    }
    public void salvar(){
        // aqui nao precisa usar o push pq e so quero pegar a ultima
        DatabaseReference databaseRef =ConfiguracaoFirebase.getDatabaseReference();
        DatabaseReference conversaref=databaseRef.child("conversas");
        conversaref.child(this.getIdremetente()).child(this.getIddestinatario()).setValue(this);




    }

    public String getIdremetente() {
        return idremetente;
    }

    public void setIdremetente(String idremetente) {
        this.idremetente = idremetente;
    }

    public String getIddestinatario() {
        return iddestinatario;
    }

    public void setIddestinatario(String iddestinatario) {
        this.iddestinatario = iddestinatario;
    }

    public String getUlimamensagem() {
        return ulimamensagem;
    }

    public void setUlimamensagem(String ulimamensagem) {
        this.ulimamensagem = ulimamensagem;
    }

    public Usuario getUsuario() {
        return usuarioexibicao;
    }

    public void setUsuario(Usuario usuario) {
        this.usuarioexibicao = usuario;
    }
}
