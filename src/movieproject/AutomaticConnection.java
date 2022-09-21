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
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author Aksel
 */
public class AutomaticConnection {
    
    public static void readCookie() {
        
        try {
            File file = new File("data/cookie.txt");
            Scanner reader = new Scanner(file);
            
            String email = reader.nextLine();
            String password = reader.nextLine();
            
            ClientInfo.logIn(email, password);
            
            reader.close();
          } catch (FileNotFoundException e) {
          }
    }
    
    
    public static void writeCookie(String email, String password) {
        
        try{

            PrintWriter writer = new PrintWriter("data/cookie.txt");
            writer.println(email);
            writer.println(password);
            writer.close();
            
            
        } catch (FileNotFoundException e) {
        }  
    }
    
    
    public static void deleteCookie() {
        new File("data/cookie.txt").delete();
    }
    
}
