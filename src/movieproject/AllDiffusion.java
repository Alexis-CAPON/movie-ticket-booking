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
import java.util.ArrayList;

/**
 * EN : Containt a list of all diffusion in the database and methods on them. <br>
 * FR : Contient une liste de toutes les diffusions de la base de donnée et quelques fonctions.
 * @author Alexis
 */
public class AllDiffusion {
    
    protected ArrayList<Diffusion> diffusion_list = new ArrayList<>();
    protected int numberofdiffusion;
    
    /**
     * EN : Constructor of the AllDiffusion class . <br>
     * FR : Constructeur de la classe AllDiffusion.
     */
    public AllDiffusion()
    {
        
        numberofdiffusion = 0;
        updateDiffusion();
    }
    
    
    
    /**
     * EN : Update the diffusion according to the database. <br>
     * FR : Met à jour les diffusions en fonction de la base de donnée.
     */
    public void updateDiffusion()
    {
        String request = "SELECT id, movieid, cinemaid, day, hours FROM broadcast;";
        
        try {
           
            Statement stat = SQLDatabase.dbLink.createStatement();
            ResultSet result = stat.executeQuery(request);
            
            while(result.next())
            {
                numberofdiffusion += 1;
                
                // Constructor Diffusion(int id, Cinema cinema, Movie movie, Date day, Time hours)
                
                diffusion_list.add(new Diffusion( result.getInt("id"), Cinema.getCinema(result.getInt("cinemaid")), Movie.getMovie(result.getInt("movieid")), result.getDate("day"), result.getTime("hours") ));
                

            }
            
            stat.close();
            result.close();
            
        }catch(SQLException e)
        {
            if(MovieProject.debugMode) System.out.println("(Debug - ACube) An error as occured in the updateDiffusion method of the AllDiffusion class.");
        }
        
    }
    
    
    
    
    
    /**
     * EN : Return all broadcasts of a cinema. <br>
     * FR : Renvoit toute les diffusions d'un cinema.
     * @param name The name of the cinema
     * @return A arraylist of Diffusion object
     */
    public static ArrayList<Diffusion> getCinemaDiffusion(String name)
    {
        ArrayList<Diffusion> list = new ArrayList<>();
        
        for(int i = 0; i<MovieProject.alldiffusion.diffusion_list.size(); i++)
        {
            if (name.equals(MovieProject.alldiffusion.diffusion_list.get(i).getCinema().getName()))  
            {
                list.add(MovieProject.alldiffusion.diffusion_list.get(i));
            }
            
        }
        
        
        return list;
    }
    
     /**
     * EN : Returns all broadcasts of a movie. <br>
     * FR : Retourne toute les diffusions d'un film.
     * @param movie Movie object wanted.
     * @return An arraylist of all the diffusion of this movie.
     */
    public static ArrayList<Diffusion> getAllDiffusion(Movie movie)
    {
        
        ArrayList<Diffusion> list = new ArrayList<>();
                
        for(int i = 0; i<MovieProject.alldiffusion.diffusion_list.size(); i++)
        {
            
            if (movie.getName().equals(MovieProject.alldiffusion.diffusion_list.get(i).getMovie().getName()))  
            {
                list.add(MovieProject.alldiffusion.diffusion_list.get(i));
            }
            
        }
        
        
        return list;
 
    }
    
    
    
    /**
     * EN : Returns all the next screenings of a film in a specific cinema after the current date/hour. <br>
     * FR : Retourne toute les prochaines diffusion d'un film dans un cinema précis.
     * @param movie Movie object wanted.
     * @param cinema Cinema object wanted.
     * @return An arraylist of all the diffusion of this movie in this cinema after the current date/hours.
     */
    public static ArrayList<Diffusion> getNextDiffusion(Movie movie, Cinema cinema)
    {
        ArrayList<Diffusion> list = new ArrayList<>();
        
        ArrayList<Diffusion> cinemaDiffusionList = AllDiffusion.getCinemaDiffusion(cinema.getName());
       
        java.util.Date date = new Date(System.currentTimeMillis());
        
        for(int i = 0; i<cinemaDiffusionList.size(); i++)
        {
            if (cinemaDiffusionList.get(i).getMovie().equals(movie))
            {
                if(cinemaDiffusionList.get(i).getDate().compareTo(date) >= 0)
                {
                    list.add(cinemaDiffusionList.get(i));
                } 
            }
             
        }
        
        
        
        
        return list;
    }
}
