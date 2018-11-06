package com.whatsapp.jmdevelopers.whatsappclone.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.whatsapp.jmdevelopers.whatsappclone.R;
import com.whatsapp.jmdevelopers.whatsappclone.adapter.ConversasAdapter;
import com.whatsapp.jmdevelopers.whatsappclone.config.ConfiguracaoFirebase;
import com.whatsapp.jmdevelopers.whatsappclone.helper.UsuarioFirebase;
import com.whatsapp.jmdevelopers.whatsappclone.model.Conversa;
import com.whatsapp.jmdevelopers.whatsappclone.model.Usuario;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversasFragment extends Fragment {
    private RecyclerView recyclerViewConversas;
    private ArrayList<Conversa> listaconversa = new ArrayList<>();
    private ConversasAdapter conversasAdapter;
    private DatabaseReference reference;
    private DatabaseReference conversasref;
    private ChildEventListener childEventListener;

    public ConversasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversas, container, false);

        conversasAdapter = new ConversasAdapter(listaconversa, getActivity());

        // configrar o recyclerview
        recyclerViewConversas = view.findViewById(R.id.recyclerconversas);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewConversas.setLayoutManager(layoutManager);
        recyclerViewConversas.setHasFixedSize(true);
        recyclerViewConversas.setAdapter(conversasAdapter);


        String identificadorusuario = UsuarioFirebase.getidentificador();
        reference=ConfiguracaoFirebase.getDatabaseReference();

        conversasref = reference.child("conversas").child(identificadorusuario);


        return view;


    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarconversas();
    }

    @Override
    public void onStop() {
        super.onStop();
        conversasref.removeEventListener(childEventListener);
    }

    public void recuperarconversas() {

        childEventListener = conversasref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Conversa conversa=dataSnapshot.getValue(Conversa.class);
                listaconversa.add(conversa);
                conversasAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
