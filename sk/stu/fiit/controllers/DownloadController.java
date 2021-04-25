/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;
import sk.stu.fiit.models.DownloadManager;
import sk.stu.fiit.models.DownloadProgressChecker;
import sk.stu.fiit.models.Downloader;
import sk.stu.fiit.views.MainView;

/**
 *
 * @author Admin
 */
public class DownloadController implements CustomTableModel{
    
    private final DownloadManager manager = DownloadManager.getDownloadManager();
    private final MainView view;

    public DownloadController(MainView view) {
        this.view = view;
    }
    
    public void download(String urlString, String pathString) throws MalformedURLException, IOException {
        Downloader objDownloader = manager.download(urlString, pathString);
//        DownloadProgressChecker downloadProgressChecker = new DownloadProgressChecker(objDownloader, this.view);
//        downloadProgressChecker.start();
    }
    
    public void pauseDownloading(int ID) throws InterruptedException, IOException {
        manager.pauseDownloading(ID);
    }

    public void resumeDownloading(int ID) throws InterruptedException, IOException {
        manager.resumeDownloading(ID);
    }
    
    public DefaultTableModel getDownloading(){
        return new DefaultTableModel(getTableData(this.manager.getDownloading()), 
                new Object[]{"ID", "Dátum", "URL adresa", "Destinácia", "Status", "Veľkosť", "Trvanie"});
    }

}
