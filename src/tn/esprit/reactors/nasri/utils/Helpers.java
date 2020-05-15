/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.nasri.utils;

import com.codename1.l10n.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.codename1.util.regex.RE;

/**
 *
 * @author nasri
 */
public class Helpers 
{
    public static String format(Date date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String format = formatter.format(date);
        
        return format;
    }
    
    public static boolean isNumeric(String input)
    {
        try
        {
            Integer.parseInt(input);
            return true;
        }
        catch(NumberFormatException ex)
        {
            return false;
        }
    }
    
    public static boolean isNumericAndPositive(String input)
    {
        if (!isNumeric(input))
            return false;
        
        return Integer.parseInt(input) > 0;
    }
    
    public static boolean phoneNumberIsValid(String phoneNumber) {
        String regex = "\\d{8}";
        
        RE re = new RE(regex);
        
        return re.match(phoneNumber);
    }


}
