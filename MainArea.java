/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pharaoh
 */
public class MainArea {
    
    
    protected String username;
    protected String password;
    private boolean stayHere;
    
    public void loginWindow() {
        
        LoginGUI login = new LoginGUI();
        login.setVisible(true);
        stayHere = true;
        while (stayHere) {
            
            if (!login.active) {
                username = login.username;
                password = login.password;
                login.dispose();
                stayHere = false;
            }
            
            else {
                System.out.println();
            }            
        }
    }
    
    public void emailInbox() {
        
        EmailInboxFrame inbox = new EmailInboxFrame();
        inbox.setVisible(true);
        inbox.emailInput(username, password);
        System.out.println(username + " " + password);
        stayHere = true;
        while (stayHere) {
            
            if (inbox.state == 1) {
                inbox.dispose();
                stayHere = false;
            }
            
            else {
                System.out.println();
            }            
        }
        
    }
    public void didThisWork() {
        
        System.out.println(username);
        System.out.println(password);
        
    }

    public static void main(String[] args) {
        
        MainArea test = new MainArea();
        test.loginWindow();
        test.didThisWork();
        test.emailInbox();
    }
}
