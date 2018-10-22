package com.whatsapp.jmdevelopers.whatsappclone.helper;

import android.util.Base64;

// a base 64 é para tcriptografar o email para salvar no banco
public class Base64Custom {
    public static String codificarbase64 (String email){
        // transforma o email em bytes                                          remove quuebra de linha
        return Base64.encodeToString(email.getBytes(),Base64.DEFAULT).replaceAll("(\\n|\\r)","");
    }
    public static String decodificarbase64(String emailcodificado){
         return new String (Base64.decode(emailcodificado,Base64.DEFAULT));


    }

}
