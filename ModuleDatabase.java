import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


public class ModuleDatabase extends Database
{


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
}
