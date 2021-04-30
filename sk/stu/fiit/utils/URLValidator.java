/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.stu.fiit.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class which validates URL address with the usage of regex
 * @author Admin
 */
public class URLValidator {
    
    public static boolean isURLValid(String urlSpec){
        Pattern pattern = Pattern.compile("http://|https://.*\\..*\\..*", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(urlSpec);
        return matcher.find();
    }
}
