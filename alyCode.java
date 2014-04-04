
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class alyCode {
    
    private String[][] staffInfo;
    protected String[][] staffNameEmail;
	
    // reads all staff information into table
    protected void getAllStaffNameEmail() {
        StaffDatabase staffDB = new StaffDatabase();
        
        staffDB.initDatabase("staff.txt");
        staffInfo = staffDB.getAllStaff();
        staffDB.close();
        
        staffNameEmail = new String[staffInfo.length][3];
		
        // init staff name/email table containing firstName+" "+lastName+" "+ some email
        for( int i = 0 ; i < staffInfo.length; i++)  {
			
            String ID = staffInfo[i][4];
            String first = staffInfo[i][0];
            String last = staffInfo[i][1];
            String email = staffInfo[i][2];
            
            staffNameEmail[i][0] = first;
            staffNameEmail[i][1] = last;
            staffNameEmail[i][2] = email;
        }
        
        staffNameEmail = sortEntries(staffNameEmail);
    }
    
    private String[][] sortEntries(String[][] details) {
        
        List<String> lastNames = new ArrayList<>();
        String[] relevantFirstName = new String[details.length];
        String[] relevantEmail = new String[details.length];
        for (String[] detail : details) {
            lastNames.add(detail[0]);
        }
        
        Collections.sort(lastNames);
        for (String[] detail : details) {
            String chosenSurname = detail[0];
            String chosenFirstName = detail[1];
            String chosenEmail = detail[2];
            int newIndex = lastNames.indexOf(chosenSurname);
            relevantFirstName[newIndex] = chosenFirstName;
            relevantEmail[newIndex] = chosenEmail;
        }
        
        for (int i = 0 ; i < lastNames.size(); i++) {
            String currentLastName = lastNames.get(i);
            details[i][0] = currentLastName;
            details[i][1] = relevantFirstName[i];
            details[i][2] = relevantEmail[i];
        }
        
        return details;
    }
        
    public static void main(String[] args) {
            
        alyCode test = new alyCode();
        test.getAllStaffNameEmail();
        for (String[] staffNameEmail1 : test.staffNameEmail) {
            System.out.println(staffNameEmail1[0] + " " + staffNameEmail1[1] + " " + staffNameEmail1[2]);
        }
    }
	
	/*
	 * Example: - you want to replace paperNames with staffNameEmail
	 * assessmentPaperComboBox = new javax.swing.JComboBox();
	 * assessmentPaperComboBox.setModel(new javax.swing.DefaultComboBoxModel(paperNames));
	 */
}