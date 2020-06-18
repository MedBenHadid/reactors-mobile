/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors;

import com.codename1.util.MathUtil;

/**
 *
 * @author Chihab
 */
public class ReactorsUtils {
    public static double distance(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLng/2) * Math.sin(dLng/2);



        return earthRadius * (2 * MathUtil.atan2(Math.sqrt(a), Math.sqrt(1-a)));
    }
    
    public static double getDistanceFromLatLonInKm(double lat1,double lon1,double lat2,double lon2) {
        double R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2-lat1);  // deg2rad below
        double dLon = deg2rad(lon2-lon1); 
        double a = 
          Math.sin(dLat/2) * Math.sin(dLat/2) +
          Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * 
          Math.sin(dLon/2) * Math.sin(dLon/2)
          ; 
        double c = 2 * MathUtil.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
        double d = R * c; // Distance in km
        return d;
    }

    static double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }

}
