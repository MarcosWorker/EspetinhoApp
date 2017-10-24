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

import java.util.List;

import br.com.forum.espetinhoapp.R;
import br.com.forum.espetinhoapp.model.adapter.AdapterCardapio;
import br.com.forum.espetinhoapp.model.bean.EspetinhoCardapio;
import br.com.forum.espetinhoapp.model.bean.Pedido;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class CardapioFragment extends Fragment {

    private RecyclerView recyclerView = null;
    private AdapterCardapio adapterCardapio = null;
    private RecyclerView.LayoutManager mLayoutManager = null;
    private RealmResults<EspetinhoCardapio> espetinhoCardapios = null;
    private View view = null;
    private FloatingActionButton fab = null;
    private Realm realm = null;
    private Pedido pedido = null;
    private List<EspetinhoCardapio>espetinhosListaCopiada = null;

    public CardapioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cardapio, container, false);

        realm = Realm.getDefaultInstance();
        espetinhoCardapios = realm.where(EspetinhoCardapio.class).findAll();
        espetinhosListaCopiada = realm.copyFromRealm(espetinhoCardapios);

        if (espetinhosListaCopiada.isEmpty()) {
            Toast.makeText(view.getContext(), "Lista vazia ... Adicione espetinhoCardapios na aba \"Novo\"", Toast.LENGTH_LONG).show();
        } else {
            recyclerView = (RecyclerView) view.findViewById(R.id.lista_cardapio);
            adapterCardapio = new AdapterCardapio(espetinhosListaCopiada);
            mLayoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(adapterCardapio);
        }
        fab = (FloatingActionButton) view.findViewById(R.id.fab_cardapio);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                double total = 0;
                espetinhosPedidos = new RealmList<EspetinhoCardapio>();
                for (EspetinhoCardapio espetinhoCardapio : adapterCardapio.cardapioAtual()) {
                    if (espetinhoCardapio.getQtd() > 0) {
                        espetinhosPedidos.add(espetinhoCardapio);
                        total = (espetinhoCardapio.getQtd() * espetinhoCardapio.getPreco()) + total;

                        realm.beginTransaction();
                        espetinhoCardapio.setQtd(0);
                        realm.commitTransaction();
                    }
                    adapterCardapio.notifyDataSetChanged();
                }

                if (espetinhosPedidos.isEmpty()) {
                    Toast.makeText(view.getContext(), "Cardapio zerado... Impossivel realizar pedido", Toast.LENGTH_SHORT).show();
                } else {
                    Number currentIdNum = realm.where(EspetinhoCardapio.class).max("id");
                    int nextId;
                    if (currentIdNum == null) {
                        nextId = 1;
                    } else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    realm.beginTransaction();
                    pedido = realm.createObject(Pedido.class, nextId);
                    pedido.setStatus(0);
                    pedido.setCliente("cliente teste");
                    pedido.setDataEntrega("Aguardando entrega");
                    pedido.setDataPedido("23/10/2017");
                    pedido.setEspetinhos(espetinhosPedidos);
                    pedido.setMesa(1);
                    pedido.setTotal(total);
                    realm.commitTransaction();

                    Toast.makeText(view.getContext(), "Pedido enviado", Toast.LENGTH_SHORT).show();
                }

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
