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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexis
 */
public class Records {
    
    private int ticketID;
    private int clientID;
    private Date date;
    private String description;
    private double totalPrice;
    
    
    /**
     * EN : Constructor of the Record class. <br>
     * FR : Constructeur de la classe Record.
     * 
     * @param ticketID Ticket ID according to the database.
     * @param  clientID Client ID related to the ticket
     * @param date The date when the ticket was created
     * @param description Difference between Movie ticket and Premium upgrade, check in PaymentAccepted.java
     * @param totalPrice The total payed
     */
    public Records(int ticketID, int clientID, Date date, String description, double totalPrice)
    {
        this.ticketID = ticketID;
        this.clientID = clientID;
        this.date = date;
        this.description = description;
        this.totalPrice = totalPrice;
    }
    
    
    /**
     * EN : Add a records to the database. <br>
     * FR : Ajoute un records à la base de donnée.
     * 
     * @param  clientID Client ID related to the ticket
     * @param date The date when the ticket was created
     * @param description Difference between Movie ticket and Premium upgrade, check in PaymentAccepted.java
     * @param totalPrice The total payed
     */
    public static void addToRecords(int clientID, Date date, String description, double totalPrice)
    {

        String request = "INSERT INTO records (clientID, date, description, totalPrice) VALUES ('"+clientID+"', '"+date+"', '"+description+"', '"+totalPrice+"');";
        
            try {
                
                Statement stat = SQLDatabase.dbLink.createStatement();
                int result = stat.executeUpdate(request);
                
                stat.close();
                if(MovieProject.debugMode) System.out.println("(Debug - ACube) Record added succesfully !");
            }
            catch(SQLException e){
                if(MovieProject.debugMode) System.out.println("(Debug - ACube) An error as occured when the request from Records class was send.");
            }
        
        
    }
    
    /**
     * EN : Create a file with all records of the database in it. <br>
     * FR : Créer un fichier avec tout les records de la bdd à l'intérieur.
     * 
     */
    public static void getAllRecords()
    {
        // Creating
        
        File file = new File("data/allrecords.txt");
        if(file.isFile()) file.delete();
        try {
            file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Records.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Writing
        try {
            FileWriter fileWriter = new FileWriter("data/allrecords.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print("ACUBE - Database - Records \n");
            printWriter.print("All records are printed here.\n\n\n\n");
            printWriter.print("Records : \n\n");
            
            String request = "SELECT tickedID, clientID, date, description, totalPrice FROM records;";
            
            try {

                Statement stat = SQLDatabase.dbLink.createStatement();
                ResultSet result = stat.executeQuery(request);

                while(result.next())
                {
                   
                    printWriter.print("Ticket ID : "+result.getInt("tickedID")+"\n");
                    printWriter.print("Client ID : "+result.getInt("clientID")+"\n");
                    printWriter.print("Date : "+result.getDate("date").toString()+"\n");
                    printWriter.print("Description : "+result.getString("description")+"\n");
                    printWriter.print("Total Price : "+result.getDouble("totalPrice")+"\n");
                    
                    printWriter.print("--------------------------------------------------------------------------- \n\n");
                    

                }

                stat.close();
                result.close();
                
                
            }catch(SQLException e2)
            {
                if(MovieProject.debugMode) System.out.println("(Debug - ACube) An error as occured in the updateCinema method of the AllCinema class.");
            }

            printWriter.close();
                
        } catch (IOException e) {
            System.out.println("Error");
        }
        
        
        
    }
    
    
    
}
