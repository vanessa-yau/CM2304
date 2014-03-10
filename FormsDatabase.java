import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class FormsDatabase extends Database
{


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
}
