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
import java.awt.Component;
import java.awt.Dimension;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;



/**
 *
 * @author Alexis
 */
public class App extends javax.swing.JFrame {
    
    private Point mouseClickPoint;
    
    private ArrayList<Movie> movielist = new ArrayList<>();

    
    /**
     * Creates new form App
     */
    public App() {
        initComponents();
        draggingFunction();
        setBackground(new Color(0,0,0,0));
        
        
        InitMoviePanel();
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
    
    public static void updateUI()
    {
       
       /// Not good but so much easier than the other method
       MovieProject.mainapp.dispose();
       MovieProject.mainapp = null;
       MovieProject.mainapp = new App();
       MovieProject.mainapp.setVisible(true);
       MovieProject.mainapp.setIconImage(new ImageIcon("src/movieproject/resources/logo.png").getImage());
    }
    
    // Select
    private ArrayList<Diffusion> selectedDiffusion = null;
    
    private BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
        
    }
    
    private void InitMoviePanel(){
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(20);
        
        String Genres[] = Movie.getAllGenre();
        filterByGenreComboBox.setModel(new javax.swing.DefaultComboBoxModel<>( Genres ));
        
        String Cinemas[] = new String[MovieProject.allcinema.numberofcinema+1];
        Cinemas[0] = "All";
        for(int i=0;i<MovieProject.allcinema.numberofcinema;i++)
            Cinemas[i+1] = MovieProject.allcinema.cinema_list.get(i).getName();
        filterByCinemaComboBox.setModel(new javax.swing.DefaultComboBoxModel<>( Cinemas ));
        
        movielist = Catalog.getByGenreInCinema("All", "All");
        displayMovies();
        
        
    }
    
    
  
    public void displayCinema(){
        
        java.awt.GridBagConstraints gridBagConstraints= new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(15,40,15,20);
        
        java.awt.GridBagConstraints gridBagConstraints2= new java.awt.GridBagConstraints();
        
        
        jPanel2.setPreferredSize(new java.awt.Dimension(1334, (MovieProject.allcinema.numberofcinema)*220));
        
        for (int i=0;i<MovieProject.allcinema.numberofcinema;i++)
        {
            
            gridBagConstraints.gridx=0;
            gridBagConstraints.gridy=i;
            
            gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
            
            gridBagConstraints2.gridx=1;
            gridBagConstraints2.gridy=i;
            
            gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints2.weightx = 1;
            
            javax.swing.JLabel icon = new javax.swing.JLabel();            
            javax.swing.JLabel textArea = new javax.swing.JLabel();
            javax.swing.JLabel highlighter = new javax.swing.JLabel(); 
            
            textArea.setOpaque(false);
            //textArea.setPreferredSize(new java.awt.Dimension(500, 190));
            
            String name= MovieProject.allcinema.cinema_list.get(i).getName();
            String address=MovieProject.allcinema.cinema_list.get(i).getAddress();
            double adultPrice=MovieProject.allcinema.cinema_list.get(i).getAdultPrice();
            double childrenPrice=MovieProject.allcinema.cinema_list.get(i).getChildrenPrice();
            double seniorPrice=MovieProject.allcinema.cinema_list.get(i).getSeniorPrice();
            
            String description= String.format("<html><h1>%s</h1><h2>Address : %s</h2><h2>Adult : £ %.2f <br/> Children: £ %.2f <br/>Senior : £ %.2f <br/></h2>",name,address,adultPrice,childrenPrice,seniorPrice);
            textArea.setText(description);
            
            icon.setName(String.valueOf(i));
            textArea.setName(String.valueOf(i));
            
            icon.setPreferredSize(new Dimension(500,190));
            
            try{
 
                URL url = new URL(MovieProject.allcinema.cinema_list.get(i).getImage());
                BufferedImage image2 = ImageIO.read(url);
 
                BufferedImage resized = resize(image2, 190,500);
                icon.setIcon(new ImageIcon(resized));
            }catch(Exception e){
            }
            
            highlighter.setPreferredSize(new Dimension(500,190));
            highlighter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movieproject/resources/highlighter2.png")));
            highlighter.setVisible(false);
            
            
            icon.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    highlighter.setVisible(true);
                }
            });
            
            icon.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    highlighter.setVisible(false);
                }
            });
            
            textArea.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    highlighter.setVisible(true);
                }
            });
            
            textArea.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    highlighter.setVisible(false);
                }
            });
            
            icon.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int index = Integer.valueOf(evt.getComponent().getName());
                    
                    CinemaPanel.setVisible(false);
                    MoviesPanel.setVisible(true);
                    CinemaSelectioned.setVisible(false);
                    MovieSelectioned.setVisible(true);
                    filterByCinemaComboBox.setSelectedIndex(index+1);
 
                }
            });
            
            textArea.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int index = Integer.valueOf(evt.getComponent().getName());
                    
                    CinemaPanel.setVisible(false);
                    MoviesPanel.setVisible(true);
                    CinemaSelectioned.setVisible(false);
                    MovieSelectioned.setVisible(true);
                    filterByCinemaComboBox.setSelectedIndex(index+1);
                }
            });
            
            highlighter.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            icon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            textArea.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            
            jPanel2.add(highlighter, gridBagConstraints);
            jPanel2.add(icon, gridBagConstraints);
            jPanel2.add(textArea,gridBagConstraints2);
        }
    }
   
 
    
    
    
    private void displayMovies(){ 
        //remove all icons
        Component components[] = gridPanel.getComponents();
        for (Component component : components) {
            if (component.getName() != null && component.getName().startsWith("deletable")) {
                gridPanel.remove(component);
            }
        }
        
        //resize the scroll panel 
        //gridPanel.setPreferredSize(new java.awt.Dimension(1334, ((movielist.size()-1)/3)*254+520));
        gridPanel.setPreferredSize(new java.awt.Dimension(1334, ((movielist.size()-1)/3)*254+450));
        //place all new icons
        java.awt.GridBagConstraints gridBagConstraints;
        java.awt.GridBagConstraints gridBagConstraints2;
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(17, 17, 17, 17);
        
        gridBagConstraints2 = new java.awt.GridBagConstraints();
        gridBagConstraints2.insets = new java.awt.Insets(27, 27, 27, 27);
        
        for (int i=0; i<movielist.size(); i++){
            gridBagConstraints.gridx = i%3;
            gridBagConstraints.gridy = i/3+1;
            
            gridBagConstraints2.gridx = i%3;
            gridBagConstraints2.gridy = i/3+1;
            
            javax.swing.JLabel icon = new javax.swing.JLabel();
            javax.swing.JLabel highlighter = new javax.swing.JLabel();
            javax.swing.JLabel name = new javax.swing.JLabel();
            javax.swing.JLabel preicon = new javax.swing.JLabel();
            
            preicon.setName("deletable"+i);
            icon.setName("deletable"+i);
            highlighter.setName("deletable"+i);
            name.setName("deletable"+i);
            
            preicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movieproject/resources/fondmovie.png")));
            icon.setIcon(new ImageIcon(movielist.get(i).getImageBuffered()));
            highlighter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movieproject/resources/highlight.png")));
            
            icon.setPreferredSize(new java.awt.Dimension(390, 210));
            icon.setMaximumSize(new java.awt.Dimension(390, 210));
            
            
            //name.setText(movielist.get(i).getName());
            name.setText("<html><h1>"+movielist.get(i).getName()+"</h1></html>");
            name.setFont(new java.awt.Font("Comfortaa", 0, 36)); // NOI18N
            name.setForeground(new java.awt.Color(255, 255, 255));
            name.setMaximumSize(new java.awt.Dimension(400, 220));
            name.setName(""); // NOI18N
            name.setPreferredSize(new java.awt.Dimension(360, 160));
            name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            name.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
            
            
            highlighter.setVisible(false);
            name.setVisible(false);
            
            
            icon.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    highlighter.setVisible(true);
                    name.setVisible(true);
                }
            });
            
            icon.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    highlighter.setVisible(false);
                    name.setVisible(false);
                }
            });
            
            icon.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int index = Integer.valueOf(evt.getComponent().getName().substring(9));
                    MoviesPanel.setVisible(false);
                    AdministrationPanel.setVisible(false);
                    ProfilePanel.setVisible(false);
                    CinemaPanel.setVisible(false);
                    
                    OneMoviePanel.removeAll();
                    javax.swing.JPanel onemovie = new OneMoviePanel2(movielist.get(index));
                    OneMoviePanel.add(onemovie, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
                    onemovie.setVisible(true);
                    
                    OneMoviePanel.setVisible(false);
                    OneMoviePanel.setVisible(true);
                }
            });
            
            highlighter.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            name.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            preicon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

            
            gridPanel.add(name, gridBagConstraints);
            gridPanel.add(highlighter, gridBagConstraints);
            gridPanel.add(icon, gridBagConstraints2);
            gridPanel.add(preicon, gridBagConstraints);
            
        }
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        SelectionPanel = new javax.swing.JPanel();
        SelectMovies = new javax.swing.JButton();
        SelectCinema = new javax.swing.JButton();
        SelectProfile = new javax.swing.JButton();
        SelectAdministration = new javax.swing.JButton();
        MovieSelectioned = new javax.swing.JLabel();
        CinemaSelectioned = new javax.swing.JLabel();
        ProfileSelectionned = new javax.swing.JLabel();
        AdminSelectionned = new javax.swing.JLabel();
        backgroundSelectedPanel = new javax.swing.JLabel();
        MoviesPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        gridPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        filterByGenreComboBox = new javax.swing.JComboBox<>();
        filterByCinemaComboBox = new javax.swing.JComboBox<>();
        ProfilePanel = new javax.swing.JPanel();
        Connected = new javax.swing.JPanel();
        emailValueLabel = new javax.swing.JLabel();
        birtdayValueLabel = new javax.swing.JLabel();
        premiumValueLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        premiumLabel = new javax.swing.JLabel();
        settingsButton = new javax.swing.JButton();
        upgradeButton = new javax.swing.JButton();
        backgroundPanel = new javax.swing.JLabel();
        imageProfile = new javax.swing.JLabel();
        Disconnected = new javax.swing.JPanel();
        hereButton = new javax.swing.JButton();
        connectButton = new javax.swing.JButton();
        backgroundPanel1 = new javax.swing.JLabel();
        AdministrationPanel = new javax.swing.JPanel();
        movieButton = new javax.swing.JButton();
        couponButton = new javax.swing.JButton();
        statsButton = new javax.swing.JButton();
        broadcastButton = new javax.swing.JButton();
        moviePanel = new javax.swing.JPanel();
        warn1 = new javax.swing.JLabel();
        clickHereButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        movie_backgroundPanel = new javax.swing.JLabel();
        broadcastPanel = new javax.swing.JPanel();
        warn2 = new javax.swing.JLabel();
        warn3 = new javax.swing.JLabel();
        clickHereButton2 = new javax.swing.JButton();
        removeButton2 = new javax.swing.JButton();
        hoursField = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        movie_backgroundPanel1 = new javax.swing.JLabel();
        couponPanel = new javax.swing.JPanel();
        clickHereButton1 = new javax.swing.JButton();
        removeButton1 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        warn4 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        warn5 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        coupons_backgroundPanel = new javax.swing.JLabel();
        statsPanel = new javax.swing.JPanel();
        recordsButton = new javax.swing.JButton();
        statistics_backgroundPanel = new javax.swing.JLabel();
        CinemaPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        OneMoviePanel = new javax.swing.JPanel();
        loginButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        unshowButton = new javax.swing.JButton();
        eventButton = new javax.swing.JButton();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1344, 756));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SelectionPanel.setBackground(new java.awt.Color(179, 157, 154));
        SelectionPanel.setAlignmentX(0.0F);
        SelectionPanel.setAlignmentY(0.0F);
        SelectionPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        SelectionPanel.setMaximumSize(new java.awt.Dimension(1336, 100));
        SelectionPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SelectMovies.setBorder(null);
        SelectMovies.setBorderPainted(false);
        SelectMovies.setContentAreaFilled(false);
        SelectMovies.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SelectMovies.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SelectMoviesMouseClicked(evt);
            }
        });
        SelectMovies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectMoviesActionPerformed(evt);
            }
        });
        SelectionPanel.add(SelectMovies, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 110, 40));
        SelectMovies.setFocusPainted(false);

        SelectCinema.setBorderPainted(false);
        SelectCinema.setContentAreaFilled(false);
        SelectCinema.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SelectCinema.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SelectCinemaMouseClicked(evt);
            }
        });
        SelectCinema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectCinemaActionPerformed(evt);
            }
        });
        SelectionPanel.add(SelectCinema, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 130, 40));
        SelectCinema.setFocusPainted(false);

        SelectProfile.setBorderPainted(false);
        SelectProfile.setContentAreaFilled(false);
        SelectProfile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SelectProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SelectProfileMouseClicked(evt);
            }
        });
        SelectProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectProfileActionPerformed(evt);
            }
        });
        SelectionPanel.add(SelectProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 30, 120, 40));
        SelectProfile.setFocusPainted(false);

        SelectAdministration.setBorderPainted(false);
        SelectAdministration.setContentAreaFilled(false);
        SelectAdministration.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SelectAdministration.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SelectAdministrationMouseClicked(evt);
            }
        });
        SelectAdministration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectAdministrationActionPerformed(evt);
            }
        });
        SelectionPanel.add(SelectAdministration, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 30, 240, 40));
        if(ClientInfo.isAdmin){
            SelectAdministration.setVisible(true);
        }
        else
        {
            SelectAdministration.setVisible(false);
        }

        SelectAdministration.setFocusPainted(false);

        MovieSelectioned.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movieproject/resources/selectedpanel.png"))); // NOI18N
        MovieSelectioned.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SelectionPanel.add(MovieSelectioned, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 70, 112, 3));
        MovieSelectioned.setVisible(true);

        CinemaSelectioned.setBackground(new java.awt.Color(41, 41, 41));
        CinemaSelectioned.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movieproject/resources/selectedpanel2.png"))); // NOI18N
        CinemaSelectioned.setMaximumSize(new java.awt.Dimension(120, 3));
        SelectionPanel.add(CinemaSelectioned, new org.netbeans.lib.awtextra.AbsoluteConstraints(334, 70, 120, 3));
        CinemaSelectioned.setVisible(false);

        ProfileSelectionned.setBackground(new java.awt.Color(41, 41, 41));
        ProfileSelectionned.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movieproject/resources/selectedpanel2.png"))); // NOI18N
        ProfileSelectionned.setMaximumSize(new java.awt.Dimension(120, 3));
        SelectionPanel.add(ProfileSelectionned, new org.netbeans.lib.awtextra.AbsoluteConstraints(616, 70, 110, 3));
        ProfileSelectionned.setVisible(false);

        AdminSelectionned.setBackground(new java.awt.Color(41, 41, 41));
        AdminSelectionned.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movieproject/resources/selectedpanel2.png"))); // NOI18N
        AdminSelectionned.setMaximumSize(new java.awt.Dimension(120, 3));
        SelectionPanel.add(AdminSelectionned, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 70, 237, 3));
        AdminSelectionned.setVisible(false);

        backgroundSelectedPanel.setBackground(new java.awt.Color(41, 41, 41));
        backgroundSelectedPanel.setFont(new java.awt.Font("Comfortaa", 0, 30)); // NOI18N
        if(ClientInfo.isAdmin){
            backgroundSelectedPanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movieproject/resources/selectlabelpanel.png"))); // NOI18N
        }
        else
        {
            backgroundSelectedPanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movieproject/resources/selectlabelpanelwithoutadmin.png"))); // NOI18N
        }
        backgroundSelectedPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        backgroundSelectedPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backgroundSelectedPanelMouseClicked(evt);
            }
        });
        SelectionPanel.add(backgroundSelectedPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1336, 100));

        getContentPane().add(SelectionPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 120, 1336, 100));

        MoviesPanel.setBackground(new java.awt.Color(230, 230, 230));
        MoviesPanel.setMaximumSize(new java.awt.Dimension(1336, 490));
        MoviesPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        gridPanel.setPreferredSize(new java.awt.Dimension(1334, 700));
        gridPanel.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Lucida Sans", 0, 24)); // NOI18N
        jLabel1.setText("By Genre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridPanel.add(jLabel1, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Lucida Sans", 0, 24)); // NOI18N
        jLabel6.setText("By Cinema");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridPanel.add(jLabel6, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Lucida Sans", 0, 24)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movieproject/resources/icon_filter.png"))); // NOI18N
        jLabel7.setText("Filter :");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(17, 145, 0, 145);
        gridPanel.add(jLabel7, gridBagConstraints);

        filterByGenreComboBox.setFont(new java.awt.Font("Lucida Sans", 0, 24)); // NOI18N
        filterByGenreComboBox.setMaximumRowCount(10);
        filterByGenreComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        filterByGenreComboBox.setPreferredSize(new java.awt.Dimension(300, 40));
        filterByGenreComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                filterByGenreComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(60, 67, 0, 67);
        gridPanel.add(filterByGenreComboBox, gridBagConstraints);

        filterByCinemaComboBox.setFont(new java.awt.Font("Lucida Sans", 0, 24)); // NOI18N
        filterByCinemaComboBox.setMaximumRowCount(10);
        filterByCinemaComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        filterByCinemaComboBox.setPreferredSize(new java.awt.Dimension(300, 40));
        filterByCinemaComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                filterByCinemaComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(60, 67, 0, 67);
        gridPanel.add(filterByCinemaComboBox, gridBagConstraints);

        jScrollPane1.setViewportView(gridPanel);

        MoviesPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1336, 490));

        getContentPane().add(MoviesPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 220, -1, -1));
        MoviesPanel.setVisible(true);

        ProfilePanel.setBackground(new java.awt.Color(230, 230, 230));
        ProfilePanel.setMaximumSize(new java.awt.Dimension(1336, 490));
        ProfilePanel.setMinimumSize(new java.awt.Dimension(1336, 490));
        ProfilePanel.setPreferredSize(new java.awt.Dimension(1336, 490));
        ProfilePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Connected.setBackground(new java.awt.Color(230, 230, 230));
        Connected.setMaximumSize(new java.awt.Dimension(1336, 490));
        Connected.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        emailValueLabel.setFont(new java.awt.Font("Comfortaa", 0, 36)); // NOI18N
        emailValueLabel.setForeground(new java.awt.Color(41, 41, 41));
        emailValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        emailValueLabel.setText("...");
        Connected.add(emailValueLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 115, 580, 50));
        if(ClientInfo.isConnected)
        {
            emailValueLabel.setText(ClientInfo.email);
        }

        birtdayValueLabel.setFont(new java.awt.Font("Comfortaa", 0, 36)); // NOI18N
        birtdayValueLabel.setForeground(new java.awt.Color(41, 41, 41));
        birtdayValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        birtdayValueLabel.setText("...");
        Connected.add(birtdayValueLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 243, 480, 40));
        if(ClientInfo.isConnected)
        {
            birtdayValueLabel.setText(ClientInfo.birthday);
        }

        premiumValueLabel.setFont(new java.awt.Font("Comfortaa", 0, 36)); // NOI18N
        premiumValueLabel.setForeground(new java.awt.Color(41, 41, 41));
        premiumValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        premiumValueLabel.setText("...");
        Connected.add(premiumValueLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 370, 520, 40));
        if(ClientInfo.isConnected)
        {
            if(ClientInfo.isPremium)
            {
                premiumValueLabel.setText("VIP Cinephile (-20%)");
            }
            else{
                premiumValueLabel.setText("Normal Cinephile (-0%)");
            }
        }

        nameLabel.setFont(new java.awt.Font("Comfortaa", 1, 48)); // NOI18N
        nameLabel.setForeground(new java.awt.Color(41, 41, 41));
        nameLabel.setText("...");
        nameLabel.setToolTipText("");
        nameLabel.setAlignmentY(0.0F);
        nameLabel.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        nameLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        nameLabel.setIconTextGap(0);
        nameLabel.setMaximumSize(new java.awt.Dimension(400, 53));
        nameLabel.setMinimumSize(new java.awt.Dimension(400, 53));
        nameLabel.setPreferredSize(new java.awt.Dimension(400, 53));
        Connected.add(nameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 396, 53));
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.setText(ClientInfo.name);

        premiumLabel.setFont(new java.awt.Font("Comfortaa", 0, 30)); // NOI18N
        premiumLabel.setForeground(new java.awt.Color(41, 41, 41));
        premiumLabel.setText("...");
        premiumLabel.setToolTipText("");
        premiumLabel.setAlignmentY(0.0F);
        premiumLabel.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        premiumLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        premiumLabel.setIconTextGap(0);
        premiumLabel.setMaximumSize(new java.awt.Dimension(400, 53));
        premiumLabel.setMinimumSize(new java.awt.Dimension(400, 53));
        premiumLabel.setPreferredSize(new java.awt.Dimension(400, 53));
        Connected.add(premiumLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 397, 53));
        premiumLabel.setHorizontalAlignment(JLabel.CENTER);

        if (ClientInfo.isPremium)
        {
            premiumLabel.setText("Premium member");
        }
        else
        {
            premiumLabel.setText("Normal member");
        }

        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        settingsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                settingsButtonMouseClicked(evt);
            }
        });
        Connected.add(settingsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 70, 60));
        settingsButton.setFocusPainted(false);

        upgradeButton.setBorderPainted(false);
        upgradeButton.setContentAreaFilled(false);
        upgradeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        upgradeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                upgradeButtonMouseClicked(evt);
            }
        });
        Connected.add(upgradeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 420, 300, 50));
        upgradeButton.setFocusPainted(false);

        if(ClientInfo.isPremium)
        upgradeButton.setVisible(false);

        backgroundPanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movieproject/resources/profilepanel.png"))); // NOI18N
        backgroundPanel.setAlignmentY(0.0F);
        Connected.add(backgroundPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1336, 490));

        imageProfile.setIcon(new javax.swing.JLabel() {
            public javax.swing.Icon getIcon() {
                try {
                    return new javax.swing.ImageIcon(
                        new java.net.URL("https://javacinema.mtxserv.com/profileimage/defaultprofile.png")
                    );
                } catch (java.net.MalformedURLException e) {
                }
                return null;
            }
        }.getIcon());
        Connected.add(imageProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 180, 180));
        if(ClientInfo.isConnected)
        {
            try{

                URL url = new URL(ClientInfo.imageProfile);
                BufferedImage image = ImageIO.read(url);

                BufferedImage resized = resize(image, 180,180);
                imageProfile.setIcon(new ImageIcon(resized));
            }catch(Exception e){
            }
        }

        ProfilePanel.add(Connected, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1336, 490));
        if(ClientInfo.isConnected)
        {
            Connected.setVisible(true);
        }
        else
        {
            Connected.setVisible(false);
        }

        Disconnected.setBackground(new java.awt.Color(230, 230, 230));
        Disconnected.setMaximumSize(new java.awt.Dimension(1336, 490));
        Disconnected.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        hereButton.setBorder(null);
        hereButton.setBorderPainted(false);
        hereButton.setContentAreaFilled(false);
        hereButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hereButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hereButtonMouseClicked(evt);
            }
        });
        Disconnected.add(hereButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 230, 100, 50));
        hereButton.setFocusPainted(false);

        connectButton.setBorder(null);
        connectButton.setBorderPainted(false);
        connectButton.setContentAreaFilled(false);
        connectButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        connectButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                connectButtonMouseClicked(evt);
            }
        });
        Disconnected.add(connectButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 380, 220, 50));
        connectButton.setFocusPainted(false);

        backgroundPanel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movieproject/resources/profilepanel2.png"))); // NOI18N
        backgroundPanel1.setAlignmentY(0.0F);
        Disconnected.add(backgroundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1336, 490));

        ProfilePanel.add(Disconnected, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1336, 490));
        if(ClientInfo.isConnected)
        {
            Disconnected.setVisible(false);
        }
        else
        {
            Disconnected.setVisible(true);
        }

        getContentPane().add(ProfilePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 220, 1336, 490));

        AdministrationPanel.setBackground(new java.awt.Color(230, 230, 230));
        AdministrationPanel.setMaximumSize(new java.awt.Dimension(1336, 490));
        AdministrationPanel.setMinimumSize(new java.awt.Dimension(1336, 490));
        AdministrationPanel.setPreferredSize(new java.awt.Dimension(1336, 490));
        AdministrationPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        movieButton.setBorderPainted(false);
        movieButton.setContentAreaFilled(false);
        movieButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        movieButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                movieButtonMouseClicked(evt);
            }
        });
        AdministrationPanel.add(movieButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, 160, 50));
        movieButton.setFocusPainted(false);

        couponButton.setBorderPainted(false);
        couponButton.setContentAreaFilled(false);
        couponButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        couponButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                couponButtonMouseClicked(evt);
            }
        });
        AdministrationPanel.add(couponButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 340, 200, 60));
        couponButton.setFocusPainted(false);

        statsButton.setBorderPainted(false);
        statsButton.setContentAreaFilled(false);
        statsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        statsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                statsButtonMouseClicked(evt);
            }
        });
        AdministrationPanel.add(statsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420, 200, 50));
        statsButton.setFocusPainted(false);

        broadcastButton.setBorderPainted(false);
        broadcastButton.setContentAreaFilled(false);
        broadcastButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        broadcastButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                broadcastButtonMouseClicked(evt);
            }
        });
        AdministrationPanel.add(broadcastButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 270, 210, 50));
        broadcastButton.setFocusPainted(false);

        moviePanel.setMaximumSize(new java.awt.Dimension(1336, 490));
        moviePanel.setMinimumSize(new java.awt.Dimension(1336, 490));
        moviePanel.setPreferredSize(new java.awt.Dimension(1336, 490));
        moviePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        warn1.setFont(new java.awt.Font("Comfortaa", 0, 18)); // NOI18N
        warn1.setForeground(new java.awt.Color(255, 0, 0));
        warn1.setText("* Please choose the movie to remove");
        moviePanel.add(warn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 440, 310, 30));
        warn1.setVisible(false);

        clickHereButton.setBorder(null);
        clickHereButton.setBorderPainted(false);
        clickHereButton.setContentAreaFilled(false);
        clickHereButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clickHereButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clickHereButtonMouseClicked(evt);
            }
        });
        moviePanel.add(clickHereButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 140, 460, 60));
        clickHereButton.setFocusPainted(false);

        removeButton.setBorder(null);
        removeButton.setBorderPainted(false);
        removeButton.setContentAreaFilled(false);
        removeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        removeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeButtonMouseClicked(evt);
            }
        });
        moviePanel.add(removeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 370, 270, 60));
        removeButton.setFocusPainted(false);

        jComboBox1.setFont(new java.awt.Font("Comfortaa", 0, 18)); // NOI18N
        String removelist[] = new String[MovieProject.catalog.numberofmovies + 1];
        removelist[0] = "Select the movie";
        for(int i = 1; i< removelist.length ; i++)
        {
            removelist[i] = MovieProject.catalog.movie_list.get(i-1).getName();
        }
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>( removelist ));
        jComboBox1.setBorder(null);
        jComboBox1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        moviePanel.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 380, 270, 40));

        movie_backgroundPanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movieproject/resources/adminpanel1.png"))); // NOI18N
        movie_backgroundPanel.setAlignmentY(0.0F);
        moviePanel.add(movie_backgroundPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1336, 490));

        AdministrationPanel.add(moviePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1336, 490));

        broadcastPanel.setMaximumSize(new java.awt.Dimension(1336, 490));
        broadcastPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        warn2.setFont(new java.awt.Font("Comfortaa", 0, 18)); // NOI18N
        warn2.setForeground(new java.awt.Color(255, 0, 0));
        warn2.setText("* Please choose the diffusion to remove");
        broadcastPanel.add(warn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 440, 340, 30));
        warn2.setVisible(false);

        warn3.setFont(new java.awt.Font("Comfortaa", 0, 18)); // NOI18N
        warn3.setForeground(new java.awt.Color(255, 0, 0));
        warn3.setText("* Please fill all the field");
        broadcastPanel.add(warn3, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 250, 380, 30));
        warn3.setVisible(false);

        clickHereButton2.setBorder(null);
        clickHereButton2.setBorderPainted(false);
        clickHereButton2.setContentAreaFilled(false);
        clickHereButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clickHereButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clickHereButton2MouseClicked(evt);
            }
        });
        broadcastPanel.add(clickHereButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 180, 260, 70));
        clickHereButton2.setFocusPainted(false);

        removeButton2.setBorder(null);
        removeButton2.setBorderPainted(false);
        removeButton2.setContentAreaFilled(false);
        removeButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        removeButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeButton2MouseClicked(evt);
            }
        });
        broadcastPanel.add(removeButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 370, 260, 70));
        removeButton2.setFocusPainted(false);

        hoursField.setFont(new java.awt.Font("Comfortaa", 0, 18)); // NOI18N
        hoursField.setForeground(new java.awt.Color(232, 232, 232));
        hoursField.setBorder(null);
        hoursField.setCaretColor(new java.awt.Color(232, 232, 232));
        hoursField.setOpaque(false);
        hoursField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                hoursFieldFocusLost(evt);
            }
        });
        broadcastPanel.add(hoursField, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 37, 320, 44));

        jComboBox3.setFont(new java.awt.Font("Comfortaa", 0, 18)); // NOI18N
        removelist = new String[MovieProject.allcinema.numberofcinema + 1];
        removelist[0] = "Select the cinema";
        for(int i = 1; i< removelist.length ; i++)
        {
            removelist[i] = MovieProject.allcinema.cinema_list.get(i-1).getName();
        }
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>( removelist ));
        jComboBox3.setBorder(null);
        jComboBox3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });
        jComboBox3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComboBox3FocusLost(evt);
            }
        });
        broadcastPanel.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 330, 360, 40));

        jComboBox4.setFont(new java.awt.Font("Comfortaa", 0, 14)); // NOI18N
        jComboBox4.setBorder(null);
        jComboBox4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });
        broadcastPanel.add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 400, 360, 40));
        if(jComboBox3.getSelectedItem().equals("Select the cinema"))
        {
            jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None" }));

        }
        else
        {
            ArrayList<Diffusion> list = AllDiffusion.getCinemaDiffusion( removelist[jComboBox3.getSelectedIndex()] );

            String dremovelist[] = new String[list.size() + 1];
            dremovelist[0] = "Select the diffusion";
            DateFormat dateFormat = new SimpleDateFormat("dd MMMMM yyyy");
            for(int i = 1; i< dremovelist.length ; i++)
            {
                dremovelist[i] = ""+ list.get(i).getMovie().getName() + " - " + dateFormat.format(list.get(i).getDay()) + " : " + list.get(i).getHours().toString();
            }
            jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>( dremovelist ));
        }

        jComboBox5.setFont(new java.awt.Font("Comfortaa", 0, 18)); // NOI18N
        jComboBox5.setBorder(null);
        jComboBox5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jComboBox5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox5ItemStateChanged(evt);
            }
        });
        jComboBox5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComboBox5FocusLost(evt);
            }
        });
        broadcastPanel.add(jComboBox5, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 180, 270, 40));
        String addlist[] = new String[MovieProject.catalog.numberofmovies + 1];
        addlist[0] = "Select the movie";
        for(int i = 1; i< addlist.length ; i++)
        {
            addlist[i] = MovieProject.catalog.movie_list.get(i-1).getName();
        }

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>( addlist ));

        jComboBox6.setFont(new java.awt.Font("Comfortaa", 0, 18)); // NOI18N
        jComboBox6.setBorder(null);
        jComboBox6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jComboBox6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox6ItemStateChanged(evt);
            }
        });
        jComboBox6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jComboBox6FocusLost(evt);
            }
        });
        broadcastPanel.add(jComboBox6, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 110, 270, 40));
        String cinemalist[] = new String[MovieProject.allcinema.numberofcinema + 1];
        cinemalist[0] = "Select the cinema";
        for(int i = 1; i< cinemalist.length ; i++)
        {
            cinemalist[i] = MovieProject.allcinema.cinema_list.get(i-1).getName();
        }

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>( cinemalist ));

        jDateChooser1.setFont(new java.awt.Font("Comfortaa", 0, 24)); // NOI18N
        jDateChooser1.setMaxSelectableDate(new java.util.Date(253370764902000L));
        jDateChooser1.setMaximumSize(new java.awt.Dimension(570, 40));
        jDateChooser1.setMinimumSize(new java.awt.Dimension(570, 40));
        jDateChooser1.setPreferredSize(new java.awt.Dimension(570, 40));
        broadcastPanel.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(952, 107, 340, 41));

        movie_backgroundPanel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movieproject/resources/adminpanel4.png"))); // NOI18N
        movie_backgroundPanel1.setAlignmentY(0.0F);
        broadcastPanel.add(movie_backgroundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1336, 490));

        AdministrationPanel.add(broadcastPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1336, 490));
        broadcastPanel.setVisible(false);

        couponPanel.setMaximumSize(new java.awt.Dimension(1336, 490));
        couponPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        clickHereButton1.setBorder(null);
        clickHereButton1.setBorderPainted(false);
        clickHereButton1.setContentAreaFilled(false);
        clickHereButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clickHereButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clickHereButton1MouseClicked(evt);
            }
        });
        couponPanel.add(clickHereButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 170, 260, 70));
        clickHereButton1.setFocusPainted(false);

        removeButton1.setBorder(null);
        removeButton1.setBorderPainted(false);
        removeButton1.setContentAreaFilled(false);
        removeButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        removeButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeButton1MouseClicked(evt);
            }
        });
        couponPanel.add(removeButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 370, 270, 60));
        removeButton1.setFocusPainted(false);

        jComboBox2.setFont(new java.awt.Font("Comfortaa", 0, 18)); // NOI18N
        jComboBox2.setBorder(null);
        jComboBox2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        couponPanel.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 380, 270, 40));
        String couponlist[] = new String[MovieProject.allcoupon.numberofcoupon + 1];
        couponlist[0] = "Select the coupon";
        for(int i = 1; i< couponlist.length ; i++)
        {
            couponlist[i] = MovieProject.allcoupon.coupon_list.get(i-1).getName();
        }

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>( couponlist ));

        warn4.setFont(new java.awt.Font("Comfortaa", 0, 18)); // NOI18N
        warn4.setForeground(new java.awt.Color(255, 0, 0));
        warn4.setText("* Please choose the coupon to remove");
        couponPanel.add(warn4, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 440, 310, 30));
        warn4.setVisible(false);

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
        couponPanel.add(nameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(623, 84, 219, 44));

        warn5.setFont(new java.awt.Font("Comfortaa", 0, 18)); // NOI18N
        warn5.setForeground(new java.awt.Color(255, 0, 0));
        warn5.setText("* Please fill all the field");
        couponPanel.add(warn5, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 240, 380, 30));
        warn5.setVisible(false);

        jComboBox7.setFont(new java.awt.Font("Comfortaa", 0, 18)); // NOI18N
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select a reduction", "-5%", "-10%", "- 20%", "-30%", "-40%" }));
        jComboBox7.setBorder(null);
        jComboBox7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        couponPanel.add(jComboBox7, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 160, 230, 40));

        coupons_backgroundPanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movieproject/resources/adminpanel2.png"))); // NOI18N
        coupons_backgroundPanel.setAlignmentY(0.0F);
        couponPanel.add(coupons_backgroundPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1336, 490));

        AdministrationPanel.add(couponPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1336, 490));
        couponPanel.setVisible(false);

        statsPanel.setMaximumSize(new java.awt.Dimension(1336, 490));
        statsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        recordsButton.setFont(new java.awt.Font("Comfortaa", 0, 36)); // NOI18N
        recordsButton.setText("Get all records");
        recordsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        recordsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recordsButtonMouseClicked(evt);
            }
        });
        statsPanel.add(recordsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 190, 410, 70));
        recordsButton.setFocusPainted(false);

        statistics_backgroundPanel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movieproject/resources/adminpanel3.png"))); // NOI18N
        statistics_backgroundPanel.setAlignmentY(0.0F);
        statsPanel.add(statistics_backgroundPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1336, 490));

        AdministrationPanel.add(statsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1336, 490));
        statsPanel.setVisible(false);

        getContentPane().add(AdministrationPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 220, 1336, 490));
        AdministrationPanel.setVisible(false);

        CinemaPanel.setMaximumSize(new java.awt.Dimension(1336, 490));
        CinemaPanel.setMinimumSize(new java.awt.Dimension(1336, 490));
        CinemaPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(1336, 490));

        jPanel2.setPreferredSize(new java.awt.Dimension(1336, 490));
        jPanel2.setLayout(new java.awt.GridBagLayout());
        jScrollPane2.setViewportView(jPanel2);

        CinemaPanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
        jScrollPane2.getVerticalScrollBar().setUnitIncrement(20);

        getContentPane().add(CinemaPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 220, -1, -1));
        displayCinema();
        CinemaPanel.setVisible(false);

        OneMoviePanel.setBackground(new java.awt.Color(250, 250, 250));
        OneMoviePanel.setMaximumSize(new java.awt.Dimension(1336, 490));
        OneMoviePanel.setMinimumSize(new java.awt.Dimension(1336, 490));
        OneMoviePanel.setPreferredSize(new java.awt.Dimension(1336, 490));
        OneMoviePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(OneMoviePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 220, -1, -1));
        OneMoviePanel.setVisible(false);

        loginButton.setFont(new java.awt.Font("Comfortaa", 0, 18)); // NOI18N
        loginButton.setText("Login / Register");
        loginButton.setBorder(null);
        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginButtonMouseClicked(evt);
            }
        });
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        getContentPane().add(loginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 25, 127, 21));
        loginButton.setFocusPainted(false);
        if (ClientInfo.isConnected)
        {
            loginButton.setText("Sign Out");
        }
        else
        {
            loginButton.setText("Login / Register");
        }

        closeButton.setAlignmentY(0.0F);
        closeButton.setBorder(null);
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
        });
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        getContentPane().add(closeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 20, 30, 30));
        closeButton.setFocusPainted(false);

        unshowButton.setAlignmentY(0.0F);
        unshowButton.setBorder(null);
        unshowButton.setBorderPainted(false);
        unshowButton.setContentAreaFilled(false);
        unshowButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        unshowButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                unshowButtonMouseClicked(evt);
            }
        });
        unshowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unshowButtonActionPerformed(evt);
            }
        });
        getContentPane().add(unshowButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1260, 20, 20, 30));
        unshowButton.setFocusPainted(false);

        eventButton.setToolTipText("Version 1.0");
        eventButton.setAlignmentY(0.0F);
        eventButton.setBorder(null);
        eventButton.setBorderPainted(false);
        eventButton.setContentAreaFilled(false);
        eventButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        eventButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eventButtonMouseClicked(evt);
            }
        });
        eventButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventButtonActionPerformed(evt);
            }
        });
        getContentPane().add(eventButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 20, 30, 30));
        eventButton.setFocusPainted(false);

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/movieproject/resources/fond.png"))); // NOI18N
        getContentPane().add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        setSize(new java.awt.Dimension(1344, 756));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_closeButtonActionPerformed

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
        // TODO add your handling code here:
        
        System.exit(0);
    }//GEN-LAST:event_closeButtonMouseClicked

    private void unshowButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_unshowButtonMouseClicked
        // TODO add your handling code here:
        
        setState(ICONIFIED);
    }//GEN-LAST:event_unshowButtonMouseClicked

    private void unshowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unshowButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_unshowButtonActionPerformed

    private void backgroundSelectedPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backgroundSelectedPanelMouseClicked
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_backgroundSelectedPanelMouseClicked

    private void SelectMoviesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectMoviesActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_SelectMoviesActionPerformed

    private void SelectCinemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectCinemaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SelectCinemaActionPerformed

    private void SelectProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectProfileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SelectProfileActionPerformed

    private void SelectAdministrationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectAdministrationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SelectAdministrationActionPerformed

    private void SelectMoviesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SelectMoviesMouseClicked
        // TODO add your handling code here:
        if  (MovieSelectioned.isVisible()) {
        } else {
        MovieSelectioned.setVisible(true);
        CinemaSelectioned.setVisible(false);
        AdminSelectionned.setVisible(false);
        ProfileSelectionned.setVisible(false);
        OneMoviePanel.setVisible(false);
        
        MoviesPanel.setVisible(true);
        CinemaPanel.setVisible(false);
        ProfilePanel.setVisible(false);
        AdministrationPanel.setVisible(false);
        }
    }//GEN-LAST:event_SelectMoviesMouseClicked

    private void SelectCinemaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SelectCinemaMouseClicked
        // TODO add your handling code here:
        if  (CinemaSelectioned.isVisible()) {
        } else {
        MovieSelectioned.setVisible(false);
        CinemaSelectioned.setVisible(true);
        AdminSelectionned.setVisible(false);
        ProfileSelectionned.setVisible(false);
        
        MoviesPanel.setVisible(false);
        CinemaPanel.setVisible(true);
        ProfilePanel.setVisible(false);
        AdministrationPanel.setVisible(false);
        OneMoviePanel.setVisible(false);
        }
    }//GEN-LAST:event_SelectCinemaMouseClicked

    private void SelectProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SelectProfileMouseClicked
        // TODO add your handling code here:
        if  (ProfileSelectionned.isVisible()) {
        } else {
        MovieSelectioned.setVisible(false);
        CinemaSelectioned.setVisible(false);
        AdminSelectionned.setVisible(false);
        ProfileSelectionned.setVisible(true);
        
        MoviesPanel.setVisible(false);
        CinemaPanel.setVisible(false);
        ProfilePanel.setVisible(true);
        AdministrationPanel.setVisible(false);
        OneMoviePanel.setVisible(false);
        
        }
        
        if(ClientInfo.isConnected) {
            
            
            
            
        }
        
        else
        {
            
        }
        
    }//GEN-LAST:event_SelectProfileMouseClicked

    private void SelectAdministrationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SelectAdministrationMouseClicked
        // TODO add your handling code here:
        if  (AdminSelectionned.isVisible()) {
        } else {
        MovieSelectioned.setVisible(false);
        CinemaSelectioned.setVisible(false);
        AdminSelectionned.setVisible(true);
        ProfileSelectionned.setVisible(false);
        
        MoviesPanel.setVisible(false);
        CinemaPanel.setVisible(false);
        ProfilePanel.setVisible(false);
        AdministrationPanel.setVisible(true);
        OneMoviePanel.setVisible(false);
        }
    }//GEN-LAST:event_SelectAdministrationMouseClicked

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_loginButtonActionPerformed

    private void loginButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginButtonMouseClicked
        // TODO add your handling code here:
        if(ClientInfo.isConnected) {
            
            ClientInfo.ID = -1;
            ClientInfo.isAdmin = false;
            ClientInfo.isConnected = false;
            ClientInfo.name = null;
            
            updateUI();
            AutomaticConnection.deleteCookie();
            
        }
        
        else
        {
            if (MovieProject.signin == null)
        {
            MovieProject.signin = new SignIn();
            MovieProject.signin.setVisible(true);
        }
        }
        
    }//GEN-LAST:event_loginButtonMouseClicked

    private void hereButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hereButtonMouseClicked
        // TODO add your handling code here:
        if (MovieProject.signin == null)
        {
            MovieProject.signin = new SignIn();
            MovieProject.signin.setVisible(true);
        }
    }//GEN-LAST:event_hereButtonMouseClicked

    private void connectButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_connectButtonMouseClicked
        // TODO add your handling code here:

        if (MovieProject.signin == null)
        {
            MovieProject.signin = new SignIn();
            MovieProject.signin.setVisible(true);
        }
    }//GEN-LAST:event_connectButtonMouseClicked

    private void movieButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_movieButtonMouseClicked
        // TODO add your handling code here:
        couponPanel.setVisible(false);
        statsPanel.setVisible(false);
        broadcastPanel.setVisible(false);
        moviePanel.setVisible(true);
    }//GEN-LAST:event_movieButtonMouseClicked

    private void couponButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_couponButtonMouseClicked
        // TODO add your handling code here:
        
        statsPanel.setVisible(false);
        moviePanel.setVisible(false);
        broadcastPanel.setVisible(false);
        couponPanel.setVisible(true);
    }//GEN-LAST:event_couponButtonMouseClicked

    private void statsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_statsButtonMouseClicked
        // TODO add your handling code here:

        moviePanel.setVisible(false);
        couponPanel.setVisible(false);
        broadcastPanel.setVisible(false);
        statsPanel.setVisible(true);
    }//GEN-LAST:event_statsButtonMouseClicked

    private void broadcastButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_broadcastButtonMouseClicked
        // TODO add your handling code here:
        moviePanel.setVisible(false);
        couponPanel.setVisible(false);
        statsPanel.setVisible(false);
        broadcastPanel.setVisible(true);
    }//GEN-LAST:event_broadcastButtonMouseClicked

    private void removeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeButtonMouseClicked
        // TODO add your handling code here:
        
        
        if (jComboBox1.getSelectedIndex() != 0)
        {
            warn1.setVisible(false);
            
            String moviename = jComboBox1.getSelectedItem().toString();
            Movie movietoremove = Movie.getMovie(moviename);
            String request = "DELETE FROM movies WHERE name='"+ jComboBox1.getSelectedItem().toString()+ "';";
        
            try{
                Statement stat = SQLDatabase.dbLink.createStatement();
                int executeDelete = stat.executeUpdate(request);
            
                stat.close();
                
                
                // Update catalog
                MovieProject.catalog.movie_list.clear();
                MovieProject.catalog.numberofmovies = 0;
                MovieProject.catalog.updateCatalog();
                
                // Update UI 
                String removelist[] = new String[MovieProject.catalog.numberofmovies + 1];
                removelist[0] = "Select the movie";
                for(int i = 1; i< removelist.length ; i++)
                {
                    removelist[i] = MovieProject.catalog.movie_list.get(i-1).getName();
                }
                jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>( removelist ));
                
                // Update UI Broadcast
                
                ArrayList<Diffusion> list = AllDiffusion.getCinemaDiffusion( jComboBox3.getSelectedItem().toString() );
                selectedDiffusion = list;
            
                String dremovelist[] = new String[list.size() + 1];
                dremovelist[0] = "Select the diffusion";
                DateFormat dateFormat = new SimpleDateFormat("dd MMMMM yyyy");
                for(int i = 1; i< dremovelist.length ; i++)
                {
                    dremovelist[i] = ""+ list.get(i-1).getMovie().getName() + " - " + dateFormat.format(list.get(i-1).getDay()) + " : " + list.get(i-1).getHours().toString();
                }
                jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>( dremovelist ));
                
                
                

            }
            catch(SQLException e)
            {
                if(MovieProject.debugMode) System.out.println("(Debug - ACube) An error as occured in the remove Movie Event.");
            }
            
            request = "DELETE FROM broadcast WHERE movieid='"+ movietoremove.getID()+ "';";
        
            try{
                Statement stat = SQLDatabase.dbLink.createStatement();
                int executeDelete = stat.executeUpdate(request);
            
                stat.close();
                
                
                // Update diffusion
                MovieProject.alldiffusion.diffusion_list.clear();
                MovieProject.alldiffusion.numberofdiffusion = 0;
                MovieProject.alldiffusion.updateDiffusion();
            }
            catch(SQLException e)
            {
                if(MovieProject.debugMode) System.out.println("(Debug - ACube) An error as occured in the remove Movie Event.");
            }
            
            
            
            App.updateUI();
            
        }
        else
        {
            warn1.setVisible(true);
        }
        
    }//GEN-LAST:event_removeButtonMouseClicked

    private void jComboBox3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox3FocusLost
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBox3FocusLost

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        // TODO add your handling code here:
        
        if(jComboBox3.getSelectedItem().equals("Select the cinema"))
        {
            jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None" }));

        }
        else
        {
            ArrayList<Diffusion> list = AllDiffusion.getCinemaDiffusion( jComboBox3.getSelectedItem().toString() );
            selectedDiffusion = list;
            
            String dremovelist[] = new String[list.size() + 1];
            dremovelist[0] = "Select the diffusion";
            DateFormat dateFormat = new SimpleDateFormat("dd MMMMM yyyy");
            for(int i = 1; i< dremovelist.length ; i++)
            {
               dremovelist[i] = ""+ list.get(i-1).getMovie().getName() + " - " + dateFormat.format(list.get(i-1).getDay()) + " : " + list.get(i-1).getHours().toString();
            }
             jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>( dremovelist ));
        }
    }//GEN-LAST:event_jComboBox3ItemStateChanged

    private void removeButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeButton2MouseClicked
        // TODO add your handling code here:
        
        
         if (jComboBox4.getSelectedIndex() != 0)
        {
            warn2.setVisible(false);
            
            int diffusionID = selectedDiffusion.get(jComboBox4.getSelectedIndex()-1).getID();
            String request = "DELETE FROM broadcast WHERE id = '"+diffusionID+"';";
        
            try{
                Statement stat = SQLDatabase.dbLink.createStatement();
                int executeDelete = stat.executeUpdate(request);
            
                stat.close();
                
                
                // Update diffusion
                MovieProject.alldiffusion.diffusion_list.clear();
                MovieProject.alldiffusion.updateDiffusion();
                MovieProject.alldiffusion.numberofdiffusion = 0;
                
                // Update UI 
                ArrayList<Diffusion> list = AllDiffusion.getCinemaDiffusion( jComboBox3.getSelectedItem().toString() );
                selectedDiffusion = list;
            
                String dremovelist[] = new String[list.size() + 1];
                dremovelist[0] = "Select the diffusion";
                DateFormat dateFormat = new SimpleDateFormat("dd MMMMM yyyy");
                for(int i = 1; i< dremovelist.length ; i++)
                {
                    dremovelist[i] = ""+ list.get(i-1).getMovie().getName() + " - " + dateFormat.format(list.get(i-1).getDay()) + " : " + list.get(i-1).getHours().toString();
                }
                jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>( dremovelist ));
                

            }
            catch(SQLException e)
            {
                if(MovieProject.debugMode) System.out.println("(Debug - ACube) An error as occured in the remove Diffusion Event.");
            }
       
            
            
            
            
        }
        else
        {
            warn2.setVisible(true);
        }
    }//GEN-LAST:event_removeButton2MouseClicked

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox4ItemStateChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBox4ItemStateChanged

    private void clickHereButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clickHereButtonMouseClicked
        // TODO add your handling code here:
        AddMovie movie = new AddMovie();
        
        movie.setVisible(true);
    }//GEN-LAST:event_clickHereButtonMouseClicked

    private void jComboBox5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox5ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ItemStateChanged

    private void jComboBox5FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox5FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5FocusLost

    private void jComboBox6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox6ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox6ItemStateChanged

    private void jComboBox6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox6FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox6FocusLost

    private void hoursFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_hoursFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_hoursFieldFocusLost

    private void clickHereButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clickHereButton2MouseClicked
        // TODO add your handling code here:
        
        boolean isValid = true;
        
        if(!inputValidation.isNotEmptyOrSpace(hoursField.getText()))
        {
            isValid = false;
        }
        
        if(jDateChooser1.getDate()==null)
        {
            isValid = false;
        }
        
        if(jComboBox5.getSelectedIndex()==0)
        {
            isValid = false;
        }
        
        if(jComboBox6.getSelectedIndex()==0)
        {
            isValid = false;
        }
        
        if(isValid)
        {
            warn3.setVisible(false);
            
            // Save the movie in the database
            Date date2 = new java.sql.Date(jDateChooser1.getDate().getTime());
            
            String request = "INSERT INTO broadcast (movieid, cinemaid, day, hours) VALUES ('"+Movie.getMovie(jComboBox5.getSelectedItem().toString()).getID()+"','"+Cinema.getCinema(jComboBox6.getSelectedItem().toString()).getId()+"','"+date2+"','"+hoursField.getText()+"');";
        
            try {
                
                Statement stat = SQLDatabase.dbLink.createStatement();
                int result = stat.executeUpdate(request);
                
                stat.close();
                if(MovieProject.debugMode) System.out.println("(Debug - ACube) Diffusion added succesfully !");
            }
            catch(SQLException e){
                if(MovieProject.debugMode) System.out.println("(Debug - ACube) An error as occured when the request to add a diffusion from App class was send.");
            }
            
            
            // Update diffusion
            MovieProject.alldiffusion.diffusion_list.clear();
            MovieProject.alldiffusion.updateDiffusion();
            MovieProject.alldiffusion.numberofdiffusion = 0;
            
            App.updateUI();
            DiffusionValid valid = new DiffusionValid();
            valid.setVisible(true);
            
            
        }
        else
        {
            warn3.setVisible(true);
            
            
        }
        
    }//GEN-LAST:event_clickHereButton2MouseClicked

    private void settingsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsButtonMouseClicked
        // TODO add your handling code here:
        
        JFileChooser choice = new JFileChooser();
        
        choice.setAcceptAllFileFilterUsed(false);
        PNGFilter filter = new PNGFilter();
        choice.addChoosableFileFilter(filter);
        
        int callback = choice.showDialog(this, "Save as profile image");
        try{
                
        if(callback ==JFileChooser.APPROVE_OPTION){
            
        
            //Save user file
            serverLink.uploadUserFile(choice.getSelectedFile().getAbsolutePath(), ClientInfo.ID);
            
            // SQL
            String request = "UPDATE customerAccount SET urlImageProfile='https://javacinema.mtxserv.com/profileimage/"+ClientInfo.ID+".png' WHERE client_id='"+ClientInfo.ID+"';";
        
            try{
                Statement stat = SQLDatabase.dbLink.createStatement();
                int executeUpdate = stat.executeUpdate(request);
            
                stat.close();
            
                if(MovieProject.debugMode) System.out.println("(Debug - ACube) Image saved in the database !");
            
            }
            catch(SQLException e)
            {
                if(MovieProject.debugMode) System.out.println("(Debug - ACube) An error as occured in the settings button click event.");
            }
            
            // Update UI
            App.updateUI();
            
            MovieProject.mainapp.MovieSelectioned.setVisible(false);
            MovieProject.mainapp.MoviesPanel.setVisible(false);
            MovieProject.mainapp.ProfileSelectionned.setVisible(true);
            MovieProject.mainapp.ProfilePanel.setVisible(true);
            
            
        } 
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this,"You must choose a png or jpg file !","PNG oR JPG FILE ONLY !",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_settingsButtonMouseClicked

    private void removeButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeButton1MouseClicked
        // TODO add your handling code here:
        
        
        if (jComboBox2.getSelectedIndex() != 0)
        {
            warn4.setVisible(false);
            
            String couponname = jComboBox2.getSelectedItem().toString();
            String request = "DELETE FROM coupons WHERE name='"+ couponname+ "';";
        
            try{
                Statement stat = SQLDatabase.dbLink.createStatement();
                int executeDelete = stat.executeUpdate(request);
            
                stat.close();
                
                
                // Update coupons
                MovieProject.allcoupon.coupon_list.clear();
                MovieProject.allcoupon.numberofcoupon = 0;
                MovieProject.allcoupon.updateCoupon();
                
                // Update UI 
                String list[] = new String[MovieProject.allcoupon.numberofcoupon + 1];
                list[0] = "Select the coupon";
                for(int i = 1; i< list.length ; i++)
                {
                    list[i] = MovieProject.allcoupon.coupon_list.get(i-1).getName();
                }
                jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>( list ));
                
                
                
                

            }
            catch(SQLException e)
            {
                if(MovieProject.debugMode) System.out.println("(Debug - ACube) An error as occured in the remove Movie Event.");
            }
 
        }
        else
        {
            warn4.setVisible(true);
        }
    }//GEN-LAST:event_removeButton1MouseClicked

    private void nameFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameFieldFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_nameFieldFocusLost

    private void clickHereButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clickHereButton1MouseClicked
        // TODO add your handling code here:
        
        
        boolean isValid = true;
        
        if(!inputValidation.isNotEmptyOrSpace(nameField.getText()))
        {
            isValid = false;
        }
        
        if(jComboBox7.getSelectedIndex()==0)
        {
            isValid = false;
        }
        
        if(isValid)
        {
            warn5.setVisible(false);
            
            int reduction[] = { 0, 5, 10, 20, 30, 40 };
            
            
            // Save the coupon in the database         
            String request = "INSERT INTO coupons (name, reduction) VALUES ('"+nameField.getText()+"', '"+reduction[jComboBox7.getSelectedIndex()]+"');";
        
            try {
                
                Statement stat = SQLDatabase.dbLink.createStatement();
                int result = stat.executeUpdate(request);
                
                stat.close();
                if(MovieProject.debugMode) System.out.println("(Debug - ACube) Coupon added succesfully !");
            }
            catch(SQLException e){
                if(MovieProject.debugMode) System.out.println("(Debug - ACube) An error as occured when the request to add a coupon from App class was send.");
            }
            
            
                // Update coupons
                MovieProject.allcoupon.coupon_list.clear();
                MovieProject.allcoupon.numberofcoupon = 0;
                MovieProject.allcoupon.updateCoupon();
                
                // Update UI 
                String list[] = new String[MovieProject.allcoupon.numberofcoupon + 1];
                list[0] = "Select the coupon";
                for(int i = 1; i< list.length ; i++)
                {
                    list[i] = MovieProject.allcoupon.coupon_list.get(i-1).getName();
                }
                jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>( list ));
            
            
        }
        else
        {
            warn5.setVisible(true);
            
            
        }
    }//GEN-LAST:event_clickHereButton1MouseClicked

    private void eventButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventButtonMouseClicked
        // TODO add your handling code here:
        new HelpPage().setVisible(true);
    }//GEN-LAST:event_eventButtonMouseClicked

    private void eventButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_eventButtonActionPerformed

    private void filterByGenreComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_filterByGenreComboBoxItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED){

            movielist = Catalog.getByGenreInCinema(filterByCinemaComboBox.getSelectedItem().toString(), filterByGenreComboBox.getSelectedItem().toString());

            displayMovies();
            revalidate();
            MoviesPanel.setVisible(false);
            MoviesPanel.setVisible(true);
        }
    }//GEN-LAST:event_filterByGenreComboBoxItemStateChanged

    private void filterByCinemaComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_filterByCinemaComboBoxItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED){

            movielist = Catalog.getByGenreInCinema(filterByCinemaComboBox.getSelectedItem().toString(), filterByGenreComboBox.getSelectedItem().toString());
            displayMovies();
            revalidate();
            MoviesPanel.setVisible(false);
            MoviesPanel.setVisible(true);
        }
    }//GEN-LAST:event_filterByCinemaComboBoxItemStateChanged

    private void upgradeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_upgradeButtonMouseClicked
        // TODO add your handling code here:
        
        new upgradeAccount().setVisible(true);
    }//GEN-LAST:event_upgradeButtonMouseClicked

    private void recordsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recordsButtonMouseClicked
        // TODO add your handling code here:
        
        Records.getAllRecords();
        if(MovieProject.debugMode) System.out.println("Record file created.");
    }//GEN-LAST:event_recordsButtonMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AdminSelectionned;
    private javax.swing.JPanel AdministrationPanel;
    private javax.swing.JPanel CinemaPanel;
    private javax.swing.JLabel CinemaSelectioned;
    private javax.swing.JPanel Connected;
    private javax.swing.JPanel Disconnected;
    private javax.swing.JLabel MovieSelectioned;
    protected javax.swing.JPanel MoviesPanel;
    protected javax.swing.JPanel OneMoviePanel;
    private javax.swing.JPanel ProfilePanel;
    private javax.swing.JLabel ProfileSelectionned;
    private javax.swing.JButton SelectAdministration;
    private javax.swing.JButton SelectCinema;
    private javax.swing.JButton SelectMovies;
    private javax.swing.JButton SelectProfile;
    private javax.swing.JPanel SelectionPanel;
    private javax.swing.JLabel background;
    private javax.swing.JLabel backgroundPanel;
    private javax.swing.JLabel backgroundPanel1;
    private javax.swing.JLabel backgroundSelectedPanel;
    private javax.swing.JLabel birtdayValueLabel;
    private javax.swing.JButton broadcastButton;
    private javax.swing.JPanel broadcastPanel;
    private javax.swing.JButton clickHereButton;
    private javax.swing.JButton clickHereButton1;
    private javax.swing.JButton clickHereButton2;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton connectButton;
    private javax.swing.JButton couponButton;
    private javax.swing.JPanel couponPanel;
    private javax.swing.JLabel coupons_backgroundPanel;
    private javax.swing.JLabel emailValueLabel;
    private javax.swing.JButton eventButton;
    private javax.swing.JComboBox<String> filterByCinemaComboBox;
    private javax.swing.JComboBox<String> filterByGenreComboBox;
    private javax.swing.JPanel gridPanel;
    private javax.swing.JButton hereButton;
    private javax.swing.JTextField hoursField;
    private javax.swing.JLabel imageProfile;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton loginButton;
    private javax.swing.JButton movieButton;
    private javax.swing.JPanel moviePanel;
    private javax.swing.JLabel movie_backgroundPanel;
    private javax.swing.JLabel movie_backgroundPanel1;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel premiumLabel;
    private javax.swing.JLabel premiumValueLabel;
    private javax.swing.JButton recordsButton;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton removeButton1;
    private javax.swing.JButton removeButton2;
    private javax.swing.JButton settingsButton;
    private javax.swing.JLabel statistics_backgroundPanel;
    private javax.swing.JButton statsButton;
    private javax.swing.JPanel statsPanel;
    private javax.swing.JButton unshowButton;
    private javax.swing.JButton upgradeButton;
    private javax.swing.JLabel warn1;
    private javax.swing.JLabel warn2;
    private javax.swing.JLabel warn3;
    private javax.swing.JLabel warn4;
    private javax.swing.JLabel warn5;
    // End of variables declaration//GEN-END:variables

}
