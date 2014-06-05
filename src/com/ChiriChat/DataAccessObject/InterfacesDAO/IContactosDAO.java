package com.ChiriChat.DataAccessObject.InterfacesDAO;

import com.ChiriChat.DataAccessObject.DataAccessObject;
import com.ChiriChat.model.Contactos;

import java.util.List;

/**
 * Created by danny on 27/05/14.
 */
public interface IContactosDAO extends DataAccessObject<Contactos> {
    public List getAllSinMiContacto(Contactos dto) throws Exception;
}
