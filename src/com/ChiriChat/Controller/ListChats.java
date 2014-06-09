package com.ChiriChat.Controller;

import IntentService.MiService;
import IntentService.Reciver;
import IntentService.Service;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ShareActionProvider;

import com.ChiriChat.Adapter.myAdapterChats;
import com.ChiriChat.R;
import com.ChiriChat.SQLiteDataBaseModel.BDSQLite;
import com.ChiriChat.SQLiteDataBaseModel.GestionBaseDatosConversaciones;
import com.ChiriChat.model.Contactos;
import com.ChiriChat.model.Conversaciones;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by danny on 31/05/14.
 */
public class ListChats extends Activity {

    private GestionBaseDatosConversaciones GBDConversaciones = new GestionBaseDatosConversaciones();
    private ListView listViewChats;

    private myAdapterChats adapterChats;

    private Menu optionsMenu;
    private ShareActionProvider provider;

    private ArrayList<Conversaciones> allChats = new ArrayList<Conversaciones>();
    private Contactos thisContacto;

    private BDSQLite bd; // Instancia de la base de datos
    private SQLiteDatabase baseDatos; // Instancia de la base de datos escritura
    private SQLiteDatabase baseDatosL;// Instancia de la base de datos lectura
    
    Bundle extra;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_chats);
        //bd= BDSQLite.getInstance(this);
        bd= new BDSQLite(this);
        baseDatos = bd.getWritableDatabase();
        baseDatosL = bd.getReadableDatabase();
        
        listViewChats = (ListView) findViewById(R.id.listView_Chats);

        allChats = GBDConversaciones.recuperarConversaciones(baseDatosL);

        Log.d("Conversaciones Lista",""+allChats.toString());

        adapterChats = new myAdapterChats(this, allChats);

        listViewChats.setAdapter(adapterChats);

        listViewChats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openConversacion(allChats.get(position));
                Log.d("CONVERSACIÓN SELECCIONADA", ""+allChats.get(position).getNombre());
            }
        });

        adapterChats.notifyDataSetChanged();
        
        
        Intent msgIntent = new Intent(this, MiService.class);;
        startService(msgIntent);
        
       
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflate = getMenuInflater();

        inflate.inflate(R.menu.menu_activity_chats, menu);

        provider = (ShareActionProvider) menu.findItem(R.id.menu_share_contactos)
                .getActionProvider();

        provider.setShareIntent(doShare());

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_contacts:
            	
                Intent intent = new Intent(this, ListContacts.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.menu_settings:
                Intent i = new Intent(this, Opciones.class);
                startActivity(i);
                this.finish();
                break;
            case R.id.menuBar_my_perfil:
                openEditPerfil();
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    /**
     * +
     * Metodo que devolvera un intent para compartir.
     * Lo usa el ActioProvider
     *
     * @return
     */
    public Intent doShare() {
        // populate the share intent with data
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "This is a message for you");
        return intent;
    }


    public void openEditPerfil() {
        Intent i = new Intent(this, EditMyPerfilUser.class);
        startActivity(i);
//        extra.putString(nombre, value);
    }

    /**
     * Metodo que abre una conversacion con el objeto obtenido de la lista.
     *
     * @param conver
     */
    public void openConversacion(Conversaciones conver) {
        Intent i = new Intent(this, ListConversation.class);
        Bundle b = new Bundle();
        b.putParcelable("conversacion", conver);

        Log.d("metodo Conversacion", conver.toString());
        i.putExtras(b);
        startActivity(i);
        this.finish();
    }
}