/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.controllers;

import java.util.List;
import sk.stu.fiit.models.TableModelItem;

/**
 *
 * @author Admin
 */
public interface CustomTableModel {
    
    default Object[][] getTableData(List<TableModelItem> list){
        Object[][] data = new Object[list.size()][4]; // tatoka 4ka
        int index = 0;
        for(TableModelItem item : list){
            data[index++] = item.getDataRow();
        }
        return  data;
    }
    
}
