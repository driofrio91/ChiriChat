package com.ChiriChat.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import com.ChiriChat.Adapter.myAdapterChats;
import com.ChiriChat.R;
import com.ChiriChat.model.Contactos;
import com.ChiriChat.model.Conversaciones;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by danny on 31/05/14.
 */
public class ListChats extends Activity {


    private ListView listViewChats;

    private myAdapterChats adapterChats;

    private Menu optionsMenu;
    private ShareActionProvider provider;

    private ArrayList<Conversaciones> allChats = null;

    private Contactos thisContacto;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_chats);

        listViewChats = (ListView) findViewById(R.id.listView_Chats);

        allChats = new ArrayList<Conversaciones>();

        adapterChats = new myAdapterChats(this, allChats);

        listViewChats.setAdapter(adapterChats);

        adapterChats.notifyDataSetChanged();
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
                break;
            case R.id.menu_settings:
                Intent i = new Intent(this, Opciones.class);
                startActivity(i);
                break;
            case R.id.menuBar_my_perfil:
                openEditPerfil();

                break;

        }


        return super.onOptionsItemSelected(item);
    }

   /* @Override
    public void onBackPressed() {
        finish();
    }*/

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
    }
}