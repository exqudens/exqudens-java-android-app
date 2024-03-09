package exqudens.java.android.application.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import exqudens.java.android.application.R;
import exqudens.java.android.application.service.MainService;

public class MainActivity extends AppCompatActivity {

    private MainService service = null;
    private boolean serviceBound = false;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MainService.LocalBinder binder = (MainService.LocalBinder) iBinder;
            service = binder.getService();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            serviceBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MainService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);

        TextView textView1 = findViewById(R.id.textView1);
        Button button1 = findViewById(R.id.button1);

        button1.setOnClickListener((View v) -> {
            String text = MainService.class.getSimpleName() + " disconnected!";
            if (serviceBound) {
                text = String.valueOf(service.getRandomInt());
            }
            textView1.setText(text);
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        serviceBound = false;
    }
}
