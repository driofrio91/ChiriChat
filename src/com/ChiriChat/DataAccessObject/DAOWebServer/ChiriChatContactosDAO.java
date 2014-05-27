package com.ChiriChat.DataAccessObject.DAOWebServer;

import com.ChiriChat.DataAccessObject.InterfacesDAO.IContactosDAO;
import com.ChiriChat.model.Contactos;

import java.util.List;

/**
 * Created by danny on 27/05/14.
 */
public class ChiriChatContactosDAO implements IContactosDAO {
    @Override
    public Contactos insert(Contactos dto) throws Exception {
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
}
