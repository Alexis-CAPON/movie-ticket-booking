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
 * @author Alexis
 */
public class Cinema {
    
    private int id;
    private String name;
    private String address;
    
    private String imageURL;
    
    private double childrenPrice;
    private double adultPrice;
    private double seniorPrice;
    
    /**
     * EN : Constructor of the Cinema class. <br>
     * FR : Constructeur de la classe Cinema.
     * 
     * @param id Theather id according to the database
     * @param name Name of the theater
     * @param address Theather address
     * @param image URL image used in CinemaPanel.java
     * @param childrenPrice Price for childrens in this theater
     * @param adultPrice Price for adults in this theater
     * @param seniorPrice Price for seniors in this theater
     * 
     */
    public Cinema(int id, String name, String address, String image, double childrenPrice, double adultPrice, double seniorPrice)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        imageURL = image;
        this.childrenPrice = childrenPrice;
        this.adultPrice = adultPrice;
        this.seniorPrice = seniorPrice;
        
    }
    
    public int getId()
    {
        return id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public String getImage()
    {
        return imageURL;
    }
    
    public double getChildrenPrice()
    {
        return childrenPrice;
    }
    
    public double getAdultPrice()
    {
        return adultPrice;
    }
    
    public double getSeniorPrice()
    {
        return seniorPrice;
    }
    
    /**
     * EN : Returns the cinema (object) according to its id. <br>
     * FR : Renvoit le cinema (objet) en fonction de son id.
     * 
     * @param id The cinema id of the wanted cinema.
     * @return The desired cinema object. 
     */
    public static Cinema getCinema(int id)
    {
        for(int i=0; i<MovieProject.allcinema.cinema_list.size(); i++)
        {
            if (MovieProject.allcinema.cinema_list.get(i).getId()== id) return MovieProject.allcinema.cinema_list.get(i);
        }
        
        return null;
    }
    
    /**
     * EN : Returns the cinema (object) according to its name. <br>
     * FR : Renvoit le cinema (objet) en fonction de son nom.
     * 
     * @param name The cinema name of the wanted cinema.
     * @return The desired cinema object. 
     */
    public static Cinema getCinema(String name)
    {
        for(int i=0; i<MovieProject.allcinema.cinema_list.size(); i++)
        {
            if (MovieProject.allcinema.cinema_list.get(i).getName().equals(name)) return MovieProject.allcinema.cinema_list.get(i);
        }
        
        return null;
    }
    
}
