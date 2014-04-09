/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *
 *@author Letsibogo Ramadi
 */



import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginSecurity {
    
    
    public LoginSecurity() { }
   
    public static String encrypt(String password) {
        byte[] encrypted = null;
        try {
           MessageDigest md = MessageDigest.getInstance("SHA-1");
           encrypted =  md.digest(password.getBytes("UTF-8"));
        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(LoginSecurity.class.getName()).log(Level.SEVERE, null, ex);
        } 
        System.out.println(byte2HexString(encrypted));
        return byte2HexString(encrypted);
}
    
    /*to convert the encrypted password to a string so that it can be stored in the file */
    public static String byte2HexString(byte[] b) {
        String result = "";
        for (int i=0; i < b.length; i++) {
            result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
}
    
    public static  boolean authenticate(String checkUsername, String checkPassword, String existing_username, String existing_pass){
        boolean authenticated = false;    

        if (checkUsername == null || checkPassword == null){               
            authenticated = false;
        }else{
            String check = encrypt(checkPassword);
            if(existing_pass.compareTo(check) == 0 && existing_username.compareTo(checkUsername) == 0){
                authenticated = true;
            }
        } 

        return authenticated;
    }

    
    
    public static void main(String[] args) throws NoSuchAlgorithmException{
        String uName = "letsibogo";//set this to username in the file
        String uPass = "e2c35061f278e5c4a0e182789ac4b247c503b80f";//set this to the encrypted password in the file
        
        String chekUser = "letsibogo";//this would be entered username
        String chekPass = "ramadi";//entered pass     
        
        //TEST LOGIN
        
       if(authenticate(chekUser,chekPass,uName,uPass )){
           System.out.println("LOGIN SUCCESS!!");
           //initialise main area/main menu here
       }else{
           System.out.println("LOGIN FAILED!!");
           //do something to show login failed, e.g: pop up message
           //then give them 2 more chances to enter details
       }   
        
    }

}
