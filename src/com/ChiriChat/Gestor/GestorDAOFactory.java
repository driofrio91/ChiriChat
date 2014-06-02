package com.ChiriChat.Gestor;

import com.ChiriChat.Factory.ChiriChatServerFactoryDAO;
import com.ChiriChat.Factory.FactoryDAO;

/**
 * Created by danny on 28/05/14.
 */
public class GestorDAOFactory {

    private static GestorDAOFactory  gestor;

    private GestorDAOFactory() {
    }

    public static GestorDAOFactory getInstance() {
        if (gestor == null) {
            gestor = new GestorDAOFactory();
        }

        return gestor;
    }

    public FactoryDAO getFactory(){
        return new ChiriChatServerFactoryDAO();
    }

}
