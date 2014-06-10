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
