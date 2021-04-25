/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Admin
 */
public class DownloadRecord implements TableModelItem, Serializable{
    
    private final int ID;
    private final Date startingDate;
    private final String url;
    private final String filePath;
    private final boolean interrupted;
    private final int size;

    public String getFilePath() {
        return filePath;
    }
    private final long timeElapsed;

    public DownloadRecord(Downloader downloader) {
        this.ID = downloader.getDownloaderId();
        this.startingDate = (Date) downloader.getDate();
        this.url = downloader.getUrl();
        this.filePath = downloader.getFilePath();
        this.interrupted = downloader.isInterrupted();
        this.size = downloader.getTotalSize();
        this.timeElapsed = new Date().getTime() - downloader.getDate().getTime();
    }
    
    private String getStringStatus(){
        if(interrupted){
            return "prerušené";
        }
        return "dokončené";
    }
    
    private String getStringTimeElapsed(){
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeElapsed) % 60;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeElapsed) % 60;
        long hours = TimeUnit.MILLISECONDS.toHours(timeElapsed) % 60;
        return String.valueOf(hours) + ":" + String.valueOf(minutes) + ":" + String.valueOf(seconds);
    }

    @Override
    public Object[] getDataRow() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
        String strID = String.valueOf(this.ID);
        String stringStartingDate = formatter.format(this.startingDate);
        String stringStatus = getStringStatus();
        String stringSize = String.valueOf(this.size);
        String stringTimeElapsed = getStringTimeElapsed();
        
        Object[] row = {strID, stringStartingDate, url, filePath, stringStatus, stringSize, stringTimeElapsed};
        
        return row;
    }
    
    
    
    
    
}
