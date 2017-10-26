package br.com.forum.espetinhoapp.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import br.com.forum.espetinhoapp.R;
import br.com.forum.espetinhoapp.model.bean.EspetinhoCardapio;
import br.com.forum.espetinhoapp.model.bean.Pedido;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by estagiario-manha on 16/10/17.
 */

public class AdapterPedido extends RecyclerView.Adapter<AdapterPedido.ViewHolder> {


    private RealmResults<Pedido> pedidos = null;
    private Realm realm = null;

    public AdapterPedido(RealmResults<Pedido> pedidos, Realm realm) {
        this.pedidos = pedidos;
        this.realm = realm;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.adapter_pedido, parent, false);
        AdapterPedido.ViewHolder vh = new AdapterPedido.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Pedido pedido = pedidos.get(position);
        holder.tvMesa.setText("Mesa - " + String.valueOf(pedido.getMesa()));
        holder.tvTotal.setText("Total - R$ " + String.valueOf(pedido.getTotal()));
        holder.tvDataEntrega.setText(pedido.getDataEntrega());
        holder.tvDataPedido.setText("Data do pedido : " + pedido.getDataPedido());
        if (pedido.getStatus() == 0) {
            holder.buttonOk.setVisibility(View.INVISIBLE);
            holder.buttonPagar.setVisibility(View.INVISIBLE);
            holder.button.setVisibility(View.VISIBLE);
        } else if (pedido.getStatus() == 1) {
            holder.buttonOk.setVisibility(View.INVISIBLE);
            holder.buttonPagar.setVisibility(View.VISIBLE);
            holder.button.setVisibility(View.INVISIBLE);
        } else if (pedido.getStatus() == 2) {
            holder.buttonOk.setVisibility(View.VISIBLE);
            holder.buttonPagar.setVisibility(View.INVISIBLE);
            holder.button.setVisibility(View.INVISIBLE);
        }

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                pedido.setStatus(1);
                java.util.Date agora = new java.util.Date();
                SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
                String data1 = formata.format(agora);
                formata = new SimpleDateFormat("hh:mm:ss");
                String hora1 = formata.format(agora);
                pedido.setDataEntrega("Data de entrega : " + data1 + " " + hora1);
                realm.commitTransaction();
                notifyItemChanged(position);
            }
        });

        holder.buttonPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                pedido.setStatus(2);
                realm.commitTransaction();
                notifyItemChanged(position);
            }
        });

        StringBuilder stringBuilderDescricao = new StringBuilder("Descrição do pedido\n");
        RealmResults<EspetinhoCardapio> espetinhoCardapios = realm.where(EspetinhoCardapio.class)
                .findAll();
        for (EspetinhoCardapio espetinhoCardapio : espetinhoCardapios) {
            stringBuilderDescricao.append(espetinhoCardapio.getNome())
                    .append("\n")
                    .append("R$ ")
                    .append(espetinhoCardapio.getPreco())
                    .append("\n")
                    .append(espetinhoCardapio.getQtd())
                    .append(" unidade(s)");
        }
        holder.tvDescricao.setText(stringBuilderDescricao.toString());

    }

    @Override
    public int getItemCount() {
        if (realm != null) {
            return pedidos.size();
        } else {
            realm = Realm.getDefaultInstance();
            return pedidos.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMesa = null;
        private TextView tvTotal = null;
        private TextView tvDataPedido = null;
        private TextView tvDataEntrega = null;
        private TextView tvDescricao = null;
        private ImageButton button = null;
        private ImageButton buttonOk = null;
        private ImageButton buttonPagar = null;

        public ViewHolder(View v) {
            super(v);

            tvMesa = (TextView) v.findViewById(R.id.mesa_adapter_pedido);
            tvTotal = (TextView) v.findViewById(R.id.total_adapter_pedido);
            tvDataPedido = (TextView) v.findViewById(R.id.data_inicio_adapter_pedido);
            tvDataEntrega = (TextView) v.findViewById(R.id.data_entregue_adapter_pedido);
            tvDescricao = (TextView) v.findViewById(R.id.pedido_adapter_pedido);
            button = (ImageButton) v.findViewById(R.id.status_button);
            buttonOk = (ImageButton) v.findViewById(R.id.status_button_ok);
            buttonPagar = (ImageButton) v.findViewById(R.id.status_button_pagar);

        }

    }
}
