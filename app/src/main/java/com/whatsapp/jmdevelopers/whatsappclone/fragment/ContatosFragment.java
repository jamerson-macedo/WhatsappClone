package com.whatsapp.jmdevelopers.whatsappclone.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.whatsapp.jmdevelopers.whatsappclone.R;
import com.whatsapp.jmdevelopers.whatsappclone.adapter.ContatosAdapter;
import com.whatsapp.jmdevelopers.whatsappclone.config.ConfiguracaoFirebase;
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

    private ArrayList<Usuario> listacontatos = new ArrayList<>();
    // recuperar o banco
    private DatabaseReference usuariosref;
    private ValueEventListener valueEventListenerContatos;


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

        // configrar o adapter
        // passar a lista de contatos
        adapter = new ContatosAdapter(listacontatos, getActivity());
        // confgurar recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewListaContatos.setLayoutManager(layoutManager);
        recyclerViewListaContatos.setHasFixedSize(true);
        recyclerViewListaContatos.setAdapter(adapter);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarcontatos();
    }
// quando nao tiver mais usando ele remove o listener
    @Override
    public void onStop() {
        super.onStop();
        usuariosref.removeEventListener(valueEventListenerContatos);
    }
    public void recuperarcontatos() {

        valueEventListenerContatos = usuariosref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // percorrer os usuarios
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Usuario usuario = dados.getValue(Usuario.class);
                    listacontatos.add(usuario);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
