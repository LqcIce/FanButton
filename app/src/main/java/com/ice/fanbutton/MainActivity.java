package com.ice.fanbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private FanBtn fanBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fanBtn = (FanBtn) findViewById(R.id.fanbtn);
        fanBtn.setOnDownActionListener(new FanBtn.OnDownActionListener() {
            @Override
            public void OnDown() {
                Toast.makeText(MainActivity.this, "Nihiao", Toast.LENGTH_LONG).show();
            }
        });
    }
}
