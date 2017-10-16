package br.com.forum.espetinhoapp.flow.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.forum.espetinhoapp.R;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class PedidosFragment extends Fragment {

    private Realm realm=null;


    public PedidosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pedidos, container, false);
    }

}
