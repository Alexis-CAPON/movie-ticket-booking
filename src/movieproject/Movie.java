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

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Alexis
 */
public class Movie {
    
    private int id;
    private String name;
    private String imageURL;
    
    private String author;
    private String[] gender;
    private Date date;
    private double duration;
    private String description;
    private BufferedImage imageBuffered;
    
    /**
     * EN : Constructor of the Movie class. <br>
     * FR : Constructeur de la classe Movie.
     * 
     * @param id Movie id according to the database.
     * @param name Movie name
     * @param image URL image that will be display in MoviesPanel and OneMoviePanel.
     * @param author Movie author
     * @param description Short description of the movie
     * @param date Release date of the movie
     * @param duration Duration time in minutes of the movie
     * @param gender List of the movie genre.
     * 
     */
    public Movie(int id, String name, String image, String author, String description, Date date, double duration, String[] gender)
    {
        this.id = id;
        this.name = name;
        this.imageURL = image;
        this.author = author;
        this.description = description;
        this.date = date;
        this.duration = duration;
        this.gender = gender;
        
        
        try{

            URL url = new URL(this.imageURL);
            BufferedImage image2 = ImageIO.read(url);

            this.imageBuffered = resize(image2, 220 ,400);
        }catch(Exception e){
        }
        
        
    }
    
    public int getID()
    {
        return id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getImage()
    {
        return imageURL;
    }
    
    public String getAuthor()
    {
        return author;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public Date getReleaseDate()
    {
        return date;
    }
    
    public double getDuration()
    {
        return duration;
    }
    
    public String[] getGender()
    {
        return gender;
    }
    
    public BufferedImage getImageBuffered()
    {
        return this.imageBuffered;
    }
    
    private BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
        
    }
    
    /**
     * EN : Return a movie (object) based on its id. <br>
     * FR : Renvoie un film (objet) en fonction de son id.
     * 
     * @param id Movie id according to the database.
     * @return Movie object wanted.
     */
    public static Movie getMovie(int id)
    {
        for (int i=0;i<MovieProject.catalog.movie_list.size();i++)
        {
            if(MovieProject.catalog.movie_list.get(i).getID() == id) return MovieProject.catalog.movie_list.get(i);
        }
        return null;
    }
    
    /**
     * EN : Return a movie (object) based on its name. <br>
     * FR : Renvoie un film (objet) en fonction de son name.
     * 
     * @param name Movie name of the wanted movie object.
     * @return Movie object wanted.
     */
    public static Movie getMovie(String name)
    {
        for (int i=0;i<MovieProject.catalog.movie_list.size();i++)
        {
            if(MovieProject.catalog.movie_list.get(i).getName().equals(name)) return MovieProject.catalog.movie_list.get(i);
        }
        return null;
    }

    /**
     * EN : Return a list of Cinema of all cinema where the movie is shown. <br>
     * FR : Renvoie une liste de tout les cinéma où le film est diffusé.
     * 
     * @param movie Movie wanted
     * @return All cinema that display this movie.
     */
    public static Cinema[] getMovieCinema(Movie movie)
    {
        ArrayList<Cinema> cinema = new ArrayList<>();
        ArrayList<Diffusion> alldiffusion = AllDiffusion.getAllDiffusion(movie);
        
        
        for(int i=0;i<alldiffusion.size();i++)
        {
            boolean isInside = false;
            for(int j=0; j< cinema.size() ; j++)
            {
                if(alldiffusion.get(i).getCinema().equals(cinema.get(j)))
                    isInside = true;
                
            }
            
            if(!isInside) cinema.add(alldiffusion.get(i).getCinema());

        }
        Cinema[] cinema_list = new Cinema[cinema.size()];
        for(int i = 0; i< cinema.size(); i++)
        {
            cinema_list[i] = cinema.get(i);
        }
        
        return cinema_list;
    }
    
    
    public static String[] getAllGenre()
    {
        
        String request = "SELECT genreid, name FROM genre ORDER BY genreid DESC;";
        String[] list = null;
        try {
           
            Statement stat = SQLDatabase.dbLink.createStatement();
            ResultSet result = stat.executeQuery(request);
            result.next();
            list = new String[result.getInt("genreid") + 1];
            int id = result.getInt("genreid");
            list[id] = result.getString("name");
            while(result.next())
            {
                id-=1; 
                list[id] = result.getString("name");  
            }
            list[0] = "All";
            stat.close();
            result.close();
        
        } catch (SQLException e) {
            
        }
        
        return list;
    }
}
