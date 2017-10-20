package br.com.forum.espetinhoapp.flow.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import br.com.forum.espetinhoapp.R;
import br.com.forum.espetinhoapp.model.bean.Espetinho;
import io.realm.Realm;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class NovoFragment extends Fragment {

    private final int TIRAR_FOTO = 1;

    private View view = null;
    private ImageButton imageButtonAddFoto = null;
    private EditText edtNovoNome = null;
    private EditText edtNovoPreco = null;
    private EditText edtNovoDescricao = null;
    private FloatingActionButton fabNovo = null;
    private Realm realm = null;
    private Espetinho espetinho = null;
    private byte[] byteArrayFoto = null;

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
        fabNovo = (FloatingActionButton) view.findViewById(R.id.fab_add_novo);

        imageButtonAddFoto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                if (view.getContext().checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            TIRAR_FOTO);
                } else {
                    tirarFoto();
                }
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

    private void tirarFoto() {
        Intent intentTirarFoto = intentTirarFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentTirarFoto.resolveActivity(view.getContext().getPackageManager()) != null) {
            startActivityForResult(intentTirarFoto, TIRAR_FOTO);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TIRAR_FOTO && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArrayFoto = stream.toByteArray();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == TIRAR_FOTO) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Now user should be able to use camera
            } else {
                // Your app will not have this permission. Turn off all functions
                // that require this permission or it will force close like your
                // original question
            }
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
