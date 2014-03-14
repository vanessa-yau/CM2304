import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


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
			System.out.println("starts getAllForms method");
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
			System.out.println("not crashed yet...");
			
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
	 * forms:
	 * [0] moduleCode; [1] paperName; [2] ELODeadline; [3] EMDeadline;
	 * [4] MLDeadline; [5] IMDeadline; [6] EM_ID; [7] IM_ID;
	 * [8] ELOCompleted; [9] EMCompleted; [10] MLCompleted; [11] IMCompleted
	 */
	public String[][] getModuleForms(String moduleCode)
	{
		try
		{
			int numForms = countForms(moduleCode);
			System.out.println( numForms );
			String[][] moduleForms = new String[numForms][12];
			for( int i=0; i<countFileLines("forms.txt"); i++ )
			{
				String line = fileReader.nextLine();
				//System.out.println("nothing");
				if ( line.contains(moduleCode)){
					String[] parts = line.split(",");
					
					moduleForms[i][0] = parts[0];
					moduleForms[i][1] = parts[1];
					moduleForms[i][2] = parts[2];
					moduleForms[i][3] = parts[3];
					moduleForms[i][4] = parts[4];
					moduleForms[i][5] = parts[5];
					moduleForms[i][6] = parts[6];
					moduleForms[i][7] = parts[7];
					moduleForms[i][8] = parts[8];
					moduleForms[i][9] = parts[9];
					moduleForms[i][10] = parts[10];
					moduleForms[i][11] = parts[11].substring(0, parts[11].length()-1);
					
					//System.out.println("moduleForms[i][11]"+moduleForms[i][11]);
				}
			}
			fileReader.close();
			System.out.println("forms table saved");
			resetFileReader();
			
			return moduleForms;
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public boolean insertAssessmentPaper(String moduleCode, String paperName, String ELODeadline,
			String EMDeadline, String MLDeadline, String IMDeadline, String EM_ID, String IM_ID,
			String ELOCompleted, String EMCompleted, String MLCompleted, String IMCompleted)
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
				","+ELOCompleted+","+EMCompleted+","+MLCompleted+","+IMCompleted+";");
		fileWriter.close();

		resetFileReader();
		return true;
	}

	public boolean deleteAssessmentPaper(String moduleCode, String paperName)
	{
		//int number_lines = countFileLines("forms.txt");
		while( fileReader.hasNext()){
			
			String line = fileReader.nextLine();
			if ( line.contains(moduleCode) && line.contains(paperName) ){
				//delete row from forms file
				
				
				//archive the row to another file and add another column for current date 
				// Date d = new Date
				
				// insert new row - using insertAssessmentPaper method above
				
			}
		}
		
		return true;
		
	}
	
	public boolean archivePaper(String entry){
		// TODO fill here
		return false;
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
}
