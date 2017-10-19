package br.com.forum.espetinhoapp.flow.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.forum.espetinhoapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RelatorioFragment extends Fragment {

    private View view = null;

    public RelatorioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_relatorio, container, false);

        return view;
    }

}
