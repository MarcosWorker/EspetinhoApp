package br.com.forum.espetinhoapp.flow.fragments;


import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
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
    private android.app.AlertDialog alerta;

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
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(final View view) {

                //atualiza lista
                adapterCardapio.notifyDataSetChanged();
                //captura tamanho da lista
                int tamanhoLista = adapterCardapio.cardapioAtual().size();
                //cria variavel do valor total do pedido
                double total = 0;

                //pegar proximo id do Pedido - inicio
                Number currentIdNumPedido = realm.where(Pedido.class).max("id");
                final int nextIdPedido;
                if (currentIdNumPedido == null) {
                    nextIdPedido = 1;
                } else {
                    nextIdPedido = currentIdNumPedido.intValue() + 1;
                }
                //pegar proximo id do Pedido - fim

                for (Espetinho espetinho : adapterCardapio.cardapioAtual()) {
                    if (espetinho.getQtd() == 0) {
                        tamanhoLista = tamanhoLista - 1;
                    } else {
                        //pegar proximo id do EspetinhoCardapio - inicio
                        Number currentIdNumEspetinhoCardapio = realm.where(EspetinhoCardapio.class).max("id");
                        int nextIdEspetinhoCardapio;
                        if (currentIdNumEspetinhoCardapio == null) {
                            nextIdEspetinhoCardapio = 1;
                        } else {
                            nextIdEspetinhoCardapio = currentIdNumEspetinhoCardapio.intValue() + 1;
                        }
                        //pegar proximo id do EspetinhoCardapio - fim

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
                if (tamanhoLista == 0) {
                    Toast.makeText(view.getContext(), "Não existe nada para pedir", Toast.LENGTH_SHORT).show();
                    carregarCardapio();

                } else {

                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(view.getContext());
                    builder.setTitle("Pedido");
                    builder.setMessage("Por favor confirme o pedido");

                    LayoutInflater factory = LayoutInflater.from(view.getContext());
                    final View dialogView = factory.inflate(R.layout.adapter_dialog_pedido, null);
                    builder.setView(dialogView);
                    final double finalTotal = total;
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                            final EditText edtDialogCliente = (EditText) dialogView.findViewById(R.id.edt_dialog_cliente);
                            final EditText edtDialogMesa = (EditText) dialogView.findViewById(R.id.edt_dialog_mesa);

                            if (edtDialogCliente == null || edtDialogCliente.getText().toString().isEmpty()) {
                                Toast.makeText(view.getContext(), "Digite um cliente antes de confirmar...", Toast.LENGTH_SHORT).show();
                            } else if (edtDialogMesa == null || edtDialogMesa.getText().toString().isEmpty()) {
                                Toast.makeText(view.getContext(), "Digite uma mesa antes de confirmar", Toast.LENGTH_SHORT).show();
                            } else {
                                realm.beginTransaction();
                                pedido = realm.createObject(Pedido.class, nextIdPedido);
                                pedido.setStatus(0);
                                pedido.setCliente(edtDialogCliente.getText().toString());
                                pedido.setDataEntrega("Aguardando entrega");
                                java.util.Date agora = new java.util.Date();
                                SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
                                String data1 = formata.format(agora);
                                formata = new SimpleDateFormat("hh:mm:ss");
                                String hora1 = formata.format(agora);
                                pedido.setDataPedido(data1 + " " + hora1);
                                pedido.setMesa(Integer.valueOf(edtDialogMesa.getText().toString()));
                                pedido.setTotal(finalTotal);
                                realm.commitTransaction();

                                Toast.makeText(view.getContext(), "Pedido enviado", Toast.LENGTH_SHORT).show();
                            }

                            carregarCardapio();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            carregarCardapio();
                        }
                    });
                    alerta = builder.create();
                    alerta.show();
                    alerta.setCanceledOnTouchOutside(false);
                    alerta.setCancelable(false);

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
