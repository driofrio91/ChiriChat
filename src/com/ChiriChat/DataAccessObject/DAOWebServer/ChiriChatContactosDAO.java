package com.ChiriChat.DataAccessObject.DAOWebServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.ChiriChat.Controller.Register;
import com.ChiriChat.DataAccessObject.InterfacesDAO.IContactosDAO;
import com.ChiriChat.model.Contactos;

/**
 * Created by danny on 27/05/14.
 */
public class ChiriChatContactosDAO implements IContactosDAO {
    private int idUsuario;
    private String nombre;
    private String estado;
    private int telefono;
    @Override
    public Contactos insert(Contactos dto) throws Exception {
        //Variables con las que se almacena los datos del usuario actual

        
    	//Creo el objeto hhtpCLient para acceder a la conexi�n web.
        HttpClient httpClient = new DefaultHttpClient();
        //Enviamos una petici�n post al insert del usuario.
        HttpPost httpPostNuevoUsuario = new HttpPost("http://chirichatserver.noip.me:85/ws/insertUsuario");
        
        //Creo el objeto Jason con los datos del contacto que se registra en la app.
        JSONObject newUsuario = new JSONObject();
        try {
            newUsuario.put("Nombre", dto.getNombre());
            newUsuario.put("Telefono", dto.getTelefono());
            newUsuario.put("Estado", dto.getEstado());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        List parametros = new ArrayList();
        //A�ade a la petici�n post el parametro json, que contiene los datos a insertar.(jason)
        parametros.add(new BasicNameValuePair("json", newUsuario.toString()));
        
        
        try {
        	//Creamos la entidad con los datos que le hemos pasado
            httpPostNuevoUsuario.setEntity(new UrlEncodedFormEntity(parametros));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //Objeto para poder obtener respuesta del server
        HttpResponse response = httpClient.execute(httpPostNuevoUsuario);
        //Obtenemos el c�digo de la respuesta
        int respuesta = response.getStatusLine().getStatusCode();
        Log.w("Respueta", ""+respuesta);
        //Si respuesta 200 todo es correcto
        if (respuesta == 200) {
            return dto;
        }
        return null;
    }

    @Override
    public boolean update(Contactos dto) throws Exception {
        return false;
    }

    @Override
    public boolean delete(Contactos dto) throws Exception {
        return false;
    }

    @Override
    public Contactos read(int id) throws Exception {
        return null;
    }

    @Override
    public List<Contactos> getAll() throws Exception {
        return null;
    }
    
    public String StreamToString(InputStream is) {
        //Creamos el Buffer
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            //Bucle para leer todas las l�neas
            //En este ejemplo al ser solo 1 la respuesta
            //Pues no har�a falta
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //retornamos el codigo l�mpio
        return sb.toString();
    }	
}
