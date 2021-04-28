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
    
    private Downloader objDownloader;
    private final long[] downloadState;
    private final MainView view;

    public DownloadProgressChecker(Downloader objDownloader, MainView view) {
        this.objDownloader = objDownloader;
        this.downloadState = new long[]{0, objDownloader.getTotalSize()};
        this.view = view;
    }

    public void setObjDownloader(Downloader objDownloader) {
        this.objDownloader = objDownloader;
    }

    @Override
    public void run() {
        while(!isInterrupted()){
            try 
            {
                downloadState[0] = objDownloader.getDownloaded();
                this.view.updateProgress(downloadState);
                sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(DownloadProgressChecker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
}
