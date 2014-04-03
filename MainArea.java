import java.io.IOException;
import java.text.ParseException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;

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
            
            JOptionPane.showMessageDialog(new LoginGUI(), 
                    "There was a problem with your username or password. Please enter valid details.", 
                    "Login Error", JOptionPane.ERROR_MESSAGE);
            
            loginWindow();
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
                Message message = inbox.getMessage();
                inbox.dispose();
                readEmailFrame(message, details);
                stayHere = false;
            }
            
            if (count == 1000) {
                count = 0;
            } 
        }
    }
    
    public void sendEmailFrame(String to) throws ParseException, IOException, MessagingException {
        SendMailFrame sendMail = new SendMailFrame();
        sendMail.setVisible(true);
        
        boolean stayHere = true;
        int count = 0;
        while (stayHere) {
            
            count++;
            if (sendMail.state == 1) {
                sendMail.dispose();
                stayHere = false;
            }
            
            else if (sendMail.state == 2) {
                sendMail.dispose();
                emailInbox();
                stayHere = false;
            }
            
            else if (sendMail.state == 3) {
                boolean hasSent = sendMail.sendingMail(username, password);
                if (hasSent) {
                    sendMail.dispose();
                    emailInbox();
                    stayHere = false;
                }
                
                else if (!hasSent) {
                    sendMail.state = 0;
                    stayHere = true;
                }
            }
            
            if (count == 1000) {
                count = 0;
            }      
        }
    }
    
    public void readEmailFrame(Message message, String[] toOpen) throws ParseException, IOException, MessagingException {
        ReadEmailFrame read = new ReadEmailFrame();
        read.fillDetails(toOpen);
        read.setMessage(message);
        read.setVisible(true);
        
        boolean stayHere = true;
        int count = 0;
        while (stayHere) {
            
            count++;
            
            if (read.state == 1) { 
                read.dispose();
                stayHere = false;
            }
            
            if (read.state == 2) { 
                read.dispose();
                emailInbox();
                stayHere = false;
            }
            
            if (read.state == 3) {
                String replyingTo = read.getRecipient();
                read.dispose();
                sendEmailFrame(replyingTo);
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
