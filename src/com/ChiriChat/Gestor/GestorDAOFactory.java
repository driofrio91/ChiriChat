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


package com.ChiriChat.Gestor;

import com.ChiriChat.Factory.Factories.ChiriChatServerFactoryDAO;
import com.ChiriChat.Factory.FactoryDAO;

/**
 * Esta clase implementara el patron singlenton, asisolo se creara una instancia
 * de nuestra factoria.
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
