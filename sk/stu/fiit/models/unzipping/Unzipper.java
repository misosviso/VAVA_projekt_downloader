/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.models.unzipping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 * @author Admin
 */
public class Unzipper {
    
    /**
     * method which unzips archive
     * @param source path to zip file
     * @param destination path to destination directory
     * @throws IOException 
     */
    public static void unzip(String source, String destination) throws IOException{
        
        File destDir = new File(destination);
        byte[] buffer = new byte[1024];
        
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(source))) {
            
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {

                File newFile = new File(destDir, zipEntry.getName());
                
                // for the .zip file itself
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } 
                // for the files of the .zip file
                else {
                    File parent = newFile.getParentFile();
                    
                    // if parent(.zip file) is not directory -> make it a directory
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }
                    
                    try (FileOutputStream fileOS = new FileOutputStream(newFile)) {
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fileOS.write(buffer, 0, len);
                        }
                    }
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        } 
        
        deleteFile(source);
        
    }
    
    private static void deleteFile(String filename){
        File objFile = new File(filename);
        objFile.delete();
    }
    
    public static boolean isZip(String zipFile) throws IOException{
        String contentType = Files.probeContentType(Paths.get(zipFile));
        System.out.println("contentType = " + contentType);
        return (contentType.equals("application/x-zip-compressed"));
    }
    
}
