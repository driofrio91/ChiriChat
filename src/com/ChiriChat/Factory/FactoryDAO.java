package com.ChiriChat.Factory;

import com.ChiriChat.DataAccessObject.InterfacesDAO.IContactosDAO;
import com.ChiriChat.DataAccessObject.InterfacesDAO.IConversacioneDAO;
import com.ChiriChat.DataAccessObject.InterfacesDAO.IMensajesDAO;

/**
 * Created by danny on 27/05/14.
 */
public interface FactoryDAO {
    public IMensajesDAO getMensajesDAO() throws Exception;
    public IConversacioneDAO getConversacionesDAO() throws Exception;
    public IContactosDAO getContactosDAO() throws Exception;
}
