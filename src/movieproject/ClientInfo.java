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

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * EN : All user information as well as functions for connection and account creation. <br>
 * FR : Toute les informations de l'utilisateur ainsi que des fonctions pour la connection et la création de compte.
 * @author Alexis
 */
public class ClientInfo {
    
    
    protected static boolean isAdmin = false;
    protected static boolean isConnected = false;
    protected static String name = null;
    protected static int ID = -1;
    protected static boolean isPremium = false;
    protected static String imageProfile = null;
    protected static String birthday = null;
    protected static String email = null;
    
    /**
     * EN : Connect the user and retrieve all his info from the database. <br>
     * FR : Connecte l'utilisateur et récupère toutes ses informations de la base de données.
     * 
     * @param email Email address of the user
     * @param password Password of the user
     * 
     */
    public static void logIn(String email, String password)
    {
        
        String request = "SELECT client_id, firstname, isAdmin, premiumLevel, urlImageProfile, birthday  FROM customerAccount WHERE email = '"+email+"' AND password = '"+password+"';";
        
        try {
           
            Statement stat = SQLDatabase.dbLink.createStatement();
            ResultSet result = stat.executeQuery(request);
            result.next();
            
            ClientInfo.ID = result.getInt("client_id");
            ClientInfo.name = result.getString("firstname");
            ClientInfo.isAdmin = result.getBoolean("isAdmin");
            ClientInfo.isPremium = result.getBoolean("premiumLevel");
            ClientInfo.imageProfile = result.getString("urlImageProfile");
            DateFormat dateFormat = new SimpleDateFormat("dd MMMMM yyyy");
            ClientInfo.birthday = dateFormat.format(result.getDate("birthday"));
            ClientInfo.email = email;
            
            
            ClientInfo.isConnected = true;
            
            stat.close();
            result.close();
            
            if(MovieProject.debugMode) System.out.println("(Debug - ACube) Client : "+ClientInfo.name+" connected !");
        
        }
        catch(SQLException e){
            if(MovieProject.debugMode) System.out.println("(Debug - ACube) An error as occured in the logIn method.");
        }
        
        
        
        
    }
    
    /**
     * EN : Send a welcome email to the address mail and register the user in the database. <br>
     * FR : Envoie un message de bienvenue à l'adresse mail et enregistre l'utilisateur et ses infos dans la base de donnée.
     * 
     * @param firstname First name of the user
     * @param lastname Last name of the user
     * @param email Email address of the user
     * @param password Password of the user
     * @param birthday Birthday of the user
     * 
     */
    public static void registerAccount(String firstname, String lastname, String email, String password, Date birthday)
    {
        String request = "INSERT INTO customerAccount (email, firstname, lastname, password, birthday, isAdmin) VALUES ('"+email+"','"+firstname+"','"+lastname+"','"+password+"','"+birthday+"','0');";
        
        // Send email
        Mail.sendWelcomeEmail(email, firstname);
        
        // SQL Stuff
        try{
            Statement stat = SQLDatabase.dbLink.createStatement();
            int executeUpdate = stat.executeUpdate(request);
            
            stat.close();
            
            if(MovieProject.debugMode) System.out.println("(Debug - ACube) Account saved in the database !");
            
        }
        catch(SQLException e)
        {
            if(MovieProject.debugMode) System.out.println("(Debug - ACube) An error as occured in the registerAccount method.");
        }
        
    }
    
    
}
