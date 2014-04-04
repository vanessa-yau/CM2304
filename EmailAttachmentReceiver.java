// Created by Aly Abdelfattah-Elmakhzangui

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Properties;
 
import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import org.jsoup.Jsoup;


public class EmailAttachmentReceiver {
    
    private Store store;
    private String userName;
    private String password;
    protected Folder folderInbox;
    protected Message[] arrayMessages;
    protected ArrayList<String> fromAddresses;
    protected ArrayList<String> textContent;
    protected ArrayList<String> subjectArray;
    protected ArrayList<String> dateArray;
    protected ArrayList<String> attachmentArray;
    
    // Fairly obvious here...
    public void setLoginDetails(String a, String b) {
        userName = a;
        password = b;
    }
    
    // Connect to the mailbox
    public boolean connect() throws ParseException {
    	
    	// Initiate e-mail protocols
        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", "imaps");
        store = null;
        
        // The different possiblities of accounts
        try {
            
            Session session = Session.getInstance(properties, null);
            store = session.getStore();
            
            // Flexibility is key in this case, e-mails could come from anywhere
            if (userName.contains("@gmail")) {
            	store.connect("imap.gmail.com", userName, password);
            }
            
            else if (userName.contains("@cardiff")) {
            	store.connect("outlook.office365.com", userName, password);
            }
            
            else if (userName.contains("@aol")) {
            	store.connect("imap.aol.com", userName, password);
            }
            
            else if (userName.contains("@hotmail")) {
            	store.connect("imap-mail.outlook.com", userName, password);
            }
            
            else if (userName.contains("@yahoo")) {
            	store.connect("imap.mail.yahoo.com", userName, password);
            }
            
            else if (userName.contains("@btinternet")) {
            	store.connect("mail.btinternet.com", userName, password);
            }
            
            else if (userName.contains("@virginmedia")) {
            	store.connect("imap.virginmedia.com", userName, password);
            }
            
            else {
                throw new IllegalArgumentException();
            }
        }
        
        catch (NoSuchProviderException ex) {
            System.out.println("No provider for imap.");
            ex.printStackTrace();
            return false;
        } 
        
        catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
            return false;
        }
        
        catch (IllegalArgumentException ex) {
            System.out.println("HIHIHIHIHIHIHIH");
            return false;
        }
        
        return true;
    }
    
    // Actually read the inbox you connected
    public void readInbox() throws MessagingException {
        
        // opens the inbox folder
        folderInbox = store.getFolder("INBOX");
        folderInbox.open(Folder.READ_WRITE);
 
        // fetches new messages from server
        arrayMessages = folderInbox.getMessages();
        
        folderInbox.close(false);
    }
    
    // Gets all the "from" e-mail addresses in the inbox
    // stores them in an ArrayList which we can then split up and use in the table
    public void getFromAddress() throws MessagingException {
        folderInbox.open(Folder.READ_WRITE);
        fromAddresses = new ArrayList<>();
        for (Message message : arrayMessages) {
            Address[] fromAddress = message.getFrom();
            String from = fromAddress[0].toString();
            fromAddresses.add(from);
        }
        folderInbox.close(false);
    }
    
    // Gets all the subject of the e-mails in the inbox
    // stores them in an ArrayList which we can then split up and use in the table
    public void getSubject() throws MessagingException {
        folderInbox.open(Folder.READ_WRITE);
        subjectArray = new ArrayList<>();
        for (Message message : arrayMessages) {
            String subject = message.getSubject();
            subjectArray.add(subject);
        }
        folderInbox.close(false);
    }
    
    // Gets all the dates of the e-mails in the inbox
    // stores them in an ArrayList which we can then split up and use in the table
    public void getDate() throws MessagingException {
        folderInbox.open(Folder.READ_WRITE);
        dateArray = new ArrayList<>();
        for (Message message : arrayMessages) {
            String sentDate = message.getSentDate().toString();
            dateArray.add(sentDate);
        }
        folderInbox.close(false);
    }
    
    public void messagesAttachment() throws MessagingException, IOException {
        folderInbox.open(Folder.READ_WRITE);
        attachmentArray = new ArrayList<>();
        for (Message message : arrayMessages) {
            String contentType = message.getContentType();
            String attachFiles = "";
            if (contentType.contains("multipart")) {
                // content may contain attachments
                Multipart multiPart = (Multipart) message.getContent();
                int numberOfParts = multiPart.getCount();
                for (int partCount = 0; partCount < numberOfParts; partCount++) {
                    MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                    String partContentType = part.getContentType();
                    partContentType = partContentType.substring(0, 11);
                    if (partContentType.equalsIgnoreCase("APPLICATION")) {
                        // this part is attachment
                        String fileName = part.getFileName();
                        if (fileName.contains("/")) {
                            int cutOff = fileName.lastIndexOf("/");
                            fileName = fileName.substring(cutOff);
                        }
                        
                        fileName = "/" + fileName;
                        attachFiles += fileName + ", ";
                    }
                }
            
                attachmentArray.add(attachFiles);
            }
            
            else {
                attachmentArray.add("");
            }
        }
        
        folderInbox.close(false);
    }
    
    public void deleteEmail(Message toDelete) throws MessagingException {
        
        folderInbox.open(Folder.READ_WRITE);
        // instead of completely deleting, moves the message to the trash bin
        Folder trash = store.getFolder("[Gmail]/Trash");
        Message[] delete = new Message[1];
        delete[0] = toDelete;
        folderInbox.copyMessages(delete, trash);
        
        folderInbox.close(false);
    }
    
    // gets the text content of each message
    public void messagesContent() throws MessagingException, IOException {
        folderInbox.open(Folder.READ_WRITE);
        textContent = new ArrayList<>();
        for (Message message : arrayMessages) {
            
            Multipart multiPart = (Multipart) message.getContent();
            int numberOfParts = multiPart.getCount();
            String text = "";
            for (int partCount = 0; partCount < numberOfParts; partCount++) {
                MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                String thisPart = getText(part);
                if (thisPart != null && !thisPart.contains("<div>")) {
                    text += thisPart;
                }
            }
            
            textContent.add(text);
        }
        
        folderInbox.close(false);
    }
    
    // a special method to deal with every type of message
    public String getText(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();
            return s;
        }

        else if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart)p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null) {
                        text = getText(bp);
                        return text;
                    }
                } 
                
                else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null){
                        String html = (String) bp.getContent();
                        String result = Jsoup.parse(html).text();
                        return result;
                    }
                } 
                
                else {
                    return getText(bp);
                }
            }
            return text;
            
        } 
        
        else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null) {
                    return s;
                }
            }
        }

        return null;
    }
    
    // disconnect
    //folderInbox.close(false);
    //store.close();
        
    public static void main(String[] args) throws ParseException, MessagingException, IOException {
    	
        //String port = "993";
        EmailAttachmentReceiver receiver = new EmailAttachmentReceiver();
        receiver.setLoginDetails("chickentika99@gmail.com", "password97");
        receiver.connect();
        receiver.readInbox();
        receiver.getFromAddress();
        receiver.getSubject();
        receiver.deleteEmail(receiver.arrayMessages[0]);
        //receiver.getDate();
        //receiver.downloadAttachment("/Users/Pharaoh/Dropbox/Dump");
 
    }
}