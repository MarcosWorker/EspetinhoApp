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
import java.util.UUID;

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

    public CardapioFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cardapio, container, false);

        realm = Realm.getDefaultInstance();

        recyclerView = (RecyclerView) view.findViewById(R.id.lista_cardapio);
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

                    final View viewAlert = getActivity().getLayoutInflater().inflate(R.layout.alert_pedido, null);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(getContext());
                    }

                    builder.setView(viewAlert)
                            .setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    double total = 0.0;

                                    StringBuilder descricao = new StringBuilder("Descrição : \n");
                                    for (Espetinho espetinho : adapterCardapio.cardapioAtual()) {
                                        if (espetinho.getQtd() > 0) {
                                            descricao.append(espetinho.getQtd())
                                                    .append(" ")
                                                    .append(espetinho.getNome())
                                                    .append(" - ")
                                                    .append(espetinho.getPreco())
                                                    .append("\n");
                                            total = (espetinho.getQtd() * espetinho.getPreco()) + total;
                                        }
                                    }

                                    dateFormat_hora = new SimpleDateFormat("HH:mm:ss");
                                    data = new Date();

                                    cal = Calendar.getInstance();
                                    cal.setTime(data);
                                    data_atual = cal.getTime();

                                    edt_mesa = (EditText) viewAlert.findViewById(R.id.mesa_pedido);

                                    if (edt_mesa.getText().toString().isEmpty()) {
                                        Toast.makeText(getContext(), "Pedido negado... Precisa digitar o numero da mesa.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        realm.beginTransaction();
                                        Pedido pedido = realm.createObject(Pedido.class, UUID.randomUUID().toString()); // Create a new object
                                        pedido.setDescricao(descricao.toString());
                                        pedido.setData(data_atual.toString());
                                        pedido.setHora(dateFormat_hora.format(data_atual));
                                        pedido.setMesa("Mesa " + edt_mesa.getText().toString());
                                        pedido.setTotal("Total - R$ " + String.valueOf(total));
                                        pedido.setStatus(0);
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
