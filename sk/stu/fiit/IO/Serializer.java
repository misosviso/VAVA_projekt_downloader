/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import sk.stu.fiit.models.RecordManager;

/**
 *
 * @author Admin
 */
public class Serializer {
    
    public static void serialize(RecordManager objRM) throws IOException{
        String filename = "history.txt";
        File objSerializationFile = new File(filename);
        
        try (FileOutputStream fileOutputStream = new FileOutputStream(objSerializationFile); 
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(objRM);
        }
    }
    
    public static RecordManager load() throws IOException{
        String filename = "history.txt";
        File objSerializationFile = new File(filename);
        
        try (FileInputStream fileInputStream = new FileInputStream(objSerializationFile); ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            RecordManager objManager = (RecordManager) objectInputStream.readObject();
            return objManager;
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }
    
    
 
    
}
