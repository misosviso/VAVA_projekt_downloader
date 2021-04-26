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
        return new DefaultTableModel(getTableData(this.manager.getDownloadedModel().subList(0, 4)), 
                new Object[]{"ID", "Dátum", "URL adresa", "Destinácia", "Status", "Veľkosť", "Trvanie"});
    }
        
    public DefaultTableModel getDownloadedZips() throws IOException{
        return new DefaultTableModel(getTableData(this.manager.getZipsModel()), 
                new Object[]{"ID", "Dátum", "URL adresa", "Destinácia", "Status", "Veľkosť", "Trvanie"});
    }
    
    public void unzipFile(int selectedZipIndex, String destinationPath) throws IOException, NotZipException{
        this.manager.unzip(selectedZipIndex, destinationPath);
    }
}
