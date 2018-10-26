package com.whatsapp.jmdevelopers.whatsappclone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whatsapp.jmdevelopers.whatsappclone.R;
import com.whatsapp.jmdevelopers.whatsappclone.model.Usuario;

import java.util.List;

public class ContatosAdapter extends RecyclerView.Adapter<ContatosAdapter.MyViewHolder> {
    // passando no construtor a lista de contatos
    private List <Usuario> contatos;
    private Context context;

    public ContatosAdapter(List <Usuario> listacontatos,Context c) {
        this.contatos=listacontatos;
        this.context=c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // configurar a amostra
       View itemlista=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.toolbar,viewGroup,false);


        return new MyViewHolder(itemlista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

    }
// aqui pega o tamanho da lista
    @Override
    public int getItemCount() {
        return contatos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
