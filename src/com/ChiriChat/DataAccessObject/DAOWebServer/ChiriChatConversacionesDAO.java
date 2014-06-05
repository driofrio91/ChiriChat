package com.ChiriChat.DataAccessObject.DAOWebServer;

import android.util.Log;
import com.ChiriChat.DataAccessObject.InterfacesDAO.IConversacioneDAO;
import com.ChiriChat.model.Conversaciones;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danny on 27/05/14.
 */
public class ChiriChatConversacionesDAO implements IConversacioneDAO{

    //Creo el objeto hhtpCLient para acceder a la conexion web.
    private HttpClient httpClient = new DefaultHttpClient();


    @Override
    public Conversaciones insert(Conversaciones dto) throws Exception {
        //Enviamos una peticion post al insert de conversacion.
        HttpPost httpPostNuevoUsuario = new HttpPost("http://chirichatserver.noip.me:85/ws/CreateConversacion");

        //Creo el objeto Jason con los datos del contacto que se registra en la app.
        JSONObject newConver = new JSONObject();
        try {
            newConver.put("Nombre", dto.getNombre());
            newConver.put("participantes", dto.getContactos());
            newConver.put("Owner", dto.getNombre());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        List parametros = new ArrayList();
        //Añade a la peticion post el parametro json, que contiene los datos a insertar.(json)
        parametros.add(new BasicNameValuePair("json", newConver.toString()));


        //Objeto para poder obtener respuesta del server
        HttpResponse response = httpClient.execute(httpPostNuevoUsuario);
        //Obtenemos el codigo de la respuesta
        int respuesta = response.getStatusLine().getStatusCode();
        Log.w("Respueta Insertar conversacion server", "" + respuesta);
        //Si respuesta 200 devuelvo mi conversacion , si no devolvere null
        if (respuesta == 200) {
            return dto;
        }

        return null;
    }

    @Override
    public boolean update(Conversaciones dto) throws Exception {
        return false;
    }

    @Override
    public boolean delete(Conversaciones dto) throws Exception {
        return false;
    }

    @Override
    public Conversaciones read(int id) throws Exception {
        return null;
    }

    @Override
    public List<Conversaciones> getAll() throws Exception {
        return null;
    }
}
