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
 * EN : Containt a list of all coupons in the database and methods on them. <br>
 * FR : Contient une liste de tout les coupons de la base de donnée et quelques fonctions.
 * @author Alexis
 */
public class AllCoupon {
    protected ArrayList<Coupon> coupon_list = new ArrayList<>();
    protected int numberofcoupon;
    
    
    /**
     * EN : Constructor of the AllCoupon class . <br>
     * FR : Constructeur de la classe AllCoupon.
     */
    public AllCoupon()
    {
        
        numberofcoupon = 0;
        updateCoupon();
    }
    
    
    
    /**
     * EN : Update the coupons according to the database. <br>
     * FR : Met à jour les coupons en fonction de la base de donnée.
     */
    public void updateCoupon()
    {
        String request = "SELECT id, name, reduction FROM coupons;";
        
        try {
           
            Statement stat = SQLDatabase.dbLink.createStatement();
            ResultSet result = stat.executeQuery(request);
            
            while(result.next())
            {
                numberofcoupon+= 1;
                
                // Constructor Coupon(int id, String name, int reduction)
                
                coupon_list.add(new Coupon( result.getInt("id"), result.getString("name"), result.getInt("reduction") ));
                

            }
            
            stat.close();
            result.close();
            
        }catch(SQLException e)
        {
            if(MovieProject.debugMode) System.out.println("(Debug - ACube) An error as occured in the updateCoupon method of the AllCoupon class.");
        }
        
    }
    
    
    
    
    
    
}
