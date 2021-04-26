/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class which validates URL address with the usage of regex
 * @author Admin
 */
public class URLValidator {
    
    public static boolean isURLValid(String urlSpec){
        Pattern pattern = Pattern.compile("http://|https://www\\..*\\..*", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(urlSpec);
        return matcher.find();
    }
    
    public static void main(String[] args) {
        String url = "https://editor.swagger.io/";
        System.out.println("isURLValid(url) = " + isURLValid(url));
    }
    
}
