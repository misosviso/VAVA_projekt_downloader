/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.models;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import sk.stu.fiit.views.MainView;

/**
 * Class which checks for progress
 * @author Admin
 */
public class DownloadProgressChecker extends Thread{
    
    private final Downloader objDownloader;
    private final int[] downloadState;
    private MainView view;

    public DownloadProgressChecker(Downloader objDownloader) throws IOException {
        this.downloadState = new int[]{0, objDownloader.getTotalSize()};
        this.objDownloader = objDownloader;
    }

    public DownloadProgressChecker(Downloader objDownloader, MainView view) {
        this.objDownloader = objDownloader;
        this.downloadState = new int[]{0, objDownloader.getTotalSize()};
        this.view = view;
    }
    
    @Override
    public void run() {
        while(objDownloader.isAlive() && !Thread.currentThread().isInterrupted()){
            try 
            {
                downloadState[0] = objDownloader.getDownloaded();
                this.view.updateProgress(downloadState);
                sleep(2500);
            } catch (InterruptedException ex) {
                Logger.getLogger(DownloadProgressChecker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        downloadState[0] = objDownloader.getDownloaded();
        this.view.updateProgress(downloadState);
    }

    
}
