package br.com.forum.espetinhoapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar myToolbar = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_cardapio:
                    myToolbar.setTitle("Cardápio");
                    return true;
                case R.id.navigation_pedido:
                    myToolbar.setTitle("Pedidos");
                    return true;
                case R.id.navigation_comentarios:
                    myToolbar.setTitle("Comentarios");
                    return true;
                case R.id.navigation_local:
                    myToolbar.setTitle("Localização");
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolbar = (Toolbar) findViewById(R.id.toolbar_main);
        myToolbar.setTitle("Cardápio");
        myToolbar.setTitleTextColor(getResources().getColor(R.color.branco));
        myToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(myToolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
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
            case R.id.menu_config:
                Toast.makeText(this, "Em construção", Toast.LENGTH_SHORT).show();
                myToolbar.setTitle("Configurações");
                break;
        }
        return true;
    }


}
