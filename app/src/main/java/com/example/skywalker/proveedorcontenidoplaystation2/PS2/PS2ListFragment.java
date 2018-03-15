package com.example.skywalker.proveedorcontenidoplaystation2.PS2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import com.example.skywalker.proveedorcontenidoplaystation2.R;
import com.example.skywalker.proveedorcontenidoplaystation2.constantes.G;
import com.example.skywalker.proveedorcontenidoplaystation2.constantes.Utilidades;
import com.example.skywalker.proveedorcontenidoplaystation2.pojos.PS2;
import com.example.skywalker.proveedorcontenidoplaystation2.proveedor.PS2Proveedor;
import com.example.skywalker.proveedorcontenidoplaystation2.proveedor.Contrato;

import java.io.FileNotFoundException;

/**
 * Created by Skywalker on 04/03/2018.
 */

public class PS2ListFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<Cursor> {

    PS2CursorAdapter mAdapter;
    LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

    ActionMode mActionMode;
    View viewSeleccionado;

    public static PS2ListFragment newInstance() {
        PS2ListFragment f = new PS2ListFragment();

        return f;
    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        if(G.VERSION_ADMINISTRADOR) {
            MenuItem menuItem = menu.add(Menu.NONE, G.INSERTAR, Menu.NONE, "INSERTAR");
            menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
            menuItem.setIcon(R.drawable.ic_nuevo_registro);
            super.onCreateOptionsMenu(menu, inflater);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case G.INSERTAR:
                Intent intent = new Intent(getActivity(), PS2InsercionActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Log.i(LOGTAG, "onCreateView");
        View v = inflater.inflate(R.layout.fragment_ps2_list, container, false);

        mAdapter = new PS2CursorAdapter(getActivity());
        setListAdapter(mAdapter);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.i(LOGTAG, "onActivityCreated");

        mCallbacks = this;

        getLoaderManager().initLoader(0, null, mCallbacks);

        if(G.VERSION_ADMINISTRADOR) {
            getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    if (mActionMode != null) {
                        return false;
                    }
                    mActionMode = getActivity().startActionMode(mActionModeCallback);
                    view.setSelected(true);
                    viewSeleccionado = view;
                    return true;
                }
            });
        }
    }

    ActionMode.Callback mActionModeCallback = new ActionMode.Callback(){

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_contextual, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch(item.getItemId()){
                case R.id.menu_contextual_borrar:
                    int PS2Id = (Integer) viewSeleccionado.getTag();
                    PS2Proveedor.deleteConBitacora(getActivity().getContentResolver(), PS2Id);
                    break;
                case R.id.menu_contextual_editar:
                    Intent intent = new Intent(getActivity(), PS2ActualizacionActivity.class);
                    PS2Id = (Integer) viewSeleccionado.getTag();

                    //Log.i("El identificador 1", "kk"+PS2Id);
                    PS2 PS2 = PS2Proveedor.read(getActivity().getContentResolver(), PS2Id);
                    //Log.i("El identificador", PS2.getNombre());
                    //intent.putExtra("ID", PS2.getID());
                    intent.putExtra("Nombre", PS2.getNombre());
                    intent.putExtra("Abreviatura", PS2.getAbreviatura());

                    intent.putExtra(Contrato.PS2._ID, PS2Id);
                    startActivity(intent);
                    break;
                default:
                    return false;
            }
            //mode.finish();
            mActionMode.finish();
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.  This
        // sample only has one Loader, so we don't care about the ID.
        // First, pick the base URI to use depending on whether we are
        // currently filtering.
        String columns[] = new String[] { Contrato.PS2._ID,
                Contrato.PS2.NOMBRE,
                Contrato.PS2.ABREVIATURA
        };

        Uri baseUri = Contrato.PS2.CONTENT_URI;

        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.

        String selection = null;

        return new CursorLoader(getActivity(), baseUri,
                columns, selection, null, null);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)

        Uri laUriBase = Uri.parse("content://"+Contrato.AUTHORITY+"/Playstation2");
        data.setNotificationUri(getActivity().getContentResolver(), laUriBase);

        mAdapter.swapCursor(data);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mAdapter.swapCursor(null);
    }

    public class PS2CursorAdapter extends CursorAdapter {
        public PS2CursorAdapter(Context context) {
            super(context, null, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            int ID = cursor.getInt(cursor.getColumnIndex(Contrato.PS2._ID));
            String nombre = cursor.getString(cursor.getColumnIndex(Contrato.PS2.NOMBRE));
            String abreviatura = cursor.getString(cursor.getColumnIndex(Contrato.PS2.ABREVIATURA));

            TextView textviewNombre = view.findViewById(R.id.textview_PS2_list_item_nombre);
            textviewNombre.setText(nombre);

            TextView textviewAbreviatura = view.findViewById(R.id.textview_PS2_list_item_abreviatura);
            textviewAbreviatura.setText(abreviatura);

            ImageView image = view.findViewById(R.id.image_view);

            try {
                Utilidades.loadImageFromStorage(getActivity(), "img_" + ID + ".jpg", image);
            } catch (FileNotFoundException e) {
                ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
                int color = generator.getColor(abreviatura); //Genera un color según el nombre
                TextDrawable drawable = TextDrawable.builder()
                        .buildRound(nombre.substring(0,1), color);
                image.setImageDrawable(drawable);
            }

            view.setTag(ID);

        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.ps2_list_item, parent, false);
            bindView(v, context, cursor);
            return v;
        }
    }

}
