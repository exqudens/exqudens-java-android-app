package exqudens.java.android.application.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

public class MainService extends Service {

    public class LocalBinder extends Binder {

        public MainService getService() {
            return MainService.this;
        }

    }

    private final IBinder binder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public int getRandomInt() {
        return new Random().nextInt(100);
    }
}
