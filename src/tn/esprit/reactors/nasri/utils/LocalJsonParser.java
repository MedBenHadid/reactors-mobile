/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.nasri.utils;

import com.codename1.io.CharArrayReader;
import com.codename1.io.JSONParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nasri
 */
public class LocalJsonParser 
{
    // input: {id=72.0,.......}
    // ouput: (int)72.0
    public static int ParseUserId(String json)
    {
        int startPos = json.indexOf('=') + 1;
        int endPos = json.indexOf(',');
        
        String tmp = json.substring(startPos, endPos);
        
        return (int)Float.parseFloat(tmp);
    }
    
    // input: {......, timestamp=1.586736E9}
    // output: 
    public static Date ParseDate(String json)
    {
        int start = json.lastIndexOf('=') + 1;
        
        String str = json.substring(start, json.length() - 1);
        long timestamp = (long)(Double.parseDouble(str) * 1000);
        
        return new Date(timestamp);
    }
}
