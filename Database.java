import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;


public class Database {
	
	String filePath;
	Scanner fileReader;
	
	
	public Database()
	{
		
	}
	
	/*
	 * Initialises the database
	 * @param path String The file path of the database
	 * @return boolean Returns true if the database file could be found and false if not
	 */
	public boolean initDatabase(String path)
	{
		
		filePath = path;
		
		try 
		{
			fileReader = new Scanner( new File(filePath));
		} 
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	
	/*
	 * Inserts a new staff into the database
	 * @param fName String Staffs first name
	 * @param lName String Staffs last name
	 * @param email	String Staffs email
	 * @param number String Staffs number
	 * @param password String Staffs password
	 * @param staffID String Staffs ID
	 * @return boolean Returns true if the staff was successfully inserted and false otherwise
	 */
	public boolean insertStaff(String fName, String lName, String email, String number, String staffID, String staffRole)
	{
		fileReader.close();
		
		PrintWriter fileWriter = null;
		try 
		{
			fileWriter = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		fileWriter.println(fName+","+lName+","+email+","+number+","+staffID+staffRole+";");
		fileWriter.close();
		
		resetFileReader();
		return true;
	}
	
	/*
	 * Deletes staff from the database if the search term matches the search attribute
	 * eg. first name = Bob
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return boolean Returns true if staff was found and deleted and false otherwise
	 */
	public boolean deleteStaff(String searchAttribute, String searchValue)
	{
		
		int attribute = 0;
		
		if(searchAttribute.compareToIgnoreCase("first name") == 0)
		{
			attribute = 0;
		}
		else if(searchAttribute.compareToIgnoreCase("last name") == 0)
		{
			attribute = 1;
		}
		else if(searchAttribute.compareToIgnoreCase("email") == 0)
		{
			attribute = 2;
		}
		else if(searchAttribute.compareToIgnoreCase("number") == 0)
		{
			attribute = 3;
		}
		else if(searchAttribute.compareToIgnoreCase("staffID") == 0)
		{
			attribute = 4;
		}
		else if(searchAttribute.compareToIgnoreCase("staff role") == 0)
		{
			attribute = 5;
		}
		
		boolean found = false;
		int lineNo = 0;
		int deleteNo = 0;
		int lineCount = 0;
		while(fileReader.hasNext())
		{
			
			int commaCount = 0;
			String line = fileReader.nextLine();
			lineCount++;
			String word = "";
			for(int i=0; i < line.length(); i++)
			{
				if(line.charAt(i) == ',' || line.charAt(i) == ';')
				{
					
					if(attribute == commaCount)
					{
						
						if(word.compareToIgnoreCase(searchValue) == 0)
						{
							found = true;
							deleteNo = lineNo;
							
						}
					}
	
					word = "";
					commaCount++;
				}
				else
				{
					word += line.charAt(i);
				}
				
				
				
			}
			
			lineNo++;
		}
		
		if(!found)
			return false;
	
		
	
		
		String[] lines = new String[lineCount-1];
		int count = 0;
		int linesIn = 0;
		
		resetFileReader();
		while(fileReader.hasNext() && count != lineCount)
		{
			String line = fileReader.nextLine();
			if(count != deleteNo)
			{
				lines[linesIn] = line;
				linesIn++;
				System.out.println(lines[linesIn-1]);
			}
			
			count++;
		}
		
		fileReader.close();
		
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(filePath, "UTF-8");
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		for(int i=0; i < lineCount-1; i++)
		{
			writer.println(lines[i]);
		}
		
		writer.close();
		resetFileReader();
		
		return true;
	}
	
	/*
	 * Gets staffs first name from the database if the search term matches the search attribute
	 * eg. first name = Bob
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String Returns the first name if found or strings of "" if not found
	 */
	public String getStaffFirstName(String searchAttribute, String searchValue)
	{
		String[] staffInfo = getStaff(searchAttribute, searchValue);
		return staffInfo[0];
	}
	
	/*
	 * Gets staffs last name from the database if the search term matches the search attribute
	 * eg. first name = Bob
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String Returns the last name if found or strings of "" if not found
	 */
	public String getStaffLastName(String searchAttribute, String searchValue)
	{
		String[] staffInfo = getStaff(searchAttribute, searchValue);
		return staffInfo[1];
	}
	
	/*
	 * Gets staffs email from the database if the search term matches the search attribute
	 * eg. first name = Bob
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String Returns the staffs email if found or strings of "" if not found
	 */
	public String getStaffEmail(String searchAttribute, String searchValue)
	{
		String[] staffInfo = getStaff(searchAttribute, searchValue);
		return staffInfo[2];
	}
	
	/*
	 * Gets staffs number from the database if the search term matches the search attribute
	 * eg. first name = Bob
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String Returns the staffs number if found or strings of "" if not found
	 */
	public String getStaffNumber(String searchAttribute, String searchValue)
	{
		String[] staffInfo = getStaff(searchAttribute, searchValue);
		return staffInfo[3];
	}
	
	/*
	 * Gets staffs ID from the database if the search term matches the search attribute
	 * eg. first name = Bob
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String Returns the ID if found or strings of "" if not found
	 */
	public String getStaffID(String searchAttribute, String searchValue)
	{
		String[] staffInfo = getStaff(searchAttribute, searchValue);
		return staffInfo[4];
	}

	/*
	 * Gets staffs ID from the database if the search term matches the search attribute
	 * eg. first name = Bob
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String Returns the ID if found or strings of "" if not found
	 */
	public String getStaffRole(String searchAttribute, String searchValue)
	{
		String[] staffInfo = getStaff(searchAttribute, searchValue);
		return staffInfo[5];
	}
	
	
	/*
	 * Gets all staff information from the database if the search term matches the search attribute
	 * eg. first name = Bob
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String[] Returns an array of strings containing the information if found or strings of "" if not found
	 * [0] = first name; [1] = last name; [2] = email; [3] = number; [4] = password; [5] = staffID
	 */
	public String[] getStaff(String searchAttribute, String searchValue)
	{
		String[] staff = new String[6];
		
		int attribute = 0;
		
		if(searchAttribute.compareToIgnoreCase("first name") == 0)
		{
			attribute = 0;
		}
		else if(searchAttribute.compareToIgnoreCase("last name") == 0)
		{
			attribute = 1;
		}
		else if(searchAttribute.compareToIgnoreCase("email") == 0)
		{
			attribute = 2;
		}
		else if(searchAttribute.compareToIgnoreCase("number") == 0)
		{
			attribute = 3;
		}
		else if(searchAttribute.compareToIgnoreCase("staffID") == 0)
		{
			attribute = 4;
		}
		else if(searchAttribute.compareToIgnoreCase("staff role") == 0)
		{
			attribute = 5;
		}
		
		boolean found = false;
		while(fileReader.hasNext() && !found)
		{
			
			int commaCount = 0;
			String line = fileReader.nextLine();
			String word = "";
			for(int i=0; i < line.length(); i++)
			{
				if(line.charAt(i) == ',' || line.charAt(i) == ';')
				{
					
					if(attribute == commaCount)
					{
						
						if(word.compareToIgnoreCase(searchValue) == 0)
						{
							found = true;
						}
					}
					
					staff[commaCount] = word;
					word = "";
					commaCount++;
				}
				else
				{
					word += line.charAt(i);
				}
				
				
				
			}

			
		}
		
		if(!found)
		{
			for(int i=0; i < 6; i++)
				staff[i] = "";
		}
		
		resetFileReader();
		
		return staff;
	}
	
	/*
	 * Inserts a new staff into the database
	 * @param fName String Staffs first name
	 * @param lName String Staffs last name
	 * @param email	String Staffs email
	 * @param number String Staffs number
	 * @param password String Staffs password
	 * @param staffID String Staffs ID
	 * @return boolean Returns true if the staff was successfully inserted and false otherwise
	 */
	public boolean insertModule(String moduleName, String moduleCode, String moduleCredits, String numCoursework, String numExams, String moduleLeader, String formType)
	{
		fileReader.close();
		
		PrintWriter fileWriter = null;
		try 
		{
			fileWriter = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		fileWriter.println(moduleName+","+moduleCode+","+moduleCredits+","+numCoursework+","+numExams+","+moduleLeader+","+formType+";");
		fileWriter.close();
		
		resetFileReader();
		return true;
	}
	
	/*
	 * Deletes staff from the database if the search term matches the search attribute
	 * eg. first name = Bob
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return boolean Returns true if staff was found and deleted and false otherwise
	 */
	public boolean deleteModule(String searchAttribute, String searchValue)
	{
		
		int attribute = 0;
		
		if(searchAttribute.compareToIgnoreCase("module name") == 0)
		{
			attribute = 0;
		}
		else if(searchAttribute.compareToIgnoreCase("module code") == 0)
		{
			attribute = 1;
		}
		else if(searchAttribute.compareToIgnoreCase("credits") == 0)
		{
			attribute = 2;
		}
		else if(searchAttribute.compareToIgnoreCase("num coursework") == 0)
		{
			attribute = 3;
		}
		else if(searchAttribute.compareToIgnoreCase("num exams") == 0)
		{
			attribute = 4;
		}
		else if(searchAttribute.compareToIgnoreCase("module leader") == 0)
		{
			attribute = 5;
		}
		else if(searchAttribute.compareToIgnoreCase("form type") == 0)
		{
			attribute = 6;
		}

		
		boolean found = false;
		int lineNo = 0;
		int deleteNo = 0;
		int lineCount = 0;
		while(fileReader.hasNext())
		{
			
			int commaCount = 0;
			String line = fileReader.nextLine();
			lineCount++;
			String word = "";
			for(int i=0; i < line.length(); i++)
			{
				if(line.charAt(i) == ',' || line.charAt(i) == ';')
				{
					
					if(attribute == commaCount)
					{
						
						if(word.compareToIgnoreCase(searchValue) == 0)
						{
							found = true;
							deleteNo = lineNo;
							
						}
					}
	
					word = "";
					commaCount++;
				}
				else
				{
					word += line.charAt(i);
				}
				
				
				
			}
			
			lineNo++;
		}
		
		if(!found)
			return false;
	
		
	
		
		String[] lines = new String[lineCount-1];
		int count = 0;
		int linesIn = 0;
		
		resetFileReader();
		while(fileReader.hasNext() && count != lineCount)
		{
			String line = fileReader.nextLine();
			if(count != deleteNo)
			{
				lines[linesIn] = line;
				linesIn++;
				System.out.println(lines[linesIn-1]);
			}
			
			count++;
		}
		
		fileReader.close();
		
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(filePath, "UTF-8");
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		for(int i=0; i < lineCount-1; i++)
		{
			writer.println(lines[i]);
		}
		
		writer.close();
		resetFileReader();
		
		return true;
	}
	
	/*
	 * Gets modules name from the database if the search term matches the search attribute
	 * eg. first name = Bob
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String Returns the module name if found or strings of "" if not found
	 */
	public String getModuleName(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getModule(searchAttribute, searchValue);
		return moduleInfo[0];
	}
	
	/*
	 * Gets module code name from the database if the search term matches the search attribute
	 * eg. first name = Bob
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String Returns the module code if found or strings of "" if not found
	 */
	public String getModuleCode(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getModule(searchAttribute, searchValue);
		return moduleInfo[1];
	}
	
	/*
	 * Gets module credits from the database if the search term matches the search attribute
	 * eg. first name = Bob
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return int Returns the module credits and -1 if not found
	 */
	public int getModuleCredits(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getModule(searchAttribute, searchValue);
		return Integer.parseInt(moduleInfo[2]);
	}
	
	
	/*
	 * Gets number of courseworks for a module from the database if the search term matches the search attribute
	 * eg. module name = Database Systems
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return int Returns the number of courseworks and -1 if not found
	 */
	public int getNumCoursework(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getModule(searchAttribute, searchValue);
		return Integer.parseInt(moduleInfo[3]);
	}
	
	/*
	 * Gets number of exams for a module from the database if the search term matches the search attribute
	 * eg. first name = Bob
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return int Returns the number of exams and -1 if not found
	 */
	public int getNumExams(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getModule(searchAttribute, searchValue);
		return Integer.parseInt(moduleInfo[4]);
	}
	
	/*
	 * Gets number of exams for a module from the database if the search term matches the search attribute
	 * eg. first name = Bob
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return int Returns the number of exams and -1 if not found
	 */
	public String getModuleLeader(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getModule(searchAttribute, searchValue);
		return moduleInfo[5];
	}
	
	public String getFormType(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getModule(searchAttribute, searchValue);
		return moduleInfo[6];
	}

	

	
	/*
	 * Gets all module information from the database if the search term matches the search attribute
	 * eg. first name = Bob
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String[] Returns an array of strings containing the information if found or strings of "" if not found
	 * [0] = module name; [1] = module code; [2] = credits; [3] = staff role; [4] = num coursework; [5] = num exmas
	 * [6] = exam paper; [7] = coursework; [8] = task deadline
	 */
	public String[] getModule(String searchAttribute, String searchValue)
	{
		String[] module = new String[7];
		
		int attribute = 0;
		
		if(searchAttribute.compareToIgnoreCase("module name") == 0)
		{
			attribute = 0;
		}
		else if(searchAttribute.compareToIgnoreCase("module code") == 0)
		{
			attribute = 1;
		}
		else if(searchAttribute.compareToIgnoreCase("credits") == 0)
		{
			attribute = 2;
		}
		else if(searchAttribute.compareToIgnoreCase("num coursework") == 0)
		{
			attribute = 3;
		}
		else if(searchAttribute.compareToIgnoreCase("num exams") == 0)
		{
			attribute = 4;
		}
		else if(searchAttribute.compareToIgnoreCase("module leader") == 0)
		{
			attribute = 5;
		}
		else if(searchAttribute.compareToIgnoreCase("form type") == 0)
		{
			attribute = 6;
		}
		
		boolean found = false;
		while(fileReader.hasNext() && !found)
		{
			
			int commaCount = 0;
			String line = fileReader.nextLine();
			String word = "";
			for(int i=0; i < line.length(); i++)
			{
				if(line.charAt(i) == ',' || line.charAt(i) == ';')
				{
					
					if(attribute == commaCount)
					{
						
						if(word.compareToIgnoreCase(searchValue) == 0)
						{
							found = true;
						}
					}
					
					module[commaCount] = word;
					word = "";
					commaCount++;
				}
				else
				{
					word += line.charAt(i);
				}
				
				
				
			}

			
		}
		
		if(!found)
		{
			for(int i=0; i < 7; i++)
				module[i] = "";
		}
		
		resetFileReader();
		
		return module;
	}
	
	public boolean insertAssessmentPaper(String moduleCode, String paperName, String ELODeadline,
										String EMDeadline, String MLDeadline, String IMDeadline, String EM_ID, String IM_ID)
	{
		fileReader.close();
		
		PrintWriter fileWriter = null;
		try 
		{
			fileWriter = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		fileWriter.println(moduleCode+","+paperName+","+ELODeadline+","+EMDeadline+","+MLDeadline+","+IMDeadline+","+EM_ID+","+IM_ID+";");
		fileWriter.close();
		
		resetFileReader();
		return true;
	}
	
	public String[] getAssessmentPapers(String searchAttribute, String searchValue)
	{
		String[] module = new String[8];
		
		int attribute = 0;
		
		if(searchAttribute.compareToIgnoreCase("module code") == 0)
		{
			attribute = 0;
		}
		else if(searchAttribute.compareToIgnoreCase("paper name") == 0)
		{
			attribute = 1;
		}
		else if(searchAttribute.compareToIgnoreCase("ELO deadline") == 0)
		{
			attribute = 2;
		}
		else if(searchAttribute.compareToIgnoreCase("EM deadline") == 0)
		{
			attribute = 3;
		}
		else if(searchAttribute.compareToIgnoreCase("ML deadline") == 0)
		{
			attribute = 4;
		}
		else if(searchAttribute.compareToIgnoreCase("IM deadline") == 0)
		{
			attribute = 5;
		}
		else if(searchAttribute.compareToIgnoreCase("EM ID") == 0)
		{
			attribute = 6;
		}
		else if(searchAttribute.compareToIgnoreCase("IM ID") == 0)
		{
			attribute = 7;
		}
		
		boolean found = false;
		while(fileReader.hasNext() && !found)
		{
			
			int commaCount = 0;
			String line = fileReader.nextLine();
			String word = "";
			for(int i=0; i < line.length(); i++)
			{
				if(line.charAt(i) == ',' || line.charAt(i) == ';')
				{
					
					if(attribute == commaCount)
					{
						
						if(word.compareToIgnoreCase(searchValue) == 0)
						{
							found = true;
						}
					}
					
					module[commaCount] = word;
					word = "";
					commaCount++;
				}
				else
				{
					word += line.charAt(i);
				}
				
				
				
			}

			
		}
		
		if(!found)
		{
			for(int i=0; i < 8; i++)
				module[i] = "";
		}
		
		resetFileReader();
		
		return module;
	}
	
	
	public String getModuleCodeAssessmentPaper(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getAssessmentPapers(searchAttribute, searchValue);
		return moduleInfo[0];
	}
	
	public String getPaperName(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getAssessmentPapers(searchAttribute, searchValue);
		return moduleInfo[1];
	}
	
	public String getELODeadline(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getAssessmentPapers(searchAttribute, searchValue);
		return moduleInfo[2];
	}
	
	public String getEMDeadline(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getAssessmentPapers(searchAttribute, searchValue);
		return moduleInfo[3];
	}
	
	public String getMLDeadline(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getAssessmentPapers(searchAttribute, searchValue);
		return moduleInfo[4];
	}
	
	public String getIMDeadline(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getAssessmentPapers(searchAttribute, searchValue);
		return moduleInfo[5];
	}
	
	public String getEMID(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getAssessmentPapers(searchAttribute, searchValue);
		return moduleInfo[6];
	}
	
	public String getIMID(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getAssessmentPapers(searchAttribute, searchValue);
		return moduleInfo[7];
	}
	
	
	/*
	 * Resets the file reader so that the file can be read again
	 * (I couldn't get the normal reset function on the Scanner to work)
	 */
	public void resetFileReader()
	{
		fileReader.close();
		try 
		{
			fileReader = new Scanner( new File(filePath));
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Closes all open I/O objects
	 * MUST be called when database is finished being used
	 */
	public void close()
	{
		fileReader.close();
	}
	

	public static void main(String[] args)
	{
		Database d = new Database();
		d.initDatabase("staff.txt");
		//d.insertStaff("Simon", "Titcomb", "LOLNOPE@nah.com", "2345678986", "nahmate", "TitS");
		//d.insertStaff("Aly", "Sheriff", "kingtut@pharoahpower.com", "11111", "egyptrules", "Tut");
		//d.insertStaff("Mez", "Gangbanger", "kingofrape@ianwatkins.com", "5678987", "kids", "Kids101");
		String a = d.getStaffFirstName("staffID", "TitS");
		String b = "";
		System.out.println(a);
		System.out.println(b);
		String mez = d.getStaffEmail("first name", "mez");
		System.out.println(mez);
		System.out.println(d.deleteStaff("staffID","Tut"));
		
		d.close();
		
		System.out.println("Completed.");

	}

}
