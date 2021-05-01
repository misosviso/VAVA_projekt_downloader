/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import sk.stu.fiit.exceptions.NoFileSelected;

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
        if(startIndex + 1 > lastIndex){
            return dirPath + "\\" + "untitled";
        }
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
     * get downloading destination path from user by using JFileChooser
     * @param startingFile file where JFileChooser will start
     * @return path
     * @throws NoFileSelected user cancels JFileChooser - no file was chosen
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
     * get downloading destination from user
     * @param urlSpec String URL address
     * @return String path + filename
     * @throws MalformedURLException if URL address is not valid
     * @throws sk.stu.fiit.exceptions.NoFileSelected User did not select any file
     */
    public static String getDownloadPath(String urlSpec) throws MalformedURLException, NoFileSelected{
        
        String defaultDirectoryPath = System.getProperty("user.home") + "\\";
        String defaultFilePath = getDefaultPath(defaultDirectoryPath, urlSpec);
        String userFilePath = getPathFromUser(new File(defaultFilePath));
        
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
     * @param zipPath path to zip file
     * @return path to unzip destination
     * @throws sk.stu.fiit.exceptions.NoFileSelected User did not select any destination
     */
    public static String getUnzipPath(String zipPath) throws NoFileSelected{
        File objZipFile = new File(zipPath);
        String dirPath = objZipFile.getParent();
        
        JFileChooser fileChooser = new JFileChooser(new File(dirPath));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int result = fileChooser.showSaveDialog(null);
        
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        
        throw new NoFileSelected();
        
    }
    
}
