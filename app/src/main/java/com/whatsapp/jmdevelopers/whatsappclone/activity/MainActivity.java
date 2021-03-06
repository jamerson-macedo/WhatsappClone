package com.whatsapp.jmdevelopers.whatsappclone.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.whatsapp.jmdevelopers.whatsappclone.R;
import com.whatsapp.jmdevelopers.whatsappclone.config.ConfiguracaoFirebase;
import com.whatsapp.jmdevelopers.whatsappclone.fragment.ContatosFragment;
import com.whatsapp.jmdevelopers.whatsappclone.fragment.ConversasFragment;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;
    private MaterialSearchView materialSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbarprincipal);
        toolbar.setTitle("Whatsapp");
        // configurando para rodar em todas versões
        setSupportActionBar(toolbar);
        auth = ConfiguracaoFirebase.getAuth();
        smartTabLayout=findViewById(R.id.viewpagertab);
        viewPager=findViewById(R.id.viewpager);
        // configurqando seach view


        // configurar abas
        final FragmentPagerItemAdapter itemAdapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Conversas", ConversasFragment.class).add("Contatos", ContatosFragment.class).create());
        viewPager.setAdapter(itemAdapter);
        smartTabLayout.setViewPager(viewPager);



        materialSearchView=findViewById(R.id.search_view);
        // listenerpara o seach view
        // para recuperar o fragment padrao quando é fechado a pesquisa
        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                ConversasFragment fragment= (ConversasFragment) itemAdapter.getPage(0);
                fragment.recarregarconversas();

            }
        });
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // esse metodo é para pesquisar so quando precionar o botao
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // esse é em tempo de execução
                // aqui pega o adapter que contem os dados das conversas trazendo para o main activity
                // pegando as conversas na posicao -0
                ConversasFragment fragment= (ConversasFragment) itemAdapter.getPage(0);
                // pegando metodos do fragment
                if(newText!= null && !newText.isEmpty()){
                    fragment.pesquisarconversas(newText.toLowerCase());


                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // configurando para receber o arquivo xml o menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        // pegando a acao do botao de pesquisa
       MenuItem item=menu.findItem(R.id.menupesquisa);
        materialSearchView.setMenuItem(item);

        return super.onCreateOptionsMenu(menu);
    }

    // metodo para verificar quem foi clicado
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menusair:
                deslogarusuario();
                finish();
                break;
            case R.id.menuconfiguracoes:
                abrirajuster();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    public void deslogarusuario() {
        try {
            auth.signOut();


        } catch (Exception e) {
            e.printStackTrace();

        }


    }
    public void abrirajuster(){
        Intent i=new Intent(MainActivity.this,AjustesActivity.class);
        startActivity(i);


    }
}
