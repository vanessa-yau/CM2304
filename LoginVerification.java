/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 *@author Letsibogo Ramadi
 */

package msettings;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginSecurity {
    
    
    public LoginSecurity() { }
   
    /*returns the two encrypted parameters separated by a comma*/
    public static String encrypt(String username,String password) {
        byte[] encryptedPass = null;
        byte[] encryptedUsername = null;
        try {
           MessageDigest md = MessageDigest.getInstance("SHA-1");
           encryptedPass =  md.digest(password.getBytes("UTF-8"));
           encryptedUsername = md.digest(username.getBytes("UTF-8"));
        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LoginSecurity.class.getName()).log(Level.SEVERE, null, ex);
        } 
        String credentials = byte2HexString(encryptedUsername) + "," +byte2HexString(encryptedPass);
        System.out.println(credentials);
        return credentials;
    }
    
    /*to convert the encrypted password to a string so that it can be stored in the file */
    public static String byte2HexString(byte[] b) {
        String result = "";
        for (int i=0; i < b.length; i++) {
            result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }
    
 
    /*tests if the username and password match existing details*/
    public static  boolean authenticate(String checkUsername, String checkPassword, String existing_details){
        boolean authenticated = false;    

        if (checkUsername == null || checkPassword == null){               
            authenticated = false;
        }else{
            String check = encrypt(checkUsername, checkPassword);
            if(existing_details.compareTo(check) == 0){
                authenticated = true;
            }
        } 

        return authenticated;
    }

    
   
    public static void main(String[] args) throws NoSuchAlgorithmException{
        //hardcoded here but should be set to the credentials in the user file 
        String existing = "6fc061f07af31f3b38c2dc00c4df9f740b707b6d,e2c35061f278e5c4a0e182789ac4b247c503b80f";
        
        String chekUser = "letsibogo";//set this to entered username
        String chekPass = "ramadi";//set this to entered password     
        
        
        //TEST LOGIN
        
       if(authenticate(chekUser,chekPass,existing)){
           System.out.println("LOGIN SUCCESS!!");
           //initialise main area/main menu here
       }else{
           System.out.println("LOGIN FAILED!!");
           //do something to show login failed, e.g: pop up message
           //then give them 2 more chances to enter details
       }  
        
    }

}
