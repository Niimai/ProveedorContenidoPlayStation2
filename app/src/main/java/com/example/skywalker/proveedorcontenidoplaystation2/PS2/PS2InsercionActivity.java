package com.example.skywalker.proveedorcontenidoplaystation2.PS2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.skywalker.proveedorcontenidoplaystation2.R;
import com.example.skywalker.proveedorcontenidoplaystation2.constantes.G;
import com.example.skywalker.proveedorcontenidoplaystation2.pojos.PS2;
import com.example.skywalker.proveedorcontenidoplaystation2.proveedor.PS2Proveedor;

/**
 * Created by Skywalker on 04/03/2018.
 */

public class PS2InsercionActivity extends AppCompatActivity {
    EditText editTextPS2Nombre;
    EditText editTextPS2Abreviatura;

    final int PETICION_SACAR_FOTO = 1;
    final int PETICION_GALERIA = 2;

    Bitmap foto = null;

    ImageView imageViewPS2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ps2_detalle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_PS2_detalle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextPS2Nombre = (EditText) findViewById(R.id.editTextPS2Nombre);
        editTextPS2Abreviatura = (EditText) findViewById(R.id.editTextPS2Abreviatura);

        imageViewPS2 = (ImageView) findViewById(R.id.image_view_PS2);

        ImageButton imageButtonCamara = (ImageButton) findViewById(R.id.image_button_camara);
        imageButtonCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sacarFoto();
            }
        });

        ImageButton imageButtonGaleria = (ImageButton) findViewById(R.id.image_button_galeria);
        imageButtonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elejirDeGaleria();
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
        switch (requestCode) {
            case PETICION_SACAR_FOTO:
                if (resultCode == RESULT_OK) {
                    foto = (Bitmap) data.getExtras().get("data");
                    imageViewPS2.setImageBitmap(foto);
                } else {
                    //El usuario hizo clic en Cancelar
                }
                break;
            case PETICION_GALERIA:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    imageViewPS2.setImageURI(uri);
                    foto = ((BitmapDrawable) imageViewPS2.getDrawable()).getBitmap();
                } else {
                    //El usuario hizo clic en Cancelar
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem menuItem = menu.add(Menu.NONE, G.GUARDAR, Menu.NONE, "Guardar");
        menuItem.setIcon(R.drawable.ic_action_guardar);
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case G.GUARDAR:
                attemptGuardar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void attemptGuardar(){
        editTextPS2Nombre.setError(null);
        editTextPS2Abreviatura.setError(null);

        String nombre = String.valueOf(editTextPS2Nombre.getText());
        String abreviatura = String.valueOf(editTextPS2Abreviatura.getText());

        if(TextUtils.isEmpty(nombre)){
            editTextPS2Nombre.setError(getString(R.string.campo_requerido));
            editTextPS2Nombre.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(abreviatura)){
            editTextPS2Abreviatura.setError(getString(R.string.campo_requerido));
            editTextPS2Abreviatura.requestFocus();
            return;
        }


     //   PS2 juego = new PS2(G.SIN_VALOR_INT, nombre, abreviatura/*, foto*/);
        PS2 juego = new PS2(G.SIN_VALOR_INT, nombre, abreviatura);
        PS2Proveedor.insertConBitacora(getContentResolver(),juego);
        finish();
    }
}
