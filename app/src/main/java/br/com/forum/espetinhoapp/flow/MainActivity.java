package br.com.forum.espetinhoapp.flow;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import br.com.forum.espetinhoapp.R;
import br.com.forum.espetinhoapp.flow.fragments.CardapioFragment;
import br.com.forum.espetinhoapp.flow.fragments.NovoFragment;
import br.com.forum.espetinhoapp.flow.fragments.PedidosFragment;
import br.com.forum.espetinhoapp.flow.fragments.RelatorioFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentTransaction fragmentTransaction = null;
    private BottomNavigationView navigation = null;
    private Intent intent = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_cardapio:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, new CardapioFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_pedido:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, new PedidosFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_novo:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, new NovoFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_relatorio:
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, new RelatorioFragment());
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }

    };

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //abre por default na tela do Cardapio
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, new CardapioFragment());
        fragmentTransaction.commit();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
