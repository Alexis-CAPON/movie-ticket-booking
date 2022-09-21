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

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


/**
 *
 * @author Alexis
 */


public class inputValidation {
    
    
    /**
     * EN : Check if the email address is valid. <br>
     * FR : Verifie si l'email est valide.
     * @param email The email address to check
     * @return true of false depending of the result.
     */
    public static boolean isValidEmail(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    } 
    
    
    /**
     * EN : Check if the string is not empty and don't have space in it. <br>
     * FR : Verifie si le string n'est pas vide ou n'a pas d'espace.
     * @param message The string to check
     * @return true of false depending of the result.
     */
    public static boolean isNotEmptyOrSpace(String message) {
        
        // Check for empty space or nothing
        boolean result = true;

        if (message.trim().isEmpty())
        {
            result = false;
        }
        
        return result;
    }
    
}
