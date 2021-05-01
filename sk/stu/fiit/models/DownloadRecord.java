/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.models;

import sk.stu.fiit.utils.UniversalFormatter;
import java.io.Serializable;
import java.util.Date;

/**
 * Class that holds information about one particular finished download(completed/interrupted)
 * @author Admin
 */
public class DownloadRecord implements TableModelItem, Serializable{
    
    private int ID;
    private Date startingDate;
    private String url;
    private String filePath;
    private boolean interrupted;
    private long size;
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
    
    public String getStringStatus(){
        if(interrupted){
            return "prerušené";
        }
        return "dokončené";
    }
    
    public String getStringTimeElapsed(){
        return UniversalFormatter.formatTimeElapsed(timeElapsed);
    }
    
    public String getStringDate(){
        return UniversalFormatter.formatDateTime(startingDate);
    }
    
    public String getStringSize(){
        return UniversalFormatter.formatSize(size);
    }

    @Override
    public Object[] getDataRow() {
        
        String strID = String.valueOf(this.ID);
        String stringStartingDate = getStringDate();
        String stringStatus = getStringStatus();
        String stringSize = getStringSize();
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

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
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
