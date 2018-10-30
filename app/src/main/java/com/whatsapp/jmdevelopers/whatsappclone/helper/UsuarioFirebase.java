package com.whatsapp.jmdevelopers.whatsappclone.helper;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.whatsapp.jmdevelopers.whatsappclone.config.ConfiguracaoFirebase;
import com.whatsapp.jmdevelopers.whatsappclone.model.Usuario;

import java.util.concurrent.ExecutionException;

public class UsuarioFirebase {
    public static String getidentificador() {
        // pega o usuario atual e codifica

        FirebaseAuth usuario = ConfiguracaoFirebase.getAuth();
        // recupera o usuario atual e email
        String email = usuario.getCurrentUser().getEmail();
        String identificadorusuario = Base64Custom.codificarbase64(email);
        return identificadorusuario;
    }

    // retorna o usuario com todos os dados
    public static FirebaseUser getusuarioatual() {
        FirebaseAuth usuario = ConfiguracaoFirebase.getAuth();
        // recupera o usuario atual e email
        return usuario.getCurrentUser();

    }

    public static boolean atualizarfotousuario(Uri url) {
        try {
            FirebaseUser user = getusuarioatual();
            // configurar o profile
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setPhotoUri(url).build();
            user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    // verifica se deu certo
                    if (!task.isSuccessful()) {
                        Log.d("Perfil", "Erro ao atualizar foto do perfil");

                    }
                }
            });

            return true;


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Usuario getDadosusuariologado() {

        FirebaseUser firebaseuser = getusuarioatual();
        Usuario usuario = new Usuario();
        // configrando os dados
        usuario.setEmail(firebaseuser.getEmail());
        usuario.setNome(firebaseuser.getDisplayName());
        if (firebaseuser.getPhotoUrl() == null) {
            usuario.setFotousuario("");

        } else {
            // configra o caminho da foto
            usuario.setFotousuario(firebaseuser.getPhotoUrl().toString());


        }
        return usuario;


    }

    public static boolean atualizarnome(String nome) {

        try {
            FirebaseUser user = getusuarioatual();
            // configurar o profile
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(nome).build();
            user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    // verifica se deu certo
                    if (!task.isSuccessful()) {
                        Log.d("Perfil", "Erro ao atualizar nome do perfil");
                    }
                }
            });

            return true;


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }
}

