package br.com.forum.espetinhoapp.model.realm;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Marco on 06/10/2017.
 */

public class MeuAppRealm extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("espetinho.realm").build();
        Realm.setDefaultConfiguration(config);
    }
}
