package br.com.forum.espetinhoapp.model.adapter;

import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import br.com.forum.espetinhoapp.R;
import br.com.forum.espetinhoapp.model.bean.Pedido;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by estagiario-manha on 16/10/17.
 */

public class AdapterPedido extends RecyclerView.Adapter<AdapterPedido.ViewHolder> {


    private RealmResults<Pedido> pedidos = null;
    private Realm realm = null;
    private AlertDialog.Builder builder = null;

    public AdapterPedido(RealmResults<Pedido> pedidos) {
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

        if (pedido.getStatus() == 1) {
            holder.buttonOk.setVisibility(View.VISIBLE);
            holder.button.setVisibility(View.INVISIBLE);
        } else {
            holder.buttonOk.setVisibility(View.INVISIBLE);
            holder.button.setVisibility(View.VISIBLE);
        }
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(view.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(view.getContext());
                }
                builder.setTitle("ATENÇÃO!!!")
                        .setMessage("Pedido já foi Entregue?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                realm = Realm.getDefaultInstance();
                                realm.beginTransaction();
                                pedido.setStatus(1);
                                realm.commitTransaction();
                                realm.close();
                                holder.button.setVisibility(View.INVISIBLE);
                                holder.buttonOk.setVisibility(View.VISIBLE);
                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setCancelable(false)
                        .show();

            }
        });

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
