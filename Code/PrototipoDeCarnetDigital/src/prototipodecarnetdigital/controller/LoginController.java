/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototipodecarnetdigital.controller;


import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author User
 */
public class LoginController {
    
    private static LoginController  log;

    private LoginController() {
        
    }
    
    public static LoginController getInstance(){
        
        if(log == null){
            log = new LoginController();
        }  
        
        return log;
    }
    
    public boolean validate(String user, String password) {
        JSONParser parser = new JSONParser();
        String passwordData = null;
        String userData = null;
        try {
            Object object = parser.parse(new FileReader("C:\\user\\virtualID.json"));
            JSONObject jsonObject = (JSONObject) object;
            userData = (String) jsonObject.get("user");
            passwordData = (String) jsonObject.get("password");

        } catch (IOException ex) {
            System.out.println("Error with data "+ex);
        } catch (org.json.simple.parser.ParseException ex) {
            System.out.println(ex);
        }

        if (user.equals(userData) && password.equals(passwordData)) {
            return true;
        } else {
            return false;
        }
    }

}