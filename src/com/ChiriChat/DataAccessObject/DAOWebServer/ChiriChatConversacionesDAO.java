/*
* Copyright (C) 2014 Alejandro Moreno Jimenez | alejandroooo887@gmail.com
*					 Danny Riofrio Jimenez | superdanny.91@gmail.com
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>. *
*/


package com.ChiriChat.DataAccessObject.DAOWebServer;

import android.util.Log;
import com.ChiriChat.DataAccessObject.InterfacesDAO.IConversacioneDAO;
import com.ChiriChat.model.Contactos;
import com.ChiriChat.model.Conversaciones;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasde que extiendo de IConversacioneDAO, tendremos en ella los metodos CRUD y lo que
 * nos hayamos declarado el la interfaz.
 */
public class ChiriChatConversacionesDAO implements IConversacioneDAO{

    //Creo el objeto hhtpCLient para acceder a la conexion web.
    private HttpClient httpClient = new DefaultHttpClient();

    /**
     * Metodo que insertara un mensaje en el web-service. Por medio de una peticio post, enviara
     * un objeto json, con los datos de las conversacion. Obtendra la respuesta del servidor y
     * contruira la conversacion para retornarlo.
     * @param dto
     * @return
     * @throws Exception
     */
    @Override
    public Conversaciones insert(Conversaciones dto) throws Exception {
        //Enviamos una peticion post al insert de conversacion.
        HttpPost httPostNuevaConver = new HttpPost("http://chirichatserver.noip.me:85/ws/CreateConversacion");

        //Creo el objeto Jason con los datos del contacto que se registra en la app.
        JSONObject newConver = new JSONObject();

        try {
            newConver.put("nombre", dto.getNombre());
            newConver.put("owner", dto.getNombre());
            ArrayList<Contactos> contactos = dto.getContactos();
            JSONArray JSONContactos = new JSONArray();
            for (int i = 0; i <contactos.size() ; i++) {
                Contactos c = contactos.get(i);
                JSONObject j = new JSONObject(c.toString());
                JSONContactos.put(i,j);
            }
            newConver.put("participantes", JSONContactos);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        List parametros = new ArrayList();
        //Añade a la peticion post el parametro json, que contiene los datos a insertar.(json)
        parametros.add(new BasicNameValuePair("json", newConver.toString()));
        Log.d("JSON de conversacion", newConver.toString());

        try {
            //Creamos la entidad con los datos que le hemos pasado
            httPostNuevaConver.setEntity(new UrlEncodedFormEntity(parametros));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        //Objeto para poder obtener respuesta del server
        HttpResponse response = httpClient.execute(httPostNuevaConver);
        //Obtenemos el codigo de la respuesta
        int respuesta = response.getStatusLine().getStatusCode();
        Log.w("Respueta Insertar conversacion server", "" + respuesta);
        //Si respuesta 200 devuelvo mi conversacion , si no devolvere null
        if (respuesta == 200) {

            //Nos conectamos para recibir los datos de respuesta
            HttpEntity entity = response.getEntity();
            //Creamos el InputStream
            InputStream is = entity.getContent();
            //Leemos el inputStream
            String temp = StreamToString(is);
            //Creamos el JSON con la cadena del inputStream
            Log.d("Cadena JSON", temp.toString());
            JSONObject jsonRecibido = new JSONObject(temp);

            Log.d("InputStreamReader", temp.toString());
            Log.d("JSON ==>", jsonRecibido.toString());

            Conversaciones conver = new Conversaciones(jsonRecibido);

            return conver;

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

    /**
     * Metoto al que se le pasara un InputStream y devolera una cadena.
     * Este metodo es llamado para tratar las respuesta del servidor.
     * @param is
     * @return
     */
    @Override
    public String StreamToString(InputStream is) {
        //Creamos el Buffer
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            //Bucle para leer todas las lineas
            //En este ejemplo al ser solo 1 la respuesta
            //Pues no hara falta
            while ((line = reader.readLine()) != null){
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
        //retornamos el codigo limpio
        return sb.toString();
    }
}
