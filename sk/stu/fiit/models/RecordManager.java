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
import sk.stu.fiit.views.MainView;

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
    private MainView view;

    public RecordManager() {
    }
    
    public void setUpView(MainView view){
        this.view = view;
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
            if(!record.isInterrupted() && Unzipper.isZip(record.getFilePath())){
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
        updateTables();
    }

    public void unzip(int selectedZipIndex, String destinationPath, boolean deleteOriginalZip) throws IOException, NotZipException {
        String filename = getZips().get(selectedZipIndex).getFilePath();
        if(!Unzipper.isZip(filename)){
            throw new NotZipException();
        }
        Unzipper.unzip(filename, destinationPath, deleteOriginalZip);
    }
    
    public void save() throws IOException{
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

    public void updateTables() {
        view.updateTables();
    }
    
}
