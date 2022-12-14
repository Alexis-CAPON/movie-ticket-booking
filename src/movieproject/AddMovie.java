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

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFileChooser;


/**
 *
 * @author Alexis
 */
public class AddMovie extends javax.swing.JFrame {

    private Point mouseClickPoint;
    
    
    public AddMovie() {
        initComponents();
        draggingFunction();
        setBackground(new Color(0,0,0,0));
    }
    
    
    private void draggingFunction() {
        
    addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            mouseClickPoint = e.getPoint();
        }

    });
    addMouseMotionListener(new MouseAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {
            Point newPoint = e.getLocationOnScreen();
            newPoint.translate(-mouseClickPoint.x, -mouseClickPoint.y);
            setLocation(newPoint);
        }
    });
    }

    
    private String selectedFilePath = null; 
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameField = new javax.swing.JTextField();
        authorField = new javax.swing.JTextField();
        descriptionField = new javax.swing.JTextField();
        durationField = new javax.swing.JTextField();
        genderField = new javax.swing.JTextField();
        imageField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        warn1 = new javax.swing.JLabel();
        warn2 = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1080, 720));
        setMinimumSize(new java.awt.Dimension(1080, 720));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1080, 720));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nameField.setFont(new java.awt.Font("Comfortaa", 0, 18)); // NOI18N
        nameField.setForeground(new java.awt.Color(232, 232, 232));
        nameField.setBorder(null);
        nameField.setCaretColor(new java.awt.Color(232, 232, 232));
        nameField.setOpaque(false);
        nameField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                nameFieldFocusLost(evt);
            }
        });
        getContentPane().add(nameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 291, 320, 45));

        authorField.setFont(new java.awt.Font("Comfortaa", 0, 18)); // NOI18N
        authorField.setForeground(new java.awt.Color(232, 232, 232));
        authorField.setBorder(null);
        authorField.setCaretColor(new java.awt.Color(232, 232, 232));
        authorField.setOpaque(false);
        authorField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                authorFieldFocusLost(evt);
            }
        });
        getContentPane().add(authorField, new org.netbeans.lib.awtextra.AbsoluteConstraints(128, 405, 320, 44));

        descriptionField.setFont(new java.awt.Font("Comfortaa", 0, 18)); // NOI18N
        descriptionField.setForeground(new java.awt.Color(232, 232, 232));
        descriptionField.setBorder(null);
        descriptionField.setCaretColor(new java.awt.Color(232, 232, 232));
        descriptionField.setOpaque(false);
        descriptionField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                descriptionFieldFocusLost(evt);
            }
        });
        getContentPane().add(descriptionField, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 518, 320, 44));

        durationField.setFont(new java.awt.Font("Comfortaa", 0, 18)); // NOI18N
        durationField.setForeground(new java.awt.Color(232, 232, 232));
        durationField.setBorder(null);
        durationField.setCaretColor(new java.awt.Color(232, 232, 232));
        durationField.setOpaque(false);
        durationField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                durationFieldFocusLost(evt);
            }
        });
        getContentPane().add(durationField, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 291, 320, 44));

        genderField.setFont(new java.awt.Font("Comfortaa", 0, 18)); // NOI18N
        genderField.setForeground(new java.awt.Color(232, 232, 232));
        genderField.setBorder(null);
        genderField.setCaretColor(new java.awt.Color(232, 232, 232));
        genderField.setOpaque(false);
        genderField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                genderFieldFocusLost(evt);
            }
        });
        getContentPane().add(genderField, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 404, 320, 44));

        imageField.setFont(new java.awt.Font("Comfortaa", 0, 18)); // NOI18N
        imageField.setForeground(new java.awt.Color(232, 232, 232));
        imageField.setBorder(null);
        imageField.setCaretColor(new java.awt.Color(232, 232, 232));
        imageField.setOpaque(false);
        getContentPane().add(imageField, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 518, 320, 44));

        jButton1.setText("Upload");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 520, -1, -1));

        addButton.setBorderPainted(false);
        addButton.setContentAreaFilled(false);
        addButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addButton.setOpaque(false);
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addButtonMouseClicked(evt);
            }
        });
        getContentPane().add(addButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 610, 400, 80));
        addButton.setFocusPainted(false);

        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
        });
        getContentPane().add(closeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1023, 20, 30, 30));
        closeButton.setFocusPainted(false);

        jDateChooser1.setFont(new java.awt.Font("Comfortaa", 0, 24)); // NOI18N
        jDateChooser1.setMaxSelectableDate(new java.util.Date(253370764902000L));
        jDateChooser1.setMaximumSize(new java.awt.Dimension(570, 40));
        jDateChooser1.setMinimumSize(new java.awt.Dimension(570, 40));
        jDateChooser1.setPreferredSize(new java.awt.Dimension(570, 40));
        getContentPane().add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 639, 340, 41));

        warn1.setFont(new java.awt.Font("Comfortaa", 0, 18)); // NOI18N
        warn1.setForeground(new java.awt.Color(255, 0, 0));
        warn1.setText("Warning : .png file only !");
        getContentPane().add(warn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 570, 310, 30));
        warn1.setVisible(false);

        warn2.setFont(new java.awt.Font("Comfortaa", 0, 18)); // NOI18N
        warn2.setForeground(new java.awt.Color(255, 0, 0));
        warn2.setText("* You must fill all the fields.");
        getContentPane().add(warn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 690, 230, 30));
        warn2.setVisible(false);

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movieproject/resources/addmovie.png"))); // NOI18N
        background.setMaximumSize(new java.awt.Dimension(1080, 720));
        background.setMinimumSize(new java.awt.Dimension(1080, 720));
        background.setPreferredSize(new java.awt.Dimension(1080, 720));
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 720));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_closeButtonMouseClicked

    private void nameFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_nameFieldFocusLost

    private void authorFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_authorFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_authorFieldFocusLost

    private void descriptionFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_descriptionFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_descriptionFieldFocusLost

    private void durationFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_durationFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_durationFieldFocusLost

    private void genderFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_genderFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_genderFieldFocusLost

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        selectedFilePath = null;
        JFileChooser choice = new JFileChooser();
        int callback=choice.showDialog(this,"Upload the selected file");
        if(callback==JFileChooser.APPROVE_OPTION)
        {
            selectedFilePath = choice.getSelectedFile().getAbsolutePath();
            imageField.setText(selectedFilePath);
        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void addButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addButtonMouseClicked
        // TODO add your handling code here:
        
        boolean isValid = true;
        
        if(!inputValidation.isNotEmptyOrSpace(nameField.getText()))
        {
            isValid = false;
        }
        
        if(!inputValidation.isNotEmptyOrSpace(authorField.getText()))
        {
            isValid = false;
        }
        
        if(!inputValidation.isNotEmptyOrSpace(descriptionField.getText()))
        {
            isValid = false;
        }
        
        if(!inputValidation.isNotEmptyOrSpace(durationField.getText()))
        {
            isValid = false;
        }
        
        if(!inputValidation.isNotEmptyOrSpace(genderField.getText()))
        {
            isValid = false;
        }
        
        if(!inputValidation.isNotEmptyOrSpace(imageField.getText()))
        {
            isValid = false;
        }
        
        if(jDateChooser1.getDate()==null)
        {
            isValid = false;
        }
        
        if(isValid)
        {
            warn2.setVisible(false);
            
            // Upload of the file
            
            if(selectedFilePath != null)
            {
                String test = "  ";
                test.replace(" ", "");
                serverLink.uploadFile(selectedFilePath, nameField.getText().toLowerCase().trim());
                selectedFilePath = "https://javacinema.mtxserv.com/movieimage/"+nameField.getText().toLowerCase().replaceAll(" ", "") + ".png";
            }
            else
            {
                
                selectedFilePath = imageField.getText();
            }
            
            // Save the movie in the database
            Date date = new java.sql.Date(jDateChooser1.getDate().getTime());
            
            String request = "INSERT INTO movies (name, imageURL, author, description, date, duration) VALUES ('"
                    + nameField.getText().replace("'","''") + "','" 
                    + selectedFilePath + "','" 
                    + authorField.getText().replace("'","''") + "','" 
                    + descriptionField.getText().replace("'","''") + "','"
                    + date + "','"
                    + Double.parseDouble(durationField.getText()) + 
                    "');";
            
            try {
                
                Statement stat = SQLDatabase.dbLink.createStatement();
                int result = stat.executeUpdate(request);
                
                stat.close();
                if(MovieProject.debugMode) System.out.println("(Debug - ACube) Movie added succesfully !");
            }
            catch(SQLException e){
                if(MovieProject.debugMode) System.out.println("(Debug - ACube) An error as occured when the request from AddMovie class was send.");
            }
            
            // Add genres
            
            // Get movieID
            int movieID = 0;
            request = "SELECT id FROM movies WHERE name = '"+nameField.getText().replace("'","''")+"';";
            
            try {
           
                Statement stat = SQLDatabase.dbLink.createStatement();
                ResultSet result = stat.executeQuery(request);
                
                result.next();
                movieID = result.getInt("id");
                stat.close();
            } catch(SQLException e){
                if(MovieProject.debugMode) System.out.println("(Debug - ACube) MovieID query Error.");
            }
            // Check for the good gender
            String[] genders = genderField.getText().toLowerCase().trim().split(",");
            ArrayList<Integer> checkedGenderID = new ArrayList<>();
            request = "SELECT genreid,name FROM genre";
            
            try{
                Statement stat = SQLDatabase.dbLink.createStatement();
                ResultSet result = stat.executeQuery(request);
                
                while(result.next())
                {
                    
                    for(int i=0; i<genders.length ; i++)
                    {
                        if(result.getString("name").toLowerCase().equals(genders[i].trim()))
                        {
                            checkedGenderID.add(result.getInt("genreid"));
                        }
                    }
                    
                }
                
                stat.close();
                result.close();
            } catch(SQLException e) { 
                if(MovieProject.debugMode) System.out.println("(Debug - ACube) Gender querry Error.");
            }
            // Add gender in the database
            
            for(int i=0;i<checkedGenderID.size();i++)
            {
                request = "INSERT INTO filmgenre( filmid, genreid ) VALUES ('"+movieID+"','"+checkedGenderID.get(i)+"');";
                try{
                    
                    Statement stat = SQLDatabase.dbLink.createStatement();
                    int result = stat.executeUpdate(request);
                
                    stat.close();
                    //if(MovieProject.debugMode) System.out.println("(Debug - ACube) Gender successfully added !");
                } catch(SQLException e) { 
                    if(MovieProject.debugMode) System.out.println("(Debug - ACube) Adding gender request error.");
                }
            }
            
            // Update catalog
            MovieProject.catalog.movie_list.clear();
            MovieProject.catalog.numberofmovies = 0;
            MovieProject.catalog.updateCatalog();
            
            this.dispose();
            App.updateUI();
            MovieValid valid = new MovieValid();
            valid.setVisible(true);
            
            
        }
        else
        {
            warn2.setVisible(true);
        }
        
    }//GEN-LAST:event_addButtonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JTextField authorField;
    private javax.swing.JLabel background;
    private javax.swing.JButton closeButton;
    private javax.swing.JTextField descriptionField;
    private javax.swing.JTextField durationField;
    private javax.swing.JTextField genderField;
    private javax.swing.JTextField imageField;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel warn1;
    private javax.swing.JLabel warn2;
    // End of variables declaration//GEN-END:variables
}
