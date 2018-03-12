package com.example.skywalker.proveedorcontenidoplaystation2.PS2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.skywalker.proveedorcontenidoplaystation2.R;
import com.example.skywalker.proveedorcontenidoplaystation2.constantes.G;

/**
 * Created by Skywalker on 04/03/2018.
 */

public class PS2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ps2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        PS2ListFragment PS2ListFragment = new PS2ListFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_ps2, PS2ListFragment);
        transaction.commit();

        if(G.VERSION_ADMINISTRADOR) {
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), PS2InsercionActivity.class);
                    startActivity(intent);
                }
            });
        }

    }
}
