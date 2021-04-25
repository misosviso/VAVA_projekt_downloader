/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.models;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class which is responsible for downloading file from URL address
 * @author Admin
 */
public class Downloader extends Thread implements TableModelItem{

    private final int downloaderID;
    private final String source;
    private final String destination;
    private volatile boolean running = true;
    private int totalSize = 0;
    private int downloaded = 0;
    private final Date startDate;
    
    /**
     * Constructor
     * @param id ID of downloading task
     * @param source URL address
     * @param destination path to the destination file
     * @throws MalformedURLException if the user given URL is not valid
     */

    public Downloader(int id, String source, String destination) throws MalformedURLException, IOException {
        this.downloaderID = id;
        this.source = source;
        this.destination = destination;
        this.totalSize = new URL(source).openConnection().getContentLength();
        this.startDate = new Date();
    }
    
    public int getDownloaderId() {
        return downloaderID;
    }
   
    @Override
    public void run() {
        System.out.println("this.source = " + this.source);
        System.out.println("this.destination = " + this.destination);
        try (BufferedInputStream inputStream = new BufferedInputStream(new URL(source).openStream());
        FileOutputStream fileOS = new FileOutputStream(destination);) {
            System.out.println("sem sa dostanem");
            byte data[] = new byte[1024];
            int byteContent;
            
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1 && !isInterrupted()) {
                downloaded += byteContent;
                fileOS.write(data, 0, byteContent);
                while(!running){
                    Downloader.yield();
                    sleep(5000);
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, "Nepodarilo sa zapisovat do suporu", ex);
        } catch (InterruptedException ex) {
            System.out.println("Stahovanie bolo interruptnute");
            Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, "Thread downloadera bol preruseny", ex);
        }
        
        if(isInterrupted()){
            new File(destination).delete();
        }
        
        RecordManager.getDownloadedManager().addRecord(this);
    }

    public int getDownloaded() {
        return downloaded;
    }
    
    public int getTotalSize() {
        return totalSize;
    }
    
    public void pauseDownloading() throws InterruptedException{
        running = false;
    }

    public void resumeDownloading() {
        running = true;
    }
    
    public void cancelDownloading() throws InterruptedException{
        System.out.println("pospim 5 sekund a interruptnem");
        sleep(5000);
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
        String strID = String.valueOf(this.downloaderID);
        String stringStartingDate = formatter.format(this.startDate);
        String stringStatus = getStringStatus();
        String stringSize = String.valueOf(this.totalSize);
        String stringTimeElapsed = getStringTimeElapsed();
        
        Object[] row = {strID, stringStartingDate, source, destination, stringStatus, stringSize, stringTimeElapsed};
        
        return row;
    }

    private String getStringStatus() {
        if(running){
            return "Prebieha sťahovanie";
        }
        return "Zapauzované";
    }

    private String getStringTimeElapsed() {
        long timeElapsed = new Date().getTime() - startDate.getTime();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeElapsed) % 60;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeElapsed) % 60;
        long hours = TimeUnit.MILLISECONDS.toHours(timeElapsed) % 60;
        return String.valueOf(hours) + ":" + String.valueOf(minutes) + ":" + String.valueOf(seconds);
    }
}
