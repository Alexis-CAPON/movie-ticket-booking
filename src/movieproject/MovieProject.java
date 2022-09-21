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

import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

/**
 * <b>Main File of the project - Where everything start</b>
 * 
 * ACUBE Team member :
 *  - Alexis CAPON
 *  - Adam EL BOUCHIKHI
 *  - Aksel BEYKARA
 * 
 * @author ACube TEAM
 * @version 1.0
 */
public class MovieProject {

    /// Activate debug mode for developpement;
    protected static boolean debugMode = false;
    
    /// User Info
    // See ClientInfo
    
    /// Register Popup
    protected static SignUp popup;
    protected static SignUp2 popup2;
    protected static App mainapp;
    protected static SignIn signin;
    
    /// Booking Popup
    protected static Booking bookpop;
    
    /// Catalog
    protected static Catalog catalog;
    
    /// Cinema
    protected static AllCinema allcinema;
    
    /// Diffusion
    protected static AllDiffusion alldiffusion;
    
    /// Coupon
    protected static AllCoupon allcoupon;
    
    /**
     * EN : Main. <br>
     * FR : Main.
     * 
     * @param args Args you want for the launch
     * 
     */
    public static void main(String[] args) {
        //Only for testing
        //ClientInfo.isAdmin = true;
        
        //Connect to the database
        SQLDatabase.connectDB();
        
        // AutomaticConnection
        AutomaticConnection.readCookie();
        
        // New instance of our loadingscreen
        LoadingScreen loading = new LoadingScreen();
        loading.setVisible(true);
        loading.setIconImage(new ImageIcon("src/movieproject/resources/logo.png").getImage());
        
        // Load all the movies, etc..
        catalog = new Catalog();
        
        // Load all cinema
        allcinema = new AllCinema();
        
        // Load all diffusion
        alldiffusion = new AllDiffusion();
        
        // Load all coupon
        allcoupon = new AllCoupon();
        
        
        try {
            //Thread.sleep(2500);
            loading.setVisible(false);
            
            try {
                // Force UI to be the beautiful windows UI
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                
                
                //New instance of our main app
                mainapp = new App();
                mainapp.setVisible(true);
                mainapp.setIconImage(new ImageIcon("src/movieproject/resources/logo.png").getImage());
            } catch(Exception e) {
                e.printStackTrace();
                if(debugMode) System.out.println("(Debug - ACube) Main App Error");
                SQLDatabase.disconnectDB();
                System.exit(0);
            }
            
            
        } catch(Exception e) {
            e.printStackTrace();
            if(debugMode) System.out.println("(Debug - ACube) Loading Screen Error");
            SQLDatabase.disconnectDB();
            System.exit(0);
        }
        
    }
   
}
