/**
 * @author Vanessa Yau
 * @author Gorata Sehuhula
 * This class provides all additional functionality to support the Module Breakdown GUI
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class MBreakdownTemp
{
	
	//init variables 
	private int activeFormNo;
	private String[] paperNames;
	private String[][] forms;
	private String[][] staffInfo;
	
	public boolean cellEditableState;
	public String[] inStaffNames;
	public String[][] internalStaff, externalStaff;
	
	public static void main(String[] args){}
	
	// Initialiser
	public MBreakdownTemp(){}
	
	// Saves all staff information to arrays for easy access
	public void retrieveStaff()
	{
		
		StaffDatabase staffDB = new StaffDatabase();
		
		staffDB.initDatabase("staff.txt");
		staffInfo = staffDB.getAllStaff();
		staffDB.close();
		
		setInternalExternalTables();
		
	}
	
	/**
	 * Called from the retrieveStaff method
	 * This method sorts the staffInfo table into
	 * separate tables of internal and external staff consisting of:
	 * [0] = staffID; [1] = firstName||lastName
	 * and a list of internal staff names only
	 */
	private void setInternalExternalTables()
	{
		
		int totalInStaff = 0;
		int totalExStaff = 0;
		
		// counts the number of staff in each type respectively
		for( int i=0 ; i<staffInfo.length; i++) 
		{
			
			if( staffInfo[i][5].equals("internal"))
			{
				totalInStaff++;
			}
			else if( staffInfo[i][5].equals("external"))
			{
				totalExStaff++;
			}
			
		}
		
		int currentIS = 0;
		int currentES = 0;
		
		inStaffNames = new String[totalInStaff];
		internalStaff = new String[totalInStaff][2];
		externalStaff = new String[totalExStaff][2];
		
		// init staff type tables containing [0] = staffID; [1] = firstName+" "+lastName
		for( int i=0 ; i<staffInfo.length; i++)
		{
			
			String ID = staffInfo[i][4];
			String first = staffInfo[i][0];
			String last = staffInfo[i][1];
		
			if( staffInfo[i][5].equals("internal"))
			{
				inStaffNames[currentIS] = first.concat(" ").concat(last);
				internalStaff[currentIS][0] = ID;
				internalStaff[currentIS][1] = inStaffNames[currentIS];
				
				currentIS++;
			}
			else if( staffInfo[i][5].equals("external"))
			{
				externalStaff[currentES][0] = ID;
				externalStaff[currentES][1] = first.concat(" ").concat(last);
				
				currentES++;
			}
			
		}
		
		return;
		
	}
	
	/**
	 * Gets the correct form from the array of forms
	 * @param 		formSelected: the name of the form to be displayed
	 * @return 		String[] containing all form information
	 */
	public String[] getMatchingForm(String formSelected)
	{
		
		activeFormNo = 0;
		
		// gets the correct form from the list of all forms
		for ( int i=0; i<forms.length; i++)
		{
			
			if (forms[i][1].equals(formSelected))
			{
				activeFormNo = i;
			}
			
		}
		
		return forms[activeFormNo];
		
	}
	
	
	// retrieve forms with matching module code
	private void getAllForms(String module_code)
	{
		
		FormsDatabase formsDB = new FormsDatabase();
		
		formsDB.initDatabase("forms.txt");
		forms = formsDB.getModuleForms(module_code);
		
		formsDB.close();
		
	}
	
	/**
	 * Generates forms for a module if module exists but has no existing forms
	 * @param module		all module information for selected module
	 */
	private void generateForms(String[] module)
	{
		
		FormsDatabase formsDB2 = new FormsDatabase();
		formsDB2.initDatabase("forms.txt");
		
		if( !module[3].equals("") )
		{
			
			int numCW = Integer.parseInt(module[3]);
			
			// generate all coursework forms
			if( numCW != 0 )
			{
				
				for( int i=0 ; i<numCW ; i++ )
				{
					
					String cwName = "cw".concat(Integer.toString(i+1));
					formsDB2.insertAssessmentPaper(module[1], cwName, "", "", "", "", "", "", "", "", "", "", "", "", "");
					
				}
				
			}
			
		}
		
		if( !module[4].equals("") )
		{
			
			int numExams = Integer.parseInt(module[4]);
			
			// generate all exam forms
			if( numExams != 0 )
			{
				
				for( int i=0 ; i<numExams ; i++ )
				{
				
					String examName = "exam".concat(Integer.toString(i+1));
					formsDB2.insertAssessmentPaper(module[1], examName, "", "", "", "", "", "", "", "", "", "", "", "", "");
				
				}
				
			}
			
		}
		
		formsDB2.close();
		
		// auto-generation notification
		String info = "No matching forms found for module ".concat(module[1].toUpperCase()+"/"+module[0]
				+".\nForms generated using module information given.");
		JOptionPane.showMessageDialog(null, info, "Auto-Generated Forms", JOptionPane.INFORMATION_MESSAGE);
		
		return;
		
	}
	
	/**
	 * Gets all forms and sets names for the assessment paper drop down box
	 * @param 		module_code	to match against all forms
	 * @return		String[] of all paper names
	 */
	public String[] getFormNames(String module_code, String[] module)
	{
		
		// count matching forms
		FormsDatabase fd = new FormsDatabase();
		fd.initDatabase("forms.txt");
		int numberMatchingForms = fd.countForms(module_code);
		fd.close();
		
		//generates forms if no forms for this module exist
		if( numberMatchingForms == 0 )
		{
			generateForms(module);
		}
		
		getAllForms(module_code);
		
		// adds to combo box list
		paperNames = new String[forms.length+1];
		paperNames[0] = "select a paper";
		
		for(int i=0; i<forms.length; i++)
		{
			paperNames[i+1] = forms[i][1];
		}
		
		return paperNames;
		
	}

	//get general form warning message
	public String getWarningMessage()
	{
		
		String warning = "Please note: ".concat("\nDeadlines must have the format: 'dd/MM/yyyy', e.g. '02/01/2015' ");
		warning = warning.concat("\nand must occur in the correct order: e.g. 02/01/2015 should occur before 03/02/2015.");
		warning = warning.concat("\nThe internal moderator and module leader cannot be the same person.");
		
		return warning;
		
	}

	/**
	 * Gets corresponding value from table and returns as string
	 * @param formTable	the table to get values from
	 * @param row		the row to match against
	 * @param col		the column to match against
	 * @return			value as string, or empty string if location empty
	 */
	private String getValue(JTable formTable, int row, int col){
		
		Object value = formTable.getValueAt(row,col);
		
		if( value == null )
		{
			
			return "";
			
		}
		
		return ((String) value );
		
	}
	
	/**
	 * Check if any deadlines have been entered into the table
	 * @param formTable		the table containing deadline entries
	 * @return true			if deadline(s) has been entered, false otherwise
	 */
	public boolean deadlineEntered(JTable formTable){
		for( int i=0 ; i<formTable.getRowCount() ; i++ )
		{
		
			if( !getValue(formTable,i,2).equals("") )
			{
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	/**
	 * Replaces the entry in the forms.txt file with updated info
	 * and archives the previous existing entry
	 * @param t				the table for the open form
	 * @param moduleInfo	the corresponding module information	
	 * @param formSelected	the form to save to file
	 */
	public void saveFormTableInfo(JTable t, String[] moduleInfo, String formSelected)
	{
		String EM_ID = getStaffID(getValue(t,3,1),externalStaff);
		String IM_ID = getStaffID(getValue(t,1,1),internalStaff);
		String PW_ID = getStaffID(getValue(t,0,1),internalStaff);
		
		String module_code = moduleInfo[1];
		
		// saves the form data in the forms table
		forms[activeFormNo][0] = module_code;
		forms[activeFormNo][1] = formSelected;
		
		forms[activeFormNo][4] = getValue(t,2,2); 
		forms[activeFormNo][5] = getValue(t,1,2);
		
		forms[activeFormNo][6] = EM_ID;
		forms[activeFormNo][7] = IM_ID;
		
		forms[activeFormNo][10] = getValue(t,2,3);
		forms[activeFormNo][11] = getValue(t,1,3);
		
		forms[activeFormNo][12] = PW_ID;
		forms[activeFormNo][13] = getValue(t,0,2);
		forms[activeFormNo][14] = getValue(t,0,3);
		
		if( checkWeighting(moduleInfo[6]) == false )
		{
			
			// weighting less than 50, then forms are not externally moderated - row missing
			forms[activeFormNo][2] = getValue(t,3,2);
			forms[activeFormNo][3] = "";
			forms[activeFormNo][8] = getValue(t,3,3);
			forms[activeFormNo][9] = "";
			
		}
		else{
			
			//forms are externally moderated
			forms[activeFormNo][2] = getValue(t,4,2);
			forms[activeFormNo][3] = getValue(t,3,2);
			forms[activeFormNo][8] = getValue(t,4,3);
			forms[activeFormNo][9] = getValue(t,3,3);
			
		}
		
		// replace form by delete (archive) existing form and creating a new record
		FormsDatabase fd = new FormsDatabase();
		fd.initDatabase("forms.txt");

		fd.deleteAssessmentPaper(module_code, formSelected);
		fd.insertAssessmentPaper(
				forms[activeFormNo][0],
				forms[activeFormNo][1],
				forms[activeFormNo][2],
				forms[activeFormNo][3],
				forms[activeFormNo][4],
				forms[activeFormNo][5],
				forms[activeFormNo][6],
				forms[activeFormNo][7],
				forms[activeFormNo][8],
				forms[activeFormNo][9],
				forms[activeFormNo][10],
				forms[activeFormNo][11],
				forms[activeFormNo][12],
				forms[activeFormNo][13],
				forms[activeFormNo][14]);
		fd.close();
		
		return;
	}
	
	/**
	 * Gets matching ID for a name
	 * @param staffName		The name to match ID with
	 * @param staffType		the table which to compare the staffName with
	 * @return				matching ID or "" if name not found
	 */
	private String getStaffID(String staffName, String[][] staffType )
	{
		
		for( int i=0 ; i<staffType.length ; i++ )
		{
			
			if( staffType[i][1].equals(staffName) )
			{
				
				return staffType[i][0];
				
			}
			
		}
		
		return "";
		
	}
	
	/**
	 * Form table override method: 
	 * set cell to editable if the module term start date has not passed
	 * @param row 			the row of a cell
	 * @param column		the column of a cell
	 * @param moduleStartDate the expected module start date
	 * @return true			if cells editable, else return false
	 */
	public boolean setCellEditable(int row, int column, String moduleStartDate)
	{
		// form stage description column
		if( column == 0 )
		{
			
			return false;
			
		}
		
		try{
			
			Date start = new SimpleDateFormat("dd/MM/yyyy").parse(moduleStartDate);
			Date d = new Date();
			
			//check if current date is after beginning of semester
			if( d.after(start) == true )
			{
				
				cellEditableState = false;
				
			}
			else
			{
				
				cellEditableState = true;
				
			}
			
		}
		catch(ParseException pe)
		{
			
			pe.printStackTrace();
			
		}
		
		if( cellEditableState == false )
		{
			
			// date deadline column entries non-editable
			if( column == 3 )
			{
				
				return true;
				
			}
			else
			{
				
				return false;
				
			}
			
		}
		else
		{
			
			if( column == 3 )
			{
				
				// prevent adding dates for stage completion if deadline not input
				return false;
				
			}
			else if( (column==1) && ((row==2)||(row==4)) )
			{
			
				// ML and ELO names set elsewhere
				return false;
				
			}
			else
			{
				
				return true;
				
			}
			
		}
		
	}
	
	/**
	 * Checks all deadline entries in a table column for correct date format
	 * @param formTable		the table to check against
	 * @param column		the column to check against
	 * @return true 		if in correct "dd/MM/yyyy" format else return false;
	 */
	public boolean validateDateFormat(JTable formTable, int column)
	{
		
		String inputDate;
		
		for( int i=0 ; i<formTable.getRowCount() ; i++ )
		{
			
			inputDate = getValue(formTable,i,column);
			if( validDate(inputDate) == false )
			{
				
				return false;
				
			}
			
		}
		
		return true;
		
	}
	
	/**
	 * checks if any input in date completed column of table has correct format
	 * @param formTable		table to compare against
	 * @return false 		if any entry has incorrect date format, return true otherwise
	 */
	public boolean validDateCompleteFormat(JTable formTable)
	{
		
		for( int i=0; i<formTable.getRowCount() ; i++ )
		{
			
			String inputDate = getValue(formTable,i,3);
			if( !inputDate.equals("") )
			{
				
				if( validDate(inputDate) == false )
				{
					
					return false;
					
				}
				
			}
			
		}
		
		return true;
		
	}
	
	/**
	 * Checks if user input row-col entry for a date has the right format
	 * @param inputDate 	a row-col entry for a deadline
	 * @return false 		if incorrect date format else return true
	 */
	public boolean validDate(String inputDate)
	{

		// check date is not empty or does not have the right size (xx/xx/xxxx)
		if( (inputDate.length()!=10)||(inputDate.length()==0) )
		{
			
			return false;
			
		}
		
		try
		{
			new SimpleDateFormat("dd/MM/yyyy").parse(inputDate);
			int dd = Integer.parseInt(inputDate.substring(0,2));
			int MM = Integer.parseInt(inputDate.substring(3,5));
			int yyyy = Integer.parseInt(inputDate.substring(6));
			
			if( MM == 2 )
			{
				
				if( (yyyy%4)==0 && dd>29 )
				{
					
					return false;
					
				}
				
			}
			else if( MM > 12 )
			{
				
				return false;
				
			}

			int daysInMonth = 28;
			
			switch(MM)
			{
				case 1: case 3: case 5: case 7: case 8: case 10: case 12: 
					daysInMonth = 31;
					break;
					
				case 4: case 6: case 9: case 11: 
					daysInMonth = 30;
					break;
					
				default: break;
					
			}
			
			if( dd>daysInMonth )
			{
				
				return false;
				
			}
			else
			{
				return true;
			}
		}
		catch (ParseException e)
		{
			
			return false;
		
		}
		
	}
	
	/**
	 * Checks if the dates for a form occur in the correct order.
	 * @param formTable		the table with entries to check
	 * @return false 		if dates out of order e.g. February before January 
	 * else return true
	 */
	public boolean validateDeadlineOrder(JTable formTable)
	{
		
		//checks if any row in the deadline column is empty
		for( int j=0 ; j<formTable.getRowCount() ; j++ )
		{
			
			if( getValue(formTable,j,2).equals("") )
			{
				
				return false;
				
			}
			
		}
		
		// if the row is not empty - then check dates occur in order
		try
		{
			
			Date before = new SimpleDateFormat("dd/MM/yyyy").parse(getValue(formTable,0,2));
			for( int i=1 ; i<formTable.getRowCount() ; i++ )
			{
				
				Date after = new SimpleDateFormat("dd/MM/yyyy").parse(getValue(formTable,i,2));
				
				if(before.after(after))
				{
					
					return false;
					
				}
				
				before = new SimpleDateFormat("dd/MM/yyyy").parse(getValue(formTable,i,2));
			
			}
			
		}
		catch(ParseException e)
		{
		
			e.printStackTrace();
			
		}
		
		return true;
	}
	
	/**
	 * Checks if paper needs to be externally moderated
	 * @param weighting		the value to check
	 * @return				true if external moderation needed, false otherwise
	 */
	private boolean checkWeighting(String weighting)
	{
		
		return ( Integer.parseInt(weighting)>=50 );
		
	}
	
	/**
	 * Complete validation for all staff names in the jTable
	 * @param 		formTable: the table to compare against
	 * @return		error message: returns "" if no errors found, else returns list of all errors found
	 */
	public String validateNames(JTable formTable, String weighting)
	{
		
		String message = "";
		String PW = getValue(formTable,0,1);
		String IM = getValue(formTable,1,1);
		String ML = getValue(formTable,2,1);
		String EM = getValue(formTable,3,1);
		
		// the EM will not need to be checked since the form does not need to be externally checked
		if( checkWeighting(weighting) == false ) 
		{
			
			EM = "";
			
		}
		
		if((validNameFormat(PW) == false)||(validNameFormat(IM) == false)
				||(validNameFormat(ML) == false)||(validNameFormat(EM) == false))
		{
			
			message = message.concat("Names must not contain non-alphabetic characters, "
				+"and must have the form:'first last'.\n");
			
		}
		
		// check if the internal names are the same
		if( PW.equals(IM) && !(PW.equals("")) )
		{
			
			message = "The Internal Moderator cannot also be the Paper Writer.";
			
		}
		
		if( IM.equals(ML) )
		{
			
			message = message.concat("\nThe Internal Moderator cannot also be the Module Leader.");
			
		}
		
		// check if input name matches a name in respective in/external lists 
		boolean nameNotFound = false;
		if( !(PW.equals("")) && (nameInArray(PW,internalStaff) == false) )
		{
			
			message = message.concat("\n"+PW+" is not a recorded internal staff member. ");
			nameNotFound = true;
			
		}
		
		if( !(IM.equals("")) && (nameInArray(IM,internalStaff) == false) )
		{
			
			message = message.concat("\n"+IM+" is not a recorded internal staff member. ");
			nameNotFound = true;
			
		}
		
		if( !(ML.equals("")) && (nameInArray(ML,internalStaff) == false) )
		{
			
			message = message.concat("\n"+ML+" is not a recorded internal staff member. ");
			nameNotFound = true;
		
		}
		
		if( !(EM.equals("")) && (nameInArray(EM,externalStaff) == false) )
		{
			
			message = message.concat("\n"+EM+" is not a recorded external staff member. ");
			nameNotFound = true;
		
		}
		
		if( nameNotFound == true )
		{
		
			message = message.concat("Please enter a different name(s).");
		
		}
		
		return message;
		
	}
	
	/**
	 * Called from validateNames method
	 * @param name		the name to be validated
	 * @return			false if name does not have correct format else return true
	 */
	private boolean validNameFormat(String name)
	{
		
		if( name.equals("") )
		{
			
			return true;
			
		}
		
		
		for(int i =0; i < name.length(); i++)
		{
		
			Character c = name.charAt(i);
			if((!Character.isLetter(c))&&!(c.compareTo(' ') == 0))
			{
				return false;
				
			}
			
		}
		
		return true;
		
	}
	
	/**
	 * Called from validateNames method
	 * @param name		a name to validate
	 * @param names		the array to compare against
	 * @return			false if name not in array, else return true
	 */
	public boolean nameInArray(String name, String[][] names)
	{
		for(int i = 0; i < names.length; i++)
		{
			if(names[i][1].equalsIgnoreCase(name))
			{
			
				return true;
				
			}
		
		}
		
		return false;
		
	}
	
}