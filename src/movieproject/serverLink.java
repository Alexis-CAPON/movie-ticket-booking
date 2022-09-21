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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
 
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Alexis
 */
public class serverLink {
    
    /**
     * EN : Upload a movie image in the server. <br>
     * FR : Envoie l'image d'un film sur le server.
     * @param path The absolute path of the file in the computer
     * @param name The name of the movie
     */
    public static void uploadFile(String path, String name)
    {
        
        String server = "ftp.mtxserv.com";
        int port = 21;
        String user = "w_623043";
        String pass = "Frite1234";
 
        FTPClient ftpClient = new FTPClient();
        try {
 
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
 
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
 
            File firstLocalFile = new File(path);
 
            String firstRemoteFile ="/public_html/movieimage/"+ name +".png";
            InputStream inputStream = new FileInputStream(firstLocalFile);
            if(MovieProject.debugMode) System.out.println("(Debug - ACube) Upload starting..");
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done)
            {
                if(MovieProject.debugMode) System.out.println("(Debug - ACube) Upload finished.");
            }
            
        } catch (IOException ex) {
            if(MovieProject.debugMode) System.out.println("(Debug - ACube) An error as occured in the uploadFile method of the serverLink class.");
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
            }
        }
        
        
        
    }
    
    /**
     * EN : Upload an image in the server but here for the profile image of the client. <br>
     * FR : Envoie une image sur le server mais ici pour l'image de profile du client.
     * @param path The absolute path of the file in the computer
     * @param clientid The client id
     */
    public static void uploadUserFile(String path, int clientid)
    {
        
        String server = "ftp.mtxserv.com";
        int port = 21;
        String user = "w_623043";
        String pass = "Frite1234";
 
        FTPClient ftpClient = new FTPClient();
        try {
 
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
 
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
 
            File firstLocalFile = new File(path);
 
            String firstRemoteFile ="/public_html/profileimage/"+ clientid +".png";
            InputStream inputStream = new FileInputStream(firstLocalFile);
            if(MovieProject.debugMode) System.out.println("(Debug - ACube) Upload starting..");
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done)
            {
                if(MovieProject.debugMode) System.out.println("(Debug - ACube) Upload finished.");
            }
            
        } catch (IOException ex) {
            if(MovieProject.debugMode) System.out.println("(Debug - ACube) An error as occured in the uploadFile method of the serverLink class.");
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
            }
        }
        
        
        
    }
    
    
}
