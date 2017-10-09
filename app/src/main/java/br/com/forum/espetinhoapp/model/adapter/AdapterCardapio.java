package br.com.forum.espetinhoapp.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.forum.espetinhoapp.R;
import br.com.forum.espetinhoapp.model.bean.Espetinho;

/**
 * Created by estagiario-manha on 06/10/17.
 */

public class AdapterCardapio extends RecyclerView.Adapter<AdapterCardapio.ViewHolder> {

    private List<Espetinho> espetinhos = null;

    public AdapterCardapio(List<Espetinho> espetinhos) {
        this.espetinhos = espetinhos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.adapter_cardapio, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Espetinho espetinho = espetinhos.get(position);
        holder.fotoEspetinho.setImageResource(espetinho.getFoto());
        holder.tvNome.setText(espetinho.getNome());
        holder.tvPreco.setText("R$ "+espetinho.getPreco());
        holder.tvDescricao.setText(espetinho.getDescricao());
        holder.tvQtd.setText(espetinho.getQtd());
        holder.btAddEspetinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                espetinho.setQtd(espetinho.getQtd()+1);
            }
        });

        if(espetinho.getQtd()<=0){
            holder.btRemoveEspetinho.setVisibility(View.INVISIBLE);
        }else{
            holder.btRemoveEspetinho.setVisibility(View.VISIBLE);
        }

        holder.btRemoveEspetinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                espetinho.setQtd(espetinho.getQtd()-1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return espetinhos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView fotoEspetinho = null;
        private ImageButton btAddEspetinho = null;
        private ImageButton btRemoveEspetinho = null;
        private TextView tvQtd = null;
        private TextView tvNome = null;
        private TextView tvPreco = null;
        private TextView tvDescricao = null;

        public ViewHolder(View v) {
            super(v);

            fotoEspetinho = (ImageView) v.findViewById(R.id.foto_cardapio);
            btAddEspetinho = (ImageButton) v.findViewById(R.id.bt_add_cardapio);
            btRemoveEspetinho = (ImageButton) v.findViewById(R.id.bt_remove_cardapio);
            tvQtd = (TextView) v.findViewById(R.id.qtd_cardapio);
            tvNome = (TextView) v.findViewById(R.id.nome_cardapio);
            tvPreco = (TextView) v.findViewById(R.id.preco_cardapio);
            tvDescricao = (TextView) v.findViewById(R.id.descricao_cardapio);

        }

    }
}
