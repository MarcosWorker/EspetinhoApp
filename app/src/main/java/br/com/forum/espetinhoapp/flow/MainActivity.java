package br.com.forum.espetinhoapp.flow;

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
import br.com.forum.espetinhoapp.flow.fragments.PedidosFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar myToolbar = null;
    private FragmentTransaction fragmentTransaction = null;
    private BottomNavigationView navigation = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_cardapio:
                    myToolbar.setTitle("Cardápio");
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, new CardapioFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_pedido:
                    myToolbar.setTitle("Pedidos");
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, new PedidosFragment());
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

        myToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        myToolbar.setTitle("Cardápio");
        myToolbar.setTitleTextColor(getResources().getColor(R.color.branco));
        myToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(myToolbar);

        //abre por default na tela do Cardapio
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, new CardapioFragment());
        fragmentTransaction.commit();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_sair:
                onDestroy();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
