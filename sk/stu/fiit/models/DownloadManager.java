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
import java.util.logging.Handler;
import java.util.logging.Logger;
import javax.net.ssl.SSLHandshakeException;
import sk.stu.fiit.views.MainView;

/**
 * Singleton class, which is holds information about all downloads and controls them
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
    
    private final List<Downloader> downloads = new LinkedList<>();
    private static DownloadManager instanceOfSelf = null;
    private static final Logger LOGGER = Logger.getLogger(DownloadManager.class.getClass().getName());
    private static final Handler fileHandler = null;

    /**
     * private constructor
     */
    private DownloadManager() {
        Logging.setupHandler(fileHandler, LOGGER);
    }
    
    /**
     * start new download
     * @param urlString URL address
     * @param pathString download destination 
     * @throws MalformedURLException Malformed URL address
     * @throws IOException 
     * @throws javax.net.ssl.SSLHandshakeException 
     */
    public void download(String urlString, String pathString) throws MalformedURLException, IOException, SSLHandshakeException{
        int ID = RecordManager.getInstanceOfSelf().generateNewIndex();
        Downloader objDownloader = new Downloader(ID, urlString, pathString, LOGGER);
        this.downloads.add(objDownloader);
        objDownloader.start(); 
    }
    
    /**
     * pause active download
     * @param selectedDownloaderIndex index of the specific downloader
     * @throws InterruptedException
     * @throws IOException 
     */
    public void pauseDownloading(int selectedDownloaderIndex) throws InterruptedException, IOException{
        downloads.get(selectedDownloaderIndex).pauseDownloading();
    }
    
    /**
     * resume paused download
     * @param selectedDownloaderIndex index of the specific downloader
     * @throws InterruptedException
     * @throws IOException 
     */
    public void resumeDownloading(int selectedDownloaderIndex) throws InterruptedException, IOException{
        downloads.get(selectedDownloaderIndex).resumeDownloading();
    }
    
    /**
     * cancel download
     * @param selectedDownloaderIndex index of the particular download
     */
    public void cancelDownloading(int selectedDownloaderIndex) {
        downloads.get(selectedDownloaderIndex).interrupt();
    }

    public List<TableModelItem> getDownloading() {
        return (List<TableModelItem>) (Object) downloads;
    }

    /**
     * remove specific downloader from the list of downloading
     * @param objDownloader particular downloader
     */
    public void remove(Downloader objDownloader) {
        this.downloads.remove(objDownloader);
    }

    public void startProgressChecker(int selectedDownloaderIndex, MainView view) throws IOException {
        new DownloadProgressChecker(downloads.get(selectedDownloaderIndex), view).start();
    }

}
