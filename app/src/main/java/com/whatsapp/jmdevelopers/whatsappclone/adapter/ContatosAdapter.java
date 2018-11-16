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
import com.whatsapp.jmdevelopers.whatsappclone.model.Usuario;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContatosAdapter extends RecyclerView.Adapter<ContatosAdapter.MyViewHolder> {
    // passando no construtor a lista de contatos
    private List<Usuario> contatos;
    private Context context;

    public ContatosAdapter(List<Usuario> listacontatos, Context c) {
        this.contatos = listacontatos;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // configurar a amostra
        View itemlista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_contatos, viewGroup, false);

        return new MyViewHolder(itemlista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        // pega a lista de contatos e apposicao
        Usuario usuario = contatos.get(i);
        holder.email.setText(usuario.getEmail());
        holder.nome.setText(usuario.getNome());
        // verifica se tem foto cpega uma string
        if (usuario.getFotousuario() != null) {
            // pega a foto dod usuario para uri e carrega

            Uri uri = Uri.parse(usuario.getFotousuario());
            Glide.with(context).load(uri).into(holder.foto);
        }else{
            holder.foto.setImageResource(R.drawable.foto);
        }


    }

    // aqui pega o tamanho da lista
    @Override
    public int getItemCount() {
        return contatos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // pegar os ids
        private CircleImageView foto;
        private TextView nome;
        private TextView email;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.fotocontato);
            nome = itemView.findViewById(R.id.nomecontato);
            email = itemView.findViewById(R.id.emailcontato);
        }
    }
}
