package br.com.forum.espetinhoapp.flow.fragments;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

    private View viewTela = null;
    private ImageButton imageButtonAddFoto = null;
    private EditText edtNovoNome = null;
    private EditText edtNovoPreco = null;
    private EditText edtNovoDescricao = null;
    private FloatingActionButton fabNovo = null;
    private Realm realm = null;
    private Espetinho espetinho = null;
    private byte[] byteArrayFoto = null;
    private ImageView imageViewFoto = null;

    public NovoFragment() {
        // Required empty public constructor
    }


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewTela = inflater.inflate(R.layout.fragment_novo, container, false);
        realm = Realm.getDefaultInstance();

        imageButtonAddFoto = (ImageButton) viewTela.findViewById(R.id.bt_add_foto);
        imageViewFoto = (ImageView) viewTela.findViewById(R.id.img_foto_novo);
        edtNovoNome = (EditText) viewTela.findViewById(R.id.edt_nome_novo);
        edtNovoPreco = (EditText) viewTela.findViewById(R.id.edt_preco_novo);
        edtNovoDescricao = (EditText) viewTela.findViewById(R.id.edt_descricao_novo);
        fabNovo = (FloatingActionButton) viewTela.findViewById(R.id.fab_add_novo);

        if (viewTela.getContext().checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    TIRAR_FOTO);
        } else {
        }

        imageViewFoto.setVisibility(View.INVISIBLE);

        imageViewFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto();
            }
        });

        imageButtonAddFoto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                tirarFoto();
            }
        });

        fabNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (byteArrayFoto == null) {
                    Toast.makeText(view.getContext(), "Por favor insira uma foto...", Toast.LENGTH_SHORT).show();
                } else if (edtNovoNome == null || edtNovoNome.getText().toString().isEmpty()) {
                    Toast.makeText(view.getContext(), "Por favor insira um nome...", Toast.LENGTH_SHORT).show();
                } else if (edtNovoPreco == null || edtNovoPreco.getText().toString().isEmpty()) {
                    Toast.makeText(view.getContext(), "Por favor insira um preço...", Toast.LENGTH_SHORT).show();
                } else if (edtNovoDescricao == null || edtNovoDescricao.getText().toString().isEmpty()) {
                    Toast.makeText(view.getContext(), "Por favor insira uma descrição...", Toast.LENGTH_SHORT).show();
                } else {

                    AlertDialog.Builder adb = new AlertDialog.Builder(view.getContext());
                    adb.setTitle("Adicionar esse espetinho?");
                    adb.setIcon(android.R.drawable.ic_dialog_alert);
                    adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Number currentIdNum = realm.where(Espetinho.class).max("id");
                            int nextId;
                            if (currentIdNum == null) {
                                nextId = 1;
                            } else {
                                nextId = currentIdNum.intValue() + 1;
                            }
                            realm.beginTransaction();
                            espetinho = realm.createObject(Espetinho.class, nextId);
                            espetinho.setNome(edtNovoNome.getText().toString());
                            espetinho.setQtd(0);
                            espetinho.setDescricao(edtNovoDescricao.getText().toString());
                            espetinho.setFoto(byteArrayFoto);
                            espetinho.setPreco(Double.valueOf(edtNovoPreco.getText().toString()));
                            realm.commitTransaction();

                            edtNovoNome.setText("");
                            edtNovoDescricao.setText("");
                            edtNovoPreco.setText("");
                            byteArrayFoto = null;
                            imageViewFoto.setImageResource(R.mipmap.ic_foto);
                            Toast.makeText(viewTela.getContext(), "Espetinho salvo com sucesso", Toast.LENGTH_SHORT).show();

                        } });
                    adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {


                        } });
                    adb.show();
                    adb.setCancelable(false);

                }

            }
        });

        return viewTela;
    }

    private void tirarFoto() {
        Intent intentTirarFoto = intentTirarFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentTirarFoto.resolveActivity(viewTela.getContext().getPackageManager()) != null) {
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
            getCircleBitmap(imageBitmap).compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArrayFoto = stream.toByteArray();
            imageButtonAddFoto.setVisibility(View.INVISIBLE);
            imageViewFoto.setVisibility(View.VISIBLE);
            imageViewFoto.setImageBitmap(getCircleBitmap(imageBitmap));
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

    public Bitmap getCircleBitmap(Bitmap scaleBitmapImage) {
        int targetWidth = 200;
        int targetHeight = 200;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (realm == null) {
            realm = Realm.getDefaultInstance();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
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
