/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.controllers;

import java.io.IOException;
import java.io.Serializable;
import javax.swing.table.DefaultTableModel;
import sk.stu.fiit.exceptions.NotZipException;
import sk.stu.fiit.models.RecordManager;
import sk.stu.fiit.views.MainView;

/**
 *
 * @author Admin
 */
public class RecordController implements CustomTableModel, Serializable{
    
    private final RecordManager manager = RecordManager.getRecordManager();
    
    public DefaultTableModel getDownloaded(){
        return new DefaultTableModel(getTableData(this.manager.getDownloadedModel()), 
                new Object[]{"ID", "Dátum", "URL adresa", "Destinácia", "Status", "Veľkosť", "Trvanie"});
    }
    
    public DefaultTableModel getRecent(){
        try {
            return new DefaultTableModel(getTableData(this.manager.getDownloadedModel().subList(0, 4)), 
                new Object[]{"ID", "Dátum", "URL adresa", "Destinácia", "Status", "Veľkosť", "Trvanie"});
        } catch (Exception e) {
            return new DefaultTableModel(getTableData(this.manager.getDownloadedModel()), 
                new Object[]{"ID", "Dátum", "URL adresa", "Destinácia", "Status", "Veľkosť", "Trvanie"});
        }
    }
        
    public DefaultTableModel getDownloadedZips() throws IOException{
        return new DefaultTableModel(getTableData(this.manager.getZipsModel()), 
                new Object[]{"ID", "Dátum", "URL adresa", "Destinácia", "Status", "Veľkosť", "Trvanie"});
    }
    
    public void unzipFile(int selectedZipIndex, String destinationPath, boolean deleteOriginalFile) throws IOException, NotZipException{
        this.manager.unzip(selectedZipIndex, destinationPath, deleteOriginalFile);
    }
    
    public String getSpecificDestination(int selectedRecordIndex){
        return this.manager.getSpecific(selectedRecordIndex).getFilePath();
    }
    
    public String getSpecificSource(int selectedRecordIndex){
        return this.manager.getSpecific(selectedRecordIndex).getUrl();
    }
    
    public String getSpecificSize(int selectedRecordIndex){
        return this.manager.getSpecific(selectedRecordIndex).getStringSize();
    }
    
    public String getSpecificStatus(int selectedRowIndex){
        return this.manager.getSpecific(selectedRowIndex).getStringStatus();
    }
    
    public String getSpecificDate(int selectedRowIndex){
        return this.manager.getSpecific(selectedRowIndex).getStringDate();
    }
    
    public String getSpecificTime(int selectedRowIndex){
        return this.manager.getSpecific(selectedRowIndex).getStringTimeElapsed();
    }
    
    public void openFileLoacation(String path) throws IOException{
        Runtime.getRuntime().exec("explorer.exe /select," + path);
    }
    
    public void setUpView(MainView view){
        this.manager.setUpView(view);
    }
}
