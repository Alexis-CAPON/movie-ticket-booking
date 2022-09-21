/*
 * Copyright (C) 2021 Alexis
 *
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package movieproject;


import java.net.Authenticator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static movieproject.MovieProject.debugMode;
/**
 * EN : All database stuff, links and methods
 * @author Alexis
 */
public class SQLDatabase {
    
    
    private static String BDD = "acubeDB";
    private static String url = "jdbc:mysql://ca685973-001.dbaas.ovh.net:35570/" + BDD;
    private static String user = "Alexis";
    private static String passwd = "BananaSplit02";  
    
    protected static Connection dbLink = null;
    
    
    
     /**
     * EN : Method to the create a link with the database. <br>
     * FR : Method pour créer un lien avec la base de donnée.
     */
    public static void connectDB()
    {

        try {
            Properties info = new Properties();
            info.put("socksProxyHost", "188.165.156.203");
            info.put("socksProxyPort", "45786"); // If the proxy is not valid anymore, contact alexis.capon@edu.ece.fr to change it.
            Authenticator.setDefault(new ProxyClass("Selrepublicwars", "K4l7AnH"));
            info.put("user", user);
            info.put("password", passwd);
            
            dbLink = DriverManager.getConnection(url, info);
            
            if(debugMode) System.out.println("(Debug - ACube) Data base connected");
            
        } catch (SQLException ex) {
            
            if(debugMode) System.out.println("(Debug - ACube) Data base connection failed, pls contact alexis.capon@edu.ece.fr to change the proxy.");
            Logger.getLogger(SQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * EN : Method to disconnect to the database. <br>
     * FR : Method pour se déconnecter de la base de donnée.
     */
    public static void disconnectDB()
    {

        try {
            dbLink.close();
            
            if(debugMode) System.out.println("(Debug - ACube) Data base is disconnected");
            
        } catch (SQLException ex) {
            Logger.getLogger(SQLDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

    
