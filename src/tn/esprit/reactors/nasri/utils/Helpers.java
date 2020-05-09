/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.nasri.utils;

import com.codename1.l10n.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
}
