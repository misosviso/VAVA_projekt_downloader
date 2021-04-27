/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.models;

import java.io.Serializable;
import java.text.DecimalFormat;
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
        RecordManager.getInstanceOfSelf().updateTables();
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
        long rawSec = TimeUnit.MILLISECONDS.toSeconds(timeElapsed) % 60;
        long rawMin = TimeUnit.MILLISECONDS.toMinutes(timeElapsed) % 60;
        long rawHour = TimeUnit.MILLISECONDS.toHours(timeElapsed) % 60;
        String sec = String.format("%02d", rawSec);
        String min = String.format("%02d", rawMin);
        String hour = String.format("%02d", rawHour);
        return hour + ":" + min + ":" + sec;
    }
    
    public String getStringDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
        return formatter.format(this.startingDate);
    }
    
    public String getStringSize(){
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("size = " + size);
        float kBsize = (float)size / (float)1024;
        float MBsize = (float)kBsize / (float)1024;
        float GBsize = (float)MBsize / (float)1024;
        if(GBsize > 1){
            return df.format(GBsize) + "GB";
        } else if(MBsize > 1){
            return df.format(MBsize) + "MB";
        } else{
            return df.format(kBsize) + "kB";
        }
        
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
