package com.whatsapp.jmdevelopers.whatsappclone.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whatsapp.jmdevelopers.whatsappclone.R;
import com.whatsapp.jmdevelopers.whatsappclone.adapter.ContatosAdapter;
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

}
