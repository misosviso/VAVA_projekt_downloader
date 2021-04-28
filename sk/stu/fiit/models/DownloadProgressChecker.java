/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.models;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import sk.stu.fiit.views.MainView;

/**
 * Class which checks for progress
 * @author Admin
 */
public class DownloadProgressChecker extends Thread{
    
    private final Downloader objDownloader;
    private final MainView view;

    public DownloadProgressChecker(Downloader objDownloader, MainView view) {
        this.objDownloader = objDownloader;
        this.view = view;
    }

    @Override
    public void run() {
        while(!isInterrupted() && objDownloader.isAlive()){
            try 
            {
                this.view.showDownloadDetail();
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(DownloadProgressChecker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.view.eraseDetailFields();
    }

    
}
