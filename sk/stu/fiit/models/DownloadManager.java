/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.models;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import javax.net.ssl.SSLHandshakeException;

/**
 * Singleton class, which is holds information about all downloads
 * @author Admin
 */
public class DownloadManager extends Thread{

    /**
     * Factory for getting instance of DownloadManager
     * @return 
     */
    public static DownloadManager getDownloadManager() {
        if(instanceOfSelf == null){
            instanceOfSelf = new DownloadManager();
        }
        return instanceOfSelf;
    }
    
    private int lastIndex = 0;
    private final List<Downloader> downloads = new LinkedList<>();
    private static DownloadManager instanceOfSelf = null;

    /**
     * private constructor
     */
    private DownloadManager() {}
    
    /**
     * start new download
     * @param urlString
     * @param pathString
     * @return 
     * @throws MalformedURLException
     * @throws IOException 
     * @throws javax.net.ssl.SSLHandshakeException 
     */
    public Downloader download(String urlString, String pathString) throws MalformedURLException, IOException, SSLHandshakeException{
        int ID = lastIndex++;
        Downloader objDownloader = new Downloader(ID, urlString, pathString);
        this.downloads.add(objDownloader);
        objDownloader.start(); 
        return objDownloader;
    }
    
    public void pauseDownloading(int ID) throws InterruptedException, IOException{
        getSpecificDownloader(ID).pauseDownloading();
    }
    
    public void resumeDownloading(int ID) throws InterruptedException, IOException{
        getSpecificDownloader(ID).resumeDownloading();
    }
    
    private Downloader getSpecificDownloader(int ID){
        for (Downloader objDownloader : downloads) {
            if(objDownloader.getDownloaderId() == ID){
                return objDownloader;
            }
        }
        return null; 
    }

    public List<TableModelItem> getDownloading() {
        return (List<TableModelItem>) (Object) downloads;
    }

}
