package com.ChiriChat.DataAccessObject.InterfacesDAO;

import com.ChiriChat.DataAccessObject.DataAccessObject;
import com.ChiriChat.model.Conversaciones;

import java.io.InputStream;

/**
 * Created by danny on 27/05/14.
 */
public interface IConversacioneDAO extends DataAccessObject<Conversaciones>{

    public String StreamToString(InputStream is);
}
