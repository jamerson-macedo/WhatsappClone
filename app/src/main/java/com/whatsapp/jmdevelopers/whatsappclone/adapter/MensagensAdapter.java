package com.whatsapp.jmdevelopers.whatsappclone.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.whatsapp.jmdevelopers.whatsappclone.R;
import com.whatsapp.jmdevelopers.whatsappclone.helper.UsuarioFirebase;
import com.whatsapp.jmdevelopers.whatsappclone.model.Mensagem;

import java.util.List;

public class MensagensAdapter extends RecyclerView.Adapter<MensagensAdapter.MyViewHolder> {
    private List<Mensagem> listademensagem;
    private Context context;
    private static final int TIPO_REMETENTE=0;
    private static final int TIPO_DESINATARIO=1;

    public MensagensAdapter(List <Mensagem> lista, Context c) {
        this.listademensagem=lista;
        this.context=c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View item=null;
        if(i==TIPO_REMETENTE){
            LayoutInflater layoutInflater;
             item=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_mensagem_remetente,viewGroup,false);

        }else if (i==TIPO_DESINATARIO){

            item=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_mensagem_destinatario,viewGroup,false);

        }
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Mensagem mensagem=listademensagem.get(i);
        String msg=mensagem.getMensagem();
        String imagem=mensagem.getImagem();
        if(imagem!=null){

            Uri uri=Uri.parse(imagem);
            Glide.with(context).load(uri).into(myViewHolder.imagemmensagem);
            // esconder o texto caso tenha foto
            myViewHolder.textomensagem.setVisibility(View.GONE);
        }else{
            myViewHolder.textomensagem.setText(msg);
            myViewHolder.imagemmensagem.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return listademensagem.size();
    }
// metodo para reornar o tipo da vizualização
    @Override
    public int getItemViewType(int position) {
        // recupera a mensagem para o item da lista
        Mensagem mensagem=listademensagem.get(position);
        String idusuario=UsuarioFirebase.getidentificador();
        // verifica se a mensagem foi enviada pelo usuario logado
        if(idusuario.equals(mensagem.getIdusuario())){
            return TIPO_REMETENTE;
        }
        return TIPO_DESINATARIO;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textomensagem;
        ImageView imagemmensagem;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textomensagem=itemView.findViewById(R.id.mensagemtexto);
            imagemmensagem=itemView.findViewById(R.id.mensagemfoto);
        }

    }

}
