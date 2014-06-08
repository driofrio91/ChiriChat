package com.ChiriChat.Factory.Factories;

import com.ChiriChat.DataAccessObject.DAOWebServer.ChiriChatContactosDAO;
import com.ChiriChat.DataAccessObject.DAOWebServer.ChiriChatConversacionesDAO;
import com.ChiriChat.DataAccessObject.DAOWebServer.ChiriChatMensajesDAO;
import com.ChiriChat.DataAccessObject.InterfacesDAO.IContactosDAO;
import com.ChiriChat.DataAccessObject.InterfacesDAO.IConversacioneDAO;
import com.ChiriChat.DataAccessObject.InterfacesDAO.IMensajesDAO;
import com.ChiriChat.Factory.FactoryDAO;

/**
 * Created by danny on 27/05/14.
 */
public class ChiriChatServerFactoryDAO implements FactoryDAO {
    @Override
    public IMensajesDAO getMensajesDAO()throws Exception{
        return new ChiriChatMensajesDAO();
    }

    @Override
    public IConversacioneDAO getConversacionesDAO()throws Exception {
        return new ChiriChatConversacionesDAO();
    }

    @Override
    public IContactosDAO getContactosDAO()throws Exception{
        return new ChiriChatContactosDAO();
    }
}
