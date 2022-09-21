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
 * EN : Containt a list of all movies in the database and methods on them. <br>
 * FR : Contient une liste de tout les films de la base de donnée et quelques fonctions.
 * @author Alexis
 */
public class Catalog {
    
    protected ArrayList<Movie> movie_list = new ArrayList<>();
    protected int numberofmovies;
    
    /**
     * EN : Constructor of the Catalog class . <br>
     * FR : Constructeur de la classe Catalog.
     */
    public Catalog()
    {
        numberofmovies = 0;
        updateCatalog();
    }

    
     /**
     * EN : Update the movie list according to the database. <br>
     * FR : Met à jour les cinéma en fonction de la base de donnée. 
     */
    public void updateCatalog()
    {
        
        String request = "SELECT id, name, imageURL, author, description, date, duration FROM movies;";
        String[] allgenderlist;
        try {
           
            Statement stat = SQLDatabase.dbLink.createStatement();
            ResultSet result = stat.executeQuery(request);
            while(result.next())
            {
                numberofmovies += 1;
                
                ArrayList<String> allgender = new ArrayList<>();
                String request2 = "SELECT genre.name FROM genre JOIN filmgenre ON genre.genreid = filmgenre.genreid WHERE filmid = '"+result.getInt("id")+"';";
                try{
                    Statement stat2 = SQLDatabase.dbLink.createStatement();
                    ResultSet result2 = stat2.executeQuery(request2);
                    
                    while(result2.next())
                    {
                        allgender.add(result2.getString("genre.name")); 
                    }
                    stat2.close();
                    result2.close();
                }catch(SQLException e2)
                {
                    if(MovieProject.debugMode) System.out.println("(Debug - ACube) An error as occured in the updateCatalog2 method of the Catalog class.");
                }
                allgenderlist = new String[allgender.size()];
                for(int i=0; i<allgender.size();i++)
                    allgenderlist[i] = allgender.get(i);
                
                
                // Constructor Movie(int id, String name, String image, String author, String description, Date date, double duration, String gender, String cinema)
                
                movie_list.add(new Movie( result.getInt("id"), result.getString("name"), result.getString("imageURL"), result.getString("author"), result.getString("description"),
                result.getDate("date"), result.getDouble("duration"), allgenderlist ));

            }
            stat.close();
            result.close();
            
        }catch(SQLException e)
        {
            if(MovieProject.debugMode) System.out.println("(Debug - ACube) An error as occured in the updateCatalog method of the Catalog class.");
        }
        
    }
   
    
    /**
     * EN : Return an list of movies where the gender is include in movie's gender. <br>
     * FR : Renvoie la liste des films ayant ce genre dans leurs genres. 
     * @param gender A gender (ex : Action, Horror)
     * @param movielist The list to sort.
     * @return All the movie of this gender.
     */
    
    public static ArrayList<Movie> getByGender(ArrayList<Movie> movielist, String gender)
    {
        gender = gender.toLowerCase();
        ArrayList<Movie> list = new ArrayList<>();
        
        String[] genderlist;
        
        if(gender.equals("all")){
            return movielist;
        }
        
        for(int i=0; i<movielist.size(); i++)
        {
            genderlist = movielist.get(i).getGender();
            for(int j=0; j<genderlist.length ; j++)
            {
                if(gender.equals(genderlist[j].toLowerCase())) list.add(movielist.get(i));
                
            }

        }

        return list;
    }
    
    

    /**
     * EN : Return an list of all movies that this cinema display currently. <br>
     * FR : Renvoie la liste des films que diffuse un cinema. 
     * @param cinemaName The name of the cinema wanted
     * @return ArrayList of all movies displayed in this cinema.
     */
    public static ArrayList<Movie> getInCinema(String cinemaName)
    {
        cinemaName = cinemaName.toLowerCase();
        ArrayList<Movie> list = new ArrayList<>();
        
        if(cinemaName.equals("all")) {
            return MovieProject.catalog.movie_list;
        }
        
        Cinema[] cinemalist;
        for(int i=0; i<MovieProject.catalog.movie_list.size(); i++)
        {
            cinemalist = Movie.getMovieCinema(MovieProject.catalog.movie_list.get(i));
            for(int j=0; j<cinemalist.length ; j++)
            {
                if(cinemaName.equals(cinemalist[j].getName().toLowerCase()))
                {
                    list.add(MovieProject.catalog.movie_list.get(i));
                }
                
            }

        }

        return list;
    }
    
    /**
     * EN : Return an list of all movies that this cinema display currently in this gender. <br>
     * FR : Renvoie la liste des films que diffuse un cinema de ce genre. 
     * @param cinemaName The name of the cinema wanted
     * @param gender The gender wanted
     * @return ArrayList of all movies displayed in this cinema.
     */
    public static ArrayList<Movie> getByGenreInCinema(String cinemaName, String gender){
        
        // Order movies by cinema in a first time;
        ArrayList<Movie> list = Catalog.getInCinema(cinemaName);
        
        // Order movies by gender;
        return Catalog.getByGender(list, gender);
    }
    
    /**
     * EN : Return an list of all movies that this cinema display currently. <br>
     * FR : Renvoie la liste des films que diffuse un cinema. 
     * @param cinema The desired cinema object.
     * @return ArrayList of all movies displayed in this cinema.
     */
    
    public static ArrayList<Movie> getInCinema(Cinema cinema)
    {
        String cinemaName = cinema.getName().toLowerCase();
        ArrayList<Movie> list = new ArrayList<>();
        
        Cinema[] cinemalist;
        for(int i=0; i<MovieProject.catalog.movie_list.size(); i++)
        {
            cinemalist = Movie.getMovieCinema(MovieProject.catalog.movie_list.get(i));
            for(int j=0; j<cinemalist.length ; j++)
            {
                if(cinemaName.equals(cinemalist[j].getName()))
                {
                    list.add(MovieProject.catalog.movie_list.get(i));
                }
                
            }

        }

        return list;
    }
    
}
