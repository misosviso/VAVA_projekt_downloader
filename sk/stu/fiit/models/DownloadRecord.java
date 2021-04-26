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
    
    private int ID;
    private Date startingDate;
    private String url;
    private String filePath;
    private boolean interrupted;
    private int size;
    private long timeElapsed;

    public DownloadRecord(Downloader downloader) {
        this.ID = downloader.getDownloaderId();
        this.startingDate = (Date) downloader.getDate();
        this.url = downloader.getUrl();
        this.filePath = downloader.getFilePath();
        this.interrupted = downloader.isInterrupted();
        this.size = downloader.getTotalSize();
        this.timeElapsed = new Date().getTime() - downloader.getDate().getTime();
    }

    public DownloadRecord() {
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
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isInterrupted() {
        return interrupted;
    }

    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }
    
}
