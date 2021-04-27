/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.models;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import sk.stu.fiit.exceptions.NoFileSelected;
import sk.stu.fiit.models.unzipping.Unzipper;

/**
 * Class that gets path and name of the file from user by using JFileChooser
 * @author Admin
 */
public class DestinationResolver {
    
    /**
     * get name of the downloaded file from URL address
     * @param urlSpec String URL address
     * @return String filename
     * @throws MalformedURLException if URL address is not valid
     */
    private static String getDefaultPath(String dirPath, String urlSpec) throws MalformedURLException{
        
        URL fileUri = new URL(urlSpec);    
        int startIndex = fileUri.toString().lastIndexOf('/');
        int lastIndex = fileUri.toString().lastIndexOf('.');
        String filename = fileUri.toString().substring(startIndex + 1, lastIndex);
        String extension = fileUri.toString().substring(lastIndex);
        String completeFilename = filename + extension;
        
        int fileIndex = 1;
        while(new File(dirPath + "\\" + completeFilename).exists()){
            String differentiator = "(" + fileIndex + ")";
            completeFilename = filename + differentiator + extension;
            fileIndex++;
        }
        
        return dirPath + "\\" + completeFilename;
    }
    
    /**
     * 
     * @param startingFile
     * @return
     * @throws NoFileSelected 
     */
    private static String getPathFromUser(File startingFile) throws NoFileSelected{
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(startingFile);
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            return filePath;
        }
        
        throw new NoFileSelected();
    }
    
    /**
     * get path to download destination
     * @param urlSpec String URL address
     * @return String path + filename
     * @throws MalformedURLException if URL address is not valid
     * @throws sk.stu.fiit.exceptions.NoFileSelected
     */
    public static String getDownloadPath(String urlSpec) throws MalformedURLException, NoFileSelected{
        
        String defaultDirectoryPath = System.getProperty("user.home");
        String defaultFilePath = getDefaultPath(defaultDirectoryPath, urlSpec);
        File defaultFile = new File(defaultFilePath);
        
        String userFilePath = getPathFromUser(defaultFile);
        
        while(new File(userFilePath).exists()){
            int userInput = JOptionPane.showConfirmDialog(null, "Súbor s týmto menom už existuje aj tak pokračovať?", "Upozornenie", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if(userInput == JOptionPane.YES_OPTION){
                return userFilePath;
            }
            userFilePath = getPathFromUser(new File(userFilePath));
        }
        
        System.out.println("userFilePath = " + userFilePath);
        return userFilePath;
    }
    
    /**
     * get path to unzip destination
     * @param zipPath
     * @return path to unzip destination
     */
    public static String getUnzipPath(String zipPath){
        File objZipFile = new File(zipPath);
        int lastIndex = objZipFile.getName().lastIndexOf('.');
        String dirName = objZipFile.getName().substring(0, lastIndex);
        String dirPath = objZipFile.getParent(); // + "\\" + dirName;
        
        File objDirFile = new File(dirPath);
        
        JFileChooser fileChooser = new JFileChooser(new File(dirPath));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showSaveDialog(null);
        String path = null;
        
        if (result == JFileChooser.APPROVE_OPTION) {
            path = fileChooser.getSelectedFile().getAbsolutePath();
        }
        
        System.out.println("path = " + path);
        return path;
        
    }
    
    public static void main(String[] args) {
        try {
            String zipPath = "C:\\Users\\42194\\Desktop\\unzip.zip";
            String unzipPath = DestinationResolver.getUnzipPath(zipPath);
            Unzipper.unzip(zipPath, unzipPath, false);
        } catch (IOException ex) {
            Logger.getLogger(DestinationResolver.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
