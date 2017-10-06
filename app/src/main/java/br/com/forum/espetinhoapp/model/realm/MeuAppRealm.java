package br.com.forum.espetinhoapp.model.realm;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Marco on 06/10/2017.
 */

public class MeuAppRealm extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
