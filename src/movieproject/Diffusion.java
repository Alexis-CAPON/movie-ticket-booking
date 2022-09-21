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
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexis
 */
public class Diffusion {
    
    private int id;
    private Cinema cinema;
    private Movie movie;
    
    private Date day;
    private Time hours;
    private java.util.Date date;
    
    /**
     * EN : Constructor of the Diffusion class. <br>
     * FR : Constructeur de la classe Diffusion.
     * 
     * @param id Diffusion id according to the database.
     * @param cinema Cinema where the diffusion is display.
     * @param movie Movie of the diffusion.
     * @param day The day when the diffusion is display.
     * @param hours The time in the day of the diffusion.
     * 
     */
    public Diffusion(int id, Cinema cinema, Movie movie, Date day, Time hours)
    {
        
        this.id = id;
        this.cinema = cinema;
        this.movie = movie;
        this.day = day;
        this.hours = hours;
        
        // Convert the day + hours in a real date.
        String datestring = day.toString().concat(" " + hours.toString());
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datestring);
        } catch (ParseException ex) {
            Logger.getLogger(Diffusion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public int getID()
    {
        return id;
    }
    
    public Cinema getCinema()
    {
        return cinema;
    }
    
    public Movie getMovie()
    {
        return movie;
    }
    
    public Date getDay()
    {
        return day;
    }
    
    public Time getHours()
    {
        return hours;
    }
    
    public java.util.Date getDate()
    {
        return date;
    }
}
