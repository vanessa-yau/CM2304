import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FormsDatabase extends Database
{

	// http://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java
	private int countFileLines(String filename) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		try {
			byte[] c = new byte[1024];
			int count = 0;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
				}
			}
			return (count == 0 && !empty) ? 1 : count;
		} 
		catch (IOException e)
		{
			e.printStackTrace();
			return -1;
		}
		finally 
		{
			is.close();
		}
	}

	public int countForms(String moduleCode){
		try
		{
			int totalLineCount = countFileLines("forms.txt");
			int numForms = 0;
			for( int i=0; i<totalLineCount; i++ )
			{
				String line = fileReader.nextLine();
				if ( line.contains(moduleCode) == true ){
					numForms++;
				}
			}
			fileReader.close();
			
			resetFileReader();
			
			return numForms;
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	
	/*
	 * Gets all form information from the database if the search term matches the search attribute
	 * eg. moduleCode = CM23303
	 * 
	 * @param moduleCode String The module code for the module you're searching for
	 * @return String[][] Returns an array of strings containing the information if found or strings of "" if not found
	 * 
	 * [0] moduleCode; [1] paperName; [2] ELODeadline; [3] EMDeadline;
	 * [4] MLDeadline; [5] IMDeadline; [6] EM_ID; [7] IM_ID;
	 * [8] ELOCompleted; [9] EMCompleted; [10] MLCompleted; [11] IMCompleted;
	 * [12] PW_ID; [13] PWDeadline; [14] PWCompleted;
	 */
	public String[][] getModuleForms(String moduleCode)
	{
		try
		{
			int lineNo = 0;
			int numForms = countForms(moduleCode);
			String[][] moduleForms = new String[numForms][15];
			for( int i=0; i<countFileLines("forms.txt"); i++ )
			{
				String line = fileReader.nextLine();
				
				if ( line.contains(moduleCode))
				{
					String[] parts = line.split(",");
					
					for( int j=0 ; j<14 ; j++ )
					{
						moduleForms[lineNo][j] = parts[j];
					}
					moduleForms[lineNo][14] = parts[14].substring(0, parts[14].length()-1);
					
					lineNo++;
				}
			}
			fileReader.close();
			
			resetFileReader();
			
			return moduleForms;
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * Inserts a new form into the database
	 * 
	 * @param moduleCode String Module code of the form
	 * @param paperName String Paper name
	 * @param ELODeadline String The ELO deadline
	 * @param EMDeadline String The EM deadline
	 * @param MLDeadline String The ML deadline
	 * @param IMDeadline String The IM deadline
	 * @param EM_ID String The EM ID
	 * @param IM_ID String The IM ID
	 * @param ELOCompleted String Recording of whether ELO has completed their task
	 * @param EMCompleted String Recording of whether EM has completed their task
	 * @param MLCompleted String Recording of whether ML has completed their task
	 * @param IMCompleted String Recording of whether IM has completed their task
	 * @param PW_ID String The Paper Author ID
	 * @param PWDeadline String PaperWritten Deadline
	 * @param PWCompleted String Recording of whether Paper Author has completed their task 
	 * @returns boolean Returns true if the form was inserted correctly
	 */
	public boolean insertAssessmentPaper(String moduleCode, String paperName, String ELODeadline,
			String EMDeadline, String MLDeadline, String IMDeadline, String EM_ID, String IM_ID,
			String ELOCompleted, String EMCompleted, String MLCompleted, String IMCompleted,
			String PW_ID, String PWDeadline, String PWCompleted)
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
		fileWriter.println(moduleCode+","+paperName+","+ELODeadline+","+EMDeadline+
				","+MLDeadline+","+IMDeadline+","+EM_ID+","+IM_ID+
				","+ELOCompleted+","+EMCompleted+","+MLCompleted+","+IMCompleted+
				","+PW_ID+","+PWDeadline+","+PWCompleted+";");
		fileWriter.close();

		resetFileReader();
		return true;
	}


	/*
	 * Deletes form from the database if the search term matches the search attribute
	 * eg. module code = CM23303
	 * 
	 * @param moduleCode String The module code of the form you're searching for
	 * @param paperName String The paper name of the form you're searching for
	 * @return boolean Returns true if the form was found and deleted and false otherwise
	 */
	public boolean deleteAssessmentPaper(String moduleCode, String paperName)
	{
		
		int count = 0;
		int foundLine = 0;
		String archiveLine = "";
		
		String tem[] = getAssessmentPapers("module code", moduleCode);
		
		while( fileReader.hasNext())
		{
			
			String line = fileReader.nextLine();
			
			if ( line.contains(moduleCode) && line.contains(paperName))
			{
				
				foundLine = count;
				archiveLine = line;
				break;
				
			}
			
			count++;
		}
		
		resetFileReader();
		
		if(archiveLine == "")
			return false;
		
		
		count = 0;
		String temp = "";
		while(fileReader.hasNext())
		{
			String line = fileReader.nextLine();
			if(count != foundLine)
			{
				temp += line+"\n";
			}
			
			count++;
			
		}
		
		fileReader.close();

		PrintWriter fileWriter = null;
		try 
		{
			fileWriter = new PrintWriter(filePath, "UTF-8");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		fileWriter.write(temp);
		fileWriter.close();

		resetFileReader();
		
		archivePaper(archiveLine);
		
		
		
		return true;
		
	}
	
	/*
	 * Archives a form by writing the entry into an archive file
	 * 
	 * @param entry String Module entry to input into the file
	 * @return boolean Returns true if form archived correctly, false otherwise
	 */
	public boolean archivePaper(String entry)
	{
		PrintWriter fileWriter = null;
		try 
		{
			fileWriter = new PrintWriter(new BufferedWriter(new FileWriter("archiveForms.txt", true)));
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
	
	//DOES ANYONE EVEN USE THIS?
	public String[] getAssessmentPapers(String searchAttribute, String searchValue)
	{
		String[] module = new String[15];

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
		else if(searchAttribute.compareToIgnoreCase("ELO completed") == 0)
		{
			attribute = 8;
		}
		else if(searchAttribute.compareToIgnoreCase("EM completed") == 0)
		{
			attribute = 9;
		}
		else if(searchAttribute.compareToIgnoreCase("ML completed") == 0)
		{
			attribute = 10;
		}
		else if(searchAttribute.compareToIgnoreCase("IM completed") == 0)
		{
			attribute = 11;
		}
		else if(searchAttribute.compareToIgnoreCase("PW ID") == 0)
		{
			attribute = 12;
		}
		else if(searchAttribute.compareToIgnoreCase("PW deadline") == 0)
		{
			attribute = 13;
		}
		else if(searchAttribute.compareToIgnoreCase("PW completed") == 0)
		{
			attribute = 14;
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
			for(int i=0; i < 15; i++)
				module[i] = "";
		}

		resetFileReader();

		return module;
	}

	/*
	 * Gets the module code from the database if the search term matches the search attribute
	 * eg. module code = CM23303
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String Returns the module code if found or strings of "" if not found
	 */
	public String getModuleCodeAssessmentPaper(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getAssessmentPapers(searchAttribute, searchValue);
		return moduleInfo[0];
	}

	/*
	 * Gets the paper name from the database if the search term matches the search attribute
	 * eg. module code = CM23303
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String Returns the paper name if found or strings of "" if not found
	 */
	public String getPaperName(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getAssessmentPapers(searchAttribute, searchValue);
		return moduleInfo[1];
	}

	/*
	 * Gets the ELO deadline from the database if the search term matches the search attribute
	 * eg. module code = CM23303
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String Returns the ELO deadline if found or strings of "" if not found
	 */
	public String getELODeadline(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getAssessmentPapers(searchAttribute, searchValue);
		return moduleInfo[2];
	}

	/*
	 * Gets the EM deadline from the database if the search term matches the search attribute
	 * eg. module code = CM23303
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String Returns the EM deadline if found or strings of "" if not found
	 */
	public String getEMDeadline(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getAssessmentPapers(searchAttribute, searchValue);
		return moduleInfo[3];
	}

	/*
	 * Gets the ML deadline from the database if the search term matches the search attribute
	 * eg. module code = CM23303
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String Returns the ML deadline if found or strings of "" if not found
	 */
	public String getMLDeadline(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getAssessmentPapers(searchAttribute, searchValue);
		return moduleInfo[4];
	}

	/*
	 * Gets the IM deadline from the database if the search term matches the search attribute
	 * eg. module code = CM23303
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String Returns the IM deadline if found or strings of "" if not found
	 */
	public String getIMDeadline(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getAssessmentPapers(searchAttribute, searchValue);
		return moduleInfo[5];
	}

	/*
	 * Gets the EM ID from the database if the search term matches the search attribute
	 * eg. module code = CM23303
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String Returns the EM ID if found or strings of "" if not found
	 */
	public String getEMID(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getAssessmentPapers(searchAttribute, searchValue);
		return moduleInfo[6];
	}

	/*
	 * Gets the IM ID from the database if the search term matches the search attribute
	 * eg. module code = CM23303
	 * 
	 * @param searchAttribute String The attribute you're searching
	 * @param searchValue String The value you're searching the attribute for
	 * @return String Returns the IM ID if found or strings of "" if not found
	 */
	public String getIMID(String searchAttribute, String searchValue)
	{
		String[] moduleInfo = getAssessmentPapers(searchAttribute, searchValue);
		return moduleInfo[7];
	}
	
}
