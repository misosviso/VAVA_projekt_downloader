/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.models;

import sk.stu.utils.UniversalFormatter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class which is responsible for downloading file from URL address
 * @author Admin
 */
public class Downloader extends Thread implements TableModelItem, Logging{

    private final int downloaderID;
    private final String source;
    private final String destination;
    private volatile boolean running = true;
    private long totalSize = 0;
    private long downloaded = 0;
    private final Date startDate;
    private long sleepingTime;
    private final Logger LOGGER;
    
    
    /**
     * Constructor
     * @param id ID of downloading task
     * @param source URL address
     * @param destination path to the destination file
     * @throws MalformedURLException if the user given URL is not valid
     */

    public Downloader(int id, String source, String destination, Logger LOGGER) throws MalformedURLException, IOException {
        this.sleepingTime = 0;
        this.downloaderID = id;
        this.source = source;
        this.destination = destination;
        this.totalSize = new URL(source).openConnection().getContentLength();
        this.startDate = new Date();
        this.LOGGER = LOGGER;
    }
    
    public int getDownloaderId() {
        return downloaderID;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public Date getStartDate() {
        return startDate;
    }
   
    @Override
    public void run() {
        System.out.println("totalSize = " + totalSize);
        LOGGER.log(Level.INFO, "Zacatie stahovania ID: " + downloaderID + ", url: " + source + " destinacia: " + destination);
        try (BufferedInputStream inputStream = new BufferedInputStream(new URL(source).openStream());
        FileOutputStream fileOS = new FileOutputStream(destination);) {
            byte data[] = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1 && !isInterrupted()) {
                downloaded += byteContent;
                fileOS.write(data, 0, byteContent);
                sleep(5000);
                while(!running){
                    Downloader.yield();
                    sleep(1000);
                    sleepingTime += 1000;
                }
            }
            
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "IO chyba pri stahovani", ex);
            interrupt();
        } catch (InterruptedException ex) {
            LOGGER.log(Level.SEVERE, "ID: " + downloaderID + " - Stahovanie bolo prerusene", ex);
            interrupt();
        }
        
        if(isInterrupted()){
            new File(destination).delete();
        }
        System.out.println("isInterrupted = " + isInterrupted());
        RecordManager.getRecordManager().addRecord(this);
        DownloadManager.getDownloadManager().remove(this);
    }

    public long getDownloaded() {
        return downloaded;
    }
    
    public long getTotalSize() {
        return totalSize;
    }
    
    public void pauseDownloading() throws InterruptedException{
        LOGGER.log(Level.INFO, "ID: " + downloaderID + " - Stahovanie bolo pozastavene pouzivatelom");
        
        running = false;
    }

    public void resumeDownloading() {
        LOGGER.log(Level.INFO, "ID: " + downloaderID + " - Stahovanie bolo obnovene pouzivatelom");
        running = true;
    }
    
    public void cancelDownloading(){
        interrupt();
    }

    public Date getDate() {
        return this.startDate;
    }

    public String getUrl() {
        return source;
        
    }

    public String getFilePath() {
        return destination;
    }

    @Override
    public Object[] getDataRow() {
        String strID = String.valueOf(this.downloaderID);
        String stringStartingDate = getStringDate();
        String stringStatus = getStringStatus();
        String stringSize = getStringSize();
        String stringTimeElapsed = getStringTimeElapsed();
        
        Object[] row = {strID, stringStartingDate, source, destination, stringStatus, stringSize, stringTimeElapsed};
        
        return row;
    }

    public String getStringStatus() {
        if(running){
            return "Prebieha sťahovanie";
        }
        return "Zapauzované";
    }

    public String getStringTimeElapsed() {
        return UniversalFormatter.formatTimeElapsed(startDate, sleepingTime);
    }
    
    public String getStringSize(){
        return UniversalFormatter.formatSize(totalSize);
    }

    public String getStringEstTime() {
        long timeElapsed = new Date().getTime() - startDate.getTime() - sleepingTime;
        long estimatedTime = (long) ((float)timeElapsed / (float)downloaded * (float)totalSize);
        return UniversalFormatter.formatTimeElapsed(estimatedTime);
    }

    public String getStringDate() {
        return UniversalFormatter.formatDate(startDate);
    }
}
