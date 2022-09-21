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
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Alexis
 */
public class PNGFilter extends FileFilter {
    
    private String description;
    private String[] extensions;
    
    /**
     * EN : Create a PNG/JPG/JPEG filter extends of FileFilter. <br>
     * FR : Créé un PNG/JPG/JPEG fitre extention de FileFilter.
     */
    public PNGFilter()
    {
        description = "Image file : png only";
        extensions = new String[] {"png","jpg","jpeg"};
    }
    
    
    @Override
    public String getDescription()
    {
        return description;
    }
    
    /**
     * EN : Accept method overrided. <br>
     * FR : Methode accept overridé.
     * @param f The file you want to apply the filter to
     * @return <code>true</code> if accepted or <code>false</code> if not
     */
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) return true;
        String filename = f.getName();
        String ext = null;
        int pointposition = filename.lastIndexOf('.');
        
        if(pointposition > 0 && pointposition < filename.length() - 1)
            ext = filename.substring(pointposition+1).toLowerCase();
        
        return ext != null && isInFilter(ext);
        
    }
    /**
     * EN : Check if the extension is in accepted extension. <br>
     * FR : Vérifie si l'extension est dans celle autorisée.
     * @param ext The extension
     * @return <code>true</code> if it's inside <code>false</code> if not
     */
    private boolean isInFilter(String ext) {
        for( int i = 0; i<extensions.length; ++i)
            if(ext.equals(extensions[i]))
                return true;
            return false;
    }
    
}
