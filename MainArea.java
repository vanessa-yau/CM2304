
import java.io.IOException;
import java.text.ParseException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;

/**
 *
 * @author Pharaoh, MezzOMG
 * 31/03 -	This won't compile without all relevant classes in the source folder at the mo.
 * 
 * There is a state list inside theFlow. If your page isn't on there just add it on and copy the ient for it.
 * 
 */


public class MainArea {
    
    
    protected String username;
    protected String password;
    protected String[] details;
    protected Message message;
    protected String modCode;
    private static byte nextState = 0;
    private boolean stayHere;
    private byte ticker = 0;
    
    public byte theFlow(byte nextState) throws ParseException, IOException, MessagingException {
    	
    	/*
    	 * -1 = Log Out
    	 * 0 = Log in
    	 * 1 = Main Menu
    	 * 2 = Settings
    	 * 3 = Inbox
    	 * 4 = Send E-mail
    	 * 5 = Read E-mail
    	 * 6 = List of Modules
    	 * 7 = Module Breakdown
    	 * 8 = Add new modules?
    	 */
    	
    	byte stateDone = 0;

    	if( nextState < -1 || nextState > 20){
    		System.out.println("Oh God No...");
    		nextState = -1;
    		return nextState;
    	}
    	//Login Window (to Main Menu)
    	if( nextState ==  0){
			while( stateDone == 0){
				stateDone = loginWindow();
			}
			nextState = 1;
			return nextState;
    	}
    	
    	//Main Menu
    	if( nextState == 1){
    		while( stateDone == 0){
    			stateDone = MainMenu();
    		}
    		nextState = stateDone;
    		return nextState;
    	}
    	
    	//E-mail Inbox
    	if( nextState == 3){
    		while( stateDone == 0){
    			stateDone = emailInbox();
    		}
    		nextState = stateDone;
    		return nextState;
    	}
    	
    	//Send e-mail
    	if( nextState == 4){
    		while( stateDone == 0){
    			stateDone = sendMail();
    		}
    		nextState = stateDone;
    		return nextState;
    	}
    	
    	//Read e-mail
    	if( nextState == 5){
    		while( stateDone == 0 ){
    			stateDone = readMail(message, details);
    		}
    		nextState = stateDone;
    		return nextState;
    	}
    	
    	//List of Modules
    	if( nextState == 6){
    		while( stateDone == 0){
    			stateDone = moduleList();
    		}
    		nextState = stateDone;
    		return nextState;
    	}
    	//Module Breakdown
    	if( nextState == 7){
    		while( stateDone == 0 ){
    			stateDone = moduleBreakdown();
    		}
    		nextState = stateDone;
    		return nextState;
    	}
    
    return nextState;
    }
    
    public byte loginWindow() {
        
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
            
            //ticker will oscillate between +-128
            else {
                ticker++;
            }  
            
        }
        // Just in case the oscillation lands on 0
        if( ticker == 0){
        	ticker = 1;
        }
        return ticker;
    }
    
    public byte MainMenu(){
    	
    	MainMenuGUI menu = new MainMenuGUI();
    	menu.setVisible(true);
    	stayHere = true;
    	byte menuNextState = 0;
    	while (stayHere) {
    		menuNextState = menu.nextState;
    		if( menuNextState != 0){
    			nextState = menuNextState;
    			stayHere = false;
    			menu.dispose();
    		}
    		
    		else{
    		ticker++;
    		}	
    	}
    	
        return nextState;
    }
    
    public byte emailInbox() throws ParseException, IOException, MessagingException{
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
    	
    	stayHere = true;
    	byte emailNextState = 0;
    	while (stayHere){
    		emailNextState = inbox.nextState;
    		if( emailNextState != 0){
    			nextState = emailNextState;
    			if( nextState == 5 ){
    				details = inbox.whichEmailToRead();
    				message = inbox.getMessage();
    			}
    			stayHere = false;
    			inbox.dispose();
    		}
    	
    		else{
    		ticker++;
    		}
    	}
    	return nextState;
    }

    public byte sendMail() throws ParseException, IOException, MessagingException{
    	SendMailFrame send = new SendMailFrame();
    	send.setVisible(true);
		stayHere = true;
		byte sendNextState = 0;
		while(stayHere){
			sendNextState = send.nextState;
			if( sendNextState != 0){
				nextState = sendNextState;
				stayHere = false;
				send.dispose();
			}
			
			else if (send.nextState == 3) {
                boolean hasSent = send.sendingMail(username, password);
                if (hasSent) {
                    send.dispose();
                    nextState = 3;
                    stayHere = false;
                }
                
                else if (!hasSent) {
                    send.nextState = 0;
                    stayHere = true;
                }
            }
			
			else{
				ticker++;
			}
		}	
		return nextState;
    }
    
    public byte readMail(Message message, String[] toOpen) throws ParseException, IOException, MessagingException{
    	ReadEmailFrame read = new ReadEmailFrame();
    	read.fillDetails(toOpen);
        read.setMessage(message);
    	read.setVisible(true);
    	stayHere = true;
    	byte readNextState = 0;
    	while(stayHere){
    		readNextState = read.nextState;
    		if( readNextState != 0 ){
    			nextState = readNextState;
    			stayHere = false;
    			read.dispose();
    		}
    		else{
    			ticker++;
    		}
    	}
    	return nextState;
    }
    
    public byte moduleBreakdown(){
    	ModuleBreakdown modBreak = new ModuleBreakdown(modCode);
    	modBreak.setVisible(true);
    	stayHere = true;
    	byte modBreakNextState = 0;
    	while(stayHere){
    		modBreakNextState = modBreak.nextState;
    		if( modBreakNextState != 0){
    			nextState = modBreakNextState;
    			stayHere = false;
    			modBreak.dispose();
    		}
    		
    		else{
    			ticker++;
    		}
    	}
        
        return nextState;
    }
    
    public byte moduleList(){
    	ModuleListGui modList = new ModuleListGui();
    	modList.setVisible(true);
    	stayHere = true;
    	byte modListNextState = 0;
    	while(stayHere){
    		modListNextState = modList.nextState;
    		modList.nextState = modListNextState;
    		if( modListNextState != 0 ){
    			nextState = modListNextState;
    			modCode = modList.modCode;
    			stayHere = false;
    			modList.dispose();
    			}
    		else{
    			ticker++;
    		}
    	}
    	return nextState;
    }
    public void didThisWork() {
        
        System.out.println(username);
        System.out.println(password);
        
    }

    public static void main(String[] args) throws ParseException, IOException, MessagingException {
        
    	int init = Integer.parseInt(args[0]);
    	byte initState = (byte)init;
    	
        MainArea test = new MainArea();
        test.username = "chickentika99@gmail.com";
    	test.password = "password97";
        nextState = initState;
        while( nextState != -1){
        	nextState = test.theFlow(nextState);     	
        }
        //test.emailInbox();
    }
}
