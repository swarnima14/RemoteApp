package com.app.appremote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.text.ParseException;

public class MainActivity2 extends AppCompatActivity {

    FirebaseRemoteConfig mFirebaseRemoteConfig;
    ConstraintLayout constraintLayout;
    String colour;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        constraintLayout = findViewById(R.id.lay);
        textView = findViewById(R.id.tvView);

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(2)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
        fetch();

    }
    
    private void fetch(){

        colour = mFirebaseRemoteConfig.getString("colour");

        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener( new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            
                            Toast.makeText(MainActivity2.this, "Fetch and activate succeeded",
                             Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(MainActivity2.this, "Fetch failed.. Check your network connection.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        display();
                    }
                });
        
    }

    private void display() {
        colour = mFirebaseRemoteConfig.getString("colour");
        if(colour.equals("red"))
        {
            constraintLayout.setBackgroundColor(Color.RED);
            textView.setText(colour);
        }

        else if(colour.equals("yellow"))
        {
            constraintLayout.setBackgroundColor(Color.YELLOW);
            textView.setText(colour);
        }

        else
        {
            constraintLayout.setBackgroundColor(Color.WHITE);
            textView.setText(colour);
        }

    }
}