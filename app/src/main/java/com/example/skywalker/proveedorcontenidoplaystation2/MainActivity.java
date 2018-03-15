package com.example.skywalker.proveedorcontenidoplaystation2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.skywalker.proveedorcontenidoplaystation2.PS2.PS2Activity;
import com.example.skywalker.proveedorcontenidoplaystation2.constantes.Utilidades;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    final int PETICION_SACAR_FOTO = 1;
    final int PETICION_GALERIA = 2;

    ImageView imageViewImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = (Button) findViewById(R.id.buttonIrALosJuegos);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TabsActivity.class);
                startActivity(intent);
            }
        });

        Button buttonCamara = (Button) findViewById(R.id.buttonCamara);
        buttonCamara.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sacarFoto();
            }

        });

        Button buttonGaleria = (Button) findViewById(R.id.buttonGaleria);
        buttonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elejirDeGaleria();
            }
        });

        imageViewImagen = (ImageView) findViewById(R.id.image_view_imagen);

        Button button2 = (Button) findViewById(R.id.buttonNavigationDrawer);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NavigationDrawerActivity.class);
                startActivity(intent);
            }
        });


    }

    void elejirDeGaleria(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PETICION_GALERIA);
    }


    void sacarFoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PETICION_SACAR_FOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case PETICION_SACAR_FOTO:
                if(resultCode==RESULT_OK){
                    Bitmap foto = (Bitmap) data.getExtras().get("data");
                    imageViewImagen.setImageBitmap(foto);
                    try {
                        Utilidades.storeImage(foto, this, "imagen.jpg");
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(),"Error: No se pudo guardar la imagen", Toast.LENGTH_LONG).show();
                    }
                } else {
                    //El usuario hizo clic en Cancelar
                }
                break;
            case PETICION_GALERIA:
                if(resultCode==RESULT_OK){
                    Uri uri = data.getData();
                    imageViewImagen.setImageURI(uri);
                } else {
                    //El usuario hizo clic en Cancelar
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
