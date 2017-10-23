package br.com.forum.espetinhoapp.flow.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.forum.espetinhoapp.R;
import br.com.forum.espetinhoapp.model.adapter.AdapterPedido;
import br.com.forum.espetinhoapp.model.bean.Pedido;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class PedidosFragment extends Fragment {

    private Realm realm = null;
    private View view = null;
    private RecyclerView recyclerView = null;
    private AdapterPedido adapterPedido = null;
    private RecyclerView.LayoutManager mLayoutManager = null;
    private RealmResults<Pedido> pedidos = null;

    public PedidosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pedidos, container, false);

        realm = Realm.getDefaultInstance();
        pedidos = realm.where(Pedido.class).findAll();

        if (pedidos.isEmpty()) {
            Toast.makeText(view.getContext(), "Lista de Pedidos vazia...", Toast.LENGTH_SHORT).show();
        } else {
            recyclerView = (RecyclerView) view.findViewById(R.id.lista_pedidos);
            adapterPedido = new AdapterPedido(pedidos,realm);
            mLayoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(adapterPedido);
        }
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
    public void onStart() {
        super.onStart();
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
