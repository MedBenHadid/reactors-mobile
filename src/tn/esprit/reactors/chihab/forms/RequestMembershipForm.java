/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.reactors.chihab.forms;

import com.codename1.ui.Form;
import tn.esprit.reactors.ReactorsForm;
import tn.esprit.reactors.chihab.models.Association;

/**
 *
 * @author Chihab
 */
public class RequestMembershipForm extends ReactorsForm{
    
    public RequestMembershipForm (Form previous, Association a){
        super("Membership request :"+a.getNom(),previous);
        
    }
    
}
