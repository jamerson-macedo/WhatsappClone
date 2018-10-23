package com.whatsapp.jmdevelopers.whatsappclone.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfiguracaoFirebase {
    private static DatabaseReference databaseReference;
    private static FirebaseAuth auth;
    private static StorageReference storage;
    // retornar a instancia do database
    public static DatabaseReference getDatabaseReference(){
        if(databaseReference==null){
            databaseReference=FirebaseDatabase.getInstance().getReference();

        }
        return databaseReference;
    }
    // retornar a instancia do auth
    public static FirebaseAuth getAuth(){
        if(auth==null){
            auth=FirebaseAuth.getInstance();

        }
        return auth;
    }// referencia ao negocio de salvar arquivos de fotos
    public static StorageReference getStorageReference(){
        if(storage==null){
            storage=FirebaseStorage.getInstance().getReference();
        }
        return storage;
    }
}
