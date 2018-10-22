package com.whatsapp.jmdevelopers.whatsappclone.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {
    public static Boolean validarpermissoes(String[] permissoes, Activity activity,int requescode){
        // verificar se ele ta usando versao maior que a masshmalow
        if(Build.VERSION.SDK_INT>=23){
            List<String>  listadepermissoes=new ArrayList<>();
            // percorrer se as permissoes ja foram acionadas
        for(String permissao:permissoes){
          boolean tempermissao = ContextCompat.checkSelfPermission(activity,permissao) == PackageManager.PERMISSION_GRANTED;
          if(!tempermissao){
              listadepermissoes.add(permissao);
          }
          if(listadepermissoes.isEmpty()){
              return true;

          }
          String[]novaspermissoes=new String[listadepermissoes.size()];
          listadepermissoes.toArray(novaspermissoes);
          // solificar permissao/
            ActivityCompat.requestPermissions(activity,novaspermissoes,requescode);



        }


        }
        return true;

    }
}
