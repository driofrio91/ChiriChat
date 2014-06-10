package com.ChiriChat.DataAccessObject.InterfacesDAO;

import com.ChiriChat.DataAccessObject.DataAccessObject;
import com.ChiriChat.model.Mensajes;

import java.io.InputStream;
import java.util.List;

/**
 * Created by danny on 27/05/14.
 */
public interface IMensajesDAO extends DataAccessObject<Mensajes>{

    public List<Mensajes> mensajesDesde(int mensajeId);
    public String StreamToString(InputStream is);
}
