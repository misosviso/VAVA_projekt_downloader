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

    public static RecordManager getDownloadedManager() {
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

    public RecordManager() {
    }

    public List<TableModelItem> getDownloadedModel() {
        return (List<TableModelItem>) (Object) records;
    }
    
    private List<DownloadRecord> getZips() throws IOException{
       List<DownloadRecord> zipDownloads = new LinkedList<>();
        for (DownloadRecord record : records) {
            if(Unzipper.isZip(record.getFilePath())){
                zipDownloads.add(record);
            }
        } 
        return zipDownloads;
    } 
    
    public List<TableModelItem> getZipsModel() throws IOException{
        return (List<TableModelItem>) (Object) getZips();
    }

    public void addRecord(Downloader objDownloader) {
        DownloadRecord objDownloadedRecord = new DownloadRecord(objDownloader);
        records.add(objDownloadedRecord);
        System.out.println(Arrays.toString(objDownloadedRecord.getDataRow()));
        try {
            save();
        } catch (IOException ex) {
            Logger.getLogger(RecordManager.class.getName()).log(Level.SEVERE, "Nepodarilo sa serializovat", ex);
        }
    }

    public void unzip(int selectedZipIndex, String destinationPath) throws IOException, NotZipException {
        String filename = getZips().get(selectedZipIndex).getFilePath();
        if(!Unzipper.isZip(filename)){
            throw new NotZipException();
        }
        Unzipper.unzip(filename, destinationPath);
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
    
}
