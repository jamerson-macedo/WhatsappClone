package com.whatsapp.jmdevelopers.whatsappclone.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.whatsapp.jmdevelopers.whatsappclone.R;
import com.whatsapp.jmdevelopers.whatsappclone.activity.ChatActivity;
import com.whatsapp.jmdevelopers.whatsappclone.adapter.ContatosAdapter;
import com.whatsapp.jmdevelopers.whatsappclone.config.ConfiguracaoFirebase;
import com.whatsapp.jmdevelopers.whatsappclone.helper.RecyclerItemClickListener;
import com.whatsapp.jmdevelopers.whatsappclone.helper.UsuarioFirebase;
import com.whatsapp.jmdevelopers.whatsappclone.model.Usuario;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {
    private RecyclerView recyclerViewListaContatos;
    // instanciar o adapter
    private ContatosAdapter adapter;
    // criando o arraylist dos contatos
    // pega a classe usuario
// QUANDO CRIAR A CHATACTIVITY AI COLOCAR O HIERARQUIA PARA MAIN ACTIVITY PARA CRIAR O BOTAO VOLTAR
    private ArrayList<Usuario> listacontatos = new ArrayList<>();
    // recuperar o banco
    private DatabaseReference usuariosref;
    private ValueEventListener valueEventListenerContatos;
private FirebaseUser usuarioatual;

    public ContatosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);
        // configurar o recyclerview
        recyclerViewListaContatos = view.findViewById(R.id.recyclerviewcontatos);
        // pegando referencia do no
        usuariosref = ConfiguracaoFirebase.getDatabaseReference().child("usuarios");
        usuarioatual= UsuarioFirebase.getusuarioatual();
        // configrar o adapter
        // passar a lista de contatos
        adapter = new ContatosAdapter(listacontatos, getActivity());
        // confgurar recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewListaContatos.setLayoutManager(layoutManager);
        recyclerViewListaContatos.setHasFixedSize(true);
        recyclerViewListaContatos.setAdapter(adapter);
        // configurar o clique
        recyclerViewListaContatos.addOnItemTouchListener(new RecyclerItemClickListener(
                getActivity(), recyclerViewListaContatos, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i=new Intent(getActivity(),ChatActivity.class);
                Usuario usuarioselecionado=listacontatos.get(position);
                i.putExtra("chatcontato",usuarioselecionado);
                startActivity(i);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarContatos();
    }

    // quando nao tiver mais usando ele remove o listener
    @Override
    public void onStop() {
        super.onStop();
        usuariosref.removeEventListener(valueEventListenerContatos);
    }

    public void recuperarContatos() {
        listacontatos.clear();

        /*Define usuário com e-mail vazio
         * em caso de e-mail vazio o usuário será utilizado como
         * cabecalho, exibindo novo grupo */
        Usuario itemGrupo = new Usuario();
        itemGrupo.setNome("Novo grupo");
        itemGrupo.setEmail("");

        listacontatos.add(itemGrupo);

        valueEventListenerContatos = usuariosref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Usuario usuario = dados.getValue(Usuario.class);
                    String emailUsuarioAtual = usuarioatual.getEmail();
                    if (!emailUsuarioAtual.equals(usuario.getEmail())) {
                        listacontatos.add(usuario);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
