package br.com.forum.espetinhoapp.model.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.forum.espetinhoapp.R;
import br.com.forum.espetinhoapp.model.bean.EspetinhoCardapio;

/**
 * Created by estagiario-manha on 06/10/17.
 */

public class AdapterCardapio extends RecyclerView.Adapter<AdapterCardapio.ViewHolder> {

    private List<EspetinhoCardapio> espetinhoCardapios = null;

    public AdapterCardapio(List<EspetinhoCardapio> espetinhoCardapios) {
        this.espetinhoCardapios = espetinhoCardapios;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        int qtd=0;
        final EspetinhoCardapio espetinhoCardapio = espetinhoCardapios.get(position);
        holder.tvNome.setText(espetinhoCardapio.getNome());
        holder.tvDescricao.setText(espetinhoCardapio.getDescricao());
        holder.tvPreco.setText("R$ " + String.valueOf(espetinhoCardapio.getPreco()));
        holder.tvQtd.setText(String.valueOf(qtd));
        holder.btAddEspetinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                notifyItemChanged(position);
            }
        });
        holder.btRemoveEspetinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (espetinhoCardapio.getQtd() == 0) {
                    Toast.makeText(v.getContext(), "Impossivel quantidades negativas...", Toast.LENGTH_SHORT).show();
                } else {
                    espetinhoCardapio.setQtd(espetinhoCardapio.getQtd() - 1);
                    notifyItemChanged(position);
                }
            }
        });

        //carregar a foto no imageView
        Bitmap bitmap = BitmapFactory.decodeByteArray(espetinhoCardapio.getFoto(), 0, espetinhoCardapio.getFoto().length);
        holder.fotoEspetinho.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return espetinhoCardapios.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public List<EspetinhoCardapio> cardapioAtual() {
        return espetinhoCardapios;
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
