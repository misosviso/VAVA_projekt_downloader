/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.models;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sk.stu.fiit.IO.Serializer;
import sk.stu.fiit.exceptions.NotZipException;
import sk.stu.fiit.models.unzipping.Unzipper;

/**
 *
 * @author Admin
 */
public class RecordManager implements Serializable{

    public static RecordManager getRecordManager() {
        if(instanceOfSelf == null){
            try {
                instanceOfSelf = Serializer.XMLload();
            } catch (IOException ex) {
                instanceOfSelf = new RecordManager();
            }
        }
        return instanceOfSelf;
    }
    
    private List<DownloadRecord> records = new LinkedList<>();
    private static RecordManager instanceOfSelf;
    private int lastID;

    public RecordManager() {
    }

    public int getLastID() {
        return lastID;
    }

    public void setLastID(int lastID) {
        this.lastID = lastID;
    }

    public List<TableModelItem> getDownloadedModel() {
        return (List<TableModelItem>) (Object) records;
    }
    
    private List<DownloadRecord> getZips() throws IOException{
       List<DownloadRecord> zipDownloads = new LinkedList<>();
        for (DownloadRecord record : records) {
            if(Unzipper.isZip(record.getFilePath()) && !record.isInterrupted()){
                zipDownloads.add(record);
            }
        } 
        return zipDownloads;
    } 
    
    public List<TableModelItem> getZipsModel() throws IOException{
        return (List<TableModelItem>) (Object) getZips();
    }

    public void addRecord(Downloader objDownloader) {
        DownloadRecord objDownloadRecord = new DownloadRecord(objDownloader);
        records.add(0, objDownloadRecord);
        System.out.println(Arrays.toString(objDownloadRecord.getDataRow()));
        try {
            save();
        } catch (IOException ex) {
            Logger.getLogger(RecordManager.class.getName()).log(Level.SEVERE, "Nepodarilo sa serializovat", ex);
        }
    }

    public void unzip(int selectedZipIndex, String destinationPath, boolean deleteOriginalZip) throws IOException, NotZipException {
        String filename = getZips().get(selectedZipIndex).getFilePath();
        if(!Unzipper.isZip(filename)){
            throw new NotZipException();
        }
        Unzipper.unzip(filename, destinationPath, deleteOriginalZip);
    }
    
    private void save() throws IOException{
        Serializer.XMLserialize(this);
    }
    
    public List<DownloadRecord> getRecords() {
        return records;
    }

    public void setRecords(List<DownloadRecord> records) {
        this.records = records;
    }

    public static RecordManager getInstanceOfSelf() {
        return instanceOfSelf;
    }

    public static void setInstanceOfSelf(RecordManager instanceOfSelf) {
        RecordManager.instanceOfSelf = instanceOfSelf;
    }

    public DownloadRecord getSpecific(int selectedRecordIndex) {
        return records.get(selectedRecordIndex);
    }
    
    public int generateNewIndex(){
        lastID++;
        System.out.println("lastID = " + lastID);
        return lastID;
    }
    
}
