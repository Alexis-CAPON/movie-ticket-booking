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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * EN : Containt a list of all theaters in the database and methods on them. <br>
 * FR : Contient une liste de tout les cinema de la base de donnée et quelques fonctions.
 * @author Alexis
 */
public class AllCinema {
    
    
    protected ArrayList<Cinema> cinema_list = new ArrayList<>();
    protected int numberofcinema;
    
    /**
     * EN : Constructor of the AllCinema class . <br>
     * FR : Constructeur de la classe AllCinema.
     */
    
    public AllCinema()
    {
        
        numberofcinema = 0;
        updateCinema();
    }
   
    
    
    /**
     * EN : Update the theaters according to the database. <br>
     * FR : Met à jour les cinemas en fonction de la base de donnée.
     */
    public void updateCinema()
    {
        String request = "SELECT id, name, address, image, childrenPrice, adultPrice, seniorPrice FROM cinema;";
        
        try {
           
            Statement stat = SQLDatabase.dbLink.createStatement();
            ResultSet result = stat.executeQuery(request);
            
            while(result.next())
            {
                numberofcinema += 1;
                
                // Constructor Cinema(int id, String name, String address)
                cinema_list.add(new Cinema( result.getInt("id"), result.getString("name"), result.getString("address"), result.getString("image"), result.getDouble("childrenPrice"), result.getDouble("adultPrice"), result.getDouble("seniorPrice") ));
                

            }
            
            stat.close();
            result.close();
            
        }catch(SQLException e)
        {
            if(MovieProject.debugMode) System.out.println("(Debug - ACube) An error as occured in the updateCinema method of the AllCinema class.");
        }
        
    }
    
    
    
}
