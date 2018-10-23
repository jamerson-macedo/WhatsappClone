package com.whatsapp.jmdevelopers.whatsappclone.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.whatsapp.jmdevelopers.whatsappclone.config.ConfiguracaoFirebase;

public class UsuarioFirebase {
    public static String getidentificador() {
        // pega o usuario atual e codifica

        FirebaseAuth usuario = ConfiguracaoFirebase.getAuth();
        // recupera o usuario atual e email
        String email = usuario.getCurrentUser().getEmail();
        String identificadorusuario = Base64Custom.codificarbase64(email);
        return identificadorusuario;
    }
    public static String getusuarioaual(){
        FirebaseAuth usuario = ConfiguracaoFirebase.getAuth();
        // recupera o usuario atual e email
        String email = usuario.getCurrentUser().getEmail();

return email;

    }


}
