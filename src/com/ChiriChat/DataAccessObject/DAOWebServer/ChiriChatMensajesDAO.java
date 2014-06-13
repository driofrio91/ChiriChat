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
import com.ChiriChat.DataAccessObject.InterfacesDAO.IMensajesDAO;
import com.ChiriChat.model.Mensajes;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasde que extiendo de IMensajesDAO, tendremos en ella los metodos CRUD y lo que
 * nos hayamos declarado el la interfaz.
 */
public class ChiriChatMensajesDAO implements IMensajesDAO {

    //Creo el objeto hhtpCLient para acceder a la conexion web.
    private HttpClient httpClient = new DefaultHttpClient();

    /**
     * Metodo en el que insertara un objeto mensaje en el web-service a traves de una peticion
     * POST, transformara el mensaje a objeto JSON a enviar, leera la respues del servidor y
     * construira el objeto mensaje que se retorna.
     * @param dto
     * @return
     * @throws Exception
     */
    @Override
    public Mensajes insert(Mensajes dto) throws Exception {

        //Enviamos una peticion post al insert del usuario.
        HttpPost httpPostNewMensaje = new HttpPost("http://chirichatserver.noip.me:85/ws/newMensaje");

        //Creo el objeto JSON con los datos del contacto que se registra en la app.
        JSONObject newUsuario = new JSONObject();
        try {
            newUsuario.put("idConver", dto.getIdConversacion());
            newUsuario.put("idUsuario", dto.getIdUsuario());
            newUsuario.put("texto", dto.getCadena());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List parametros = new ArrayList();
        //Añade a la peticion post el parametro json, que contiene los datos a insertar.(json)
        parametros.add(new BasicNameValuePair("json", newUsuario.toString()));



            //Creamos la entidad con los datos que le hemos pasado
            httpPostNewMensaje.setEntity(new UrlEncodedFormEntity(parametros));

        //Objeto para poder obtener respuesta del server
        HttpResponse response = httpClient.execute(httpPostNewMensaje);
        //Obtenemos el codigo de la respuesta
        int respuesta = response.getStatusLine().getStatusCode();
        Log.w("Respueta", "" + respuesta);
        //Si respuesta 200 devuelvo mi usuario , si no devolvere null
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

            Mensajes mensaje = new Mensajes(jsonRecibido);

            return mensaje;
        }



        return null;
    }

    @Override
    public boolean update(Mensajes dto) throws Exception {
        return false;
    }

    @Override
    public boolean delete(Mensajes dto) throws Exception {
        return false;
    }

    @Override
    public Mensajes read(int id) throws Exception {
        return null;
    }

    @Override
    public List<Mensajes> getAll() throws Exception {
        return null;
    }

    @Override
    public List<Mensajes> mensajesDesde(int mensajeId) {
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
                sb.append(line);
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
