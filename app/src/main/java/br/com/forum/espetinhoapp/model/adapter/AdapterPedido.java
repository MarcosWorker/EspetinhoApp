package br.com.forum.espetinhoapp.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import br.com.forum.espetinhoapp.R;
import br.com.forum.espetinhoapp.model.bean.EspetinhoCardapio;
import br.com.forum.espetinhoapp.model.bean.Pedido;
import io.realm.Realm;
import io.realm.RealmList;
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
        holder.tvMesa.setText(String.valueOf(pedido.getMesa()));
        holder.tvTotal.setText(String.valueOf(pedido.getTotal()));
        if (pedido.getStatus() == 0) {
            holder.buttonOk.setVisibility(View.INVISIBLE);
            holder.button.setVisibility(View.VISIBLE);
        } else {
            holder.buttonOk.setVisibility(View.VISIBLE);
            holder.button.setVisibility(View.INVISIBLE);
        }

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                pedido.setStatus(1);
                realm.commitTransaction();
                notifyItemChanged(position);
            }
        });

        StringBuilder stringBuilderDescricao = new StringBuilder("Descrição do pedido\n");
        RealmResults<EspetinhoCardapio> espetinhoCardapios= realm.where(EspetinhoCardapio.class)
                .equalTo("idPedido",pedido.getId())
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
        private TextView tvDescricao = null;
        private ImageButton button = null;
        private ImageButton buttonOk = null;

        public ViewHolder(View v) {
            super(v);

            tvMesa = (TextView) v.findViewById(R.id.mesa_adapter_pedido);
            tvTotal = (TextView) v.findViewById(R.id.total_adapter_pedido);
            tvDescricao = (TextView) v.findViewById(R.id.pedido_adapter_pedido);
            button = (ImageButton) v.findViewById(R.id.status_button);
            buttonOk = (ImageButton) v.findViewById(R.id.status_button_ok);

        }

    }
}
