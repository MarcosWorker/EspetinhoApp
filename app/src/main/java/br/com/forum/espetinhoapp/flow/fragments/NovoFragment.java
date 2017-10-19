package br.com.forum.espetinhoapp.flow.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import br.com.forum.espetinhoapp.R;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class NovoFragment extends Fragment {

    private View view = null;
    private ImageButton imageButtonAddFoto = null;
    private EditText edtNovoNome = null;
    private EditText edtNovoPreco = null;
    private EditText edtNovoDescricao = null;
    private FloatingActionButton fabNovo = null;
    private Realm realm = null;

    public NovoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_novo, container, false);
        realm = Realm.getDefaultInstance();

        imageButtonAddFoto = (ImageButton) view.findViewById(R.id.bt_add_foto);
        edtNovoNome = (EditText) view.findViewById(R.id.edt_nome_novo);
        edtNovoPreco = (EditText) view.findViewById(R.id.edt_preco_novo);
        edtNovoDescricao = (EditText) view.findViewById(R.id.edt_descricao_novo);
        fabNovo=(FloatingActionButton)view.findViewById(R.id.fab_add_novo);

        imageButtonAddFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Em construção...", Toast.LENGTH_SHORT).show();
            }
        });

        fabNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Em construção...", Toast.LENGTH_SHORT).show();
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
