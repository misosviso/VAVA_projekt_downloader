/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import sk.stu.fiit.exceptions.InvalidUrlException;
import sk.stu.fiit.models.DownloadManager;
import sk.stu.utils.URLValidator;
import sk.stu.fiit.views.MainView;
import sk.stu.utils.UniversalFormatter;

/**
 *
 * @author Admin
 */
public class DownloadController implements CustomTableModel{
    
    private final DownloadManager manager = DownloadManager.getDownloadManager();
    private int selectedDownload;
    private final MainView view;

    public DownloadController(MainView view) {
        this.view = view;
    }
    
    public void download(String urlString, String pathString) throws MalformedURLException, IOException, InvalidUrlException {
        if(!URLValidator.isURLValid(urlString)){
            throw new InvalidUrlException();
        }
        manager.download(urlString, pathString);

    }
        
    public DefaultTableModel getDownloading(){
        return new DefaultTableModel(getTableData(this.manager.getDownloading()), 
                new Object[]{"ID", "Dátum", "URL adresa", "Destinácia", "Status", "Veľkosť", "Trvanie"});
    }
    
    public String getFreeSpace(){
        long space = new File("c:").getUsableSpace();
        return UniversalFormatter.formatSize(space);
    }
    
    public String getFileSize(String urlAddress) throws MalformedURLException, IOException {
        return  UniversalFormatter.formatSize(new URL(urlAddress).openConnection().getContentLength());
    }
    
    public String getProgramStatus(){
        int downloads = this.manager.getDownloading().size();
        switch (downloads) {
            case 0: return "Pripravený na sťahovanie";
            case 1: return"Aktuálne sa sťahuje 1 súbor";
            case 2: return"Aktuálne sa sťahuje 2 súbory";
            case 3: return"Aktuálne sa sťahuje 3 súbory";
            default: return "Aktuálne sa sťahuje " + downloads + " súborov";
        }
    }
    
    public String getProgramStatusEnglish(){
        int downloads = this.manager.getDownloading().size();
        switch (downloads) {
            case 0: return "Ready for downloading";
            case 1: return "1 file is being downloaded";
            default: return downloads + " files are being downloaded";
        }
    }
    
    public void startProgressChecker(){
        this.manager.startProgressChecker(selectedDownload, this.view);
    }
    
    public void setUpIndex(int index){
        this.selectedDownload = index;
    }

    public void pauseDownloading() throws InterruptedException, IOException {
        this.manager.pauseDownloading(selectedDownload);
    }

    public void resumeDownloading() throws InterruptedException, IOException {
        this.manager.resumeDownloading(selectedDownload);
    }

    public void cancelDownloading() {
        this.manager.cancelDownloading(selectedDownload);
    }
    
    public String getDownloadingSource(){
        return this.manager.getSource(selectedDownload);
    }
    public String getDownloadingDest(){
        return this.manager.getDest(selectedDownload);
    }
    public String getDownloadingStart(){
        return this.manager.getStart(selectedDownload);
    }
    public String getDownloadingTime(){
        return this.manager.getDownTime(selectedDownload);
    }
    public String getDownloadingSize(){
        return this.manager.getDownSize(selectedDownload);
    }
    public String getDownloadingStatus(){
        return this.manager.getDownStat(selectedDownload);
    }
    public String getEstimatedTime(){
        return this.manager.getEstTime(selectedDownload);
    }
    public String getPercentage(){
        return this.manager.getPercentage(selectedDownload);
    }
    public void clean(){
        this.manager.interruptAll();
    }

    public TableModel getDownloadingEnglish() {
        return new DefaultTableModel(getTableData(this.manager.getDownloading()), 
                new Object[]{"ID", "Date", "URL address", "Destination", "Status", "Size", "Duration"});
    }

    public long getDownloadedSize() {
        return manager.getDownloadedSize(selectedDownload);
    }

    public long getTotalSize() {
        return manager.getTotalSize(selectedDownload);
    }

}
