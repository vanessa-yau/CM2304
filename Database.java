import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;


public class Database {

	String filePath;
	Scanner fileReader;
	File staff, modules, forms;
	String[][] allForms;

	public Database()
	{
		staff = new File("staff.txt");
		modules = new File("modules.txt");
		forms = new File("forms.txt");
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

	public String[][] getAllStaff()
	{

		String[][] allStaff = null;
		int lineCount = 0;


		while(fileReader.hasNext())
		{

			lineCount++;
			fileReader.nextLine();

		}


		allStaff = new String[lineCount][6];

		resetFileReader();

		int count = 0;
		while(fileReader.hasNext())
		{
			String line = fileReader.nextLine();
			int attribute = 0;
			String value = "";

			for(int i=0; i < line.length(); i++)
			{
				if(line.charAt(i) == ',' || line.charAt(i) == ';' )
				{
					allStaff[count][attribute] = value;
					attribute++;
					value = "";
				}
				else
				{
					value += line.charAt(i);
				}
			}

			count++;
		}

		return allStaff;
	}

	public String[][] getModuleForms(String moduleCode)
	{
		String[][] allForms = null;
		int lineCount = 0;


		while(fileReader.hasNext())
		{

			lineCount++;
			fileReader.nextLine();

		}


		allForms = new String[lineCount][12];

		resetFileReader();

		int count = 0;
		while(fileReader.hasNext())
		{
			String line = fileReader.nextLine();
			int attribute = 0;
			String value = "";

			for(int i=0; i < line.length(); i++)
			{
				if(line.charAt(i) == ',' || line.charAt(i) == ';' )
				{
					allForms[count][attribute] = value;
					attribute++;
					value = "";
				}
				else
				{
					value += line.charAt(i);
				}
			}

			count++;
		}
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		int arrSize = 0;
		
		for(int i=0; i < allForms.length ; i++)
		{
			if( allForms[i][0].equals(moduleCode) )
			{
				list.add(i);
				arrSize++;
			}
		}
		
		int position = 0;
		String[][] modForms = new String[arrSize][12];
		
		for( int v : list)
		{
			for( int w=0; w<12; w++)
			{
				modForms[position][w] = allForms[v][w];
			}
			
			position++;
			
		}
		
		return modForms;
		
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
		fileWriter.println(fName+","+lName+","+email+","+number+","+staffID+","+staffRole+";");
		fileWriter.close();

		resetFileReader();
		return true;
	}

	public boolean archiveStaff(String entry)
	{

		PrintWriter fileWriter = null;
		try 
		{
			fileWriter = new PrintWriter(new BufferedWriter(new FileWriter("staffArchive.txt", true)));

		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return false;
		}
		fileWriter.println(entry);
		fileWriter.close();

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
			else
			{
				archiveStaff(line);
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
	 * [0] = module name; [1] = module code; [2] = credits; [3] = num coursework; [4] = num exams
	 * [5] = module leader; [6] = form type
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

	/*
	 * [0] moduleCode; [1] paperName; [2] IM_ID; [3] EM_ID; 
	 * [4] IMDeadline; [5] MLDeadline; [6] EMDeadline; [7] ELODeadline;
	 * [8] IMCompleted; [9] MLCompleted; [10] EMCompleted; [11] ELOCompleted
	 */
	public boolean insertAssessmentPaper(String moduleCode, String paperName, String IM_ID,
			String EM_ID, String IMDeadline, String MLDeadline, String EMDeadline, String ELODeadline,
			String IMCompleted, String MLCompleted, String EMCompleted, String ELOCompleted)
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
		fileWriter.println(moduleCode+","+paperName+","+IM_ID+","+EM_ID+","+IMDeadline+","
				+MLDeadline+","+EMDeadline+","+ELODeadline+","+IMCompleted+","+MLCompleted+","
				+EMCompleted+","+ELOCompleted+";");
		fileWriter.close();

		resetFileReader();
		return true;
	}

	public String[] getAssessmentPapers(String searchAttribute, String searchValue)
	{
		String[] module = new String[12];

		int attribute = 0;

		if(searchAttribute.compareToIgnoreCase("module code") == 0)
		{
			attribute = 0;
		}
		else if(searchAttribute.compareToIgnoreCase("paper name") == 0)
		{
			attribute = 1;
		}
		else if(searchAttribute.compareToIgnoreCase("IM_ID") == 0)
		{
			attribute = 2;
		}
		else if(searchAttribute.compareToIgnoreCase("EM_ID") == 0)
		{
			attribute = 3;
		}
		else if(searchAttribute.compareToIgnoreCase("IM deadline") == 0)
		{
			attribute = 4;
		}
		else if(searchAttribute.compareToIgnoreCase("ML deadline") == 0)
		{
			attribute = 5;
		}
		else if(searchAttribute.compareToIgnoreCase("EM deadline") == 0)
		{
			attribute = 6;
		}
		else if(searchAttribute.compareToIgnoreCase("ELO deadline") == 0)
		{
			attribute = 7;
		}
		/*else if(searchAttribute.compareToIgnoreCase("IM completed") == 0)
		{
			attribute = 8;
		}
		else if(searchAttribute.compareToIgnoreCase("ML completed") == 0)
		{
			attribute = 9;
		}
		else if(searchAttribute.compareToIgnoreCase("EM completed") == 0)
		{
			attribute = 10;
		}
		else if(searchAttribute.compareToIgnoreCase("ELO completed") == 0)
		{
			attribute = 11;
		}*/
		
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
			for(int i=0; i < 12; i++)
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
		/*Database d = new Database();
		d.initDatabase("forms.txt");
		d.insertAssessmentPaper("cm2304", "cw1", "ELODate1", "EMDeadline1", "MLDeadline1", "IMDeadline1", "1001001", "2002002");
		d.insertAssessmentPaper("cm2304", "cw2", "ELODate2", "EMDeadline2", "MLDeadline2", "IMDeadline2", "1001001", "2002002");
		d.insertAssessmentPaper("cm2304", "cw3", "ELODate3", "EMDeadline3", "MLDeadline3", "IMDeadline3", "1001001", "2002002");
		d.insertAssessmentPaper("cm2304", "cw4", "ELODate4", "EMDeadline4", "MLDeadline4", "IMDeadline4", "1001001", "2002002");
		d.initDatabase("modules.txt");
		d.insertModule("group project", "cm2304", "20", "4", "0", "Simon Titcomb", "+50%");
		/*d.insertStaff("Simon", "Titcomb", "LOLNOPE@nah.com", "2345678986", "nahmate", "TitS");
		d.insertStaff("Aly", "Sheriff", "kingtut@pharoahpower.com", "11111", "egyptrules", "Tut");
		d.insertStaff("Mez", "Gangbanger", "kingofrape@ianwatkins.com", "5678987", "kids", "Kids101");

		String a = d.getStaffFirstName("staffID", "TitS");
		String b = "";
		System.out.println(a);
		System.out.println(b);
		String mez = d.getStaffEmail("first name", "mez");
		System.out.println(mez);
		System.out.println(d.deleteStaff("staffID","Tut"));
		d.close();
		 */


		System.out.println("Completed.");

		/*Database d = new Database();
		d.initDatabase("staff.txt");

		System.out.println("Started.");

		String[][] a = d.getAllStaff();
		for(int i=0; i < a.length; i++)
		{
			for(int j=0; j < 6; j++)
			{
				System.out.print(a[i][j]+" ");
			}

			System.out.println(" ");
		}

		System.out.println("Completed.");

		d.close();*/


	}

}
