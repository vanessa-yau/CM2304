import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ModuleDatabase extends Database
{


	/*
	 * Inserts a new module into the database
	 * @param moduleName String Module name
	 * @param moduleCode String Module code
	 * @param moduleCredits	String Module credits
	 * @param numCoursework String Number of pieces of coursework
	 * @param numExams String Number of exams
	 * @param moduleLeader String Leader of the module
	 * @param weighting String module weighting 
	 * @param moduleTerm String either autumn/spring
	 * @return boolean Returns true if the module was successfully inserted and false otherwise
	 */
	 public void insertModule(String moduleName, String moduleCode, String moduleCredits, String numCoursework, String numExams, String moduleLeader, String weighting, String moduleTerm)
	 {
		BufferedWriter newModule;

		try 
		{
                    newModule = new BufferedWriter(new FileWriter(filePath, true));                    
                    newModule.write(moduleName+","+moduleCode+","+moduleCredits+","+numCoursework+","+numExams+","+moduleLeader+","+weighting+","+moduleTerm+"; \n");
                    newModule.close();
         	} 
		catch (IOException e) 
		{
                    System.out.println("Error : Problem adding new module to database");
		}
	 }

	/*
	 * Deletes module from the database if the search term matches the search attribute
	 * eg. module name = Group Project
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return boolean Returns true if module was found and deleted and false otherwise
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
		else if(searchAttribute.compareToIgnoreCase("weighting") == 0)
		{
			attribute = 6;
		}
		else if(searchAttribute.compareToIgnoreCase("module term") == 0)
		{
			attribute = 7;
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
				archiveModule(line);
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
	 * eg. module name = Group Project
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
	 * Gets module code from the database if the search term matches the search attribute
	 * eg. module name = Group Project
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
	 * eg. module name = Group Project
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
	 * eg. module name = Group Project
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
	 * Gets the module leader for a module from the database if the search term matches the search attribute
	 * eg. module name = Group Project
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String Returns the module leader and "" if not found
	 */
	public String getModuleLeader(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getModule(searchAttribute, searchValue);
		return moduleInfo[5];
	}

	/*
	 * Gets the weighting for a module from the database if the search term matches the search attribute
	 * eg. module name = Group Project
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String Returns the form type and "" if not found
	 */
	public String getWeighting(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getModule(searchAttribute, searchValue);
		return moduleInfo[6];
	}

	/*
	 * Gets the term for a module from the database if the search term matches the search attribute
	 * eg. module name = Group Project
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String Returns the form type and "" if not found
	 */
	public String getModuleTerm(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getModule(searchAttribute, searchValue);
		return moduleInfo[7];
	}
	
	/*
	 * Gets all module information from the database if the search term matches the search attribute
	 * eg. module name = Group Project
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String[] Returns an array of strings containing the information if found or strings of "" if not found
	 * [0] = module name; [1] = module code; [2] = credits; [3] = num coursework; [4] = num exams
	 * [5] = module leader; [6] = weighting; [7] = module term;
	 */
	public String[] getModule(String searchAttribute, String searchValue)
	{
		String[] module = new String[8];

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
		else if(searchAttribute.compareToIgnoreCase("weighting") == 0)
		{
			attribute = 6;
		}
		else if(searchAttribute.compareToIgnoreCase("module term") == 0)
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
	
	/*
	 * Returns all modules recorded in the file
	 * 
	 * @returns String[][] Returns all module entries in a file
	 */
	public String[][] getAllModules(){
		
		String[][] allModules = null;
		int lineCount = 0;
		
		
		while(fileReader.hasNext())
		{

			lineCount++;
			fileReader.nextLine();

		}
		
		
		allModules = new String[lineCount][8];
		
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
					allModules[count][attribute] = value;
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
		
		return allModules;
	
	}
	
	/*
	 * Archives a module by writing the entry into an archive file
	 * 
	 * @param entry String Module entry to input into the file
	 * @return boolean Returns true if module archived correctly, false otherwise
	 */
	public boolean archiveModule(String entry)
	{
		PrintWriter fileWriter = null;
		try 
		{
			fileWriter = new PrintWriter(new BufferedWriter(new FileWriter("archiveModule.txt", true)));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		entry.replace(';', ',');
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		
		
		entry += dateFormat.format(date);
		entry += ";";
		
		
		fileWriter.println(entry);
		fileWriter.close();
		return true;
	}
}
