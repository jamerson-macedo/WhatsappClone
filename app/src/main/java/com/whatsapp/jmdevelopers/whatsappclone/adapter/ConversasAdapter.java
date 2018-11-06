package com.whatsapp.jmdevelopers.whatsappclone.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.whatsapp.jmdevelopers.whatsappclone.R;
import com.whatsapp.jmdevelopers.whatsappclone.model.Conversa;
import com.whatsapp.jmdevelopers.whatsappclone.model.Usuario;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConversasAdapter extends RecyclerView.Adapter<ConversasAdapter.MyViewHolder> {
    List<Conversa> listaconversas;
    Context context;

    public ConversasAdapter(List<Conversa> listadeconversa,Context c) {
        this.listaconversas=listadeconversa;
        this.context=c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View itemlista=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_contatos,viewGroup,false);
      return new MyViewHolder(itemlista);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
    Conversa conversa=listaconversas.get(i);
    myViewHolder.ultimamensagem.setText(conversa.getUlimamensagem());
    // pega o usuario que queremos exibir

    Usuario usuario=conversa.getUsuario();
    myViewHolder.nome.setText(usuario.getNome());
    if(usuario.getFotousuario()!=null){
        Uri uri=Uri.parse(usuario.getFotousuario());
        Glide.with(context).load(uri).into(myViewHolder.foto);

    }else {

        myViewHolder.foto.setImageResource(R.drawable.foto);

    }



    }

    @Override
    public int getItemCount() {
        return listaconversas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView foto;
        TextView nome,ultimamensagem;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foto=itemView.findViewById(R.id.fotocontato);
            nome=itemView.findViewById(R.id.nomecontato);
            ultimamensagem=itemView.findViewById(R.id.emailcontato);

        }
    }

}
