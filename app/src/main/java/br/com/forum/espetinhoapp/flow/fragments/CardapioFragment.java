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
import br.com.forum.espetinhoapp.model.bean.Espetinho;
import br.com.forum.espetinhoapp.model.bean.EspetinhoCardapio;
import br.com.forum.espetinhoapp.model.bean.Pedido;
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
    private List<Espetinho> espetinhosAdapter = null;
    private View view = null;
    private FloatingActionButton fab = null;
    private Realm realm = null;
    private Pedido pedido = null;
    private EspetinhoCardapio espetinhoCardapio = null;

    public CardapioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cardapio, container, false);

        realm = Realm.getDefaultInstance();
        carregarCardapio();
        fab = (FloatingActionButton) view.findViewById(R.id.fab_cardapio);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                adapterCardapio.notifyDataSetChanged();

                double total = 0;

                Number currentIdNumPedido = realm.where(Pedido.class).max("id");
                int nextIdPedido;
                if (currentIdNumPedido == null) {
                    nextIdPedido = 1;
                } else {
                    nextIdPedido = currentIdNumPedido.intValue() + 1;
                }

                for (Espetinho espetinho : adapterCardapio.cardapioAtual()) {
                    if (espetinho.getQtd() == 0) {
                        adapterCardapio.cardapioAtual().remove(espetinho);
                    } else {
                        Number currentIdNumEspetinhoCardapio = realm.where(EspetinhoCardapio.class).max("id");
                        int nextIdEspetinhoCardapio;
                        if (currentIdNumEspetinhoCardapio == null) {
                            nextIdEspetinhoCardapio = 1;
                        } else {
                            nextIdEspetinhoCardapio = currentIdNumEspetinhoCardapio.intValue() + 1;
                        }

                        realm.beginTransaction();
                        espetinhoCardapio = realm.createObject(EspetinhoCardapio.class, nextIdEspetinhoCardapio);
                        espetinhoCardapio.setQtd(espetinho.getQtd());
                        espetinhoCardapio.setPreco(espetinho.getPreco());
                        espetinhoCardapio.setDescricao(espetinho.getDescricao());
                        espetinhoCardapio.setNome(espetinho.getNome());
                        espetinhoCardapio.setIdPedido(nextIdPedido);
                        realm.commitTransaction();

                        //calculo do total
                        total = (espetinho.getQtd() * espetinho.getPreco()) + total;
                    }
                }
                if (adapterCardapio.cardapioAtual().isEmpty()) {
                    Toast.makeText(view.getContext(), "Não existe nada para pedir", Toast.LENGTH_SHORT).show();
                    carregarCardapio();

                } else {
                    realm.beginTransaction();
                    pedido = realm.createObject(Pedido.class, nextIdPedido);
                    pedido.setStatus(0);
                    pedido.setCliente("cliente teste");
                    pedido.setDataEntrega("Aguardando entrega");
                    pedido.setDataPedido("23/10/2017");
                    pedido.setMesa(1);
                    pedido.setTotal(total);
                    realm.commitTransaction();

                    Toast.makeText(view.getContext(), "Pedido enviado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void carregarCardapio() {
        espetinhos = realm.where(Espetinho.class).findAll();
        espetinhosAdapter = realm.copyFromRealm(espetinhos);

        if (espetinhosAdapter.isEmpty()) {
            Toast.makeText(view.getContext(), "Lista vazia ... Adicione espetinhoCardapios na aba \"Novo\"", Toast.LENGTH_LONG).show();
        } else {
            recyclerView = (RecyclerView) view.findViewById(R.id.lista_cardapio);
            adapterCardapio = new AdapterCardapio(espetinhosAdapter);
            mLayoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(adapterCardapio);
        }
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
