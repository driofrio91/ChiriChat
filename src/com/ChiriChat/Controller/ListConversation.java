package com.ChiriChat.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.ChiriChat.Adapter.myAdapterMensajes;
import com.ChiriChat.R;
import com.ChiriChat.model.Contactos;
import com.ChiriChat.model.Mensajes;

import java.util.ArrayList;

/**
 * Created by neosistec on 13/05/2014.
 */
public class ListConversation extends Activity  {

    private EditText editText;
    private Button buttonSend;
    private ListView lisViewMensajes;

    private myAdapterMensajes adapterMensajes;

    private ArrayList<Mensajes> allMensajes;

    private Contactos contacto;

    private ShareActionProvider provider;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_conversation);


        editText = (EditText) findViewById(R.id.text_sent_msg);
        buttonSend = (Button) findViewById(R.id.bt_sent_msg);
        lisViewMensajes = (ListView) findViewById(android.R.id.list);


        //Recupero el nombre del contacto
        Bundle extras = getIntent().getExtras();

        //Recogemos el contacto que hemos pasabo poer bundle al hacer click en un contacto de la lista
        if (extras != null){
            contacto = getIntent().getParcelableExtra("contacto");
            //Cambiamos el titulo de la actividad
            this.setTitle(contacto.getNombre());
            Log.d("ID", contacto.toString());

        }



        allMensajes = new ArrayList<Mensajes>();

        adapterMensajes = new myAdapterMensajes(this, allMensajes);

        lisViewMensajes.setAdapter(adapterMensajes);
       // setRetainInstance(true);
        if (allMensajes.size() > 0){
            lisViewMensajes.setSelection(allMensajes.size()-1);
        }
        Log.d("SAved instance", String.valueOf(savedInstanceState));
        if (savedInstanceState != null){

            allMensajes = savedInstanceState.getParcelableArrayList("list");

            adapterMensajes.notifyDataSetChanged();
        }

        lisViewMensajes.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        lisViewMensajes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                removeItemFromList(position);

                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (allMensajes.size() > 0){
            lisViewMensajes.setSelection(allMensajes.size()-1);
        }
    }

   protected void removeItemFromList(int position) {
       final int deletePosition = position;

       AlertDialog.Builder alert = new AlertDialog.Builder(
               ListConversation.this);

       alert.setTitle("Delete");
       alert.setMessage("Do you want delete this item?");
       alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               // TOD O Auto-generated method stub

               // main code on after clicking yes
               allMensajes.remove(deletePosition);
               adapterMensajes.notifyDataSetChanged();
               adapterMensajes.notifyDataSetInvalidated();

           }
       });
       alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

               dialog.dismiss();
           }
       });

       alert.show();

   }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        ArrayList<Mensajes> valores = adapterMensajes.getItemMensajes();
        outState.putParcelableArrayList("list",valores);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        allMensajes = savedInstanceState.getParcelableArrayList("list");
//        Log.d("onREstoreIn", allMensajes.get(allMensajes.size()-1).toString() );

        adapterMensajes.setItemMensajes(allMensajes);
        adapterMensajes.notifyDataSetChanged();
        // setRetainInstance(true);
        if (allMensajes.size() > 0){
            lisViewMensajes.setSelection(allMensajes.size()-1);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_activity_conversation, menu);

        provider = (ShareActionProvider) menu.findItem(R.id.menu_share_conversacion).getActionProvider();

        provider.setShareIntent(doShare());



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.perfil:
                Intent i = new Intent(this, PerfilUser.class);
                Bundle b = new Bundle();
                b.putParcelable("contacto" ,this.contacto);
                i.putExtras(b);
                startActivity(i);
                break;


        }

        return super.onOptionsItemSelected(item);
    }



    public void send(View view){

        String cadena = editText.getText().toString().trim();

       if (!cadena.isEmpty()){

          Mensajes men = new Mensajes(cadena,1);

           allMensajes.add(men);

           adapterMensajes.notifyDataSetChanged();

           lisViewMensajes.setSelection(allMensajes.size()-1);

           editText.setText("");
       }

    }

    /**+
     * Metodo que devolvera un intent para compartir.
     * Lo usa el ActioProvider
     * @return
     */
    public Intent doShare() {
        // populate the share intent with data
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "This is a message for you");
        return intent;
    }



}