/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.models;

/**
 * Functional interface which is used for creating tables from data from classes Downloader and DownloadRecord
 * @author Admin
 */
public interface TableModelItem {

    public Object[] getDataRow();
    
}
