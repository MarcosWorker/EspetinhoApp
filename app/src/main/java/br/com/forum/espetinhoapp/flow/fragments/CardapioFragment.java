package br.com.forum.espetinhoapp.flow.fragments;


import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.forum.espetinhoapp.R;
import br.com.forum.espetinhoapp.model.adapter.AdapterCardapio;
import br.com.forum.espetinhoapp.model.bean.Espetinho;
import br.com.forum.espetinhoapp.model.bean.Pedido;
import io.realm.Realm;

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
    private FloatingActionButton fab = null;
    private Realm realm = null;
    private SimpleDateFormat dateFormat_hora = null;
    private Date data = null;
    private Calendar cal = null;
    private Date data_atual = null;
    private AlertDialog.Builder builder = null;
    private EditText edt_mesa = null;
    private LayoutInflater inflaterAlert = null;

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

        realm = Realm.getDefaultInstance();

        edt_mesa = (EditText) view.findViewById(R.id.mesa_pedido);

        inflaterAlert = getActivity().getLayoutInflater();

        recyclerView = (RecyclerView) view.findViewById(R.id.lista_cardapio);
        listarEspetinhos();
        adapterCardapio = new AdapterCardapio(espetinhos);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterCardapio);

        fab = (FloatingActionButton) view.findViewById(R.id.fab_cardapio);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                boolean vazio = true;

                for (Espetinho espetinho : adapterCardapio.cardapioAtual()) {
                    if (espetinho.getQtd() > 0) {
                        vazio = false;
                    }
                }

                if (vazio) {
                    Toast.makeText(getContext(), "Não existe nada para pedir", Toast.LENGTH_SHORT).show();
                } else {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(getContext());
                    }

                    builder.setView(inflaterAlert.inflate(R.layout.alert_pedido, null))
                            .setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    StringBuilder descricao = new StringBuilder("Descrição : \n");
                                    for (Espetinho espetinho : adapterCardapio.cardapioAtual()) {
                                        descricao.append(espetinho.getNome())
                                                .append(" - ")
                                                .append(espetinho.getPreco())
                                                .append("\n");
                                    }

                                    dateFormat_hora = new SimpleDateFormat("HH:mm:ss");
                                    data = new Date();

                                    cal = Calendar.getInstance();
                                    cal.setTime(data);
                                    data_atual = cal.getTime();

                                    if (edt_mesa.getText().equals("")) {
                                        Toast.makeText(getContext(), "Pedido negado... Precisa digitar o numero da mesa.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        realm.beginTransaction();
                                        Pedido pedido = realm.createObject(Pedido.class); // Create a new object
                                        pedido.setDescricao(descricao.toString());
                                        pedido.setData(data_atual.toString());
                                        pedido.setHora(dateFormat_hora.format(data_atual));
                                        pedido.setMesa(edt_mesa.getText().toString());
                                        realm.commitTransaction();

                                        Toast.makeText(getContext(), "Pedido enviado...", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setCancelable(false)
                            .show();

                }


            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        realm.close();
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        realm = Realm.getDefaultInstance();
    }
}
