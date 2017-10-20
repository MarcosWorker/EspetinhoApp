package br.com.forum.espetinhoapp.flow.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.forum.espetinhoapp.R;
import br.com.forum.espetinhoapp.model.adapter.AdapterCardapio;
import br.com.forum.espetinhoapp.model.bean.Espetinho;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class CardapioFragment extends Fragment {

    private RecyclerView recyclerView = null;
    private AdapterCardapio adapterCardapio = null;
    private RecyclerView.LayoutManager mLayoutManager = null;
    private RealmResults<Espetinho> espetinhos = null;
    private View view = null;
    private FloatingActionButton fab = null;
    private Realm realm = null;

    public CardapioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cardapio, container, false);

        realm = Realm.getDefaultInstance();
        espetinhos = realm.where(Espetinho.class).findAll();

        if (espetinhos.isEmpty()) {
            Toast.makeText(view.getContext(), "Lista vazia ... Adicione espetinhos na aba \"Novo\"", Toast.LENGTH_LONG).show();
        } else {
            recyclerView = (RecyclerView) view.findViewById(R.id.lista_cardapio);
            adapterCardapio = new AdapterCardapio(espetinhos);
            mLayoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(adapterCardapio);
        }
        fab = (FloatingActionButton) view.findViewById(R.id.fab_cardapio);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Toast.makeText(view.getContext(), "Em construção", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }
}
