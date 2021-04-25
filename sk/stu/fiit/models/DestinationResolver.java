/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.models;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import sk.stu.fiit.exceptions.NoFileSelected;

/**
 * Class that gets path and name of the file for downloading from user
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
     * @return path to unzip destination
     */
    public static String getUnzipPath(){
        
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(null);
        String path = null;
        
        if (result == JFileChooser.APPROVE_OPTION) {
            path = fileChooser.getSelectedFile().getAbsolutePath();
        }
        
        return path;
        
    }
}
