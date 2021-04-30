/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Class which is used for formatting size, date and time to string
 * @author Admin
 */
public class UniversalFormatter {
    
    /**
     * format long size to String size with unit(GB, MB, kB)
     * @param size size in Bytes
     * @return String size + unit
     */
    public static String formatSize(long size){
        DecimalFormat df = new DecimalFormat("0.00");
        
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
    
    /**
     * format elapsed time
     * @param startDate start of measuring time
     * @param sleepingTime time which the download was paused
     * @return elapsed time in format hh:mm:ss
     */
    public static String formatTimeElapsed(Date startDate, long sleepingTime){
        long timeElapsed = new Date().getTime() - startDate.getTime() - sleepingTime;
        long rawSec = TimeUnit.MILLISECONDS.toSeconds(timeElapsed) % 60;
        long rawMin = TimeUnit.MILLISECONDS.toMinutes(timeElapsed) % 60;
        long rawHour = TimeUnit.MILLISECONDS.toHours(timeElapsed) % 60;
        String sec = String.format("%02d", rawSec);
        String min = String.format("%02d", rawMin);
        String hour = String.format("%02d", rawHour);
        return String.valueOf(hour) + ":" + String.valueOf(min) + ":" + String.valueOf(sec);
    }
    
    /**
     * format elapsed time
     * @param timeElapsed elapsed time in miliseconds
     * @return elapsed time in format hh:mm:ss
     */
    public static String formatTimeElapsed(long timeElapsed){
        long rawSec = TimeUnit.MILLISECONDS.toSeconds(timeElapsed) % 60;
        long rawMin = TimeUnit.MILLISECONDS.toMinutes(timeElapsed) % 60;
        long rawHour = TimeUnit.MILLISECONDS.toHours(timeElapsed) % 60;
        String sec = String.format("%02d", rawSec);
        String min = String.format("%02d", rawMin);
        String hour = String.format("%02d", rawHour);
        return String.valueOf(hour) + ":" + String.valueOf(min) + ":" + String.valueOf(sec);
    }
    
    /**
     * format date to HH:mm:ss - dd. MM. yyyy
     * @param startingDate
     * @return 
     */
    public static String formatDate(Date startingDate){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss - dd. MM. yyyy");  
        return formatter.format(startingDate);
    }
}
