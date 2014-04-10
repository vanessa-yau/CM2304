import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



public class StaffDatabase extends Database
{

	File staffTable;

	public StaffDatabase(){}

	/*
	 * Inserts a new staff into the database
	 * 
	 * @param lName String Staffs last name
	 * @param fName String Staffs first name
	 * @param email	String Staffs email
	 * @param number String Staffs phone number
	 * @param staffID Object Staff ID object
	 * @param staffType String Staff type
	 */
	 public final void insertStaff(String fName, String lName,String email , String number, Object staffID , String staffType)
	{
		BufferedWriter newStaff;
		
		try 
		{
                    newStaff = new BufferedWriter(new FileWriter(filePath, true));                    
                    newStaff.write(fName+","+lName+","+email+","+number+","+staffID+","+staffType+ "; \n");
                    newStaff.close();
         	} 
		catch (IOException e) 
		{
                    System.out.println("Error : Problem adding new user to database");
		}
         }
         
        
	/*
	 * Archives a staff by writing the entry into an archive file
	 * 
	 * @param entry String Staff entry to input into the file
	 * @return boolean Returns true if staff archived correctly, false otherwise
	 */
	public boolean archiveStaff(String entry)
	{
		PrintWriter fileWriter = null;
		try 
		{
			fileWriter = new PrintWriter(new BufferedWriter(new FileWriter("archiveStaff.txt", true)));
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
	 * @return String Returns the staff role if found or strings of "" if not found
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
	 * Returns all staff recorded in the file
	 * 
	 * @returns String[][] Returns all staff entries in a file
	 */
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
}
