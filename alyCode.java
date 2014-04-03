public class alyCode {
	
	private String[][] staffInfo;
	protected String[][] staffNameEmail;
	
	// constructor
	protected alyCode(){
		//
	}
	
	// reads all staff information into table
	protected void getAllStaffNameEmail()
	{
		StaffDatabase staffDB = new StaffDatabase();
		
		staffDB.initDatabase("staff.txt");
		staffInfo = staffDB.getAllStaff();
		staffDB.close();
		
		staffNameEmail = new String[staffInfo.length][3];
		
		// init staff name/email table containing firstName+" "+lastName+" "+ some email
		for( int i = 0 ; i < staffInfo.length; i++) 
		{
			
			String ID = staffInfo[i][2];
			String first = staffInfo[i][0];
			String last = staffInfo[i][1];
			String email = staffInfo[i][4];
			
			staffNameEmail[i][0] = first;
                        staffNameEmail[i][1] = last;
                        staffNameEmail[i][2] = email;
		}
	}
        
        public static void main(String[] args) {
            
            alyCode test = new alyCode();
            test.getAllStaffNameEmail();
            for (String[] staffNameEmail1 : test.staffNameEmail) {
                System.out.println(staffNameEmail1[0]);
                System.out.println(staffNameEmail1[1]);
                System.out.println(staffNameEmail1[2]);
            }
        }
	
	/*
	 * Example: - you want to replace paperNames with staffNameEmail
	 * assessmentPaperComboBox = new javax.swing.JComboBox();
	 * assessmentPaperComboBox.setModel(new javax.swing.DefaultComboBoxModel(paperNames));
	 */
}