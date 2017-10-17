package br.com.forum.espetinhoapp.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.forum.espetinhoapp.R;
import br.com.forum.espetinhoapp.model.bean.Pedido;
import io.realm.Realm;

/**
 * Created by estagiario-manha on 16/10/17.
 */

public class AdapterPedido extends RecyclerView.Adapter<AdapterPedido.ViewHolder> {


    private List<Pedido> pedidos = null;
    private Realm realm = null;

    public AdapterPedido(List<Pedido> pedidos) {
        this.pedidos = pedidos;
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
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Pedido pedido = pedidos.get(position);

        holder.tvMesa.setText(pedido.getMesa());
        holder.tvDescricao.setText(pedido.getDescricao());
        holder.tvTotal.setText(pedido.getTotal());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                holder.button.setBackground(view.setBackgroundResource());
                Toast.makeText(view.getContext(), "Em construção...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return pedidos.size();
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

        public ViewHolder(View v) {
            super(v);

            tvMesa = (TextView) v.findViewById(R.id.mesa_adapter_pedido);
            tvTotal = (TextView) v.findViewById(R.id.total_adapter_pedido);
            tvDescricao = (TextView) v.findViewById(R.id.pedido_adapter_pedido);
            button = (ImageButton) v.findViewById(R.id.status_button);

        }

    }
}
