package com.ChiriChat.DataAccessObject.DAOWebServer;

import com.ChiriChat.DataAccessObject.InterfacesDAO.IMensajesDAO;
import com.ChiriChat.model.Mensajes;

import java.io.InputStream;
import java.util.List;

/**
 * Created by danny on 27/05/14.
 */
public class ChiriChatMensajesDAO implements IMensajesDAO {
    @Override
    public Mensajes insert(Mensajes dto) throws Exception {
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
    public String StreamToString(InputStream is) {
        return null;
    }
}
