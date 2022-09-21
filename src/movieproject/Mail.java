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

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * @author Alexis
 */
public class Mail {
    
    
    /**
     * EN : Retrieves the open session for sending an email. <br>
     * FR : Recupère la session ouverte pour l'envoie d'un mail.
     * @return The session openned
     */
    public static Session getMailSession()
    {
        
        Properties properties = new Properties();
        
        
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        String email = "acubeserver@gmail.com";
        String password = "Kgy16a2ylkbpyAKghs5";
        
        Session session = Session.getInstance(properties, new Authenticator() {
            
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email,password);
            }
 
        });
        
        
        return session;
        
    }
    
    /**
     * EN : Send a welcome email. <br>
     * FR : Envoie un mail de bienvenue.
     * 
     * @param emailreceiver Email address of the receiver
     * @param namereceiver Name of the receiver
     */
    public static void sendWelcomeEmail(String emailreceiver, String namereceiver)
    {
        
        Session newsession = Mail.getMailSession();
        
        
        try{
            
            
            Message message = new MimeMessage(newsession);
        
            message.setFrom(new InternetAddress("acubeserver@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailreceiver));
            message.setSubject("Welcome "+namereceiver+" ! Thanks for joining ACube !");
            String html = "<html>\n" +
                          "<head>\n" +
                          "<title>Welcome to Acube</title>\n" +
                          "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                          "</head>\n" +
                          "<body style=\"background-color:#FFFFFF; margin-top: 0px; margin-bottom: 0px; margin-left: 0px; margin-right: 0px;\">\n" +
                          "<!-- Save for Web Slices (Sans titre-1.psd) -->\n" +
                          "<img src=\"https://javacinema.mtxserv.com/welcomeemail.jpg\" width=\"800\" height=\"964\" alt=\"\">\n" +
                          "<!-- End Save for Web Slices -->\n" +
                          "</body>\n" +
                          "</html>";
            message.setContent(html, "text/html");
            
            
            Transport.send(message);
        
        } catch( MessagingException e) {
            
            if(MovieProject.debugMode) System.out.println("(Debug - ACube) An error as occured in the Mail class in the sendWelcomeEmail method !");
            
        }
        
        
        
    }
}
