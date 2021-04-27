/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import javax.swing.table.DefaultTableModel;
import sk.stu.fiit.exceptions.InvalidUrlException;
import sk.stu.fiit.models.DownloadManager;
import sk.stu.utils.URLValidator;
import sk.stu.fiit.views.MainView;

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
        DecimalFormat df = new DecimalFormat("0.00");
        float kBspace = (float)space / (float)1024;
        float MBspace = (float)kBspace / (float)1024;
        float GBspace = (float)MBspace / (float)1024;
        float TBspace = (float)GBspace / (float)1024;
        if(TBspace > 1){
            return df.format(TBspace) + "TB";
        } else if(GBspace > 1){
            return df.format(GBspace) + "GB";
        } else if(MBspace > 1){
            return df.format(MBspace) + "MB";
        } else{
            return df.format(kBspace) + "kB";
        }
    }
    
    public String getProgramStatus(){
        int downloads = this.manager.getDownloading().size();
        return switch (downloads) {
            case 0 -> "Aktuálne sa nič nesťahuje";
            case 1 -> "Aktuálne sa sťahuje 1 súbor";
            case 2 -> "Aktuálne sa sťahuje 2 súbory";
            case 3 -> "Aktuálne sa sťahuje 3 súbory";
            default -> "Aktuálne sa sťahuje " + downloads + " súborov";
        };
    }
    
    public void startProgressChecker(int selectedDownloaderIndex) throws IOException{
        this.manager.startProgressChecker(selectedDownloaderIndex, this.view);
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
    public void clean(){
        this.manager.interruptAll();
    }
}
