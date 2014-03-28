import java.io.IOException;
import java.text.ParseException;
import javax.mail.MessagingException;

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
    
    
    private String username;
    private String password;
    private String sendTo;
    
    public void loginWindow() {
        
        LoginGUI login = new LoginGUI();
        login.setVisible(true);
        boolean stayHere = true;
        int count = 0;
        while (stayHere) {
            
            count++;
            if (!login.active) {
                username = login.username;
                password = login.password;
                login.dispose();
                stayHere = false;
            }
            
            if (count == 1000) {
                count = 0;
            }         
        }
    }
    
    public void emailInbox() throws ParseException, IOException, MessagingException {
        
        EmailInboxFrame inbox = new EmailInboxFrame();
        boolean valid = inbox.canConnect(username, password);
        
        if (!valid) {
            inbox.dispose();
        }
        
        inbox.emailInput();
        inbox.setVisible(true);
        
        boolean stayHere = true;
        int count = 0;
        while (stayHere) {
            
            count++;
            if (inbox.state == 1) {
                inbox.dispose();
                stayHere = false;
            }
            
            else if (inbox.state == 2) {
                sendTo = inbox.selectedEmails();
                inbox.dispose();
                sendEmailFrame(sendTo);
                stayHere = false;
            }
            
            else if (inbox.state == 3) {
                String[] details = inbox.whichEmailToRead();
                inbox.dispose();
                readEmailFrame(details);
                stayHere = false;
            }
            
            if (count == 1000) {
                count = 0;
            } 
        }
    }
    
    public void sendEmailFrame(String to) throws ParseException, IOException, MessagingException {
        SendMailFrame sendMail = new SendMailFrame();
        sendMail.setComboBoxOption(to);
        sendMail.setVisible(true);
        
        boolean stayHere = true;
        int count = 0;
        while (stayHere) {
            
            count++;
            if (sendMail.state == 1) {
                sendMail.state = 0;
                sendMail.dispose();
                stayHere = false;
            }
            
            else if (sendMail.state == 2) {
                sendMail.state = 0;
                sendMail.dispose();
                emailInbox();
                stayHere = false;
            }
            
            else if (sendMail.state == 3) {
                sendMail.state = 0;
                sendMail.sendingMail(username, password);
                sendMail.dispose();
                emailInbox();
                stayHere = false;
            }
            
            if (count == 1000) {
                count = 0;
            }      
        }
    }
    
    public void readEmailFrame(String[] toOpen) {
        ReadEmailFrame read = new ReadEmailFrame();
        read.fillDetails(toOpen);
        read.setVisible(true);
        
        boolean stayHere = true;
        int count = 0;
        while (stayHere) {
            
            count++;
            
            if (read.state == 1) { 
                read.dispose();
                stayHere = false;
            }
            
            if (count == 1000) {
                count = 0;
            }      
        }
    }

    public static void main(String[] args) throws ParseException, IOException, MessagingException {
        
        MainArea test = new MainArea();
        test.loginWindow();
        test.emailInbox();
    }
}
