/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.stu.fiit.controllers;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import sk.stu.fiit.views.MainView;

/**
 *
 * @author Admin
 */
public class DateTimeController {
    
    public static void countDateAndTime(JLabel dateLbl, JLabel timeLbl, MainView view){
        new Thread(){
            @Override
            public void run() {
                while (true) {
                    Calendar cal = new GregorianCalendar();
                    
                    int day = cal.get(Calendar.DATE);
                    int month = cal.get(Calendar.MONTH);
                    int year = cal.get(Calendar.YEAR);
                    dateLbl.setText(day + "." + (month + 1) + "." + year);
                    
                    int rawHour = cal.get(Calendar.HOUR_OF_DAY);
                    int rawMin = cal.get(Calendar.MINUTE);
                    int rawSec = cal.get(Calendar.SECOND);
                    
                    String sec = String.format("%02d", rawSec);
                    String min = String.format("%02d", rawMin);
                    String hour = String.format("%02d", rawHour);
                    timeLbl.setText(hour + ":" + min + ":" + sec);
                    view.displayDownloadTable();
                    view.displayState();
                    
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DateTimeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
    }
}
