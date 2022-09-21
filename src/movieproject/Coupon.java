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

/**
 *
 * @author Alexis
 */
public class Coupon {
    
    private int id;
    private String name;
    private int reductionPercent;
    
    /**
     * EN : Constructor of the Coupon class. <br>
     * FR : Constructeur de la classe Coupon.
     * 
     * @param id Coupon id according to the database
     * @param name Name of the coupon (ex : ACUBE - Student)
     * @param reduction Reduction of the coupon in <b>PERCENTAGE</b> !
     *
     * 
     */
    public Coupon(int id, String name, int reduction)
    {
        this.id = id;
        this.name = name;
        this.reductionPercent = reduction;
        
    }
    
    public int getID()
    {
        return id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public int getReduction()
    {  
        return reductionPercent;
    }
    
    
    
    
}
