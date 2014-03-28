// Created by Aly Abdelfattah-Elmakhzangui

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Properties;
 
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;

import org.jsoup.Jsoup;

public class EmailAttachmentReceiver {
    
    private Store store;
    private String userName;
    private String password;
    private String input;
    private Folder folderInbox;
    protected Message[] arrayMessages;
    protected ArrayList<String> fromAddresses;
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
    }
    
    // Gets all the "from" e-mail addresses in the inbox
    // stores them in an ArrayList which we can then split up and use in the table
    public void getFromAddress() throws MessagingException {
        
        fromAddresses = new ArrayList<>();
        for (Message message : arrayMessages) {
            Address[] fromAddress = message.getFrom();
            String from = fromAddress[0].toString();
            fromAddresses.add(from);
        }
    }
    
    // Gets all the subject of the e-mails in the inbox
    // stores them in an ArrayList which we can then split up and use in the table
    public void getSubject() throws MessagingException {
        
        subjectArray = new ArrayList<>();
        for (Message message : arrayMessages) {
            String subject = message.getSubject();
            subjectArray.add(subject);
        }
    }
    
    // Gets all the dates of the e-mails in the inbox
    // stores them in an ArrayList which we can then split up and use in the table
    public void getDate() throws MessagingException {
        
        dateArray = new ArrayList<>();
        for (Message message : arrayMessages) {
            String sentDate = message.getSentDate().toString();
            dateArray.add(sentDate);
        }
    }
    
    public void hasAttachment() throws MessagingException, IOException {
        
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
                        attachFiles += fileName + ", ";
                        if (fileName.contains("/")) {
                            int cutOff = fileName.lastIndexOf("/");
                            fileName = fileName.substring(cutOff);
                        }
                        
                        fileName = "/" + fileName;
                    }
                }
            
                attachmentArray.add(attachFiles);
            }
            
            else {
                attachmentArray.add("");
            }
        }
    }
    
    public void deleteEmail(Message toDelete) throws MessagingException {
        Folder trash = store.getFolder("[Gmail]/Trash");
        Message[] delete = new Message[1];
        delete[0] = toDelete;
        folderInbox.copyMessages(delete, trash);
        //toDelete.setFlag(Flags.Flag.DELETED, true);
    }
    
    // Again, fairly obvious...
    public void downloadAttachment(String saveDirectory) throws ParseException, MessagingException, IOException {
            
        for (int i = 0; i < arrayMessages.length; i++) {
            Message message = arrayMessages[i];

                Address[] fromAddress = message.getFrom();
                String from = fromAddress[0].toString();
                String subject = message.getSubject();
                String sentDate = message.getSentDate().toString();
 
                String contentType = message.getContentType();
                String messageContent = "";
 
                // store attachment file name, separated by comma
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
                                attachFiles += fileName + ", ";
                                
                                if (fileName.contains("/")) {
                                    int cutOff = fileName.lastIndexOf("/");
                                    fileName = fileName.substring(cutOff);
                                }
                                
                                fileName = "/" + fileName;
                                part.saveFile(saveDirectory + fileName);
                            } 
                                
                            else {
                        	
                                // this part may be the message content
                                messageContent = part.getContent().toString();
                			
                            }
                	}
                		
                        // Once an attachment is downloaded, it is removed from the list of files attached
                	if (attachFiles.length() > 1) {
                            
                            attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                	}
                    
                } 
                	
                // Normal text you'd normally get in an e-mail
                else if (contentType.contains("text/plain")) {
                		
                    Object content = message.getContent();
                    if (content != null) {
                	messageContent = content.toString();
                    }
                }
                	
                // On the off-chance we get a HTML e-mail
                // Haven't got it to work yet, but when/ if I do I'll upload a revised version
                else if (contentType.contains("text/html")) {
                    
                    Object content = message.getContent();
                    if (content != null) {
                	messageContent = content.toString();
                	messageContent = Jsoup.parse(messageContent).text();
                    }
                }
                
                // print out details of each message
                System.out.println("Message #" + (i + 1) + ":");
                System.out.println("\t From: " + from);
                System.out.println("\t Subject: " + subject);
                System.out.println("\t Sent Date: " + sentDate);
                System.out.println("\t Message: " + messageContent);
                System.out.println("\t Attachments: " + attachFiles);
        }
 
        // disconnect
        folderInbox.close(false);
        store.close();
       
    }
 
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