package br.com.forum.espetinhoapp.flow.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.forum.espetinhoapp.R;
import br.com.forum.espetinhoapp.model.adapter.AdapterCardapio;
import br.com.forum.espetinhoapp.model.bean.Espetinho;

/**
 * A simple {@link Fragment} subclass.
 */
public class CardapioFragment extends Fragment {

    private RecyclerView recyclerView = null;
    private AdapterCardapio adapterCardapio = null;
    private RecyclerView.LayoutManager mLayoutManager = null;
    private List<Espetinho> espetinhos = null;
    private View view = null;
    private Espetinho espetinho = null;

    public CardapioFragment() {
        // Required empty public constructor
    }

    public void listarEspetinhos() {
        espetinhos = new ArrayList<>();

        espetinho = new Espetinho();
        espetinho.setNome("Carne");
        espetinho.setPreco(5.00);
        espetinho.setDescricao("5 pedaços de Carne Bovina");
        espetinho.setQtd(0);
        espetinho.setStatus(0);
        espetinho.setFoto(R.mipmap.ic_launcher);
        espetinhos.add(espetinho);

        espetinho = new Espetinho();
        espetinho.setNome("Frango");
        espetinho.setPreco(5.00);
        espetinho.setDescricao("5 pedaços de Frango");
        espetinho.setQtd(0);
        espetinho.setStatus(0);
        espetinho.setFoto(R.mipmap.ic_launcher);
        espetinhos.add(espetinho);

        espetinho = new Espetinho();
        espetinho.setNome("Carne de sol");
        espetinho.setPreco(5.00);
        espetinho.setDescricao("5 pedaços de Carne de sol");
        espetinho.setQtd(0);
        espetinho.setStatus(0);
        espetinho.setFoto(R.mipmap.ic_launcher);
        espetinhos.add(espetinho);

        espetinho = new Espetinho();
        espetinho.setNome("Maminha");
        espetinho.setPreco(5.00);
        espetinho.setDescricao("5 pedaços de Maminha");
        espetinho.setQtd(0);
        espetinho.setStatus(0);
        espetinho.setFoto(R.mipmap.ic_launcher);
        espetinhos.add(espetinho);

        espetinho = new Espetinho();
        espetinho.setNome("Picanha");
        espetinho.setPreco(5.00);
        espetinho.setDescricao("5 pedaços de Picanha");
        espetinho.setQtd(0);
        espetinho.setStatus(0);
        espetinho.setFoto(R.mipmap.ic_launcher);
        espetinhos.add(espetinho);

        espetinho = new Espetinho();
        espetinho.setNome("Carne de sol com queijo");
        espetinho.setPreco(5.00);
        espetinho.setDescricao("5 pedaços de Quiejo coalho enrrolados com Carne de sol");
        espetinho.setQtd(0);
        espetinho.setStatus(0);
        espetinho.setFoto(R.mipmap.ic_launcher);
        espetinhos.add(espetinho);

        espetinho = new Espetinho();
        espetinho.setNome("Frango com Bacon");
        espetinho.setPreco(5.00);
        espetinho.setDescricao("5 pedaços de Frango enrrolados com Bacon");
        espetinho.setQtd(0);
        espetinho.setStatus(0);
        espetinho.setFoto(R.mipmap.ic_launcher);
        espetinhos.add(espetinho);

        espetinho = new Espetinho();
        espetinho.setNome("Mistão");
        espetinho.setPreco(5.00);
        espetinho.setDescricao("1 pedaço de Carne de sol + 1 pedaço de Frango + 1 pedaço de picanha +1 pedaço de Maminha + 1 pedaço de Queijo Coalho");
        espetinho.setQtd(0);
        espetinho.setStatus(0);
        espetinho.setFoto(R.mipmap.ic_launcher);
        espetinhos.add(espetinho);

        espetinho = new Espetinho();
        espetinho.setNome("Pão de alho");
        espetinho.setPreco(5.00);
        espetinho.setDescricao("Pão assado ao molho de alho");
        espetinho.setQtd(0);
        espetinho.setStatus(0);
        espetinho.setFoto(R.mipmap.ic_launcher);
        espetinhos.add(espetinho);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cardapio, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.lista_cardapio);
        listarEspetinhos();
        adapterCardapio = new AdapterCardapio(espetinhos);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterCardapio);

        return view;
    }

}
